package reoger.hut.chatinstant.view;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import org.jivesoftware.smack.roster.RosterEntry;

import java.util.ArrayList;
import java.util.List;

import reoger.hut.chatinstant.R;
import reoger.hut.chatinstant.adapter.FriendsAdapter;
import reoger.hut.chatinstant.interfaces.IControlFriends;
import reoger.hut.chatinstant.listener.ItemClickListener;
import reoger.hut.chatinstant.persenter.UserPresenter;
import reoger.hut.chatinstant.utils.ShareHelper;
import reoger.hut.chatinstant.utils.log;

public class UserActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener,IControlFriends.IView {

    private RecyclerView mRecyclerView;
    private FriendsAdapter mAdapter;
    private List<RosterEntry> list;
    private RecyclerView.LayoutManager manager;
    private UserPresenter presenter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user);
        presenter = new UserPresenter(this,this);
        initView();
    }

    private void initView() {
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);



        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
//                        .setAction("Action", null).show();

                //添加好友
                startActivity(new Intent(UserActivity.this,AddFirendsActivity.class));
            }
        });

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        list = new ArrayList<>();

        mRecyclerView = (RecyclerView) findViewById(R.id.recycler_friends);
        manager = new LinearLayoutManager(this,LinearLayoutManager.VERTICAL,false);
        mAdapter = new FriendsAdapter(list,this);
        if(mRecyclerView == null)
            log.d("mrecyclerView is null!!");
        else
            mRecyclerView.setAdapter(mAdapter);
        mRecyclerView.setLayoutManager(manager);
        presenter.getFirends();

        mRecyclerView.addOnItemTouchListener(new ItemClickListener(mRecyclerView, new ItemClickListener.OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                Intent intent = new Intent(UserActivity.this,ChatActivity.class);
                intent.putExtra("jid",list.get(position).getJid().toString());
                startActivity(intent);
            }

            @Override
            public void onItemLongClick(View view, int postion) {

            }
        }));
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.user, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_camera) {
            // Handle the camera action
        } else if (id == R.id.nav_gallery) {

        } else if (id == R.id.nav_slideshow) {

        } else if (id == R.id.nav_manage) {
            startActivity(new Intent(UserActivity.this,SettingsActivity.class));
        } else if (id == R.id.nav_share) {
            ShareHelper.shareApp(this);
        } else if (id == R.id.nav_send) {
            startActivity(new Intent(UserActivity.this,AboutActivity.class));
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    @Override
    public void getResult(List<RosterEntry> firends) {
        list.addAll(firends);
        if(mAdapter!=null)
        mAdapter.notifyDataSetChanged();

    }
}
