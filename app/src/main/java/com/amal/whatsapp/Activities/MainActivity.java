package com.amal.whatsapp.Activities;

import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.amal.whatsapp.Applications.Whatyclean;
import com.amal.whatsapp.Models.StorageSize;
import com.amal.whatsapp.R;
import com.amal.whatsapp.Utils.Constants;
import com.amal.whatsapp.Utils.FileNameUtils;
import com.amal.whatsapp.Utils.StorageUtil;
import com.splunk.mint.Mint;

import java.io.File;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    private static final int REQUEST_STORAGE_PERMISSION = 0;
    private long media_size = 0;
    private ArrayList<File> imagesFilesReceived = new ArrayList<>();
    private ArrayList<File> imagesFilesSent = new ArrayList<>();
    private ArrayList<File> videoFilesReceived = new ArrayList<>();
    private ArrayList<File> videoFilesSent = new ArrayList<>();
    private ArrayList<File> audioFilesReceived = new ArrayList<>();
    private ArrayList<File> audioFilesSent = new ArrayList<>();
    private ArrayList<File> voiceFiles = new ArrayList<>();

    //Views
    private ProgressBar progressBar;
    private TextView total_media_size;
    private ImageButton nextButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Mint.initAndStartSession(MainActivity.this, "7b5724a6");
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        try {
            getSupportActionBar().hide();
        } catch (Exception e) {
            e.printStackTrace();
        }

        progressBar = (ProgressBar) findViewById(R.id.progress);
        progressBar.setIndeterminate(true);
        total_media_size = (TextView) findViewById(R.id.media_size);
        nextButton = (ImageButton) findViewById(R.id.next_button);
        nextButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Whatyclean.imagesFilesReceived = imagesFilesReceived;
                Whatyclean.imagesFilesSent = imagesFilesSent;
                Whatyclean.videoFilesReceived = videoFilesReceived;
                Whatyclean.videoFilesSent = videoFilesSent;
                Whatyclean.audioFilesReceived = audioFilesReceived;
                Whatyclean.audioFilesSent = audioFilesSent;
                Whatyclean.voiceFiles = voiceFiles;
                Intent intent = new Intent(MainActivity.this,Navigation_Activity.class);
               /* intent.putExtra(Constants.IMAGE_FILES_RECEIVED,imagesFilesReceived);
                intent.putExtra(Constants.IMAGE_FILES_SENT,imagesFilesSent);
                intent.putExtra(Constants.VIDEO_FILES_RECEIVED,videoFilesReceived);
                intent.putExtra(Constants.VIDEO_FILES_SENT,videoFilesSent);
                intent.putExtra(Constants.AUDIO_FILES_RECEIVED,audioFilesReceived);
                intent.putExtra(Constants.AUDIO_FILES_SENT,audioFilesSent);
                intent.putExtra(Constants.VOICE_FILES_RECEIVED,voiceFiles);*/

                startActivity(intent);
            }
        });

        RequestPermissions();

    }

    private void RequestPermissions() {
        if (ContextCompat.checkSelfPermission(this,
                "android.permission.READ_EXTERNAL_STORAGE")
                != PackageManager.PERMISSION_GRANTED) {

            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                requestPermissions(
                        new String[]{"android.permission.READ_EXTERNAL_STORAGE", "android.permission.WRITE_EXTERNAL_STORAGE"},
                        REQUEST_STORAGE_PERMISSION);
            }

        } else {
            //permission already granted
            scanFolder();
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        switch (requestCode) {
            case REQUEST_STORAGE_PERMISSION: {
                // If request is cancelled, the result arrays are empty.
                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {

                    // permission is granted, yay!
                    scanFolder();

                } else {

                    // permission denied, boo! Disable the
                    // functionality that depends on this permission.
                }
                return;
            }

            // other 'case' lines to check for other
            // permissions this app might request
        }
    }

    private void scanFolder() {

        File whatsapp_root = new File(Environment.getExternalStorageDirectory() + "/WhatsApp");
        if (whatsapp_root.exists() && whatsapp_root.isDirectory()) {
            File whatsapp_media = new File(whatsapp_root.getPath() + "/Media");
            if (whatsapp_media.exists() && whatsapp_media.isDirectory()) {

                File imageFolder = new File(whatsapp_media.getPath() + "/WhatsApp Images");
                if (imageFolder.exists() && imageFolder.isDirectory()) {
                    scanImages(imageFolder);
                }

                File videoFolder = new File(whatsapp_media.getPath() + "/WhatsApp Video");
                if (videoFolder.exists() && videoFolder.isDirectory()) {
                    scanVideos(videoFolder);
                }

                File audioFolder = new File(whatsapp_media.getPath() + "/WhatsApp Audio");
                if (audioFolder.exists() && audioFolder.isDirectory()) {
                    scanAudio(audioFolder);
                }

                File voiceNotesFolder = new File(whatsapp_media.getPath() + "/WhatsApp Audio");
                if (voiceNotesFolder.exists() && voiceNotesFolder.isDirectory()) {
                    scanVoiceNotes(voiceNotesFolder);
                }

                StorageSize mStorageSize = StorageUtil.convertStorageSize(media_size);
                progressBar.setVisibility(View.GONE);
                total_media_size.setVisibility(View.VISIBLE);
                total_media_size.setText(String.format("%.2f", mStorageSize.value) + " " + mStorageSize.suffix);
                int tot_count = imagesFilesReceived.size() + imagesFilesSent.size() +
                        videoFilesReceived.size() + videoFilesSent.size() + audioFilesReceived.size()
                        + audioFilesSent.size() + voiceFiles.size();
                Toast.makeText(this, "" + tot_count, Toast.LENGTH_SHORT).show();
            }
        }
    }

    private void scanImages(File folder) {
        File[] receivedImages = folder.listFiles();
        for (int i = 0; i < receivedImages.length; i++) {
            if (!receivedImages[i].isDirectory() && (FileNameUtils.getExtension(receivedImages[i]).equals("jpg") ||
                    FileNameUtils.getExtension(receivedImages[i]).equals("jpeg"))) {
                media_size = media_size + receivedImages[i].length();
                imagesFilesReceived.add(receivedImages[i]);
            }
        }
        // accessing sent images
        File sentImageFolder = new File(folder.getPath() + "/Sent");
        if (sentImageFolder.exists() && sentImageFolder.isDirectory()) {
            File[] sentImages = sentImageFolder.listFiles();
            for (int i = 0; i < sentImages.length; i++) {
                if (!sentImages[i].isDirectory() && (FileNameUtils.getExtension(sentImages[i]).equals("jpg") ||
                        FileNameUtils.getExtension(sentImages[i]).equals("jpeg"))) {
                    media_size = media_size + sentImages[i].length();
                    imagesFilesSent.add(sentImages[i]);
                }
            }
        }

    }

    private void scanVideos(File folder) {
        File[] receivedVideos = folder.listFiles();
        for (int i = 0; i < receivedVideos.length; i++) {
            if (!receivedVideos[i].isDirectory() && FileNameUtils.getExtension(receivedVideos[i]).equals("mp4")) {
                media_size = media_size + receivedVideos[i].length();
                videoFilesReceived.add(receivedVideos[i]);
            }
        }
        // accessing sent videos
        File sentVideoFolder = new File(folder.getPath() + "/Sent");
        if (sentVideoFolder.exists() && sentVideoFolder.isDirectory()) {
            File[] sentVideos = sentVideoFolder.listFiles();
            for (int i = 0; i < sentVideos.length; i++) {
                if (!sentVideos[i].isDirectory() && FileNameUtils.getExtension(sentVideos[i]).equals("mp4")) {
                    media_size = media_size + sentVideos[i].length();
                    videoFilesSent.add(sentVideos[i]);
                }
            }
        }

    }

    private void scanAudio(File folder) {
        File[] receivedAudios = folder.listFiles();
        for (int i = 0; i < receivedAudios.length; i++) {
            if (!receivedAudios[i].isDirectory() && (FileNameUtils.getExtension(receivedAudios[i]).equals("aac") ||
                    FileNameUtils.getExtension(receivedAudios[i]).equals("mp3") ||
                    FileNameUtils.getExtension(receivedAudios[i]).equals("m4a") ||
                    FileNameUtils.getExtension(receivedAudios[i]).equals("amr") ||
                    FileNameUtils.getExtension(receivedAudios[i]).equals("wav") ||
                    FileNameUtils.getExtension(receivedAudios[i]).equals("ogg") ||
                    FileNameUtils.getExtension(receivedAudios[i]).equals("tta") ||
                    FileNameUtils.getExtension(receivedAudios[i]).equals("wma") ||
                    FileNameUtils.getExtension(receivedAudios[i]).equals("m4p") ||
                    FileNameUtils.getExtension(receivedAudios[i]).equals("opus")
            )) {
                media_size = media_size + receivedAudios[i].length();
                audioFilesReceived.add(receivedAudios[i]);
            }
        }
        // accessing sent audio
        File sentAudioFolder = new File(folder.getPath() + "/Sent");
        if (sentAudioFolder.exists() && sentAudioFolder.isDirectory()) {
            File[] sentAudios = sentAudioFolder.listFiles();
            for (int i = 0; i < sentAudios.length; i++) {
                if (!sentAudios[i].isDirectory() && (FileNameUtils.getExtension(sentAudios[i]).equals("aac") ||
                        FileNameUtils.getExtension(sentAudios[i]).equals("mp3") ||
                        FileNameUtils.getExtension(sentAudios[i]).equals("m4a") ||
                        FileNameUtils.getExtension(sentAudios[i]).equals("amr") ||
                        FileNameUtils.getExtension(sentAudios[i]).equals("wav") ||
                        FileNameUtils.getExtension(sentAudios[i]).equals("ogg") ||
                        FileNameUtils.getExtension(sentAudios[i]).equals("tta") ||
                        FileNameUtils.getExtension(sentAudios[i]).equals("wma") ||
                        FileNameUtils.getExtension(sentAudios[i]).equals("m4p") ||
                        FileNameUtils.getExtension(sentAudios[i]).equals("opus")
                )) {
                    media_size = media_size + sentAudios[i].length();
                    audioFilesSent.add(sentAudios[i]);
                }
            }
        }

    }

    private void scanVoiceNotes(File folder) {
        File[] receivedvoiceNotes = folder.listFiles();
        for (int i = 0; i < receivedvoiceNotes.length; i++) {
            if (!receivedvoiceNotes[i].isDirectory() && (FileNameUtils.getExtension(receivedvoiceNotes[i]).equals("aac") ||
                    FileNameUtils.getExtension(receivedvoiceNotes[i]).equals("mp3") ||
                    FileNameUtils.getExtension(receivedvoiceNotes[i]).equals("m4a") ||
                    FileNameUtils.getExtension(receivedvoiceNotes[i]).equals("amr") ||
                    FileNameUtils.getExtension(receivedvoiceNotes[i]).equals("wav") ||
                    FileNameUtils.getExtension(receivedvoiceNotes[i]).equals("ogg") ||
                    FileNameUtils.getExtension(receivedvoiceNotes[i]).equals("tta") ||
                    FileNameUtils.getExtension(receivedvoiceNotes[i]).equals("wma") ||
                    FileNameUtils.getExtension(receivedvoiceNotes[i]).equals("m4p") ||
                    FileNameUtils.getExtension(receivedvoiceNotes[i]).equals("opus")
            )) {
                media_size = media_size + receivedvoiceNotes[i].length();
                voiceFiles.add(receivedvoiceNotes[i]);
            } else {
                if (receivedvoiceNotes[i].isDirectory()) {
                    File[] subReceivedvoiceNotes = receivedvoiceNotes[i].listFiles();
                    for (int j = 0; j < subReceivedvoiceNotes.length; j++) {
                        if (!subReceivedvoiceNotes[j].isDirectory() && (FileNameUtils.getExtension(subReceivedvoiceNotes[j]).equals("aac") ||
                                FileNameUtils.getExtension(subReceivedvoiceNotes[j]).equals("mp3") ||
                                FileNameUtils.getExtension(subReceivedvoiceNotes[j]).equals("m4a") ||
                                FileNameUtils.getExtension(subReceivedvoiceNotes[j]).equals("amr") ||
                                FileNameUtils.getExtension(subReceivedvoiceNotes[j]).equals("wav") ||
                                FileNameUtils.getExtension(subReceivedvoiceNotes[j]).equals("ogg") ||
                                FileNameUtils.getExtension(subReceivedvoiceNotes[j]).equals("tta") ||
                                FileNameUtils.getExtension(subReceivedvoiceNotes[j]).equals("wma") ||
                                FileNameUtils.getExtension(subReceivedvoiceNotes[j]).equals("m4p") ||
                                FileNameUtils.getExtension(subReceivedvoiceNotes[j]).equals("opus")
                        )) {
                            media_size = media_size + subReceivedvoiceNotes[j].length();
                            voiceFiles.add(subReceivedvoiceNotes[j]);
                        }
                    }
                }
            }
        }

    }

}
