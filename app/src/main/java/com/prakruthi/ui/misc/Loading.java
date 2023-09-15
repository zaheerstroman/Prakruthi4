package com.prakruthi.ui.misc;

import android.app.ProgressDialog;
import android.content.Context;

import com.prakruthi.ui.Login;

public class Loading {
    private static ProgressDialog progress;

    public static void show(Context context) {
        if (progress == null) {
            progress = new ProgressDialog(context);
            progress.setTitle("Loading");
            progress.setMessage("Please Wait...");
            progress.setProgressStyle(ProgressDialog.STYLE_SPINNER);
            progress.setIndeterminate(true);
            progress.setCancelable(false);
        }
        progress.show();
    }

    public static void hide() {
        if (progress != null) {
            progress.dismiss();
            progress = null;
        }
    }
}
