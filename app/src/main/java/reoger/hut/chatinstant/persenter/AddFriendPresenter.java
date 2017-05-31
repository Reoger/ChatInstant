package reoger.hut.chatinstant.persenter;

import android.content.Context;

import org.jivesoftware.smack.roster.Roster;
import org.jivesoftware.smack.tcp.XMPPTCPConnection;
import org.jivesoftware.smackx.search.ReportedData;
import org.jivesoftware.smackx.search.UserSearchManager;
import org.jivesoftware.smackx.xdata.Form;
import org.jxmpp.jid.BareJid;
import org.jxmpp.jid.DomainBareJid;
import org.jxmpp.jid.impl.JidCreate;

import java.util.ArrayList;

import reoger.hut.chatinstant.interfaces.IControlAddFriend;
import reoger.hut.chatinstant.utils.XmppManager;
import reoger.hut.chatinstant.utils.log;

/**
 * Created by 24540 on 2017/5/29.
 */

public class AddFriendPresenter implements IControlAddFriend.IPersenter {
    private IControlAddFriend.IView view;
    private Context context;

    public AddFriendPresenter(IControlAddFriend.IView view, Context context) {
        this.view = view;
        this.context = context;
    }

    /**
     * 添加好友
     * @param user 用户名
     * @param nickName 昵称
     * @param groupName 组名
     * @return true 表示添加成功 false 表示添加失败
     */
    @Override
    public void searchUser(BareJid user, String nickName, String groupName) {
        try {
            Roster.getInstanceFor(XmppManager.getConnection()).createEntry(user,nickName,new String[]{groupName});
        } catch (Exception e) {
            e.printStackTrace();
            view.addFail();
        }
        view.addSuccess();
    }

    @Override
    public void search(String jid) {
        XMPPTCPConnection manager = XmppManager.getConnection();
        UserSearchManager searchManager = new UserSearchManager(manager);
        try {

            DomainBareJid domainBareJid = JidCreate.domainBareFrom(jid);

            Form form = searchManager.getSearchForm(domainBareJid);
            Form answer = form.createAnswerForm();
            answer.setAnswer("username",true);
          //  answer.setAnswer("search",jid); //搜索的条件
            ReportedData searchResults = searchManager.getSearchResults(answer, domainBareJid);

            ArrayList<String > usrlist = new ArrayList<>();

            for (ReportedData.Row row : searchResults.getRows()) {
                String username  =  row.getValues("username").get(0);
                usrlist.add(username);
                log.d("搜索的用户名是"+username);
            }


        } catch (Exception e) {
            e.printStackTrace();
            log.e(e);
        }
    }

}
