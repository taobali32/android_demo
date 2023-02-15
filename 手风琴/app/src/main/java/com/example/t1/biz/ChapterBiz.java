package com.example.t1.biz;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;

import com.example.t1.bean.Chapter;
import com.example.t1.bean.ChapterItem;
import com.example.t1.dao.ChapterDao;
import com.example.utils.HttpUtils;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.security.auth.callback.Callback;

public class ChapterBiz {

    private ChapterDao chapterDao = new ChapterDao();

    public void loadDatas(Context context, Callback callback,boolean useCache){
        //
        @SuppressLint("StaticFieldLeak") AsyncTask<Boolean,Void, List<Chapter>> asyncTask = new AsyncTask<Boolean, Void, List<Chapter>>() {
            private Exception ex;

            @SuppressLint("StaticFieldLeak")
            @Override

            protected List<Chapter> doInBackground(Boolean... booleans) {
                boolean isUseCache = booleans[0];
                List<Chapter> charterList = new ArrayList<>();

               try {
                   if (isUseCache){
                       charterList.addAll(chapterDao.loadFromDb(context));
                       //从缓存中读取
                   }

                   if (charterList.isEmpty()){
                       //从网络中读取
                       List<Chapter> chapterListFromNet = loadFomNet(context);

                       chapterDao.insertToDb(context,chapterListFromNet);

                       charterList.addAll(chapterListFromNet);
                   }

               }catch (Exception e){
                   e.printStackTrace();
                   this.ex = e;
               }

                return charterList;
            }

            @Override
            protected void onPostExecute(List<Chapter> chapters) {
                if (ex != null){
                    callback.onFailed(ex);
                }

                callback.onSucceed(chapters);
            }
        };

        asyncTask.execute(useCache);
    }

    private List<Chapter> loadFomNet(Context context) throws IOException {
        String url = "http://www.imooc.com/api/expandablelistview";

        List<Chapter> chapterList = new ArrayList<>();

        String content = HttpUtils.doGet(url);

        if (content != null){
            chapterList = parseContent(content);
        }

        return chapterList;
    }

    private List<Chapter> parseContent(String content) {
        List<Chapter> chapterList = new ArrayList<>();

        try {
            JSONObject root = new JSONObject(content);
            int errorCode = root.optInt("errorCode");
            if (errorCode == 0){
                JSONArray dataJsonArray = root.optJSONArray("data");

                for (int i = 0; i < dataJsonArray.length(); i++) {
                    //
                    JSONObject chapterJsonObject = dataJsonArray.getJSONObject(i);

                    int id = chapterJsonObject.optInt("id");
                    String name = chapterJsonObject.optString("name");

                    Chapter chapter = new Chapter(id,name);
                    chapterList.add(chapter);

                    // 解析子节点
                    JSONArray childrenJsonArray = chapterJsonObject.optJSONArray("children");

                    if (childrenJsonArray != null){
                        for (int j = 0; j < childrenJsonArray.length(); j++) {
                            JSONObject childrenJsonObject = childrenJsonArray.getJSONObject(j);

                            int childrenId = childrenJsonObject.optInt("id");
                            String childrenName = childrenJsonObject.optString("name");

                            ChapterItem childrenChapter = new ChapterItem(childrenId,childrenName);

                            chapter.addChild(childrenChapter);
                        }
                    }
                }
            }

        } catch (JSONException e) {
            throw new RuntimeException(e);
        }

        return chapterList;
    }

    public static interface Callback{
        void onSucceed(List<Chapter> ChapterBizList);
        void onFailed(Exception e);

    }
}
