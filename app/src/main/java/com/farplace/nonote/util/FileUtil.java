package com.farplace.nonote.util;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

public class FileUtil {
    public static String loadTextFile(String path) {
        try {
            BufferedReader bufferedReader = new BufferedReader(new FileReader(path));
            String line = "";
            StringBuilder stringBuilder = new StringBuilder(line);
            while ((line = bufferedReader.readLine()) != null) {
                stringBuilder.append(line).append("\n");
            }
            bufferedReader.close();
            return stringBuilder.toString();
        } catch (IOException e) {
            return "";
        }
    }

    public static void saveTextFile(String path, String content) {
        try {
            File parentFile = new File(path).getParentFile();
            if (parentFile == null || !parentFile.exists()) parentFile.mkdirs();

            BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter(path));
            bufferedWriter.write(content);
            bufferedWriter.flush();
            bufferedWriter.close();
        } catch (IOException e) {
            //ignore
        }
    }
}
