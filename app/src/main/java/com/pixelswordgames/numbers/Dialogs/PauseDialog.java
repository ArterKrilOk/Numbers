package com.pixelswordgames.numbers.Dialogs;

import android.content.Context;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatDialog;
import androidx.core.app.DialogCompat;

import com.pixelswordgames.numbers.R;
import com.pixelswordgames.numbers.Views.ClickableTextView;

public class PauseDialog extends AppCompatDialog {

    public PauseDialog(Context context) {
        super(context);
    }

    public PauseDialog(Context context, int theme) {
        super(context, theme);
    }

    protected PauseDialog(Context context, boolean cancelable, OnCancelListener cancelListener) {
        super(context, false, cancelListener);
    }

    protected PauseDialog(Context context, OnCancelListener cancelListener) {
        super(context, false, cancelListener);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.pause_dialog);

        ClickableTextView playBtn = findViewById(R.id.playBtn);
        ClickableTextView exitBtn = findViewById(R.id.exitBtn);

        /*playBtn.setOnClickListener(v -> cancel());
        exitBtn.setOnClickListener(v -> getOwnerActivity().finish());*/
    }
}
