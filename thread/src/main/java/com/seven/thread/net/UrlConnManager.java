package com.seven.thread.net;

import android.text.TextUtils;
import android.util.Log;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;

public class UrlConnManager {

    public static void useHttpUrlConnectionPost(String url) {
        InputStream mInputStream = null;
        HttpURLConnection mHttpURLConnection = UrlConnManager.getHttpURLConnection(url);
        Log.i("UrlConnManager",mHttpURLConnection.toString());
        try {
            List<KeyValuePair> postParams = new ArrayList<>();
            //要传递的参数
            postParams.add(new KeyValuePair("username", "moon"));
            postParams.add(new KeyValuePair("password", "123"));
//            UrlConnManager.postParams(mHttpURLConnection.getOutputStream(), postParams);
//            mHttpURLConnection.connect();//只要调用getResponseCode()，就不必要调用connect方法(调用也无妨)
            mInputStream = mHttpURLConnection.getInputStream();
            int code = mHttpURLConnection.getResponseCode();
            String respose = converStreamToString(mInputStream);
            Log.i("wangshu", "请求状态码:" + code + "\n请求结果:\n" + respose);
            mInputStream.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static String converStreamToString(InputStream is) throws IOException {
        BufferedReader reader = new BufferedReader(new InputStreamReader(is));
        StringBuffer sb = new StringBuffer();
        String line = null;
        while ((line = reader.readLine()) != null) {
            sb.append(line + "\n");
        }
        String respose = sb.toString();
        return respose;
    }

    /**
     * 获取HttpURLConnection
     *
     * @param url
     * @return
     */
    private static HttpURLConnection getHttpURLConnection(String url) {
        HttpURLConnection mHttpURLConnection = null;
        try {
            URL mUrl = new URL(url);
            mHttpURLConnection = (HttpURLConnection) mUrl.openConnection();
            //设置链接超时时间
            mHttpURLConnection.setConnectTimeout(15000);
            //设置读取超时时间
            mHttpURLConnection.setReadTimeout(15000);
            //设置请求参数
//            mHttpURLConnection.setRequestMethod("POST");
            //添加Header
            mHttpURLConnection.setRequestProperty("Connection", "Keep-Alive");
            //接收输入流
//            mHttpURLConnection.setDoInput(true);
            //传递参数时需要开启
//            mHttpURLConnection.setDoOutput(true);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return mHttpURLConnection;
    }

    /**
     * post请求参数
     *
     * @param output
     * @param paramsList
     * @throws IOException
     */
    private static void postParams(OutputStream output, List<KeyValuePair> paramsList) throws IOException {
        StringBuilder mStringBuilder = new StringBuilder();
        for (KeyValuePair pair : paramsList) {
            if (!TextUtils.isEmpty(mStringBuilder)) {
                mStringBuilder.append("&");
            }
            mStringBuilder.append(URLEncoder.encode(pair.getKey(), "UTF-8"));
            mStringBuilder.append("=");
            mStringBuilder.append(URLEncoder.encode(pair.getValue(), "UTF-8"));
        }
        BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(output, "UTF-8"));
        writer.write(mStringBuilder.toString());
        writer.flush();
        writer.close();
    }

}
