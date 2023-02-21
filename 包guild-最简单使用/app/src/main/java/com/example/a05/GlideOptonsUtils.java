package com.example.a05;

import android.annotation.SuppressLint;

import com.bumptech.glide.request.RequestOptions;

public class GlideOptonsUtils
{
    @SuppressLint("CheckResult")
    public static RequestOptions baseOptions()
    {
        RequestOptions options = new RequestOptions();
        options.placeholder(R.drawable.ic_launcher_background);
        options.error(R.drawable.ic_launcher_foreground);
        return options;
    }

    public static RequestOptions getCircleOptions()
    {
        RequestOptions options = new RequestOptions();
        return baseOptions().circleCrop();
    }
}
