package com.pixelswordgames.numbers.Activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.AlertDialog;
import android.app.Dialog;
import android.os.Bundle;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.TextView;

import com.google.android.material.snackbar.Snackbar;
import com.pixelswordgames.numbers.Adapters.SolutionViewAdapter;
import com.pixelswordgames.numbers.Dialogs.ResultDialog;
import com.pixelswordgames.numbers.R;
import com.pixelswordgames.numbers.Utils.RecyclerItemClickListener;
import com.pixelswordgames.numbers.Utils.TaskGenerator;
import com.pixelswordgames.numbers.Views.TimeBarView;

import java.util.List;

public class GameActivity extends AppCompatActivity {

    private TimeBarView timeBarView;
    private TextView taskView;
    private SolutionViewAdapter adapter;
    private List<TaskGenerator.Task> tasks;
    private int curTask = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game);

        TaskGenerator generator = new TaskGenerator(this);
        generator.setLvl(getIntent().getIntExtra("lvl", 1));
        tasks = generator.generateAllTasks();
        adapter = new SolutionViewAdapter();

        timeBarView = findViewById(R.id.timeBarView);
        taskView = findViewById(R.id.taskView);
        RecyclerView recyclerView = findViewById(R.id.recyclerView);

        timeBarView.setCurMillis(10000);

        recyclerView.setLayoutManager(new GridLayoutManager(this,3));
        recyclerView.setHasFixedSize(true);
        recyclerView.setAdapter(adapter);
        recyclerView.addOnItemTouchListener(new RecyclerItemClickListener(this, recyclerView, new RecyclerItemClickListener.OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                checkSolution(adapter.getSolution(position));
            }

            @Override
            public void onLongItemClick(View view, int position) {

            }
        }));

        timeBarView.setOnTimeEndListener(time -> {
            runOnUiThread(() -> showResult(false, time));
        });

        setTask(curTask);
    }

    private void setTask(int task){
        taskView.setText(tasks.get(task).text);
        adapter.generateSolutions(tasks.get(task).value);
    }

    private void checkSolution(int solution){
        if(solution == tasks.get(curTask).value){
            curTask++;
            timeBarView.addMillis(1000);
            if(curTask == tasks.size()) {
                timeBarView.stop();
                showResult(true, timeBarView.getTime());
                return;
            }

            Animation animNext = AnimationUtils.loadAnimation(this, R.anim.next);
            taskView.startAnimation(animNext);

            setTask(curTask);
        } else {
            timeBarView.addMillis(-2000);
            Animation animShake = AnimationUtils.loadAnimation(this, R.anim.shake);
            taskView.startAnimation(animShake);
        }
    }

    private void showResult(boolean isWin, long millis){
        new ResultDialog(this, isWin, millis).show();
    }

    @Override
    protected void onResume() {
        super.onResume();
        timeBarView.start();
    }

    @Override
    protected void onPause() {
        super.onPause();
        timeBarView.stop();
    }


}