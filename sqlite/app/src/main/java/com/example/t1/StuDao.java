package com.example.t1;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.widget.Toast;

public class StuDao {
    private SQLiteDatabase db;

    public StuDao(Context context) {
        String path = context.getFilesDir() + "/stu3.db";

        SQLiteOpenHelper helper = new SQLiteOpenHelper(context, path, null, 1) {
            @Override
            public void onCreate(SQLiteDatabase sqLiteDatabase) {
                //  创建
//                Toast.makeText(MainActivity.this, "数据库创建成功", Toast.LENGTH_SHORT).show();

                //  创建表
                sqLiteDatabase.execSQL("create table stu3(_id integer primary key autoincrement, name varchar(20), age integer)");
            }

            @Override
            public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
                //  升级
//                Toast.makeText(MainActivity.this, "数据库升级成功", Toast.LENGTH_SHORT).show();
            }
        };

        db = helper.getReadableDatabase();
    }

    public void close(){
        db.close();
    }

    public void deleteAll(){
        String sql = "delete from stu3";
        db.execSQL(sql);
    }

    public void deleteByName(String name){
        String sql = "delete from stu3 where name = ?";
        db.execSQL(sql,new Object[]{name});
    }

    public void deleteByAge(int age){
        String sql = "delete from stu3 where age = ?";
        db.execSQL(sql,new Object[]{age});
    }

    public Cursor query(int id){
        String sql = "select * from stu3 where _id = ?";
        return db.rawQuery(sql,new String[]{id+""});
    }

    //  查询
    public Cursor query(){
        String sql = "select * from stu3";
        return db.rawQuery(sql,null);
    }

    public void insert(String name, int age) {
        String sql = "insert into stu3(name,age) values(?,?)";
        db.execSQL(sql, new Object[]{name, age});
    }

    public void delete(int id){
        String sql = "delete from stu3 where _id = ?";
        db.execSQL(sql,new Object[]{id});
    }

    public void update(int id, String name, int age){
        String sql = "update stu3 set name = ?, age = ? where _id = ?";
        db.execSQL(sql,new Object[]{name,age,id});
    }
}
