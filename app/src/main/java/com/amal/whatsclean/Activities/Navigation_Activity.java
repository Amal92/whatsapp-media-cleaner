package com.amal.whatsclean.Activities;

import android.os.Bundle;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.widget.Toast;

import com.amal.whatsclean.Fragments.AudioFiles_Fragment;
import com.amal.whatsclean.Fragments.ImageFiles_Fragment;
import com.amal.whatsclean.Fragments.VideoFiles_Fragment;
import com.amal.whatsclean.Fragments.VoiceNotes_Fragment;
import com.amal.whatsclean.R;
import com.amal.whatsclean.Utils.Constants;
import com.ashokvarma.bottomnavigation.BottomNavigationBar;
import com.ashokvarma.bottomnavigation.BottomNavigationItem;

import java.io.File;
import java.util.ArrayList;

public class Navigation_Activity extends AppCompatActivity {

    public static ArrayList<File> imagesFilesReceived = new ArrayList<>();
    public static ArrayList<File> imagesFilesSent = new ArrayList<>();
    public static ArrayList<File> videoFilesReceived = new ArrayList<>();
    public static ArrayList<File> videoFilesSent = new ArrayList<>();
    public static ArrayList<File> audioFilesReceived = new ArrayList<>();
    public static ArrayList<File> audioFilesSent = new ArrayList<>();
    public static ArrayList<File> voiceFiles = new ArrayList<>();

    private BottomNavigationBar bottomNavigationBar;
    private FragmentTransaction transaction;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_navigation);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        Bundle bundle = getIntent().getExtras();
        try {
            imagesFilesReceived = (ArrayList<File>) bundle.get(Constants.IMAGE_FILES_RECEIVED);
            imagesFilesSent = (ArrayList<File>) bundle.get(Constants.IMAGE_FILES_SENT);
            videoFilesReceived = (ArrayList<File>) bundle.get(Constants.VIDEO_FILES_RECEIVED);
            videoFilesSent = (ArrayList<File>) bundle.get(Constants.VIDEO_FILES_SENT);
            audioFilesReceived = (ArrayList<File>) bundle.get(Constants.AUDIO_FILES_RECEIVED);
            audioFilesSent = (ArrayList<File>) bundle.get(Constants.AUDIO_FILES_SENT);
            voiceFiles = (ArrayList<File>) bundle.get(Constants.VOICE_FILES_RECEIVED);
        } catch (Exception e) {
            e.printStackTrace();
        }
        initialiseBottomNavigationBar();
        if (findViewById(R.id.fragment_container) != null) {

            if (savedInstanceState != null) {
                return;
            }
            ImageFiles_Fragment imageFiles_fragment = new ImageFiles_Fragment();
            transaction = getSupportFragmentManager().beginTransaction();
            transaction.add(R.id.fragment_container, imageFiles_fragment).commit();
        }



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
                .addItem(new BottomNavigationItem(R.drawable.record_icon, "Voice Notes"))
                .setMode(BottomNavigationBar.MODE_CLASSIC)
                .setBackgroundStyle(BottomNavigationBar.BACKGROUND_STYLE_RIPPLE)
                .initialise();

        bottomNavigationBar.setTabSelectedListener(new BottomNavigationBar.OnTabSelectedListener() {
            @Override
            public void onTabSelected(int position) {
                switch (position){
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

}
