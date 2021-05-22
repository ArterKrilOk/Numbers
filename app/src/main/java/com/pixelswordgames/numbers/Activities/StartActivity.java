package com.pixelswordgames.numbers.Activities;

import androidx.appcompat.app.AppCompatActivity;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.ValueAnimator;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.pixelswordgames.numbers.Game.GameUpdater;
import com.pixelswordgames.numbers.R;
import com.pixelswordgames.numbers.Views.BGChangingLayout;

public class StartActivity extends AppCompatActivity {

    private BGChangingLayout changingLayout;
    private GameUpdater gameUpdater;
    private ProgressBar progressBar;
    private ImageView imageView;
    private TextView textView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_start);

        gameUpdater = new GameUpdater(this);

        changingLayout = findViewById(R.id.bgLayout);
        textView = findViewById(R.id.textView);
        progressBar = findViewById(R.id.progressBar);
        imageView = findViewById(R.id.imageView);

        changingLayout.setOnAnimationEnd(this::openMainActivity);

        gameUpdater.setOnUpdateListener(new GameUpdater.OnUpdateListener() {
            @Override
            public void onProgress(int state, String message) {
                progressBar.setVisibility(View.VISIBLE);
                textView.setVisibility(View.VISIBLE);
                textView.setText(message);
            }

            @Override
            public void onUpdateFinished(boolean updated) {
                progressBar.setVisibility(View.INVISIBLE);
                textView.setVisibility(View.INVISIBLE);
                startFade();
            }
        });

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                gameUpdater.update();
            }
        }, 2000);
    }

    private void startFade(){
        ValueAnimator va = ValueAnimator.ofArgb(255, 0);
        va.setDuration(3000);
        va.addUpdateListener(animation -> {
            int v = (Integer) animation.getAnimatedValue();
            changingLayout.setBackgroundColor(Color.rgb(v,v,v));
            imageView.setAlpha(v / 255f);
        });
        va.start();

        va.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationEnd(Animator animation) {
                super.onAnimationEnd(animation);

                openMainActivity();
            }
        });
    }

    private void openMainActivity(){
        Intent intent = new Intent(StartActivity.this, MainActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(intent);
    }
}