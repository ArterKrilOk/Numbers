package com.pixelswordgames.numbers.Activities;

import android.os.Bundle;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.LoadAdError;
import com.google.android.gms.ads.rewarded.RewardedAd;
import com.google.android.gms.ads.rewarded.RewardedAdLoadCallback;
import com.pixelswordgames.numbers.Adapters.SolutionViewAdapter;
import com.pixelswordgames.numbers.DB.DBLab;
import com.pixelswordgames.numbers.Dialogs.ResultDialog;
import com.pixelswordgames.numbers.Game.Level;
import com.pixelswordgames.numbers.Game.Score;
import com.pixelswordgames.numbers.Game.Task;
import com.pixelswordgames.numbers.Game.TaskGenerator;
import com.pixelswordgames.numbers.Game.Tasks;
import com.pixelswordgames.numbers.R;
import com.pixelswordgames.numbers.Views.TasksCountView;
import com.pixelswordgames.numbers.Views.TimeBarView;

import java.util.List;

public class GameActivity extends AppCompatActivity {

    private static final String ADS_ID = "ca-app-pub-1950508283527188/9986893746";

    private SolutionViewAdapter adapter;
    private Level level;
    private List<Task> tasks;

    private ResultDialog resultDialog;
    private TimeBarView timeBarView;
    private TasksCountView tasksCountView;
    private TextView taskView;

    private int curTask = 0;
    private boolean isEnabled = true;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game);

        TaskGenerator generator = new TaskGenerator(this);
        generator.setLvl(getIntent().getIntExtra("lvl", 1));
        tasks = generator.generateAllTasks();
        level = new Tasks(this).getLevel(getIntent().getIntExtra("lvl", 1));
        adapter = new SolutionViewAdapter();
        resultDialog = new ResultDialog(this);

        timeBarView = findViewById(R.id.timeBarView);
        taskView = findViewById(R.id.taskView);
        tasksCountView = findViewById(R.id.tasksCountView);
        RecyclerView recyclerView = findViewById(R.id.recyclerView);

        timeBarView.setCurMillis(level.getTime());
        tasksCountView.setTasks(tasks.size());

        recyclerView.setLayoutManager(new GridLayoutManager(this, 3));
        recyclerView.setHasFixedSize(true);
        recyclerView.setAdapter(adapter);

        adapter.setOnItemClickListener(position -> checkSolution(adapter.getSolution(position)));

        timeBarView.setOnTimeEndListener(time  -> showResult(false, time));
        resultDialog.setOnDialogClosedListener(new ResultDialog.OnDialogClosedListener() {
            @Override
            public void onContinue(boolean isWin) {
                finish();
            }

            @Override
            public void onADs() {
                isEnabled = false;
                showADs();
            }
        });

        setTask(curTask);
    }

    private void setTask(int task) {
        taskView.setText(tasks.get(task).text);
        adapter.setSolutions(tasks.get(task).solutions);
    }

    private void checkSolution(String solution) {
        if (!isEnabled)
            return;
        if(curTask >= tasks.size())
            return;
        if (solution.equals(tasks.get(curTask).correctSolution)) {
            curTask++;
            tasksCountView.setCurTask(curTask);
            timeBarView.addMillis(level.getSuccessTime());
            if (curTask == tasks.size()) {
                timeBarView.stop();
                DBLab.get(this).saveScore(new Score(
                        level.getLvl(),
                        timeBarView.getTime() / 1000f,
                        ""
                ));
                isEnabled = false;
                showResult(true, timeBarView.getTime());
                return;
            }

            Animation animNext = AnimationUtils.loadAnimation(this, R.anim.next);
            taskView.startAnimation(animNext);

            setTask(curTask);
        } else {
            timeBarView.addMillis(-level.getFailedTime());
            Animation animShake = AnimationUtils.loadAnimation(this, R.anim.shake);
            taskView.startAnimation(animShake);
        }
    }

    private void showADs() {
        AdRequest adRequest = new AdRequest.Builder().build();
        RewardedAd.load(this, ADS_ID,
                adRequest, new RewardedAdLoadCallback() {
                    @Override
                    public void onAdFailedToLoad(@NonNull LoadAdError loadAdError) {
                        finish();
                    }

                    @Override
                    public void onAdLoaded(@NonNull RewardedAd rewardedAd) {
                        rewardedAd.show(GameActivity.this, rewardItem -> {
                            isEnabled = true;
                            timeBarView.setCurMillis(level.getTime());
                            timeBarView.start();
                        });
                    }
                });
    }

    private void showResult(boolean isWin, long millis) {
        resultDialog.setWin(isWin);
        resultDialog.setTime(millis);
        resultDialog.show();
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