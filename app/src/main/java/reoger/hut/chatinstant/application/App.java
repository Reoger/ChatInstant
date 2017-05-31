package reoger.hut.chatinstant.application;

import android.app.Application;

import com.squareup.leakcanary.LeakCanary;

import org.greenrobot.greendao.database.Database;
import org.jivesoftware.smack.android.AndroidSmackInitializer;

import reoger.hut.chatinstant.bean.DaoMaster;
import reoger.hut.chatinstant.bean.DaoSession;

/**
 * Created by 24540 on 2017/5/25.
 */

public class App extends Application {

    private static App app;


    public static App getInstance()  {
        if(app==null){
            return null;
        }
        return app;
    }
    private DaoSession daoSession;

    @Override
    public void onCreate() {
        super.onCreate();
        app = this;
        new AndroidSmackInitializer().initialize();
        DaoMaster.DevOpenHelper helper = new DaoMaster.DevOpenHelper(this, "ChatInstant-db" );
        Database db =  helper.getWritableDb();
        daoSession = new DaoMaster(db).newSession();
        if (LeakCanary.isInAnalyzerProcess(this)) {
            return;
        }
        LeakCanary.install(this);
    }

    public DaoSession getDaoSession() {
        return daoSession;
    }

}
