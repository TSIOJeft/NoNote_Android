package com.farplace.nonote.data;

import android.content.Context;

public class MainData {
    public static String workFolder;

    public void init(Context context) {
        workFolder = context.getExternalFilesDir(null).toString();
    }
}
