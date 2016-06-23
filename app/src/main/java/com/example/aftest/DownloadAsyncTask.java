package com.example.aftest;

import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import android.widget.Toast;

import org.json.JSONException;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.lang.ref.WeakReference;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;
import java.util.List;

import javax.net.ssl.HttpsURLConnection;

public class DownloadAsyncTask extends AsyncTask<String, Void, List<Promotion>> {

    private ProgressDialog dialog;
    private Handler handler;
    private WeakReference<MainActivity> mainActivityWeakReference;

    public DownloadAsyncTask(MainActivity activity, Handler handler){
        this.mainActivityWeakReference = new WeakReference<MainActivity>(activity);
        this.handler = handler;
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
        if(mainActivityWeakReference != null && mainActivityWeakReference.get() != null)
            dialog = new ProgressDialog(mainActivityWeakReference.get());
            dialog.setTitle("Please Wait");
            dialog.setMessage("Data is being fetched");
            dialog.show();
    }

    @Override
    protected List<Promotion> doInBackground(String... params) {
        if(params.length<0){
            return null;
        }
        try {
            URL url = new URL(params[0]);
            HttpsURLConnection connection = (HttpsURLConnection) url.openConnection();
            connection.setRequestMethod("GET");
            connection.setConnectTimeout(60000);
            InputStream inputStream = connection.getInputStream();
            BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));
            StringBuilder builder = new StringBuilder();
            String line = "";
            while((line = reader.readLine()) != null){
                builder.append(line);
            }
            if (Environment.MEDIA_MOUNTED.equals(Environment.getExternalStorageState())) {
                File file = new File(Environment.getExternalStorageDirectory()+File.separator+"AFPromotions.json");
                if(!file.exists()){
                    file.createNewFile();
                }
                FileWriter writer = new FileWriter(file);
                writer.write(builder.toString());
                writer.flush();
                writer.close();
            } else {
                Toast.makeText(mainActivityWeakReference.get(), "Storage is not available for caching the data", Toast.LENGTH_SHORT).show();
            }
            return AFUtil.getPromotionDetails(builder.toString());
        }
        catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (ProtocolException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    protected void onPostExecute(List<Promotion> result) {
        super.onPostExecute(result);
        if(dialog != null && dialog.isShowing()){
            dialog.dismiss();
        }
        Message msg = handler.obtainMessage();
        msg.what = 0;
        msg.obj = result;
        handler.sendMessage(msg);
    }
}
