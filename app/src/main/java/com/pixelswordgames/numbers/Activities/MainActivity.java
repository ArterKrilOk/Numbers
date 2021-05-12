package com.pixelswordgames.numbers.Activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;

import com.pixelswordgames.numbers.R;
import com.pixelswordgames.numbers.Views.AnimatedCharsView;

public class MainActivity extends AppCompatActivity {

    private AnimatedCharsView animatedCharsView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        animatedCharsView = findViewById(R.id.charsView);

        findViewById(R.id.playBtn).setOnClickListener(v -> startGame());
    }

    private void startGame(){
        Intent intent = new Intent(MainActivity.this, GameActivity.class);
        startActivity(intent);
    }

    @Override
    protected void onResume() {
        super.onResume();

        animatedCharsView.start();
    }

    @Override
    protected void onPause() {
        super.onPause();

        animatedCharsView.stop();
    }
}