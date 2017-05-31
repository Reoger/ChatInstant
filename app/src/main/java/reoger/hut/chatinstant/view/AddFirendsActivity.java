package reoger.hut.chatinstant.view;

import android.os.Bundle;
import android.support.constraint.ConstraintLayout;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;

import org.jxmpp.jid.BareJid;
import org.jxmpp.jid.impl.JidCreate;
import org.jxmpp.stringprep.XmppStringprepException;

import reoger.hut.chatinstant.R;
import reoger.hut.chatinstant.interfaces.IControlAddFriend;
import reoger.hut.chatinstant.interfaces.IControlAddFriend.IView;
import reoger.hut.chatinstant.persenter.AddFriendPresenter;

public class AddFirendsActivity extends AppCompatActivity implements IView,OnClickListener{
    private ConstraintLayout coordinatorLayout;
    private IControlAddFriend.IPersenter persenter;

    private EditText mETName;
    private EditText mETNickName;
    private EditText mETgroup;

    private Button mBAdd;
    private Button mBSearch;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_firends);

        initView();


    }

    private void initView() {
        coordinatorLayout = (ConstraintLayout) findViewById(R.id.add_friends_constraintLayout);

        mETName = (EditText) findViewById(R.id.add_friend_name);
        mETNickName = (EditText) findViewById(R.id.add_friend_nickname);
        mETgroup = (EditText) findViewById(R.id.add_friend_group);
        mBAdd = (Button) findViewById(R.id.add_friends_but);
        mBSearch = (Button) findViewById(R.id.add_friend_search);

        persenter = new AddFriendPresenter(this,this);

        mBAdd.setOnClickListener(this);
        mBSearch.setOnClickListener(this);


    }

    @Override
    public void addFail() {
        Snackbar.make(coordinatorLayout, "添加失败", Snackbar.LENGTH_SHORT)
                .setAction("重试", new OnClickListener() {
                    @Override
                    public void onClick(View v) {
                      //  persenter.searchUser();
                    }
                })
                .show();
    }

    @Override
    public void addSuccess() {
        Snackbar.make(coordinatorLayout, "添加成功", Snackbar.LENGTH_SHORT).show();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.add_friends_but:
                String user = mETName.getText().toString();
                String nickName = mETNickName.getText().toString();
                String group = mETgroup.getText().toString();
                BareJid bareJid = null;
                try {
                    bareJid = JidCreate.bareFrom(user);
                } catch (XmppStringprepException e) {
                    e.printStackTrace();
                }
                persenter.searchUser(bareJid,nickName,group);
                break;

            case R.id.add_friend_search:
                String users = mETName.getText().toString();
                persenter.search(users);
                break;
        }
    }
}
