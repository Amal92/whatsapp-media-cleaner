package com.amal.whatsyclean.Activities;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.format.DateUtils;
import android.view.MenuItem;
import android.view.View;
import android.widget.ProgressBar;

import com.amal.whatsyclean.Applications.Whatyclean;
import com.amal.whatsyclean.Fragments.AudioFiles_Fragment;
import com.amal.whatsyclean.Fragments.ImageFiles_Fragment;
import com.amal.whatsyclean.Fragments.VideoFiles_Fragment;
import com.amal.whatsyclean.Fragments.VoiceNotes_Fragment;
import com.amal.whatsyclean.Pojo.Media_File;
import com.amal.whatsyclean.Pojo.Sorted_Media_Files;
import com.amal.whatsyclean.R;
import com.amal.whatsyclean.Utils.Constants;
import com.ashokvarma.bottomnavigation.BottomNavigationBar;
import com.ashokvarma.bottomnavigation.BottomNavigationItem;

import org.joda.time.DateTime;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

public class Navigation_Activity extends AppCompatActivity {

    private boolean isResumed = false;

    public static ArrayList<File> imagesFilesReceived = new ArrayList<>();
    public static ArrayList<File> imagesFilesSent = new ArrayList<>();
    public static ArrayList<File> videoFilesReceived = new ArrayList<>();
    public static ArrayList<File> videoFilesSent = new ArrayList<>();
    public static ArrayList<File> audioFilesReceived = new ArrayList<>();
    public static ArrayList<File> audioFilesSent = new ArrayList<>();
    public static ArrayList<File> voiceFiles = new ArrayList<>();

    public static ArrayList<Sorted_Media_Files> sortedImageMediaFiles = new ArrayList<>();
    public static ArrayList<Sorted_Media_Files> sortedVideoMediaFiles = new ArrayList<>();
    public static ArrayList<Sorted_Media_Files> sortedAudioMediaFiles = new ArrayList<>();
    public static ArrayList<Sorted_Media_Files> sortedVoiceMediaFiles = new ArrayList<>();

