package com.example.editphotovideo.utils.helper;


import android.Manifest;
import android.os.Build;

public class Default {
    //about app
    public static final String EMAIL = "tranduyhung171199@gmail.com";
    public static final String SUBJECT = "Feedback: Funny Voice Changer";
    public static final String PRIVACY_POLICY = "https://sites.google.com/view/funny-voice-changer-onet/home";

    public static final String[] PHONE_PERMISSIONS = new String[]{Manifest.permission.READ_CONTACTS};////xin mọi lơi

    public static final String[] SMS_PERMISSIONS = new String[]{Manifest.permission.RECEIVE_SMS};
    public static final String[] POST_NOTIFICATIONS_PERMISSION = Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU?new String[]{ Manifest.permission.POST_NOTIFICATIONS } : new String[]{};
}
