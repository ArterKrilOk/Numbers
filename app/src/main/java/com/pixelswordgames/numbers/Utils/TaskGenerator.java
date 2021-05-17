package com.pixelswordgames.numbers.Utils;


import android.content.Context;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class TaskGenerator {

    private Context context;
    private int lvl;

    public TaskGenerator(Context context){
        this.context = context;
    }

    public void setLvl(int lvl) {
        this.lvl = lvl;
    }

    public Task createTask(){
        if(lvl < 4)
            return simplePlusTask(10, 10);
        return simplePlusTask(100, 100);
    }

    private Task simplePlusTask(int aLimit, int bLimit){
        int a = new Random().nextInt(aLimit);
        int b = new Random().nextInt(bLimit);
        return new Task(a + " + " + b, a + b);
    }

    public List<Task> generateAllTasks(){
        List<Task> tasks = new ArrayList<>();
        for(int i = 0; i < getTaskCount(); ++i)
            tasks.add(createTask());
        return tasks;
    }

    public int getTaskCount(){
        if(lvl < 10)
            return 10;
        if(lvl < 20)
            return 15;
        if(lvl < 30)
            return 20;
        if(lvl < 50)
            return 25;
        return 30;
    }

    public static class Task {
        public String text;
        public int value;

        public Task(String text, int value) {
            this.text = text;
            this.value = value;
        }
    }
}
