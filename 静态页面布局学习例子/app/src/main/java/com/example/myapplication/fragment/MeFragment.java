package com.example.myapplication.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.myapplication.LoginActivity;
import com.example.myapplication.R;

/**
 * 我的
 */
public class MeFragment extends Fragment {

    protected Button $mButtonLogin;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return  inflater.inflate(R.layout.fragment_me,container,false);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {

        $mButtonLogin = (Button) getView().findViewById(R.id.btn_login);

        $mButtonLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent login = new Intent(getActivity(), LoginActivity.class);
                startActivity(login);
            }
        });

        super.onActivityCreated(savedInstanceState);


        View $f = getView().findViewById(R.id.kouling);
        $f.setVisibility(View.GONE);

        $f.getVisibility();
    }
}
