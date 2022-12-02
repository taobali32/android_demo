package com.example.myapplication7;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

public class MainActivity extends AppCompatActivity implements ListFragment.OnTitleClickListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        findViewById(R.id.textView).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(MainActivity.this,StaticLoadFragmentActivity.class));
            }
        });

        ListFragment listFragment = ListFragment.newInstance("BBB");
        getSupportFragmentManager().beginTransaction()
                .add(R.id.leftBox,listFragment)
                .commit();

        listFragment.setOnTitleClickListener(this);
    }


    @Override
    public void onClick(String title) {
        setTitle(title);
    }
}