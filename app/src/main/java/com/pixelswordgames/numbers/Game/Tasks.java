package com.pixelswordgames.numbers.Game;

import android.content.Context;

import com.google.gson.Gson;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.pixelswordgames.numbers.R;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

public class Tasks {
    @SerializedName("version")
    @Expose
    private Double version;
    @SerializedName("updatedAt")
    @Expose
    private String updatedAt;
    @SerializedName("channel")
    @Expose
    private String channel;
    @SerializedName("levels")
    @Expose
    private List<Level> levels;

    public Tasks() { }

    public Tasks(Context context) {
        levels = new ArrayList<>();

        File file = new File(context.getCacheDir(), "lvls");

        InputStreamReader inputStreamReader = null;
        if(file.exists()) {
            try {
                inputStreamReader = new InputStreamReader(new FileInputStream(file));
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
        } else inputStreamReader = new InputStreamReader(context.getResources().openRawResource(R.raw.lvls));



        Gson gson = new Gson();
        Tasks tasks = gson.fromJson(
                inputStreamReader,
                Tasks.class
        );

        this.levels = tasks.getLevels();
        this.channel = tasks.getChannel();
        this.updatedAt = tasks.getUpdatedAt();
        this.version = tasks.getVersion();
    }

    public Double getVersion() {
        return version;
    }

    public String getUpdatedAt() {
        return updatedAt;
    }

    public String getChannel() {
        return channel;
    }

    public List<Level> getLevels() {
        return levels;
    }

    public Level getLevel(int lvl) {
        for(Level l : levels)
            if(l.getLvl() == lvl)
                return l;
        return levels.get(0);
    }
}
