package com.amal.whatsapp.Applications;

import android.app.Application;

import java.io.File;
import java.util.ArrayList;

/**
 * Created by amal on 05/05/16.
 */
public class Whatyclean extends Application {
    public static ArrayList<File> imagesFilesReceived = new ArrayList<>();
    public static ArrayList<File> imagesFilesSent = new ArrayList<>();
    public static ArrayList<File> videoFilesReceived = new ArrayList<>();
    public static ArrayList<File> videoFilesSent = new ArrayList<>();
    public static ArrayList<File> audioFilesReceived = new ArrayList<>();
    public static ArrayList<File> audioFilesSent = new ArrayList<>();
    public static ArrayList<File> voiceFiles = new ArrayList<>();
}
