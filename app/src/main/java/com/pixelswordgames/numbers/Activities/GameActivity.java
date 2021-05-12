package com.pixelswordgames.numbers.Activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.os.Bundle;
import android.os.CancellationSignal;

import com.pixelswordgames.numbers.Dialogs.PauseDialog;
import com.pixelswordgames.numbers.R;
import com.pixelswordgames.numbers.Views.TimeBarView;

public class GameActivity extends AppCompatActivity {

    private TimeBarView timeBar;
    private PauseDialog pauseDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game);

        pauseDialog = new PauseDialog(this);

        timeBar = findViewById(R.id.timeBar);
        timeBar.setCurMillis(60000);

        pauseDialog.setOnCancelListener(dialog -> timeBar.start());

        timeBar.start();
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    @Override
    protected void onPause() {
        super.onPause();
        timeBar.stop();
        pauseDialog.show();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        timeBar.stop();
    }
}