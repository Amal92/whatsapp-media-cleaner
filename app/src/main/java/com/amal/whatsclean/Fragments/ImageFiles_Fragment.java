package com.amal.whatsclean.Fragments;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.format.DateUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.amal.whatsclean.Activities.Navigation_Activity;
import com.amal.whatsclean.Pojo.Media_File;
import com.amal.whatsclean.Pojo.Sorted_Media_Files;
import com.amal.whatsclean.R;
import com.amal.whatsclean.Utils.Constants;

import org.joda.time.DateTime;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

/**
 * A simple {@link Fragment} subclass.
 */
public class ImageFiles_Fragment extends Fragment {

    private ArrayList<Sorted_Media_Files> sortedMediaFiles = new ArrayList<>();

    public ImageFiles_Fragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_image_files_, container, false);
        processData();


        return view;
    }

    private void processData() {
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
        imageArray.addAll(Navigation_Activity.imagesFilesReceived);
        imageArray.addAll(Navigation_Activity.imagesFilesSent);
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
            sortedMediaFiles.add(twenty_year_sorted_media_file);
        }
        if (!ten_year_sorted_media_file.media_files.isEmpty()) {
            sortedMediaFiles.add(ten_year_sorted_media_file);
        }
        if (!five_year_sorted_media_file.media_files.isEmpty()) {
            sortedMediaFiles.add(five_year_sorted_media_file);
        }
        if (!two_year_sorted_media_file.media_files.isEmpty()) {
            sortedMediaFiles.add(two_year_sorted_media_file);
        }
        if (!year_sorted_media_file.media_files.isEmpty()) {
            sortedMediaFiles.add(year_sorted_media_file);
        }
        if (!six_month_sorted_media_file.media_files.isEmpty()) {
            sortedMediaFiles.add(six_month_sorted_media_file);
        }
        if (!month_sorted_media_file.media_files.isEmpty()) {
            sortedMediaFiles.add(month_sorted_media_file);
        }
        if (!week_sorted_media_file.media_files.isEmpty()) {
            sortedMediaFiles.add(week_sorted_media_file);
        }
        if (!yesterday_sorted_media_file.media_files.isEmpty()) {
            sortedMediaFiles.add(yesterday_sorted_media_file);
        }
        if (!today_sorted_media_file.media_files.isEmpty()) {
            sortedMediaFiles.add(two_year_sorted_media_file);
        }


    }


}
