package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ListView;

import com.example.myapplication.adapter.RequestDataAdapter;
import com.example.myapplication.model.LessonInfo;
import com.example.myapplication.model.LessonResult;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

public class RequestDataActivity extends AppCompatActivity {

    ListView listView;
    private static final String Request_DATA_URL = "https://www.imooc.com/api/teacher?type=2&page=1";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_request_data);

        listView = (ListView) findViewById(R.id.main_list_view);

        LayoutInflater layoutInflater = (LayoutInflater)getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View footerView = layoutInflater.inflate(R.layout.footer_list_view,null);
        listView.addFooterView(footerView);

        new RequestDataAsyncTask().execute();
    }

    public class RequestDataAsyncTask extends AsyncTask<Void,Void,String>{

        @Override
        protected void onPostExecute(String result) {
            super.onPostExecute(result);
            //  结束请求数据后

            LessonResult lessonResult = new LessonResult();

            try {
                JSONObject jsonObject = new JSONObject(result);

                final int status = jsonObject.getInt("status");
                final String msg = jsonObject.getString("msg");

                lessonResult.setmStatus(status);
                lessonResult.setmMessage(msg);

                List<LessonInfo> lessonInfos = new ArrayList<>();
                JSONArray dataArray = jsonObject.getJSONArray("data");

                for (int i = 0; i < dataArray.length(); i++) {
                    LessonInfo lessonInfo = new LessonInfo();

                    JSONObject jsonObject1 = (JSONObject) dataArray.get(i);

                    final String name =  jsonObject1.getString("name");
                    lessonInfo.setmName(name);
                    lessonInfos.add(lessonInfo);
                }

                lessonResult.setmLessonInfoList(lessonInfos);

            } catch (JSONException e) {
                e.printStackTrace();
            }

            listView.setAdapter(new RequestDataAdapter(RequestDataActivity.this,lessonResult.getmLessonInfoList()));
        }

        @Override
        protected void onPreExecute() {
            //  开始请求数据前

            super.onPreExecute();
        }

        @Override
        protected String doInBackground(Void... voids) {

            return request("https://www.imooc.com/api/teacher?type=2&page=1");
        }

        private String request(String urlString) {

            try {
                URL url = new URL(urlString);

                HttpURLConnection connection = (HttpURLConnection) url.openConnection();
                connection.setRequestMethod("GET");
                connection.setConnectTimeout(30000);

                int responseCode = connection.getResponseCode();

                String responseMessage = connection.getResponseMessage();

                if (responseCode == HttpURLConnection.HTTP_OK){
                    InputStreamReader inputStreamReader = new InputStreamReader(connection.getInputStream());

                    BufferedReader bufferedReader = new BufferedReader(inputStreamReader);

                    StringBuilder stringBuilder = new StringBuilder();

                    String line;

                    while ( (line = bufferedReader.readLine()) != null ){
                        stringBuilder.append(line);
                    }

                    return stringBuilder.toString();
                }

            }catch (MalformedURLException e){
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
            return urlString;
        }
    }
}