package com.amal.whatsyclean.Fragments;


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

import com.amal.whatsyclean.Activities.Navigation_Activity;
import com.amal.whatsyclean.Adapters.Voice.voiceExpandableRecyclerViewAdapter;
import com.amal.whatsyclean.Models.StorageSize;
import com.amal.whatsyclean.R;
import com.amal.whatsyclean.Utils.StorageUtil;

import java.io.File;

/**
 * A simple {@link Fragment} subclass.
 */
public class VoiceNotes_Fragment extends Fragment {

    public static voiceExpandableRecyclerViewAdapter adapter;
    RecyclerView mRecyclerView;
    LinearLayoutManager mLayoutManager;
    FloatingActionButton mFloatingActionButton;
    private TextView no_voice_text;


    public VoiceNotes_Fragment() {
        // Required empty public constructor
    }

    @Override
    public void onPause() {
        super.onPause();
        //  eraseChecks();
    }

    @Override
    public void onStop() {
        super.onStop();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_voice_notes, container, false);

        try {
            ((AppCompatActivity) getActivity()).getSupportActionBar().setTitle("Voice Messages");
        } catch (Exception e) {
            e.printStackTrace();
        }
        no_voice_text = (TextView) view.findViewById(R.id.no_voice_text);
        mRecyclerView = (RecyclerView) view.findViewById(R.id.voiceRecyclerView);
        mFloatingActionButton = (FloatingActionButton) view.findViewById(R.id.voiceDeleteButton);
        mLayoutManager = new LinearLayoutManager(getActivity());
        mRecyclerView.setLayoutManager(mLayoutManager);
        adapter = new voiceExpandableRecyclerViewAdapter(getActivity(), Navigation_Activity.sortedVoiceMediaFiles);
        mRecyclerView.setAdapter(adapter);
        mFloatingActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (checkForFileToDelete()) {
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
                            deleteVoiceFiles();
                            mAlertDialog.dismiss();
                        }
                    });
                } else {
                    Toast.makeText(getActivity(), "Select a file to delete", Toast.LENGTH_SHORT).show();
                }
            }
        });
        checkEmptyList();

        return view;
    }

    private void checkEmptyList(){
        if (Navigation_Activity.sortedImageMediaFiles.isEmpty()){
            mFloatingActionButton.setVisibility(View.GONE);
            no_voice_text.setVisibility(View.VISIBLE);
        }
    }

    private boolean checkForFileToDelete() {
        for (int i = 0; i < Navigation_Activity.sortedVoiceMediaFiles.size(); i++) {
            for (int j = 0; j < Navigation_Activity.sortedVoiceMediaFiles.get(i).media_files.size(); j++) {
                if (Navigation_Activity.sortedVoiceMediaFiles.get(i).media_files.get(j).checked)
                    return true;
            }
        }
        return false;
    }

    private void deleteVoiceFiles() {
        long totalFileSize = 0;
        int count = 0;
        for (int i = 0; i < Navigation_Activity.sortedVoiceMediaFiles.size(); i++) {
            for (int j = 0; j < Navigation_Activity.sortedVoiceMediaFiles.get(i).media_files.size(); j++) {
                if (Navigation_Activity.sortedVoiceMediaFiles.get(i).media_files.get(j).checked) {
                    File file = Navigation_Activity.sortedVoiceMediaFiles.get(i).media_files.get(j).file;
                    long size = file.length();
                    if (file.delete()) {
                        Navigation_Activity.sortedVoiceMediaFiles.get(i).size = Navigation_Activity.sortedVoiceMediaFiles.get(i).size - size;
                        totalFileSize = totalFileSize + size;
                        count++;
                        Navigation_Activity.sortedVoiceMediaFiles.get(i).media_files.remove(j);
                        adapter.notifyChildItemRemoved(i, j);
                    }
                    j--;
                }
            }
        }

        for (int i = 0; i < Navigation_Activity.sortedVoiceMediaFiles.size(); i++) {
            if (Navigation_Activity.sortedVoiceMediaFiles.get(i).media_files.isEmpty()) {
                Navigation_Activity.sortedVoiceMediaFiles.remove(i);
                adapter.notifyParentItemRemoved(i);
            } else {
                adapter.notifyParentItemChanged(i);
            }
        }
        showCustomToast(totalFileSize, count);
        checkEmptyList();
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
        for (int j = 0; j < Navigation_Activity.sortedVoiceMediaFiles.size(); j++) {
            if (Navigation_Activity.sortedVoiceMediaFiles.get(j).isChecked)
                Navigation_Activity.sortedVoiceMediaFiles.get(j).isChecked = false;
            for (int k = 0; k < Navigation_Activity.sortedVoiceMediaFiles.get(j).media_files.size(); k++) {
                if (Navigation_Activity.sortedVoiceMediaFiles.get(j).media_files.get(k).checked)
                    Navigation_Activity.sortedVoiceMediaFiles.get(j).media_files.get(k).checked = false;
            }
        }
    }


}
