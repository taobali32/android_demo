package com.example.t1;


import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.SimpleCursorAdapter;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;


/**
 * 原生的 不好用
 */
public class MainActivityOld extends AppCompatActivity  {

    protected EditText nameEdit,ageEdit,idEdit;
    private RadioGroup genderGp;

    private SQLiteDatabase db;

    private String genderStr = "男";

    private RadioButton malerb;


    private ListView stuList;

    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        nameEdit = (EditText) findViewById(R.id.name_add);

        ageEdit = (EditText) findViewById(R.id.age_add);

        idEdit = (EditText) findViewById(R.id.id_edt);

        stuList = (ListView) findViewById(R.id.stu_list);

        malerb = findViewById(R.id.male);
        //
        genderGp = (RadioGroup) findViewById(R.id.gender_gp);
        genderGp.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int i) {
                if (i == R.id.male){
                    genderStr = "男";
                }else {
                    genderStr = "女";
                }
            }
        });

        init();

    }

    public void init(){
        String path = getFilesDir() + "/stu3.db";

        SQLiteOpenHelper helper = new SQLiteOpenHelper(this,path,null,1) {
            @Override
            public void onCreate(SQLiteDatabase sqLiteDatabase) {
                //  创建
                Toast.makeText(MainActivityOld.this, "数据库创建成功", Toast.LENGTH_SHORT).show();

                //  创建表
                sqLiteDatabase.execSQL("create table stu3(_id integer primary key autoincrement, name varchar(20), age integer)");
            }

            @Override
            public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
                //  升级
                Toast.makeText(MainActivityOld.this, "数据库升级成功", Toast.LENGTH_SHORT).show();
            }
        };

        //  获取数据库对象
        //  存在直接打开
        //  不存在创建在打开
        //  数据库存在，版本号升高了，调用数据库升级成功
//                db.rawQuery(); 查询 select * from table
//                db.execSQL();  执行 curd
        db = helper.getReadableDatabase();
    }

    public void operate(View v){
        switch (v.getId()){
            case R.id.insert_btn:
                //

                String nameStr = nameEdit.getText().toString();
                String ageStr = ageEdit.getText().toString();

                ContentValues values = new ContentValues();
                values.put("name",nameStr);
                values.put("age",ageStr);
                long id = db.insert("stu3",null,values);

                Toast.makeText(this, "添加成功:" + id, Toast.LENGTH_SHORT).show();
                break;
            case R.id.update_btn:

//
//                String sql4 = "update stu3 set name = ?,age = ? where _id = ?";
//
//                db.execSQL(sql4,new String[]{nameEdit.getText().toString(),ageEdit.getText().toString(),idEdit.getText().toString()});

                ContentValues values2 = new ContentValues();
                values2.put("name",nameEdit.getText().toString());
                values2.put("age",ageEdit.getText().toString());

                db.update("stu3",values2,"_id = ?",new String[]{idEdit.getText().toString()});

                nameEdit.setText("");
                ageEdit.setText("");
                idEdit.setText("");
                malerb.setChecked(true);

                Toast.makeText(this, "更改成功", Toast.LENGTH_SHORT).show();

                break;
            case R.id.delete_btn:

                db.delete("stu3","_id = ?",new String[]{idEdit.getText().toString()});

                nameEdit.setText("");
                ageEdit.setText("");
                idEdit.setText("");
                malerb.setChecked(true);

                Toast.makeText(this, "删除成功", Toast.LENGTH_SHORT).show();

                break;
            case R.id.select_btn:

//                Cursor c =  db.rawQuery($sql,null);

                //  参数2， 查询的列
                //  参数3， where条件
                //  参数4， where条件的值
                //  参数5， group by
                //  参数6， having
                //  参数7， order by

                String selection = null;
                if (idEdit.getText().toString().equals("")){
                    selection = null;
                }else {
                    selection = "_id = " + idEdit.getText().toString();
                }

                Cursor c = db.query("stu3",null,selection,null,null,null,null);

                SimpleCursorAdapter adapter = new SimpleCursorAdapter(
                  this,
                  R.layout.item,
                  c,
                  new String[]{"_id","name","age"},
                  new int[]{R.id.id_item,R.id.name_item,R.id.age_item}
//                  CursorAdapter.FLAG_REGISTER_CONTENT_OBSERVER
                );

                stuList.setAdapter(adapter);

                break;
        }
    }


}