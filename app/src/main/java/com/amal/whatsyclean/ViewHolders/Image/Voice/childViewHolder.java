package com.amal.whatsyclean.ViewHolders.Image.Voice;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.view.View;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.amal.whatsyclean.Activities.Navigation_Activity;
import com.amal.whatsyclean.Fragments.VoiceNotes_Fragment;
import com.amal.whatsyclean.Models.StorageSize;
import com.amal.whatsyclean.Pojo.Media_File;
import com.amal.whatsyclean.R;
import com.amal.whatsyclean.Utils.StorageUtil;
import com.bignerdranch.expandablerecyclerview.ChildViewHolder;
import com.bumptech.glide.Glide;

/**
 * Created by amal on 04/05/16.
 */
public class childViewHolder extends ChildViewHolder {

    private TextView name, size;
    private CheckBox checkBox;
    private ImageView imageView;
    private LinearLayout mLinearLayout;

    /**
     * Default constructor.
     *
     * @param itemView The {@link View} being hosted in this ViewHolder
     */
    public childViewHolder(View itemView) {
        super(itemView);
        name = (TextView) itemView.findViewById(R.id.child_text_name);
        mLinearLayout = (LinearLayout) itemView.findViewById(R.id.child_click_layout);
        size = (TextView) itemView.findViewById(R.id.child_text_size);
        imageView = (ImageView) itemView.findViewById(R.id.imageView);
        checkBox = (CheckBox) itemView.findViewById(R.id.child_checkbox);
        checkBox.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (((CheckBox) v).isChecked()) {
                    int parentPos = getParentAdapterPosition();
                    int childPos = getChildAdapterPosition();
                    Navigation_Activity.sortedVoiceMediaFiles.get(parentPos).media_files.get(childPos).checked = true;
                    boolean flag = false;
                    for (int i = 0; i < Navigation_Activity.sortedVoiceMediaFiles.get(parentPos).media_files.size(); i++) {
                        if (!Navigation_Activity.sortedVoiceMediaFiles.get(parentPos).media_files.get(i).checked) {
                            flag = true;
                        }
                    }
                    if (!flag) {
                        Navigation_Activity.sortedVoiceMediaFiles.get(parentPos).isChecked = true;
                        VoiceNotes_Fragment.adapter.notifyParentItemChanged(parentPos);
                    }
                } else {
                    int parentPos = getParentAdapterPosition();
                    int childPos = getChildAdapterPosition();
                    Navigation_Activity.sortedVoiceMediaFiles.get(parentPos).media_files.get(childPos).checked = false;
                    if ( Navigation_Activity.sortedVoiceMediaFiles.get(parentPos).isChecked) {
                        Navigation_Activity.sortedVoiceMediaFiles.get(parentPos).isChecked = false;
                        VoiceNotes_Fragment.adapter.notifyParentItemChanged(parentPos);
                    }
                }
            }
        });

    }

    public void bind(final Context mContext, final Media_File media_file) {
        name.setText(media_file.file.getName());
        StorageSize mStorageSize = StorageUtil.convertStorageSize(media_file.file.length());
        size.setText(String.format("%.2f", mStorageSize.value) + " " + mStorageSize.suffix);
        checkBox.setChecked(media_file.checked);
        Glide.with(mContext).load(media_file.file).placeholder(R.drawable.microphone).into(imageView);
        mLinearLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Intent.ACTION_VIEW, Uri.fromFile(media_file.file));
                intent.setDataAndType(Uri.fromFile(media_file.file), "audio/*");
                mContext.startActivity(intent);
            }
        });
    }
}
