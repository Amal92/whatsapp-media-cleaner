package com.amal.whatsyclean.receivers;

import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Environment;
import android.support.v4.app.NotificationCompat;
import android.support.v4.app.TaskStackBuilder;
import android.support.v4.content.ContextCompat;
import android.text.format.DateUtils;
import android.widget.RemoteViews;

import com.amal.whatsyclean.Activities.MainActivity;
import com.amal.whatsyclean.Models.StorageSize;
import com.amal.whatsyclean.R;
import com.amal.whatsyclean.Utils.FileNameUtils;
import com.amal.whatsyclean.Utils.SharedPreferencesManager;
import com.amal.whatsyclean.Utils.StorageUtil;

import java.io.File;
import java.util.ArrayList;

/**
 * Created by amal on 21/06/16.
 */
public class ReceiverService extends BroadcastReceiver {

    private ArrayList<File> imagesFilesReceived = new ArrayList<>();
    private ArrayList<File> imagesFilesSent = new ArrayList<>();
    private ArrayList<File> videoFilesReceived = new ArrayList<>();
    private ArrayList<File> videoFilesSent = new ArrayList<>();
    private ArrayList<File> audioFilesReceived = new ArrayList<>();
    private ArrayList<File> audioFilesSent = new ArrayList<>();
    private ArrayList<File> voiceFiles = new ArrayList<>();
    private long media_size = 0;
    private long today_media_size = 0;
    private int today_count = 0;
    private Context mContext;

    private void scanFolder() {

        init();

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

                File voiceNotesFolder = new File(whatsapp_media.getPath() + "/WhatsApp Voice Notes");
                if (voiceNotesFolder.exists() && voiceNotesFolder.isDirectory()) {
                    scanVoiceNotes(voiceNotesFolder);
                }

            }
        }
    }

    private void init() {
        today_count = 0;
        today_media_size = 0;
        media_size = 0;
        imagesFilesSent.clear();
        imagesFilesReceived.clear();
        videoFilesSent.clear();
        videoFilesReceived.clear();
        audioFilesSent.clear();
        audioFilesReceived.clear();
        voiceFiles.clear();
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

    private void todayCountSize() {
        for (int i = 0; i < imagesFilesReceived.size(); i++) {
            if (DateUtils.isToday(imagesFilesReceived.get(i).lastModified())) {
                today_count++;
                today_media_size = today_media_size + imagesFilesReceived.get(i).length();
            }
        }
        for (int i = 0; i < imagesFilesSent.size(); i++) {
            if (DateUtils.isToday(imagesFilesSent.get(i).lastModified())) {
                today_count++;
                today_media_size = today_media_size + imagesFilesSent.get(i).length();
            }
        }
        for (int i = 0; i < videoFilesReceived.size(); i++) {
            if (DateUtils.isToday(videoFilesReceived.get(i).lastModified())) {
                today_count++;
                today_media_size = today_media_size + videoFilesReceived.get(i).length();
            }
        }
        for (int i = 0; i < videoFilesSent.size(); i++) {
            if (DateUtils.isToday(videoFilesSent.get(i).lastModified())) {
                today_count++;
                today_media_size = today_media_size + videoFilesSent.get(i).length();
            }
        }
        for (int i = 0; i < audioFilesReceived.size(); i++) {
            if (DateUtils.isToday(audioFilesReceived.get(i).lastModified())) {
                today_count++;
                today_media_size = today_media_size + audioFilesReceived.get(i).length();
            }
        }
        for (int i = 0; i < audioFilesSent.size(); i++) {
            if (DateUtils.isToday(audioFilesSent.get(i).lastModified())) {
                today_count++;
                today_media_size = today_media_size + audioFilesSent.get(i).length();
            }
        }
        for (int i = 0; i < voiceFiles.size(); i++) {
            if (DateUtils.isToday(voiceFiles.get(i).lastModified())) {
                today_count++;
                today_media_size = today_media_size + voiceFiles.get(i).length();
            }
        }

        if (today_count != 0) {
            generateNotification();
        }
    }

    private void generateNotification() {
        NotificationManager mNotificationManager = (NotificationManager) mContext.getSystemService(Context.NOTIFICATION_SERVICE);


        RemoteViews remoteViews = new RemoteViews(mContext.getPackageName(),
                R.layout.custom_notification_layout);

        Intent notificationIntent = new Intent(mContext, MainActivity.class);
        NotificationCompat.Builder builder = new NotificationCompat.Builder(mContext)
                // Set Icon
                .setSmallIcon(getNotificationIcon())
                // Set Ticker Message
                // Dismiss Notification
                .setAutoCancel(true)
                // Set PendingIntent into Notification
//                .setContentIntent(notificationIntent)
                // Set RemoteViews into Notification
                .setContent(remoteViews)
                .setPriority(NotificationCompat.PRIORITY_MAX)
                .setColor(Color.parseColor("#ffffff"));


        TaskStackBuilder taskStackBuilder = TaskStackBuilder.create(mContext);
        taskStackBuilder.addParentStack(MainActivity.class);
        taskStackBuilder.addNextIntent(notificationIntent);
        PendingIntent contentIntent = taskStackBuilder.getPendingIntent(987654, PendingIntent.FLAG_UPDATE_CURRENT);
        builder.setContentIntent(contentIntent);


        // Locate and set the Image into customnotificationtext.xml ImageViews
        remoteViews.setImageViewResource(R.id.iv_custom, R.drawable.ic_notification);
        StorageSize mStorageSize = StorageUtil.convertStorageSize(today_media_size);
        remoteViews.setTextViewText(R.id.text_msg, "Whatsapp has downloaded " + today_count + " files consuming " + String.format("%.2f", mStorageSize.value) + mStorageSize.suffix + " today");
        mNotificationManager.notify(1, builder.build());
    }

    private int getNotificationIcon() {
        boolean marshmallow = (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP);
        return marshmallow ? R.drawable.ic_notification : R.drawable.ic_notification;
    }

    @Override
    public void onReceive(Context context, Intent intent) {
        mContext = context;
        if (SharedPreferencesManager.getBooleanPreference(SharedPreferencesManager.NOTIFICATION_PREFERENCE, true))
            new scanFolderAsyncTask().execute();
    }

    private class scanFolderAsyncTask extends AsyncTask<Void, Void, Void> {

        @Override
        protected Void doInBackground(Void... params) {
            scanFolder();
            return null;
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            if (ContextCompat.checkSelfPermission(mContext.getApplicationContext(),
                    "android.permission.READ_EXTERNAL_STORAGE")
                    != PackageManager.PERMISSION_GRANTED) {
                this.cancel(true);
            }
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
            todayCountSize();
        }
    }

}
