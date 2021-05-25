package com.pixelswordgames.numbers.Game;

import android.content.Context;
import android.os.AsyncTask;

import com.pixelswordgames.numbers.R;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.GET;
import retrofit2.internal.EverythingIsNonNull;

public class GameUpdater {
    private static final String UpdateBaseUrl = "https://numbers.pixelsg.space/";
    private static final String RemoteFile = "tasks.json";
    private static final String RemoteFilePath = UpdateBaseUrl + RemoteFile;
    public static final String TasksFile = "tasks";

    private final Context context;
    private OnUpdateListener onUpdateListener;
    private final LoadTask loadTask;
    private final ApiService apiServece;

    public GameUpdater(Context context) {
        this.context = context;
        Retrofit retrofit = new Retrofit.Builder()
                .addConverterFactory(GsonConverterFactory.create())
                .baseUrl(UpdateBaseUrl)
                .build();
        apiServece = retrofit.create(ApiService.class);
        loadTask = new LoadTask();
    }

    public void setOnUpdateListener(OnUpdateListener onUpdateListener) {
        this.onUpdateListener = onUpdateListener;
    }

    public static File getTasksFile(Context context){
        if(!context.getCacheDir().exists())
            return new File(context.getExternalCacheDir(), TasksFile);
        return new File(context.getCacheDir(), TasksFile);
    }

    public void update(){
        if(onUpdateListener != null)
            onUpdateListener.onProgress(2,context.getString(R.string.getting_info));

        Tasks tasks = new Tasks(context);

        apiServece.getVersionData().enqueue(new Callback<Tasks>() {
            @Override
            @EverythingIsNonNull
            public void onResponse(Call<Tasks> call, Response<Tasks> response) {
                if(onUpdateListener != null) {
                    if (response.isSuccessful() && response.body() != null) {
                        if(response.body().getVersion() > tasks.getVersion())
                            loadTask.execute(RemoteFilePath);
                        else onUpdateListener.onUpdateFinished(false);
                    } else onUpdateListener.onUpdateFinished(false);
                }
            }

            @Override
            @EverythingIsNonNull
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
                File file = getTasksFile(context);

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
