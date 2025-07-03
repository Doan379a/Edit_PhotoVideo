package com.videomaker.photovideo.editvideo.libffmpeg;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Bitmap.Config;
import android.graphics.BitmapFactory;
import android.graphics.BitmapFactory.Options;
import android.os.Environment;
import android.util.Log;


import com.videomaker.photovideo.editvideo.ui.editmovie.TokanData.Glob;

import java.io.BufferedInputStream;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.OutputStream;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Formatter;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

public class FileUtils {

    public static File APP_DIRECTORY;
    public static File TEMP_DIRECTORY;
    public static File TEMP_DIRECTORY_AUDIO;
    public static File TEMP_VID_DIRECTORY;
    public static File frameFile;
    public static long mDeleteFileCount = 0;

    // Gọi 1 lần trong Application hoặc MainActivity.onCreate()
    public static void init(Context context) {
        File baseDir = new File(context.getExternalFilesDir(null), Glob.app_name);  // "Photo_Video_Edit"
        APP_DIRECTORY = baseDir;
        TEMP_DIRECTORY = new File(APP_DIRECTORY, ".temp");
        TEMP_DIRECTORY_AUDIO = new File(APP_DIRECTORY, ".temp_audio");
        TEMP_VID_DIRECTORY = new File(TEMP_DIRECTORY, ".temp_vid");
        frameFile = new File(APP_DIRECTORY, ".frame.png");

        if (!TEMP_DIRECTORY.exists()) TEMP_DIRECTORY.mkdirs();
        if (!TEMP_VID_DIRECTORY.exists()) TEMP_VID_DIRECTORY.mkdirs();
    }

    public static File getImageDirectory(String theme) {
        File imageDir = new File(TEMP_DIRECTORY, theme);
        if (!imageDir.exists()) imageDir.mkdirs();
        return imageDir;
    }

    public static boolean deleteThemeDir(String theme) {
        return deleteFile(getImageDirectory(theme));
    }

    public static void deleteTempDir() {
        if (TEMP_DIRECTORY != null && TEMP_DIRECTORY.exists()) {
            File[] children = TEMP_DIRECTORY.listFiles();
            if (children != null) {
                for (File child : children) {
                    new Thread(() -> {
                        if (child.isDirectory()) {
                            deleteFile(child);
                        } else {
                            child.delete();
                        }
                    }).start();
                }
            }
        }
    }

    public static boolean deleteFile(File mFile) {
        if (mFile == null || !mFile.exists()) return false;
        if (mFile.isDirectory()) {
            File[] children = mFile.listFiles();
            if (children != null) {
                for (File child : children) {
                    mDeleteFileCount += child.length();
                    deleteFile(child);
                }
            }
        }
        mDeleteFileCount += mFile.length();
        return mFile.delete();
    }

    public static String getDuration(long milliseconds) {
        String format = "";
        String secondsString = "";
        String minutesString = "";
        int hours = (int) (milliseconds / 3600000);
        int minutes = ((int) (milliseconds % 3600000)) / 60000;
        int seconds = (int) (((milliseconds % 3600000) % 60000) / 1000);
        if (hours > 0) {
            format = hours + ":";
        }
        if (minutes < 10) {
            minutesString = "0" + minutes;
        } else {
            minutesString = "" + minutes;
        }
        if (seconds < 10) {
            secondsString = "0" + seconds;
        } else {
            secondsString = "" + seconds;
        }
        return format + minutesString + ":" + secondsString;
    }

}
