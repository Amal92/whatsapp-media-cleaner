package com.amal.whatsapp.Fragments;


import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.amal.whatsapp.Activities.Navigation_Activity;
import com.amal.whatsapp.Adapters.Image.expandableRecyclerViewAdapter;
import com.amal.whatsapp.Models.StorageSize;
import com.amal.whatsapp.R;
import com.amal.whatsapp.Utils.StorageUtil;

import java.io.File;

/**
 * A simple {@link Fragment} subclass.
 */
public class ImageFiles_Fragment extends Fragment {

    public static expandableRecyclerViewAdapter adapter;
    RecyclerView mRecyclerView;
    LinearLayoutManager mLayoutManager;
    FloatingActionButton mFloatingActionButton;


    public ImageFiles_Fragment() {
        // Required empty public constructor
    }


    @Override
    public void onPause() {
        super.onPause();
        eraseChecks();
    }

    @Override
    public void onStop() {
        super.onStop();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_image_files_, container, false);
        try {
            ((AppCompatActivity)getActivity()).getSupportActionBar().setTitle("Image Messages");
        } catch (Exception e) {
            e.printStackTrace();
        }
        mRecyclerView = (RecyclerView) view.findViewById(R.id.recyclerView);
        mFloatingActionButton = (FloatingActionButton) view.findViewById(R.id.imageDeleteButton);
        mLayoutManager = new LinearLayoutManager(getActivity());
        mRecyclerView.setLayoutManager(mLayoutManager);
        adapter = new expandableRecyclerViewAdapter(getActivity(), Navigation_Activity.sortedImageMediaFiles);
        mRecyclerView.setAdapter(adapter);
        mFloatingActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                AlertDialog.Builder mBuilder = new AlertDialog.Builder(getActivity());
                LayoutInflater inflater = getActivity().getLayoutInflater();
                View dialogView = inflater.inflate(R.layout.dialog_layout, null);
                mBuilder.setView(dialogView);
                Button cancel_button = (Button) dialogView.findViewById(R.id.cancel_button);
                Button continue_button = (Button) dialogView.findViewById(R.id.continue_button);
                final AlertDialog mAlertDialog = mBuilder.create();
                mAlertDialog.show();
                cancel_button.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        mAlertDialog.dismiss();
                    }
                });
                continue_button.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        deleteImageFiles();
                        mAlertDialog.dismiss();
                    }
                });

            }
        });

        return view;
    }

    private void deleteImageFiles() {
        long totalFileSize = 0;
        int count = 0;
        for (int i = 0; i < Navigation_Activity.sortedImageMediaFiles.size(); i++) {
            for (int j = 0; j < Navigation_Activity.sortedImageMediaFiles.get(i).media_files.size(); j++) {
                if (Navigation_Activity.sortedImageMediaFiles.get(i).media_files.get(j).checked) {
                    File file = Navigation_Activity.sortedImageMediaFiles.get(i).media_files.get(j).file;
                    long size = file.length();
                    if (file.delete()) {
                        Navigation_Activity.sortedImageMediaFiles.get(i).size = Navigation_Activity.sortedImageMediaFiles.get(i).size - size;
                        totalFileSize = totalFileSize + size;
                        count++;
                        Navigation_Activity.sortedImageMediaFiles.get(i).media_files.remove(j);
                        adapter.notifyChildItemRemoved(i, j);
                    }
                    j--;
                }
            }
        }

        for (int i = 0; i < Navigation_Activity.sortedImageMediaFiles.size(); i++) {
            if (Navigation_Activity.sortedImageMediaFiles.get(i).media_files.isEmpty()) {
                Navigation_Activity.sortedImageMediaFiles.remove(i);
                adapter.notifyParentItemRemoved(i);
            } else {
                adapter.notifyParentItemChanged(i);
            }
        }
        showCustomToast(totalFileSize, count);
    }


    private void showCustomToast(long totalSize, int count) {
        View toastView = LayoutInflater.from(getActivity()).inflate(R.layout.custom_toast_layout, null);
        Toast customToast = new Toast(getActivity());
        customToast.setView(toastView);
        TextView deletedSize = (TextView) toastView.findViewById(R.id.tv_deleted_size);
        TextView deleteCount = (TextView) toastView.findViewById(R.id.tv_delete_count);
        StorageSize mStorageSize = StorageUtil.convertStorageSize(totalSize);
        deleteCount.setText(count + " " + "files cleaned");
        deletedSize.setText("" + String.format("%.2f", mStorageSize.value) + mStorageSize.suffix);
        customToast.setDuration(Toast.LENGTH_LONG);
        customToast.show();
    }

    private void eraseChecks() {
        for (int j = 0; j < Navigation_Activity.sortedImageMediaFiles.size(); j++) {
            if (Navigation_Activity.sortedImageMediaFiles.get(j).isChecked)
                Navigation_Activity.sortedImageMediaFiles.get(j).isChecked = false;
            for (int k = 0; k < Navigation_Activity.sortedImageMediaFiles.get(j).media_files.size(); k++) {
                if (Navigation_Activity.sortedImageMediaFiles.get(j).media_files.get(k).checked)
                    Navigation_Activity.sortedImageMediaFiles.get(j).media_files.get(k).checked = false;
            }
        }
    }


}
