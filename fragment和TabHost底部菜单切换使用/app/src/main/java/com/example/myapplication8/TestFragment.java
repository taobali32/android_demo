package com.example.myapplication8;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

public class TestFragment extends Fragment {

    private static final String TITLE = "title";
    private String mTitle;

    public static TestFragment newInstance(String title){
        TestFragment fragment = new TestFragment();

        Bundle bundle = new Bundle();

        bundle.putString(TITLE,title);

        fragment.setArguments(bundle);

        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        //
        View view = inflater.inflate(R.layout.fragment_test,null);

//        getArguments()getArguments
        if (getArguments() != null){
            mTitle = String.valueOf(getArguments().getString(TITLE));
        }

        TextView textView = (TextView)  view.findViewById(R.id.text_view);

        textView.setText(mTitle);

        return view;
    }
}
