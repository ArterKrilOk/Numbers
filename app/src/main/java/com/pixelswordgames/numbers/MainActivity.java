package com.pixelswordgames.numbers;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;

import com.pixelswordgames.numbers.Activities.GameActivity;
import com.pixelswordgames.numbers.Views.AnimatedCharsView;

public class MainActivity extends AppCompatActivity {

    private AnimatedCharsView animatedCharsView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        animatedCharsView = findViewById(R.id.charsView);

        findViewById(R.id.playBtn).setOnClickListener(v -> startGame(1));
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

    private void startGame(int lvl){
        Intent intent = new Intent(MainActivity.this, GameActivity.class);
        intent.putExtra("lvl", lvl);
        startActivity(intent);
    }
}