    //views
    private BottomNavigationBar bottomNavigationBar;
    private FragmentTransaction transaction;
    private ProgressBar mProgressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_navigation);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        Bundle bundle = getIntent().getExtras();
        try {
            imagesFilesReceived = Whatyclean.imagesFilesReceived;
            imagesFilesSent = Whatyclean.imagesFilesSent;
            videoFilesReceived = Whatyclean.videoFilesReceived;
            videoFilesSent = Whatyclean.videoFilesSent;
            audioFilesReceived = Whatyclean.audioFilesReceived;
            audioFilesSent = Whatyclean.audioFilesSent;
            voiceFiles = Whatyclean.voiceFiles;
        } catch (Exception e) {
            e.printStackTrace();
        }
        mProgressBar = (ProgressBar) findViewById(R.id.progress_navigation);

        new processAsyncTask().execute();

    }

    private void initialiseBottomNavigationBar() {
        bottomNavigationBar = (BottomNavigationBar) findViewById(R.id.bottom_navigation_bar);
        bottomNavigationBar
                .setActiveColor(android.R.color.white)
                .setInActiveColor(R.color.lt_grey)
                .setBarBackgroundColor(R.color.colorPrimary);

        bottomNavigationBar
                .addItem(new BottomNavigationItem(R.drawable.image_icon, "Images"))
                .addItem(new BottomNavigationItem(R.drawable.video_icon, "Videos"))
                .addItem(new BottomNavigationItem(R.drawable.audio_icon, "Audios"))
                .addItem(new BottomNavigationItem(R.drawable.record_icon, "Voices"))
                .setMode(BottomNavigationBar.MODE_CLASSIC)
                .setBackgroundStyle(BottomNavigationBar.BACKGROUND_STYLE_RIPPLE)
                .initialise();

        bottomNavigationBar.setTabSelectedListener(new BottomNavigationBar.OnTabSelectedListener() {
            @Override
            public void onTabSelected(int position) {
                switch (position) {
                    case 0:
                        ImageFiles_Fragment imageFiles_fragment = new ImageFiles_Fragment();
                        transaction = getSupportFragmentManager().beginTransaction();
                        transaction.replace(R.id.fragment_container, imageFiles_fragment).commit();
                        break;
                    case 1:
                        VideoFiles_Fragment videoFiles_fragment = new VideoFiles_Fragment();
                        transaction = getSupportFragmentManager().beginTransaction();
                        transaction.replace(R.id.fragment_container, videoFiles_fragment).commit();
                        break;
                    case 2:
                        AudioFiles_Fragment audioFiles_fragment = new AudioFiles_Fragment();
                        transaction = getSupportFragmentManager().beginTransaction();
                        transaction.replace(R.id.fragment_container, audioFiles_fragment).commit();
                        break;
                    case 3:
                        VoiceNotes_Fragment voiceNotes_fragment = new VoiceNotes_Fragment();
                        transaction = getSupportFragmentManager().beginTransaction();
                        transaction.replace(R.id.fragment_container, voiceNotes_fragment).commit();
                        break;
                }
            }

            @Override
            public void onTabUnselected(int position) {

            }

            @Override
            public void onTabReselected(int position) {

            }
        });
    }

    private void processImageData() {
        sortedImageMediaFiles.clear();
        Sorted_Media_Files today_sorted_media_file = new Sorted_Media_Files();

        Calendar yesterday_calendar = Calendar.getInstance();
        yesterday_calendar.add(Calendar.DATE, -1);
        Sorted_Media_Files yesterday_sorted_media_file = new Sorted_Media_Files();

        Calendar week_calendar = Calendar.getInstance();
        week_calendar.add(Calendar.DATE, -7);
        Sorted_Media_Files week_sorted_media_file = new Sorted_Media_Files();

        Calendar month_calender = Calendar.getInstance();
        month_calender.add(Calendar.MONTH, -1);
        Sorted_Media_Files month_sorted_media_file = new Sorted_Media_Files();

        Calendar six_month_calender = Calendar.getInstance();
        six_month_calender.add(Calendar.MONTH, -6);
        Sorted_Media_Files six_month_sorted_media_file = new Sorted_Media_Files();

        Calendar year_calender = Calendar.getInstance();
        year_calender.add(Calendar.YEAR, -1);
        Sorted_Media_Files year_sorted_media_file = new Sorted_Media_Files();

        Calendar two_year_calender = Calendar.getInstance();
        two_year_calender.add(Calendar.YEAR, -2);
        Sorted_Media_Files two_year_sorted_media_file = new Sorted_Media_Files();

        Calendar five_year_calender = Calendar.getInstance();
        five_year_calender.add(Calendar.YEAR, -5);
        Sorted_Media_Files five_year_sorted_media_file = new Sorted_Media_Files();

        Calendar ten_year_calender = Calendar.getInstance();
        ten_year_calender.add(Calendar.YEAR, -10);
        Sorted_Media_Files ten_year_sorted_media_file = new Sorted_Media_Files();

        Calendar twenty_year_calender = Calendar.getInstance();
        twenty_year_calender.add(Calendar.YEAR, -20);
        Sorted_Media_Files twenty_year_sorted_media_file = new Sorted_Media_Files();

        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");

        ArrayList<File> imageArray = new ArrayList<>();
        imageArray.addAll(imagesFilesReceived);
        imageArray.addAll(imagesFilesSent);
        for (int i = 0; i < imageArray.size(); i++) {
            long millisec = imageArray.get(i).lastModified();
            DateTime file_date = new DateTime(simpleDateFormat.format(new Date(millisec)));

            if (DateUtils.isToday(file_date.getMillis())) {
                File file = imageArray.get(i);
                today_sorted_media_file.name = Constants.TIME_LINE[9];
                today_sorted_media_file.isChecked = false;
                today_sorted_media_file.size = today_sorted_media_file.size + file.length();
                Media_File media_file = new Media_File();
                media_file.file = file;
                media_file.checked = false;
                media_file.size = file.length();
                today_sorted_media_file.media_files.add(media_file);
            } else if (file_date.equals(new DateTime(simpleDateFormat.format(yesterday_calendar.getTime())))) {
                File file = imageArray.get(i);
                yesterday_sorted_media_file.name = Constants.TIME_LINE[8];
                yesterday_sorted_media_file.isChecked = false;
                yesterday_sorted_media_file.size = yesterday_sorted_media_file.size + file.length();
                Media_File media_file = new Media_File();
                media_file.file = file;
                media_file.checked = false;
                media_file.size = file.length();
                yesterday_sorted_media_file.media_files.add(media_file);
            } else if (file_date.equals(new DateTime(simpleDateFormat.format(week_calendar.getTime()))) || file_date.isAfter(new DateTime(simpleDateFormat.format(week_calendar.getTime())))) {
                File file = imageArray.get(i);
                week_sorted_media_file.name = Constants.TIME_LINE[7];
                week_sorted_media_file.isChecked = false;
                week_sorted_media_file.size = week_sorted_media_file.size + file.length();
                Media_File media_file = new Media_File();
                media_file.file = file;
                media_file.checked = false;
                media_file.size = file.length();
                week_sorted_media_file.media_files.add(media_file);
            } else if (file_date.equals(new DateTime(simpleDateFormat.format(month_calender.getTime()))) || file_date.isAfter(new DateTime(simpleDateFormat.format(month_calender.getTime())))) {
                File file = imageArray.get(i);
                month_sorted_media_file.name = Constants.TIME_LINE[6];
                month_sorted_media_file.isChecked = false;
                month_sorted_media_file.size = month_sorted_media_file.size + file.length();
                Media_File media_file = new Media_File();
                media_file.file = file;
                media_file.checked = false;
                media_file.size = file.length();
                month_sorted_media_file.media_files.add(media_file);
            } else if (file_date.equals(new DateTime(simpleDateFormat.format(six_month_calender.getTime()))) || file_date.isAfter(new DateTime(simpleDateFormat.format(six_month_calender.getTime())))) {
                File file = imageArray.get(i);
                six_month_sorted_media_file.name = Constants.TIME_LINE[5];
                six_month_sorted_media_file.isChecked = false;
                six_month_sorted_media_file.size = six_month_sorted_media_file.size + file.length();
                Media_File media_file = new Media_File();
                media_file.file = file;
                media_file.checked = false;
                media_file.size = file.length();
                six_month_sorted_media_file.media_files.add(media_file);
            } else if (file_date.equals(new DateTime(simpleDateFormat.format(year_calender.getTime()))) || file_date.isAfter(new DateTime(simpleDateFormat.format(year_calender.getTime())))) {
                File file = imageArray.get(i);
                year_sorted_media_file.name = Constants.TIME_LINE[4];
                year_sorted_media_file.isChecked = false;
                year_sorted_media_file.size = year_sorted_media_file.size + file.length();
                Media_File media_file = new Media_File();
                media_file.file = file;
                media_file.checked = false;
                media_file.size = file.length();
                year_sorted_media_file.media_files.add(media_file);
            } else if (file_date.equals(new DateTime(simpleDateFormat.format(two_year_calender.getTime()))) || file_date.isAfter(new DateTime(simpleDateFormat.format(two_year_calender.getTime())))) {
                File file = imageArray.get(i);
                two_year_sorted_media_file.name = Constants.TIME_LINE[3];
                two_year_sorted_media_file.isChecked = false;
                two_year_sorted_media_file.size = two_year_sorted_media_file.size + file.length();
                Media_File media_file = new Media_File();
                media_file.file = file;
                media_file.checked = false;
                media_file.size = file.length();
                two_year_sorted_media_file.media_files.add(media_file);
            } else if (file_date.equals(new DateTime(simpleDateFormat.format(five_year_calender.getTime()))) || file_date.isAfter(new DateTime(simpleDateFormat.format(five_year_calender.getTime())))) {
                File file = imageArray.get(i);
                five_year_sorted_media_file.name = Constants.TIME_LINE[2];
                five_year_sorted_media_file.isChecked = false;
                five_year_sorted_media_file.size = five_year_sorted_media_file.size + file.length();
                Media_File media_file = new Media_File();
                media_file.file = file;
                media_file.checked = false;
                media_file.size = file.length();
                five_year_sorted_media_file.media_files.add(media_file);
            } else if (file_date.equals(new DateTime(simpleDateFormat.format(ten_year_calender.getTime()))) || file_date.isAfter(new DateTime(simpleDateFormat.format(ten_year_calender.getTime())))) {
                File file = imageArray.get(i);
                ten_year_sorted_media_file.name = Constants.TIME_LINE[1];
                ten_year_sorted_media_file.isChecked = false;
                ten_year_sorted_media_file.size = ten_year_sorted_media_file.size + file.length();
                Media_File media_file = new Media_File();
                media_file.file = file;
                media_file.checked = false;
                media_file.size = file.length();
                ten_year_sorted_media_file.media_files.add(media_file);
            } else if (file_date.equals(new DateTime(simpleDateFormat.format(twenty_year_calender.getTime()))) || file_date.isAfter(new DateTime(simpleDateFormat.format(twenty_year_calender.getTime())))) {
                File file = imageArray.get(i);
                twenty_year_sorted_media_file.name = Constants.TIME_LINE[0];
                twenty_year_sorted_media_file.isChecked = false;
                twenty_year_sorted_media_file.size = twenty_year_sorted_media_file.size + file.length();
                Media_File media_file = new Media_File();
                media_file.file = file;
                media_file.checked = false;
                media_file.size = file.length();
                twenty_year_sorted_media_file.media_files.add(media_file);
            }

        }

        if (!twenty_year_sorted_media_file.media_files.isEmpty()) {
            sortedImageMediaFiles.add(twenty_year_sorted_media_file);
        }
        if (!ten_year_sorted_media_file.media_files.isEmpty()) {
            sortedImageMediaFiles.add(ten_year_sorted_media_file);
        }
        if (!five_year_sorted_media_file.media_files.isEmpty()) {
            sortedImageMediaFiles.add(five_year_sorted_media_file);
        }
        if (!two_year_sorted_media_file.media_files.isEmpty()) {
            sortedImageMediaFiles.add(two_year_sorted_media_file);
        }
        if (!year_sorted_media_file.media_files.isEmpty()) {
            sortedImageMediaFiles.add(year_sorted_media_file);
        }
        if (!six_month_sorted_media_file.media_files.isEmpty()) {
            sortedImageMediaFiles.add(six_month_sorted_media_file);
        }
        if (!month_sorted_media_file.media_files.isEmpty()) {
            sortedImageMediaFiles.add(month_sorted_media_file);
        }
        if (!week_sorted_media_file.media_files.isEmpty()) {
            sortedImageMediaFiles.add(week_sorted_media_file);
        }
        if (!yesterday_sorted_media_file.media_files.isEmpty()) {
            sortedImageMediaFiles.add(yesterday_sorted_media_file);
        }
        if (!today_sorted_media_file.media_files.isEmpty()) {
            sortedImageMediaFiles.add(today_sorted_media_file);
        }

        if (!sortedImageMediaFiles.isEmpty()){
            sortedImageMediaFiles.get(sortedImageMediaFiles.size()-1).isInitiallyExpanded = true;
        }

    }

    private void processVideoData() {
        sortedVideoMediaFiles.clear();
        Sorted_Media_Files today_sorted_media_file = new Sorted_Media_Files();

        Calendar yesterday_calendar = Calendar.getInstance();
        yesterday_calendar.add(Calendar.DATE, -1);
        Sorted_Media_Files yesterday_sorted_media_file = new Sorted_Media_Files();

        Calendar week_calendar = Calendar.getInstance();
        week_calendar.add(Calendar.DATE, -7);
        Sorted_Media_Files week_sorted_media_file = new Sorted_Media_Files();

        Calendar month_calender = Calendar.getInstance();
        month_calender.add(Calendar.MONTH, -1);
        Sorted_Media_Files month_sorted_media_file = new Sorted_Media_Files();

        Calendar six_month_calender = Calendar.getInstance();
        six_month_calender.add(Calendar.MONTH, -6);
        Sorted_Media_Files six_month_sorted_media_file = new Sorted_Media_Files();

        Calendar year_calender = Calendar.getInstance();
        year_calender.add(Calendar.YEAR, -1);
        Sorted_Media_Files year_sorted_media_file = new Sorted_Media_Files();

        Calendar two_year_calender = Calendar.getInstance();
        two_year_calender.add(Calendar.YEAR, -2);
        Sorted_Media_Files two_year_sorted_media_file = new Sorted_Media_Files();

        Calendar five_year_calender = Calendar.getInstance();
        five_year_calender.add(Calendar.YEAR, -5);
        Sorted_Media_Files five_year_sorted_media_file = new Sorted_Media_Files();

        Calendar ten_year_calender = Calendar.getInstance();
        ten_year_calender.add(Calendar.YEAR, -10);
        Sorted_Media_Files ten_year_sorted_media_file = new Sorted_Media_Files();

        Calendar twenty_year_calender = Calendar.getInstance();
        twenty_year_calender.add(Calendar.YEAR, -20);
        Sorted_Media_Files twenty_year_sorted_media_file = new Sorted_Media_Files();

        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");

        ArrayList<File> videoArray = new ArrayList<>();
        videoArray.addAll(videoFilesReceived);
        videoArray.addAll(videoFilesSent);
        for (int i = 0; i < videoArray.size(); i++) {
            long millisec = videoArray.get(i).lastModified();
            DateTime file_date = new DateTime(simpleDateFormat.format(new Date(millisec)));

            if (DateUtils.isToday(file_date.getMillis())) {
                File file = videoArray.get(i);
                today_sorted_media_file.name = Constants.TIME_LINE[9];
                today_sorted_media_file.isChecked = false;
                today_sorted_media_file.size = today_sorted_media_file.size + file.length();
                Media_File media_file = new Media_File();
                media_file.file = file;
                media_file.checked = false;
                media_file.size = file.length();
                today_sorted_media_file.media_files.add(media_file);
            } else if (file_date.equals(new DateTime(simpleDateFormat.format(yesterday_calendar.getTime())))) {
                File file = videoArray.get(i);
                yesterday_sorted_media_file.name = Constants.TIME_LINE[8];
                yesterday_sorted_media_file.isChecked = false;
                yesterday_sorted_media_file.size = yesterday_sorted_media_file.size + file.length();
                Media_File media_file = new Media_File();
                media_file.file = file;
                media_file.checked = false;
                media_file.size = file.length();
                yesterday_sorted_media_file.media_files.add(media_file);
            } else if (file_date.equals(new DateTime(simpleDateFormat.format(week_calendar.getTime()))) || file_date.isAfter(new DateTime(simpleDateFormat.format(week_calendar.getTime())))) {
                File file = videoArray.get(i);
                week_sorted_media_file.name = Constants.TIME_LINE[7];
                week_sorted_media_file.isChecked = false;
                week_sorted_media_file.size = week_sorted_media_file.size + file.length();
                Media_File media_file = new Media_File();
                media_file.file = file;
                media_file.checked = false;
                media_file.size = file.length();
                week_sorted_media_file.media_files.add(media_file);
            } else if (file_date.equals(new DateTime(simpleDateFormat.format(month_calender.getTime()))) || file_date.isAfter(new DateTime(simpleDateFormat.format(month_calender.getTime())))) {
                File file = videoArray.get(i);
                month_sorted_media_file.name = Constants.TIME_LINE[6];
                month_sorted_media_file.isChecked = false;
                month_sorted_media_file.size = month_sorted_media_file.size + file.length();
                Media_File media_file = new Media_File();
                media_file.file = file;
                media_file.checked = false;
                media_file.size = file.length();
                month_sorted_media_file.media_files.add(media_file);
            } else if (file_date.equals(new DateTime(simpleDateFormat.format(six_month_calender.getTime()))) || file_date.isAfter(new DateTime(simpleDateFormat.format(six_month_calender.getTime())))) {
                File file = videoArray.get(i);
                six_month_sorted_media_file.name = Constants.TIME_LINE[5];
                six_month_sorted_media_file.isChecked = false;
                six_month_sorted_media_file.size = six_month_sorted_media_file.size + file.length();
                Media_File media_file = new Media_File();
                media_file.file = file;
                media_file.checked = false;
                media_file.size = file.length();
                six_month_sorted_media_file.media_files.add(media_file);
            } else if (file_date.equals(new DateTime(simpleDateFormat.format(year_calender.getTime()))) || file_date.isAfter(new DateTime(simpleDateFormat.format(year_calender.getTime())))) {
                File file = videoArray.get(i);
                year_sorted_media_file.name = Constants.TIME_LINE[4];
                year_sorted_media_file.isChecked = false;
                year_sorted_media_file.size = year_sorted_media_file.size + file.length();
                Media_File media_file = new Media_File();
                media_file.file = file;
                media_file.checked = false;
                media_file.size = file.length();
                year_sorted_media_file.media_files.add(media_file);
            } else if (file_date.equals(new DateTime(simpleDateFormat.format(two_year_calender.getTime()))) || file_date.isAfter(new DateTime(simpleDateFormat.format(two_year_calender.getTime())))) {
                File file = videoArray.get(i);
                two_year_sorted_media_file.name = Constants.TIME_LINE[3];
                two_year_sorted_media_file.isChecked = false;
                two_year_sorted_media_file.size = two_year_sorted_media_file.size + file.length();
                Media_File media_file = new Media_File();
                media_file.file = file;
                media_file.checked = false;
                media_file.size = file.length();
                two_year_sorted_media_file.media_files.add(media_file);
            } else if (file_date.equals(new DateTime(simpleDateFormat.format(five_year_calender.getTime()))) || file_date.isAfter(new DateTime(simpleDateFormat.format(five_year_calender.getTime())))) {
                File file = videoArray.get(i);
                five_year_sorted_media_file.name = Constants.TIME_LINE[2];
                five_year_sorted_media_file.isChecked = false;
                five_year_sorted_media_file.size = five_year_sorted_media_file.size + file.length();
                Media_File media_file = new Media_File();
                media_file.file = file;
                media_file.checked = false;
                media_file.size = file.length();
                five_year_sorted_media_file.media_files.add(media_file);
            } else if (file_date.equals(new DateTime(simpleDateFormat.format(ten_year_calender.getTime()))) || file_date.isAfter(new DateTime(simpleDateFormat.format(ten_year_calender.getTime())))) {
                File file = videoArray.get(i);
                ten_year_sorted_media_file.name = Constants.TIME_LINE[1];
                ten_year_sorted_media_file.isChecked = false;
                ten_year_sorted_media_file.size = ten_year_sorted_media_file.size + file.length();
                Media_File media_file = new Media_File();
                media_file.file = file;
                media_file.checked = false;
                media_file.size = file.length();
                ten_year_sorted_media_file.media_files.add(media_file);
            } else if (file_date.equals(new DateTime(simpleDateFormat.format(twenty_year_calender.getTime()))) || file_date.isAfter(new DateTime(simpleDateFormat.format(twenty_year_calender.getTime())))) {
                File file = videoArray.get(i);
                twenty_year_sorted_media_file.name = Constants.TIME_LINE[0];
                twenty_year_sorted_media_file.isChecked = false;
                twenty_year_sorted_media_file.size = twenty_year_sorted_media_file.size + file.length();
                Media_File media_file = new Media_File();
                media_file.file = file;
                media_file.checked = false;
                media_file.size = file.length();
                twenty_year_sorted_media_file.media_files.add(media_file);
            }

        }

        if (!twenty_year_sorted_media_file.media_files.isEmpty()) {
            sortedVideoMediaFiles.add(twenty_year_sorted_media_file);
        }
        if (!ten_year_sorted_media_file.media_files.isEmpty()) {
            sortedVideoMediaFiles.add(ten_year_sorted_media_file);
        }
        if (!five_year_sorted_media_file.media_files.isEmpty()) {
            sortedVideoMediaFiles.add(five_year_sorted_media_file);
        }
        if (!two_year_sorted_media_file.media_files.isEmpty()) {
            sortedVideoMediaFiles.add(two_year_sorted_media_file);
        }
        if (!year_sorted_media_file.media_files.isEmpty()) {
            sortedVideoMediaFiles.add(year_sorted_media_file);
        }
        if (!six_month_sorted_media_file.media_files.isEmpty()) {
            sortedVideoMediaFiles.add(six_month_sorted_media_file);
        }
        if (!month_sorted_media_file.media_files.isEmpty()) {
            sortedVideoMediaFiles.add(month_sorted_media_file);
        }
        if (!week_sorted_media_file.media_files.isEmpty()) {
            sortedVideoMediaFiles.add(week_sorted_media_file);
        }
        if (!yesterday_sorted_media_file.media_files.isEmpty()) {
            sortedVideoMediaFiles.add(yesterday_sorted_media_file);
        }
        if (!today_sorted_media_file.media_files.isEmpty()) {
            sortedVideoMediaFiles.add(today_sorted_media_file);
        }

        if (!sortedVideoMediaFiles.isEmpty()){
            sortedVideoMediaFiles.get(sortedVideoMediaFiles.size()-1).isInitiallyExpanded = true;
        }

    }

    private void processAudioData() {
        sortedAudioMediaFiles.clear();
        Sorted_Media_Files today_sorted_media_file = new Sorted_Media_Files();

        Calendar yesterday_calendar = Calendar.getInstance();
        yesterday_calendar.add(Calendar.DATE, -1);
        Sorted_Media_Files yesterday_sorted_media_file = new Sorted_Media_Files();

        Calendar week_calendar = Calendar.getInstance();
        week_calendar.add(Calendar.DATE, -7);
        Sorted_Media_Files week_sorted_media_file = new Sorted_Media_Files();

        Calendar month_calender = Calendar.getInstance();
        month_calender.add(Calendar.MONTH, -1);
        Sorted_Media_Files month_sorted_media_file = new Sorted_Media_Files();

        Calendar six_month_calender = Calendar.getInstance();
        six_month_calender.add(Calendar.MONTH, -6);
        Sorted_Media_Files six_month_sorted_media_file = new Sorted_Media_Files();

        Calendar year_calender = Calendar.getInstance();
        year_calender.add(Calendar.YEAR, -1);
        Sorted_Media_Files year_sorted_media_file = new Sorted_Media_Files();

        Calendar two_year_calender = Calendar.getInstance();
        two_year_calender.add(Calendar.YEAR, -2);
        Sorted_Media_Files two_year_sorted_media_file = new Sorted_Media_Files();

        Calendar five_year_calender = Calendar.getInstance();
        five_year_calender.add(Calendar.YEAR, -5);
        Sorted_Media_Files five_year_sorted_media_file = new Sorted_Media_Files();

        Calendar ten_year_calender = Calendar.getInstance();
        ten_year_calender.add(Calendar.YEAR, -10);
        Sorted_Media_Files ten_year_sorted_media_file = new Sorted_Media_Files();

        Calendar twenty_year_calender = Calendar.getInstance();
        twenty_year_calender.add(Calendar.YEAR, -20);
        Sorted_Media_Files twenty_year_sorted_media_file = new Sorted_Media_Files();

        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");

        ArrayList<File> audioArray = new ArrayList<>();
        audioArray.addAll(audioFilesReceived);
        audioArray.addAll(audioFilesSent);
        for (int i = 0; i < audioArray.size(); i++) {
            long millisec = audioArray.get(i).lastModified();
            DateTime file_date = new DateTime(simpleDateFormat.format(new Date(millisec)));

            if (DateUtils.isToday(file_date.getMillis())) {
                File file = audioArray.get(i);
                today_sorted_media_file.name = Constants.TIME_LINE[9];
                today_sorted_media_file.isChecked = false;
                today_sorted_media_file.size = today_sorted_media_file.size + file.length();
                Media_File media_file = new Media_File();
                media_file.file = file;
                media_file.checked = false;
                media_file.size = file.length();
                today_sorted_media_file.media_files.add(media_file);
            } else if (file_date.equals(new DateTime(simpleDateFormat.format(yesterday_calendar.getTime())))) {
                File file = audioArray.get(i);
                yesterday_sorted_media_file.name = Constants.TIME_LINE[8];
                yesterday_sorted_media_file.isChecked = false;
                yesterday_sorted_media_file.size = yesterday_sorted_media_file.size + file.length();
                Media_File media_file = new Media_File();
                media_file.file = file;
                media_file.checked = false;
                media_file.size = file.length();
                yesterday_sorted_media_file.media_files.add(media_file);
            } else if (file_date.equals(new DateTime(simpleDateFormat.format(week_calendar.getTime()))) || file_date.isAfter(new DateTime(simpleDateFormat.format(week_calendar.getTime())))) {
                File file = audioArray.get(i);
                week_sorted_media_file.name = Constants.TIME_LINE[7];
                week_sorted_media_file.isChecked = false;
                week_sorted_media_file.size = week_sorted_media_file.size + file.length();
                Media_File media_file = new Media_File();
                media_file.file = file;
                media_file.checked = false;
                media_file.size = file.length();
                week_sorted_media_file.media_files.add(media_file);
            } else if (file_date.equals(new DateTime(simpleDateFormat.format(month_calender.getTime()))) || file_date.isAfter(new DateTime(simpleDateFormat.format(month_calender.getTime())))) {
                File file = audioArray.get(i);
                month_sorted_media_file.name = Constants.TIME_LINE[6];
                month_sorted_media_file.isChecked = false;
                month_sorted_media_file.size = month_sorted_media_file.size + file.length();
                Media_File media_file = new Media_File();
                media_file.file = file;
                media_file.checked = false;
                media_file.size = file.length();
                month_sorted_media_file.media_files.add(media_file);
            } else if (file_date.equals(new DateTime(simpleDateFormat.format(six_month_calender.getTime()))) || file_date.isAfter(new DateTime(simpleDateFormat.format(six_month_calender.getTime())))) {
                File file = audioArray.get(i);
                six_month_sorted_media_file.name = Constants.TIME_LINE[5];
                six_month_sorted_media_file.isChecked = false;
                six_month_sorted_media_file.size = six_month_sorted_media_file.size + file.length();
                Media_File media_file = new Media_File();
                media_file.file = file;
                media_file.checked = false;
                media_file.size = file.length();
                six_month_sorted_media_file.media_files.add(media_file);
            } else if (file_date.equals(new DateTime(simpleDateFormat.format(year_calender.getTime()))) || file_date.isAfter(new DateTime(simpleDateFormat.format(year_calender.getTime())))) {
                File file = audioArray.get(i);
                year_sorted_media_file.name = Constants.TIME_LINE[4];
                year_sorted_media_file.isChecked = false;
                year_sorted_media_file.size = year_sorted_media_file.size + file.length();
                Media_File media_file = new Media_File();
                media_file.file = file;
                media_file.checked = false;
                media_file.size = file.length();
                year_sorted_media_file.media_files.add(media_file);
            } else if (file_date.equals(new DateTime(simpleDateFormat.format(two_year_calender.getTime()))) || file_date.isAfter(new DateTime(simpleDateFormat.format(two_year_calender.getTime())))) {
                File file = audioArray.get(i);
                two_year_sorted_media_file.name = Constants.TIME_LINE[3];
                two_year_sorted_media_file.isChecked = false;
                two_year_sorted_media_file.size = two_year_sorted_media_file.size + file.length();
                Media_File media_file = new Media_File();
                media_file.file = file;
                media_file.checked = false;
                media_file.size = file.length();
                two_year_sorted_media_file.media_files.add(media_file);
            } else if (file_date.equals(new DateTime(simpleDateFormat.format(five_year_calender.getTime()))) || file_date.isAfter(new DateTime(simpleDateFormat.format(five_year_calender.getTime())))) {
                File file = audioArray.get(i);
                five_year_sorted_media_file.name = Constants.TIME_LINE[2];
                five_year_sorted_media_file.isChecked = false;
                five_year_sorted_media_file.size = five_year_sorted_media_file.size + file.length();
                Media_File media_file = new Media_File();
                media_file.file = file;
                media_file.checked = false;
                media_file.size = file.length();
                five_year_sorted_media_file.media_files.add(media_file);
            } else if (file_date.equals(new DateTime(simpleDateFormat.format(ten_year_calender.getTime()))) || file_date.isAfter(new DateTime(simpleDateFormat.format(ten_year_calender.getTime())))) {
                File file = audioArray.get(i);
                ten_year_sorted_media_file.name = Constants.TIME_LINE[1];
                ten_year_sorted_media_file.isChecked = false;
                ten_year_sorted_media_file.size = ten_year_sorted_media_file.size + file.length();
                Media_File media_file = new Media_File();
                media_file.file = file;
                media_file.checked = false;
                media_file.size = file.length();
                ten_year_sorted_media_file.media_files.add(media_file);
            } else if (file_date.equals(new DateTime(simpleDateFormat.format(twenty_year_calender.getTime()))) || file_date.isAfter(new DateTime(simpleDateFormat.format(twenty_year_calender.getTime())))) {
                File file = audioArray.get(i);
                twenty_year_sorted_media_file.name = Constants.TIME_LINE[0];
                twenty_year_sorted_media_file.isChecked = false;
                twenty_year_sorted_media_file.size = twenty_year_sorted_media_file.size + file.length();
                Media_File media_file = new Media_File();
                media_file.file = file;
                media_file.checked = false;
                media_file.size = file.length();
                twenty_year_sorted_media_file.media_files.add(media_file);
            }

        }

        if (!twenty_year_sorted_media_file.media_files.isEmpty()) {
            sortedAudioMediaFiles.add(twenty_year_sorted_media_file);
        }
        if (!ten_year_sorted_media_file.media_files.isEmpty()) {
            sortedAudioMediaFiles.add(ten_year_sorted_media_file);
        }
        if (!five_year_sorted_media_file.media_files.isEmpty()) {
            sortedAudioMediaFiles.add(five_year_sorted_media_file);
        }
        if (!two_year_sorted_media_file.media_files.isEmpty()) {
            sortedAudioMediaFiles.add(two_year_sorted_media_file);
        }
        if (!year_sorted_media_file.media_files.isEmpty()) {
            sortedAudioMediaFiles.add(year_sorted_media_file);
        }
        if (!six_month_sorted_media_file.media_files.isEmpty()) {
            sortedAudioMediaFiles.add(six_month_sorted_media_file);
        }
        if (!month_sorted_media_file.media_files.isEmpty()) {
            sortedAudioMediaFiles.add(month_sorted_media_file);
        }
        if (!week_sorted_media_file.media_files.isEmpty()) {
            sortedAudioMediaFiles.add(week_sorted_media_file);
        }
        if (!yesterday_sorted_media_file.media_files.isEmpty()) {
            sortedAudioMediaFiles.add(yesterday_sorted_media_file);
        }
        if (!today_sorted_media_file.media_files.isEmpty()) {
            sortedAudioMediaFiles.add(today_sorted_media_file);
        }

        if (!sortedAudioMediaFiles.isEmpty()){
            sortedAudioMediaFiles.get(sortedAudioMediaFiles.size()-1).isInitiallyExpanded = true;
        }

    }

    private void processVoiceData() {
        sortedVoiceMediaFiles.clear();
        Sorted_Media_Files today_sorted_media_file = new Sorted_Media_Files();

        Calendar yesterday_calendar = Calendar.getInstance();
        yesterday_calendar.add(Calendar.DATE, -1);
        Sorted_Media_Files yesterday_sorted_media_file = new Sorted_Media_Files();

        Calendar week_calendar = Calendar.getInstance();
        week_calendar.add(Calendar.DATE, -7);
        Sorted_Media_Files week_sorted_media_file = new Sorted_Media_Files();

        Calendar month_calender = Calendar.getInstance();
        month_calender.add(Calendar.MONTH, -1);
        Sorted_Media_Files month_sorted_media_file = new Sorted_Media_Files();

        Calendar six_month_calender = Calendar.getInstance();
        six_month_calender.add(Calendar.MONTH, -6);
        Sorted_Media_Files six_month_sorted_media_file = new Sorted_Media_Files();

        Calendar year_calender = Calendar.getInstance();
        year_calender.add(Calendar.YEAR, -1);
        Sorted_Media_Files year_sorted_media_file = new Sorted_Media_Files();

        Calendar two_year_calender = Calendar.getInstance();
        two_year_calender.add(Calendar.YEAR, -2);
        Sorted_Media_Files two_year_sorted_media_file = new Sorted_Media_Files();

        Calendar five_year_calender = Calendar.getInstance();
        five_year_calender.add(Calendar.YEAR, -5);
        Sorted_Media_Files five_year_sorted_media_file = new Sorted_Media_Files();

        Calendar ten_year_calender = Calendar.getInstance();
        ten_year_calender.add(Calendar.YEAR, -10);
        Sorted_Media_Files ten_year_sorted_media_file = new Sorted_Media_Files();

        Calendar twenty_year_calender = Calendar.getInstance();
        twenty_year_calender.add(Calendar.YEAR, -20);
        Sorted_Media_Files twenty_year_sorted_media_file = new Sorted_Media_Files();

        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");

        ArrayList<File> voiceArray = new ArrayList<>();
        voiceArray.addAll(voiceFiles);
        for (int i = 0; i < voiceArray.size(); i++) {
            long millisec = voiceArray.get(i).lastModified();
            DateTime file_date = new DateTime(simpleDateFormat.format(new Date(millisec)));

            if (DateUtils.isToday(file_date.getMillis())) {
                File file = voiceArray.get(i);
                today_sorted_media_file.name = Constants.TIME_LINE[9];
                today_sorted_media_file.isChecked = false;
                today_sorted_media_file.size = today_sorted_media_file.size + file.length();
                Media_File media_file = new Media_File();
                media_file.file = file;
                media_file.checked = false;
                media_file.size = file.length();
                today_sorted_media_file.media_files.add(media_file);
            } else if (file_date.equals(new DateTime(simpleDateFormat.format(yesterday_calendar.getTime())))) {
                File file = voiceArray.get(i);
                yesterday_sorted_media_file.name = Constants.TIME_LINE[8];
                yesterday_sorted_media_file.isChecked = false;
                yesterday_sorted_media_file.size = yesterday_sorted_media_file.size + file.length();
                Media_File media_file = new Media_File();
                media_file.file = file;
                media_file.checked = false;
                media_file.size = file.length();
                yesterday_sorted_media_file.media_files.add(media_file);
            } else if (file_date.equals(new DateTime(simpleDateFormat.format(week_calendar.getTime()))) || file_date.isAfter(new DateTime(simpleDateFormat.format(week_calendar.getTime())))) {
                File file = voiceArray.get(i);
                week_sorted_media_file.name = Constants.TIME_LINE[7];
                week_sorted_media_file.isChecked = false;
                week_sorted_media_file.size = week_sorted_media_file.size + file.length();
                Media_File media_file = new Media_File();
                media_file.file = file;
                media_file.checked = false;
                media_file.size = file.length();
                week_sorted_media_file.media_files.add(media_file);
            } else if (file_date.equals(new DateTime(simpleDateFormat.format(month_calender.getTime()))) || file_date.isAfter(new DateTime(simpleDateFormat.format(month_calender.getTime())))) {
                File file = voiceArray.get(i);
                month_sorted_media_file.name = Constants.TIME_LINE[6];
                month_sorted_media_file.isChecked = false;
                month_sorted_media_file.size = month_sorted_media_file.size + file.length();
                Media_File media_file = new Media_File();
                media_file.file = file;
                media_file.checked = false;
                media_file.size = file.length();
                month_sorted_media_file.media_files.add(media_file);
            } else if (file_date.equals(new DateTime(simpleDateFormat.format(six_month_calender.getTime()))) || file_date.isAfter(new DateTime(simpleDateFormat.format(six_month_calender.getTime())))) {
                File file = voiceArray.get(i);
                six_month_sorted_media_file.name = Constants.TIME_LINE[5];
                six_month_sorted_media_file.isChecked = false;
                six_month_sorted_media_file.size = six_month_sorted_media_file.size + file.length();
                Media_File media_file = new Media_File();
                media_file.file = file;
                media_file.checked = false;
                media_file.size = file.length();
                six_month_sorted_media_file.media_files.add(media_file);
            } else if (file_date.equals(new DateTime(simpleDateFormat.format(year_calender.getTime()))) || file_date.isAfter(new DateTime(simpleDateFormat.format(year_calender.getTime())))) {
                File file = voiceArray.get(i);
                year_sorted_media_file.name = Constants.TIME_LINE[4];
                year_sorted_media_file.isChecked = false;
                year_sorted_media_file.size = year_sorted_media_file.size + file.length();
                Media_File media_file = new Media_File();
                media_file.file = file;
                media_file.checked = false;
                media_file.size = file.length();
                year_sorted_media_file.media_files.add(media_file);
            } else if (file_date.equals(new DateTime(simpleDateFormat.format(two_year_calender.getTime()))) || file_date.isAfter(new DateTime(simpleDateFormat.format(two_year_calender.getTime())))) {
                File file = voiceArray.get(i);
                two_year_sorted_media_file.name = Constants.TIME_LINE[3];
                two_year_sorted_media_file.isChecked = false;
                two_year_sorted_media_file.size = two_year_sorted_media_file.size + file.length();
                Media_File media_file = new Media_File();
                media_file.file = file;
                media_file.checked = false;
                media_file.size = file.length();
                two_year_sorted_media_file.media_files.add(media_file);
            } else if (file_date.equals(new DateTime(simpleDateFormat.format(five_year_calender.getTime()))) || file_date.isAfter(new DateTime(simpleDateFormat.format(five_year_calender.getTime())))) {
                File file = voiceArray.get(i);
                five_year_sorted_media_file.name = Constants.TIME_LINE[2];
                five_year_sorted_media_file.isChecked = false;
                five_year_sorted_media_file.size = five_year_sorted_media_file.size + file.length();
                Media_File media_file = new Media_File();
                media_file.file = file;
                media_file.checked = false;
                media_file.size = file.length();
                five_year_sorted_media_file.media_files.add(media_file);
            } else if (file_date.equals(new DateTime(simpleDateFormat.format(ten_year_calender.getTime()))) || file_date.isAfter(new DateTime(simpleDateFormat.format(ten_year_calender.getTime())))) {
                File file = voiceArray.get(i);
                ten_year_sorted_media_file.name = Constants.TIME_LINE[1];
                ten_year_sorted_media_file.isChecked = false;
                ten_year_sorted_media_file.size = ten_year_sorted_media_file.size + file.length();
                Media_File media_file = new Media_File();
                media_file.file = file;
                media_file.checked = false;
                media_file.size = file.length();
                ten_year_sorted_media_file.media_files.add(media_file);
            } else if (file_date.equals(new DateTime(simpleDateFormat.format(twenty_year_calender.getTime()))) || file_date.isAfter(new DateTime(simpleDateFormat.format(twenty_year_calender.getTime())))) {
                File file = voiceArray.get(i);
                twenty_year_sorted_media_file.name = Constants.TIME_LINE[0];
                twenty_year_sorted_media_file.isChecked = false;
                twenty_year_sorted_media_file.size = twenty_year_sorted_media_file.size + file.length();
                Media_File media_file = new Media_File();
                media_file.file = file;
                media_file.checked = false;
                media_file.size = file.length();
                twenty_year_sorted_media_file.media_files.add(media_file);
            }

        }

        if (!twenty_year_sorted_media_file.media_files.isEmpty()) {
            sortedVoiceMediaFiles.add(twenty_year_sorted_media_file);
        }
        if (!ten_year_sorted_media_file.media_files.isEmpty()) {
            sortedVoiceMediaFiles.add(ten_year_sorted_media_file);
        }
        if (!five_year_sorted_media_file.media_files.isEmpty()) {
            sortedVoiceMediaFiles.add(five_year_sorted_media_file);
        }
        if (!two_year_sorted_media_file.media_files.isEmpty()) {
            sortedVoiceMediaFiles.add(two_year_sorted_media_file);
        }
        if (!year_sorted_media_file.media_files.isEmpty()) {
            sortedVoiceMediaFiles.add(year_sorted_media_file);
        }
        if (!six_month_sorted_media_file.media_files.isEmpty()) {
            sortedVoiceMediaFiles.add(six_month_sorted_media_file);
        }
        if (!month_sorted_media_file.media_files.isEmpty()) {
            sortedVoiceMediaFiles.add(month_sorted_media_file);
        }
        if (!week_sorted_media_file.media_files.isEmpty()) {
            sortedVoiceMediaFiles.add(week_sorted_media_file);
        }
        if (!yesterday_sorted_media_file.media_files.isEmpty()) {
            sortedVoiceMediaFiles.add(yesterday_sorted_media_file);
        }
        if (!today_sorted_media_file.media_files.isEmpty()) {
            sortedVoiceMediaFiles.add(today_sorted_media_file);
        }

        if (!sortedVoiceMediaFiles.isEmpty()){
            sortedVoiceMediaFiles.get(sortedVoiceMediaFiles.size()-1).isInitiallyExpanded = true;
        }

    }

