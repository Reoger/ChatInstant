package reoger.hut.chatinstant.persenter;

import android.content.Context;

import org.greenrobot.greendao.query.Query;
import org.jivesoftware.smack.chat2.Chat;
import org.jivesoftware.smack.chat2.ChatManager;
import org.jxmpp.jid.EntityBareJid;
import org.jxmpp.jid.impl.JidCreate;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import reoger.hut.chatinstant.application.App;
import reoger.hut.chatinstant.bean.ChatLog;
import reoger.hut.chatinstant.bean.ChatLogDao;
import reoger.hut.chatinstant.bean.ChatMsg;
import reoger.hut.chatinstant.bean.DaoSession;
import reoger.hut.chatinstant.interfaces.IControlChat;
import reoger.hut.chatinstant.utils.Constant;
import reoger.hut.chatinstant.utils.DESUtils;
import reoger.hut.chatinstant.utils.XmppManager;

/**
 * Created by 24540 on 2017/5/26.
 */

public class ChatPresenter implements IControlChat.IPrsenter {
    private IControlChat.iView view;
    private Context mContext;
    private ChatLogDao chatLogDao;
    private DaoSession daoSession;
    private List<ChatMsg> lists = new ArrayList<>();
    private ChatManager chatManager;
    private EntityBareJid jid;
    private String mJid;
    private Chat chat;

    public ChatPresenter(IControlChat.iView view, Context mContext, String mjid) {
        this.view = view;
        this.mContext = mContext;
        daoSession = App.getInstance().getDaoSession();
        chatLogDao = daoSession.getChatLogDao();
        chatManager = XmppManager.getChatManager();
        mJid = mjid;
        try {
            this.jid = JidCreate.entityBareFrom(mJid);
            chat = chatManager.chatWith(jid);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void initData() {
        try {
            Query<ChatLog> build = chatLogDao.queryBuilder().where(ChatLogDao.Properties.Jid.eq(mJid)).build();
            List<ChatLog> list = build.list();
            ChatMsg msg;
            for (ChatLog chatLog : list) {
                String str = chatLog.getContent();
                synchronized (str) {
//                    if (Constant.IsEncryption)
//                        str = DESUtils.decryptDES(str, Constant.DES_KEY);
                    msg = new ChatMsg(str, chatLog.getOther() ? ChatMsg.TYPE_RECEIVED : ChatMsg.TYPE_SEND);
                }
                lists.add(msg);
            }
            view.initDataFromDb(lists);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    /**
     * 发送消息
     *
     * @param body 消息内容
     */
    @Override
    public void sendChatMessage(String body) {
        try {
            String content = body;
            chatLogDao.insert(new ChatLog(null, mJid, false, content,new Date()));//保存到数据库
            if (Constant.IsEncryption) {
                content = DESUtils.encryptDES(content, Constant.DES_KEY);//加密
            }
            chat.send(content);//发送消息
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void deleteDataInDb() {

        Query<ChatLog> build = chatLogDao.queryBuilder().where(ChatLogDao.Properties.Jid.eq(mJid)).build();
        for(ChatLog chatLog:build.list()){
            chatLogDao.delete(chatLog);
        }
    }

}
