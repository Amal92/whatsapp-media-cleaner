package com.amal.whatsapp.Activities;

import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.amal.whatsapp.Adapters.ListAdapter;
import com.amal.whatsapp.Applications.Whatyclean;
import com.amal.whatsapp.Models.StorageSize;
import com.amal.whatsapp.R;
import com.amal.whatsapp.Utils.FileNameUtils;
import com.amal.whatsapp.Utils.SharedPreferencesManager;
import com.amal.whatsapp.Utils.StorageUtil;
import com.amal.whatsapp.receivers.SetAlarmBroadcastReciever;
import com.rey.material.app.BottomSheetDialog;
import com.rey.material.widget.Switch;
import com.splunk.mint.Mint;

import java.io.File;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    private static final int REQUEST_STORAGE_PERMISSION = 0;
    private long media_size = 0;
    private boolean ifPresent = false;
    private ListAdapter itemsAdapter;
    private ArrayList<String> file_counts = new ArrayList<>();
    private ArrayList<File> imagesFilesReceived = new ArrayList<>();
    private ArrayList<File> imagesFilesSent = new ArrayList<>();
    private ArrayList<File> videoFilesReceived = new ArrayList<>();
    private ArrayList<File> videoFilesSent = new ArrayList<>();
    private ArrayList<File> audioFilesReceived = new ArrayList<>();
    private ArrayList<File> audioFilesSent = new ArrayList<>();
    private ArrayList<File> voiceFiles = new ArrayList<>();

    //Views
    private ProgressBar progressBar;
    private TextView total_media_size, no_data;
    private ImageButton nextButton;
    private ListView mListView;
    private ImageButton settingsButton;

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

       /* mListView = (ListView) findViewById(R.id.file_count_list);
        itemsAdapter = new ListAdapter(this,file_counts);
        mListView.setAdapter(itemsAdapter);*/

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
                Intent intent = new Intent(MainActivity.this, Navigation_Activity.class);
                startActivity(intent);
                finish();
            }
        });
        settingsButton = (ImageButton) findViewById(R.id.settingsButton);
        settingsButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showBottomDialog();
            }
        });
        no_data = (TextView) findViewById(R.id.no_data_text);

        Intent alarmReceiver = new Intent(this, SetAlarmBroadcastReciever.class);
        sendBroadcast(alarmReceiver);

        RequestPermissions();

    }

    private void showBottomDialog() {
        View view = getLayoutInflater().inflate(R.layout.bottom_sheet_layout, null);
        BottomSheetDialog mBottomSheetDialog = new BottomSheetDialog(this);
        mBottomSheetDialog.contentView(view)
                .heightParam(ViewGroup.LayoutParams.WRAP_CONTENT)
                .cancelable(true);
        Switch mSwitch = (Switch) view.findViewById(R.id.notification_switch);
        mSwitch.setChecked(SharedPreferencesManager.getBooleanPreference(SharedPreferencesManager.NOTIFICATION_PREFERENCE, true));
        mSwitch.setOnCheckedChangeListener(new Switch.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(Switch view, boolean checked) {
                if (checked) {
                    SharedPreferencesManager.setBooleanPreference(SharedPreferencesManager.NOTIFICATION_PREFERENCE, true);
                } else {
                    SharedPreferencesManager.setBooleanPreference(SharedPreferencesManager.NOTIFICATION_PREFERENCE, false);
                }
            }
        });
        mBottomSheetDialog.show();

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
            new scanAsyncTask().execute();
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
                    new scanAsyncTask().execute();

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
                    ifPresent = true;
                    scanImages(imageFolder);
                }

                File videoFolder = new File(whatsapp_media.getPath() + "/WhatsApp Video");
                if (videoFolder.exists() && videoFolder.isDirectory()) {
                    ifPresent = true;
                    scanVideos(videoFolder);
                }

                File audioFolder = new File(whatsapp_media.getPath() + "/WhatsApp Audio");
                if (audioFolder.exists() && audioFolder.isDirectory()) {
                    ifPresent = true;
                    scanAudio(audioFolder);
                }

                File voiceNotesFolder = new File(whatsapp_media.getPath() + "/WhatsApp Voice Notes");
                if (voiceNotesFolder.exists() && voiceNotesFolder.isDirectory()) {
                    ifPresent = true;
                    scanVoiceNotes(voiceNotesFolder);
                }

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

    private class scanAsyncTask extends AsyncTask<Void, Void, Void> {

        @Override
        protected Void doInBackground(Void... params) {
            scanFolder();
            return null;
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            progressBar.setVisibility(View.VISIBLE);
            total_media_size.setVisibility(View.GONE);
            nextButton.setEnabled(false);
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
            progressBar.setVisibility(View.GONE);
            total_media_size.setVisibility(View.VISIBLE);
            if (ifPresent) {
                no_data.setVisibility(View.GONE);
                nextButton.setEnabled(true);
                StorageSize mStorageSize = StorageUtil.convertStorageSize(media_size);
                total_media_size.setText(String.format("%.2f", mStorageSize.value) + " " + mStorageSize.suffix);
            } else {
                total_media_size.setText("---");
                no_data.setVisibility(View.VISIBLE);
            }
           /* file_counts.add("" + (imagesFilesReceived.size() + imagesFilesSent.size()));
            file_counts.add("" + (videoFilesReceived.size() + videoFilesSent.size()));
            file_counts.add("" + (audioFilesReceived.size() + audioFilesSent.size()));
            file_counts.add("" + voiceFiles.size());
            itemsAdapter.notifyDataSetChanged();*/

          /*  int tot_count = imagesFilesReceived.size() + imagesFilesSent.size() +
                    videoFilesReceived.size() + videoFilesSent.size() + audioFilesReceived.size()
                    + audioFilesSent.size() + voiceFiles.size();
            Toast.makeText(MainActivity.this, "" + tot_count, Toast.LENGTH_SHORT).show();*/
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
    }
}
