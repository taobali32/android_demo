package com.example.t1;


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
 * 用着还行
 */
public class MainActivityOold extends AppCompatActivity  {

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
                Toast.makeText(MainActivityOold.this, "数据库创建成功", Toast.LENGTH_SHORT).show();

                //  创建表
                sqLiteDatabase.execSQL("create table stu3(_id integer primary key autoincrement, name varchar(20), age integer)");
            }

            @Override
            public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
                //  升级
                Toast.makeText(MainActivityOold.this, "数据库升级成功", Toast.LENGTH_SHORT).show();
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

                String nameStr = nameEdit.getText().toString();
                String ageStr = ageEdit.getText().toString();

//                String sql = "insert into stu(name,age) values('"+ nameStr +"',"+ ageStr +")";
//                db.execSQL(sql);

                String sql = "insert into stu3(name,age) values(?,?)";
                db.execSQL(sql,new Object[]{nameStr,ageStr});


                nameEdit.setText("");
                ageEdit.setText("");
                idEdit.setText("");
                malerb.setChecked(true);

                Toast.makeText(this, "添加成功", Toast.LENGTH_SHORT).show();
                break;
            case R.id.update_btn:


                String sql4 = "update stu3 set name = ?,age = ? where _id = ?";

                db.execSQL(sql4,new String[]{nameEdit.getText().toString(),ageEdit.getText().toString(),idEdit.getText().toString()});

                nameEdit.setText("");
                ageEdit.setText("");
                idEdit.setText("");
                malerb.setChecked(true);

                Toast.makeText(this, "更改成功", Toast.LENGTH_SHORT).show();

                break;
            case R.id.delete_btn:

                db.execSQL("delete from stu3 where _id = ?",new String[]{idEdit.getText().toString()});
                nameEdit.setText("");
                ageEdit.setText("");
                idEdit.setText("");
                malerb.setChecked(true);

                Toast.makeText(this, "删除成功", Toast.LENGTH_SHORT).show();

                break;
            case R.id.select_btn:

                String idStr = idEdit.getText().toString();

                String $sql = "select * from stu3";
                if (!idStr.equals("")){
                    $sql += " where _id = " + idStr;
                }
                //  查询
                Cursor c =  db.rawQuery($sql,null);

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