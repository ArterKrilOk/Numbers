package com.pixelswordgames.numbers;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.pixelswordgames.numbers.Views.AnimatedCharsView;

public class MainActivity extends AppCompatActivity {

    private AnimatedCharsView animatedCharsView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        animatedCharsView = findViewById(R.id.charsView);
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