private class processAsyncTask extends AsyncTask<Void,Void,Void>{

    @Override
    protected Void doInBackground(Void... params) {
        processImageData();
        processVideoData();
        processAudioData();
        processVoiceData();
        return null;
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
        mProgressBar.setVisibility(View.VISIBLE);
    }

    @Override
    protected void onPostExecute(Void aVoid) {
        super.onPostExecute(aVoid);
        mProgressBar.setVisibility(View.GONE);
        if (isResumed) {
            if (findViewById(R.id.fragment_container) != null) {

                ImageFiles_Fragment imageFiles_fragment = new ImageFiles_Fragment();
                transaction = getSupportFragmentManager().beginTransaction();
                transaction.add(R.id.fragment_container, imageFiles_fragment).commit();
            }
        }
        initialiseBottomNavigationBar();
    }
}

    @Override
    protected void onPostResume() {
        super.onPostResume();
        isResumed=true;
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    @Override
    protected void onPause() {
        super.onPause();
        isResumed=false;
    }

    @Override
    protected void onStop() {
        super.onStop();
        isResumed = false;
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        startActivity(new Intent(this,MainActivity.class));
        finish();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                // do something useful
                onBackPressed();
                return(true);
        }

        return(super.onOptionsItemSelected(item));
    }
}
