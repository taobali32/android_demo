package com.example.utils;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

public class HttpUtils {
    public static String doGet(String urlStr) throws IOException {

        HttpURLConnection conn = null;
        InputStream is = null;
        ByteArrayOutputStream baos = null;

        try {
            URL url = new URL(urlStr);
            conn = (HttpURLConnection) url.openConnection();
            conn.setReadTimeout(5000);
            conn.setRequestMethod("GET");

            if (conn.getResponseCode() == 200){
                is = conn.getInputStream();

                baos = new ByteArrayOutputStream();
                int len = -1;
                byte[] buf = new byte[512];

                while (( len = is.read(buf)) != -1){
                    baos.write(buf,0,len);
                }
                baos.flush();

                return baos.toString();
            }


        }catch (MalformedURLException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }finally {
            if (baos != null){
                baos.close();
            }

            if (is != null){
                is.close();
            }

            conn.disconnect();
        }

        return null;
    }
}
