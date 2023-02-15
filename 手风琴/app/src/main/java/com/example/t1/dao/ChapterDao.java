package com.example.t1.dao;

import static android.database.sqlite.SQLiteDatabase.CONFLICT_REPLACE;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.example.t1.bean.Chapter;
import com.example.t1.bean.ChapterItem;

import java.util.ArrayList;
import java.util.List;

public class ChapterDao {
    public List<Chapter> loadFromDb(Context context){
        ChapterDbHelper dbHelper = ChapterDbHelper.getInstance(context);
        SQLiteDatabase db = dbHelper.getWritableDatabase();

        List<Chapter> chapterList = new ArrayList<>();
        Chapter chapter = null;
        Cursor cursor = db.rawQuery("select * from " + Chapter.TABLE_NAME, null);
        while (cursor.moveToNext()) {
            chapter = new Chapter();
            @SuppressLint("Range") int id = cursor.getInt(cursor.getColumnIndex(Chapter.COL_ID));
            @SuppressLint("Range") String name = cursor.getString(cursor.getColumnIndex(Chapter.COL_NAME));
            chapter.setId(id);
            chapter.setName(name);
            chapterList.add(chapter);
        }
        cursor.close();

        ChapterItem chapterItem = null;
        for (Chapter tmpChapter : chapterList) {
            int pid = tmpChapter.getId();
            cursor = db.rawQuery("select * from " + ChapterItem.TABLE_NAME + " where " + ChapterItem.COL_PID + " = ? ", new String[]{pid + ""});
            while (cursor.moveToNext()) {
                chapterItem = new ChapterItem();
                @SuppressLint("Range") int id = cursor.getInt(cursor.getColumnIndex(ChapterItem.COL_ID));
                @SuppressLint("Range") String name = cursor.getString(cursor.getColumnIndex(ChapterItem.COL_NAME));
                chapterItem.setId(id);
                chapterItem.setName(name);
                chapterItem.setPid(pid);
                tmpChapter.addChild(chapterItem);
            }
            cursor.close();
        }

        return chapterList;
    }

    public void insertToDb(Context context, List<Chapter> chapters){
        if (chapters == null || chapters.isEmpty()) {
            return;
        }
        ChapterDbHelper dbHelper = ChapterDbHelper.getInstance(context);
        SQLiteDatabase db = dbHelper.getWritableDatabase();

        db.beginTransaction();

        ContentValues cv = null;
        for (Chapter chapter : chapters) {
            cv = new ContentValues();
            cv.put(Chapter.COL_ID, chapter.getId());
            cv.put(Chapter.COL_NAME, chapter.getName());
            db.insertWithOnConflict(Chapter.TABLE_NAME, null, cv, CONFLICT_REPLACE);

            List<ChapterItem> chapterItems = chapter.getChildren();
            for (ChapterItem chapterItem : chapterItems) {
                cv = new ContentValues();
                cv.put(ChapterItem.COL_ID, chapterItem.getId());
                cv.put(ChapterItem.COL_NAME, chapterItem.getName());
                cv.put(ChapterItem.COL_PID, chapter.getId());
                db.insertWithOnConflict(ChapterItem.TABLE_NAME, null, cv, CONFLICT_REPLACE);
            }
        }
        db.setTransactionSuccessful();
        db.endTransaction();
    }
}
