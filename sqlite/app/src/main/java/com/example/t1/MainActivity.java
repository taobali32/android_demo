package com.example.t1;


import android.Manifest;
import android.app.Activity;
import android.app.Application;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.View;
import android.widget.CursorAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.SimpleCursorAdapter;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.sql.SQLFeatureNotSupportedException;


/**
 * 用着还行
 */
public class MainActivity extends AppCompatActivity  {

    protected EditText nameEdit,ageEdit,idEdit;
    private RadioGroup genderGp;

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

    }



    public void operate(View v){
        switch (v.getId()){
            case R.id.insert_btn:
                String nameStr = nameEdit.getText().toString();
                String ageStr = ageEdit.getText().toString();

                StuDao dao = new StuDao(this);
                dao.insert(nameStr,Integer.parseInt(ageStr));

                nameEdit.setText("");
                ageEdit.setText("");
                idEdit.setText("");
                malerb.setChecked(true);

                Toast.makeText(this, "添加成功", Toast.LENGTH_SHORT).show();
                break;
            case R.id.update_btn:
                String idStr = idEdit.getText().toString();
                String nameStr2 = nameEdit.getText().toString();
                String ageStr2 = ageEdit.getText().toString();

                StuDao dao2 = new StuDao(this);
                dao2.update(Integer.parseInt(idStr),nameStr2,Integer.parseInt(ageStr2));


                Toast.makeText(this, "更改成功", Toast.LENGTH_SHORT).show();

                break;
            case R.id.delete_btn:
                String idStr3 = idEdit.getText().toString();
                StuDao dao3 = new StuDao(this);
                dao3.delete(Integer.parseInt(idStr3));

                nameEdit.setText("");
                ageEdit.setText("");
                idEdit.setText("");
                malerb.setChecked(true);

                Toast.makeText(this, "删除成功", Toast.LENGTH_SHORT).show();

                break;
            case R.id.select_btn:
                String idStr4 = idEdit.getText().toString();

                StuDao dao4 = new StuDao(this);
                Cursor c = null;
                if (idStr4.equals("")){
                    c = dao4.query();
                }else {
                    c = dao4.query(Integer.parseInt(idStr4));
                }

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