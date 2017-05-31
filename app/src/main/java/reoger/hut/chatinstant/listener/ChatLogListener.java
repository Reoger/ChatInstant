package reoger.hut.chatinstant.listener;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;
import org.jivesoftware.smack.chat2.Chat;
import org.jivesoftware.smack.chat2.IncomingChatMessageListener;
import org.jivesoftware.smack.packet.Message;
import org.jxmpp.jid.EntityBareJid;

import java.util.Date;

import reoger.hut.chatinstant.application.App;
import reoger.hut.chatinstant.bean.ChatLog;
import reoger.hut.chatinstant.bean.ChatLogDao;
import reoger.hut.chatinstant.bean.DaoSession;
import reoger.hut.chatinstant.utils.Constant;
import reoger.hut.chatinstant.utils.DESUtils;
import reoger.hut.chatinstant.utils.log;

/**
 * Created by 24540 on 2017/5/25.
 * 监听器
 */

public class ChatLogListener implements IncomingChatMessageListener {
    private DaoSession daoSession;
    private ChatLogDao chatLogDao;


    public ChatLogListener() {
        daoSession = App.getInstance().getDaoSession();
        chatLogDao = daoSession.getChatLogDao();
    }

    @Subscribe(threadMode = ThreadMode.BACKGROUND)
    @Override
    public void newIncomingMessage(EntityBareJid from, Message message, Chat chat) {
        log.e("来自 " + from + "  的消息 ,内容为" + message.getBody() + "|----------------------------------" + message.getFrom());
        try {
            String content = message.getBody();
            synchronized (content) {
                if (Constant.IsEncryption)
                    content = DESUtils.decryptDES(content, Constant.DES_KEY);//解密
                ChatLog chatlog = new ChatLog(null, from.toString(), true, content,new Date());
                chatLogDao.insert(chatlog);//保存到数据库
                EventBus.getDefault().post(chatlog);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

    }
}
