package reoger.hut.chatinstant.persenter;

import android.content.Context;

import org.jivesoftware.smack.roster.Roster;
import org.jivesoftware.smack.roster.RosterEntry;
import org.jivesoftware.smack.tcp.XMPPTCPConnection;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import reoger.hut.chatinstant.interfaces.IControlFriends;
import reoger.hut.chatinstant.utils.XmppManager;

/**
 * Created by 24540 on 2017/5/26.
 * UserActivity 的实现类
 */

public class UserPresenter implements IControlFriends.IPersenter {
    private IControlFriends.IView view;
    private Context context;
    private List<RosterEntry> lists = new ArrayList<>();

    public UserPresenter(IControlFriends.IView view, Context context) {
        this.view = view;
        this.context = context;
    }

    @Override
    public void getFirends() {
        XMPPTCPConnection xmpptcpConnection = XmppManager.getConnection();
        Roster roster = Roster.getInstanceFor(xmpptcpConnection);
        Collection<RosterEntry> firends = roster.getEntries();
        for (RosterEntry firend : firends) {
            lists.add(firend);
        }
        view.getResult(lists);

    }
}
