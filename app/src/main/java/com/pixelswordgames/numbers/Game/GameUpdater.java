package com.pixelswordgames.numbers.Game;

import android.content.Context;
import android.graphics.Bitmap;
import android.os.AsyncTask;

import com.pixelswordgames.numbers.R;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.List;
import java.util.Map;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;
import retrofit2.http.Query;

public class GameUpdater {
    private static final String UpdateBaseUrl = "https://numbers.pixelsg.space/";

    private Context context;
    private OnUpdateListener onUpdateListener;
    private LoadTask loadTask;
    private ApiService apiServece;

    public GameUpdater(Context context) {
        this.context = context;
        Retrofit retrofit = new Retrofit.Builder()
                .addConverterFactory(GsonConverterFactory.create())
                .baseUrl(UpdateBaseUrl)
                .build();
        apiServece = retrofit.create(ApiService.class);
        loadTask = new LoadTask();
    }

    public GameUpdater setOnUpdateListener(OnUpdateListener onUpdateListener) {
        this.onUpdateListener = onUpdateListener;
        return this;
    }

    public void update(){
        if(onUpdateListener != null)
            onUpdateListener.onProgress(2,context.getString(R.string.getting_info));

        Tasks tasks = new Tasks(context);

        apiServece.getVersionData().enqueue(new Callback<Tasks>() {
            @Override
            public void onResponse(Call<Tasks> call, Response<Tasks> response) {
                if(onUpdateListener != null) {
                    if (response.isSuccessful() && response.body() != null) {
                        if(response.body().getVersion() > tasks.getVersion())
                            loadTask.execute(UpdateBaseUrl + "lvls.json");
                        else onUpdateListener.onUpdateFinished(false);
                    } else onUpdateListener.onUpdateFinished(false);
                }
            }

            @Override
            public void onFailure(Call<Tasks> call, Throwable t) {
                if(onUpdateListener != null)
                    onUpdateListener.onUpdateFinished(false);
            }
        });
    }

    private class LoadTask extends AsyncTask<String,Integer, Boolean> {

        @Override
        protected void onCancelled() {
            super.onCancelled();
            if(onUpdateListener != null)
                onUpdateListener.onUpdateFinished(false);
        }

        @Override
        protected Boolean doInBackground(String... urls) {
            InputStream input = null;
            OutputStream output = null;
            HttpURLConnection connection = null;

            if(onUpdateListener != null)
                onUpdateListener.onProgress(3,context.getString(R.string.updating));

            try{
                File file = new File(context.getCacheDir(), "lvls");

                URL url = new URL(urls[0]);
                connection = (HttpURLConnection) url.openConnection();
                connection.setDoInput(true);
                connection.connect();

                input = connection.getInputStream();
                output = new FileOutputStream(file);

                int fileLength = connection.getContentLength();

                byte[] data = new byte[1024];
                long total = 0;
                int count;
                while ((count = input.read(data)) != -1) {
                    total += count;
                    if (fileLength > 0)
                        publishProgress((int) (total * 100 / fileLength));
                    output.write(data, 0, count);
                }

                return true;
            }catch (IOException e) {
                e.printStackTrace();
            } finally {
                try {
                    if (output != null)
                        output.close();
                    if (input != null)
                        input.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }

                if (connection != null)
                    connection.disconnect();
            }
            return false;
        }


        @Override
        protected void onPostExecute(Boolean isUpdated) {
            super.onPostExecute(isUpdated);
            if(onUpdateListener != null)
                onUpdateListener.onUpdateFinished(true);

        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }
    }

    private interface ApiService {
        @GET("version.php")
        Call<Tasks> getVersionData();
    }

    public interface OnUpdateListener {
        void onProgress(int state, String message);
        void onUpdateFinished(boolean updated);
    }
}
