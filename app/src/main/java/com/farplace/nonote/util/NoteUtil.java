package com.farplace.nonote.util;

import com.farplace.nonote.data.MainData;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class NoteUtil {
    public List<File> getNotes() {
        File file = new File(MainData.workFolder + File.separator + "note");
        File[] notes = file.listFiles();
        if (notes != null) {
            return Arrays.asList(notes);
        } else {
            return new ArrayList<>();
        }
    }

    public static String getNoteName(String fileName) {
        if (fileName.contains(".")) {
            return fileName.substring(0, fileName.lastIndexOf("."));
        } else {
            return fileName;
        }
    }
}
