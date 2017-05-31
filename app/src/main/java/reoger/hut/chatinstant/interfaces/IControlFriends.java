package reoger.hut.chatinstant.interfaces;

import org.jivesoftware.smack.roster.RosterEntry;

import java.util.List;

/**
 * Created by 24540 on 2017/5/26.
 */

public class IControlFriends {
    public interface IPersenter{
        void getFirends();//显示所有好友

    }


    public interface IView{
        void getResult(List<RosterEntry> firends);

    }

}
