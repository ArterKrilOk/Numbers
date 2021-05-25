package com.pixelswordgames.numbers.Dialogs;

import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.view.animation.AnimationUtils;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatDialog;

import com.pixelswordgames.numbers.R;

public class ResultDialog extends AppCompatDialog {

    private boolean isWin, adsClicked;
    private long time;
    private OnDialogClosedListener onDialogClosedListener;

    public ResultDialog(Context context) {
        super(context, R.style.Theme_Numbers_Dialog);
        setCancelable(false);
    }

    public ResultDialog(Context context, int theme) {
        super(context, R.style.Theme_Numbers_Dialog);
        setCancelable(false);
    }

    public ResultDialog(Context context, boolean cancelable, OnCancelListener cancelListener) {
        super(context, cancelable, cancelListener);

        this.isWin = false;
        this.time = 0;
        setCancelable(false);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.result_dialog);

        findViewById(R.id.continueBtn).setOnClickListener(v -> {
            adsClicked = false;
            dismiss();
        });
        findViewById(R.id.adsBtn).setOnClickListener(v -> {
            adsClicked = true;
            dismiss();
        });
        setContent();
    }

    private void setContent(){
       if(findViewById(R.id.resultView) == null)
           return;

        ((TextView) findViewById(R.id.resultView)).setText(isWin? R.string.result_complete : R.string.result_failed);
        findViewById(R.id.adsBtn).setVisibility(isWin? View.GONE : View.VISIBLE);
        ((TextView) findViewById(R.id.resultTimeView)).setText(getContext().getString(R.string.result_time, String.format("%.2f", time / 1000f)));
    }

    public void setWin(boolean win) {
        isWin = win;
        setContent();
    }

    public void setTime(long time) {
        this.time = time;
        setContent();
    }

    @Override
    public void dismiss() {
        super.dismiss();

        if(onDialogClosedListener != null) {
            if (!adsClicked)
                onDialogClosedListener.onContinue(isWin);
            else onDialogClosedListener.onADs();
        }
    }

    public void setOnDialogClosedListener(OnDialogClosedListener onDialogClosedListener) {
        this.onDialogClosedListener = onDialogClosedListener;
    }

    public interface OnDialogClosedListener {
        void onContinue(boolean isWin);
        void onADs();
    }
}
