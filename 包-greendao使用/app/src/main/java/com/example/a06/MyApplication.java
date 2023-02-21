package com.example.a06;

import android.app.Application;
import android.database.sqlite.SQLiteDatabase;

import com.example.a06.model.DaoMaster;
import com.example.a06.model.DaoSession;

public class MyApplication extends Application {

    public static DaoSession daoSession;

    @Override
    public void onCreate() {
        super.onCreate();

        initDb();
    }

    /**
     * Initialize the database
     */
    public void initDb(){
            DaoMaster.DevOpenHelper helper = new DaoMaster.DevOpenHelper(this, "notes-db", null);

            SQLiteDatabase db = helper.getWritableDatabase();

            DaoMaster daoMaster = new DaoMaster(db);
            daoSession = daoMaster.newSession();

    }

}
