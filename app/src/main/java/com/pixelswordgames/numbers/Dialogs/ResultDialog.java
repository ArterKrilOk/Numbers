package com.pixelswordgames.numbers.Dialogs;

import android.content.Context;
import android.os.Bundle;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatDialog;

import com.pixelswordgames.numbers.R;

public class ResultDialog extends AppCompatDialog {

    private final boolean isWin;
    private final long time;

    public ResultDialog(Context context, boolean isWin, long time) {
        super(context);
        this.isWin = isWin;
        this.time = time;
    }

    public ResultDialog(Context context, int theme) {
        super(context, theme);

        this.isWin = false;
        this.time = 0;
    }

    public ResultDialog(Context context, boolean cancelable, OnCancelListener cancelListener) {
        super(context, cancelable, cancelListener);

        this.isWin = false;
        this.time = 0;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.result_dialog);

        findViewById(R.id.continueBtn).setOnClickListener(v -> dismiss());
        ((TextView) findViewById(R.id.resultView)).setText(isWin? R.string.result_complete : R.string.result_failed);
        ((TextView) findViewById(R.id.textView)).setText(getContext().getString(R.string.result_time, time / 1000f));
    }

}
