package com.example.myapplication7;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

public class ListFragment extends Fragment {

    public static final String AA = "aa";
    private String title;

    private User user;


    public void setUser(User user) {
        this.user = user;
    }

    public class User{

    }

    public static ListFragment newInstance(String title){
        ListFragment fragment = new ListFragment();

        Bundle bundle = new Bundle();
        bundle.putString(AA,title);

        fragment.setArguments(bundle);

        return fragment;
    }


    public static ListFragment newInstance(String title,User user){
        ListFragment fragment = new ListFragment();

        Bundle bundle = new Bundle();
        bundle.putString(AA,title);

        fragment.setArguments(bundle);
        fragment.setUser(user);

        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (getArguments() != null){
            title = getArguments().getString(AA);
        }

    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        //
        View view = inflater.inflate(R.layout.fragment_list, container, false);

        TextView textView = view.findViewById(R.id.textView);

        textView.setText(title);


        textView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (mOnTitleClickListener != null){
                    mOnTitleClickListener.onClick(title);
                }
            }
        });

        return view;
    }


    OnTitleClickListener mOnTitleClickListener;
    public interface OnTitleClickListener{
        void onClick(String title);
    }


    public void setOnTitleClickListener(OnTitleClickListener onTitleClickListener){
        mOnTitleClickListener = onTitleClickListener;
    }

}
