package reoger.hut.chatinstant.view;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.ArrayList;
import java.util.List;

import reoger.hut.chatinstant.R;
import reoger.hut.chatinstant.adapter.MsgAdapter;
import reoger.hut.chatinstant.bean.ChatLog;
import reoger.hut.chatinstant.bean.ChatMsg;
import reoger.hut.chatinstant.interfaces.IControlChat;
import reoger.hut.chatinstant.persenter.ChatPresenter;
import reoger.hut.chatinstant.utils.log;



public class ChatActivity extends AppCompatActivity implements IControlChat.iView {

    private ListView msgListView;
    private EditText inputText;
    private Button send;
    private MsgAdapter adapter;
    public List<ChatMsg> msgList = new ArrayList<ChatMsg>();
    private ChatPresenter chatPresenter;
    private String jid;

    private Toolbar toolbar;


    /**
     * 接收来自监听器回传的数据
     * @param chatLog 传入的数据
     */
    @Subscribe(threadMode = ThreadMode.MAIN)
    public  void onShowDateEvent(ChatLog chatLog){
        if(chatLog.getJid().equals(jid)){
            ChatMsg msg = new ChatMsg(chatLog.getContent(),ChatMsg.TYPE_RECEIVED);
            msgList.add(msg);
            adapter.notifyDataSetChanged();
            msgListView.smoothScrollToPosition(adapter.getCount() -1);
        }
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat);
        initView();
        EventBus.getDefault().register(this);
        initAction();
    }


    private void initAction() {
        send.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String content = inputText.getText().toString();
                if (!"".equals(content)) {
                    chatPresenter.sendChatMessage(content);
                    ChatMsg msg = new ChatMsg(content, ChatMsg.TYPE_SEND);
                    msgList.add(msg);
                    adapter.notifyDataSetChanged();
                    msgListView.setSelection(msgList.size());
                    inputText.setText("");

                }
            }
        });
    }

    private void initView() {
        Intent intent = getIntent();
        jid = intent.getStringExtra("jid");
        adapter = new MsgAdapter(ChatActivity.this, R.layout.item_msg, msgList);
        inputText = (EditText) findViewById(R.id.input_text);
        send = (Button) findViewById(R.id.send);
        msgListView = (ListView) findViewById(R.id.msg_list_view);
        msgListView.setAdapter(adapter);

        chatPresenter = new ChatPresenter(this, this, jid);

        chatPresenter.initData();
        toolbar = (Toolbar) findViewById(R.id.toolbar_chat);

        toolbar.setTitle("正在与"+jid+"聊天");
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        toolbar.setOnMenuItemClickListener(onMenuItemClickListener);
    }

    @Override
    public void initDataFromDb(List<ChatMsg> msgs) {
        if (msgs.size() > 0) {
            msgList.addAll(msgs);
            if (adapter != null)
                adapter.notifyDataSetChanged();
            msgListView.smoothScrollToPosition(adapter.getCount() -1);
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.chat_menu,menu);
        return true;
    }

    private Toolbar.OnMenuItemClickListener onMenuItemClickListener = new Toolbar.OnMenuItemClickListener() {
        @Override
        public boolean onMenuItemClick(MenuItem item) {
            switch (item.getItemId()){
                case R.id.action_edit :
                    log.d("点击了编辑");
                    break;
                case R.id.action_clear:

                    msgList.clear();
                    adapter.notifyDataSetChanged();
                    chatPresenter.deleteDataInDb();
                    break;
                case R.id.action_set:
                    log.d("点击了设置");
                    break;
            }
            return false;
        }
    };

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == android.R.id.home) {
           finish();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }
}