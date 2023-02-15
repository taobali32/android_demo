package com.example.t1.dao;

import android.app.Activity;
import android.app.Application;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import androidx.annotation.Nullable;

import com.example.t1.bean.Chapter;
import com.example.t1.bean.ChapterItem;

public class ChapterDbHelper extends SQLiteOpenHelper {
    private static String DB_NAME = "db_chapter.db";
    private static final int VERSION = 1;

    private static Context mContext;

    private static ChapterDbHelper instance;

    public static synchronized ChapterDbHelper getInstance(Context context){

        if (instance == null){
            instance = new ChapterDbHelper(context.getApplicationContext());
        }
        return instance;
    }

    public ChapterDbHelper(@Nullable Context context) {
        super(context, DB_NAME, null, VERSION);
    }


    @Override
    public void onCreate(SQLiteDatabase db) {

        db.execSQL("CREATE TABLE IF NOT EXISTS " + Chapter.TABLE_NAME + "("
                + Chapter.COL_ID + " INTEGER PRIMARY KEY AUTOINCREMENT,"
                + Chapter.COL_NAME + " TEXT NOT NULL)");

        db.execSQL("CREATE TABLE IF NOT EXISTS " + ChapterItem.TABLE_NAME + "("
                + ChapterItem.COL_ID + " INTEGER PRIMARY KEY AUTOINCREMENT,"
                + ChapterItem.COL_NAME + " TEXT NOT NULL,"
                + ChapterItem.COL_PID + " INTEGER NOT NULL)");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        //
    }
}
