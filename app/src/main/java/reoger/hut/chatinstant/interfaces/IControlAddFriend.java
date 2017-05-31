package reoger.hut.chatinstant.interfaces;

import org.jxmpp.jid.BareJid;

/**
 * Created by 24540 on 2017/5/29.
 */

public interface IControlAddFriend {
    public interface IPersenter{
        void searchUser(BareJid user, String nickName, String groupName);
        void search(String jid);
    }
    public interface  IView{
        void addFail();
        void addSuccess();
    }
}
