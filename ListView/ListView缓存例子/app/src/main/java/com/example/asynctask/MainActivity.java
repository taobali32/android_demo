package com.example.asynctask;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;

/**
 * 申请网络权限
 * 布局
 * 下载之前做什么
 * 下载中
 * 下载后
 */
public class MainActivity extends AppCompatActivity {

    private ProgressBar progressBar;
    private Button button;
    private TextView $textView;
    private int progress = 0;
    private String mFilePath;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initView();

        setListener();

        setInitData();
    }


    /**
     * 参数
     * 进度
     * 结果
     */
    public class DownloadAsyncTask extends AsyncTask<String,Integer,Boolean>{

        /**
         * 在异步任务之前,在主线程中
         */
        @Override
        protected void onPreExecute() {
            super.onPreExecute();

            button.setText("下载中...");
            $textView.setText("下载中...");

            progressBar.setProgress(progress);
        }

        @Override
        protected void onPostExecute(Boolean aBoolean) {
            super.onPostExecute(aBoolean);

            button.setText("下载完成");
            $textView.setText(aBoolean ? mFilePath : "下载失败");
        }

        @Override
        protected void onProgressUpdate(Integer... values) {
            if (values != null && values.length > 0){

                //  收到进度
                progressBar.setProgress(values[0]);
            }
        }

        @Override
        protected Boolean doInBackground(String... params) {
            if (params != null && params.length > 0){
                String apkUrl = params[0];

                try {
                    URL url = new URL(apkUrl);

                    URLConnection urlConnection = url.openConnection();

                    InputStream inputStream = urlConnection.getInputStream();

                    //  获取下载内容长度
                    int contentLength = urlConnection.getContentLength();

                    progressBar.setProgress(progress);
                    progressBar.setMax(contentLength);
//                    Log.e("contentLength", String.valueOf(contentLength));

//                    return true;

                    // 下载到本地存储地址

//                    mFilePath = "storage/emulated/0/imooc.apk";

                    mFilePath = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS)
                            + File.separator + "imooc.apk";

                    //  如果老的存在则先删除
                    File apkFile = new File(mFilePath);
                    if (apkFile.exists()){
                        boolean result = apkFile.delete();

                        if (!result){
                            return false;
                        }
                    }

                    //  已下载的大小
                    int downloadSize = 0;

                    byte[] bytes = new byte[1024];

                    int length;

                    //  创建一个输出管道
                    OutputStream outputStream = new FileOutputStream(mFilePath);


                    //  不断的读取数据,直到-1为止
                    while ( (length = inputStream.read(bytes)) != -1){
                         outputStream.write(bytes,0,length);

                         downloadSize += length;

                        // 发送进度
                        publishProgress(downloadSize);

                        Log.e("downloadSize: " , String.valueOf( downloadSize ));



                    }

                    inputStream.close();
                    outputStream.close();

                    return true;

                } catch (MalformedURLException e) {
                    e.printStackTrace();
                    return false;

                } catch (IOException e) {
                    e.printStackTrace();
                    return false;
                }

            }

            return false;
        }
    }

    private void setInitData() {
        progressBar.setProgress(progress);

        button.setText("准备下载");
        $textView.setText("准备下载");
    }

    private void setListener() {
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DownloadAsyncTask asyncTask = new DownloadAsyncTask();

                String url = "http://pro.danshanghu.jiabaoleshop.com/hlyy163.apk";
                asyncTask.execute(url);
            }
        });
    }

    private void initView() {
        progressBar = (ProgressBar) findViewById(R.id.progressBar);

        button = (Button) findViewById(R.id.button);

        $textView = (TextView) findViewById(R.id.textView);

    }
}