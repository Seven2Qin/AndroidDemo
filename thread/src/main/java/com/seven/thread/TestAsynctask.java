package com.seven.thread;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Environment;
import android.support.v4.content.FileProvider;
import android.util.Log;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.lang.ref.WeakReference;
import java.net.HttpURLConnection;
import java.net.URL;

public class TestAsynctask extends AsyncTask<String, Integer, String> {

    private WeakReference<Activity> mWeakReference = null;
    ProgressDialog progressDialog;

    public TestAsynctask(Activity activity) {
        mWeakReference = new WeakReference<>(activity);

        progressDialog = new ProgressDialog(activity);
        progressDialog.setTitle("提示");
        progressDialog.setMessage("正在下载...");
        progressDialog.setIndeterminate(false);
        progressDialog.setMax(100);
        progressDialog.setCancelable(false);                    //设置不可点击界面之外的区域让对话框小时
        progressDialog.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);         //进度条类型
        progressDialog.show();
    }


    @Override
    protected void onPreExecute() {
        super.onPreExecute();
    }

    @Override
    protected String doInBackground(String... params) {
        URL url;
        HttpURLConnection conn;
        BufferedInputStream bis = null;
        FileOutputStream fos = null;
        File file = null;

        try {
            url = new URL(params[0]);
            conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("GET");
            conn.setConnectTimeout(5000);
            bis = new BufferedInputStream(conn.getInputStream());

            int fileLength = conn.getContentLength();
            String fileName = Environment.getExternalStorageDirectory().getPath() + "/action.apk";
            Log.e("TestAsynctask", fileName);
            file = new File(fileName);
            if (!file.exists()) {
                if (!file.getParentFile().exists()) {
                    file.getParentFile().mkdirs();
                }
                file.createNewFile();
            }
            fos = new FileOutputStream(file);
            byte data[] = new byte[4096];
            long total = 0;
            int count;
            while ((count = bis.read(data)) != -1) {
                total += count;
                publishProgress((int) (total * 100 / fileLength));
                fos.write(data, 0, count);
                fos.flush();
            }
            fos.flush();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                if (fos != null) {
                    fos.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
            try {
                if (bis != null) {
                    bis.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        return file.getAbsolutePath();
    }

    @Override
    protected void onProgressUpdate(Integer... values) {
        super.onProgressUpdate(values);
        progressDialog.setProgress(values[0]);
    }

    @Override
    protected void onPostExecute(String result) {
        super.onPostExecute(result);
        progressDialog.dismiss();
        Log.e("TestAsynctask", "result---->" + result);
        if (result != null)
            openFile(new File(result));
    }


    //打开APK程序代码
    private void openFile(File file) {
        // TODO Auto-generated method stub
        if (file == null) return;

        Intent intent = new Intent(Intent.ACTION_VIEW);
//判断是否是AndroidN以及更高的版本
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            intent.setFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
            Uri contentUri = FileProvider.getUriForFile(mWeakReference.get(), BuildConfig.APPLICATION_ID + ".fileProvider", file);
            intent.setDataAndType(contentUri, "application/vnd.android.package-archive");
        } else {
            intent.setDataAndType(Uri.fromFile(file), "application/vnd.android.package-archive");
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        }
        mWeakReference.get().startActivity(intent);

    }

}