package com.amal.whatsyclean.ViewHolders.Image.Voice;

import android.view.View;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.TextView;

import com.amal.whatsyclean.Activities.Navigation_Activity;
import com.amal.whatsyclean.Fragments.VoiceNotes_Fragment;
import com.amal.whatsyclean.Models.StorageSize;
import com.amal.whatsyclean.Pojo.Sorted_Media_Files;
import com.amal.whatsyclean.R;
import com.amal.whatsyclean.Utils.StorageUtil;
import com.bignerdranch.expandablerecyclerview.ParentViewHolder;


/**
 * Created by amal on 04/05/16.
 */
public class HeaderViewHolder extends ParentViewHolder {

    private TextView header_title, header_size;
    private ImageView btn_expand_toggle;
    private CheckBox header_checkbox;

    /**
     * Default constructor.
     *
     * @param itemView The {@link View} being hosted in this ViewHolder
     */
    public HeaderViewHolder(final View itemView) {
        super(itemView);
        header_title = (TextView) itemView.findViewById(R.id.header_title);
        btn_expand_toggle = (ImageView) itemView.findViewById(R.id.btn_expand_toggle);
        header_size = (TextView) itemView.findViewById(R.id.header_size);
        header_checkbox = (CheckBox) itemView.findViewById(R.id.header_checkbox);
        header_checkbox.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (((CheckBox) v).isChecked()) {

                    int pos = getParentAdapterPosition();
                    Navigation_Activity.sortedVoiceMediaFiles.get(pos).isChecked = true;
                    for (int i = 0; i < Navigation_Activity.sortedVoiceMediaFiles.get(pos).media_files.size(); i++) {
                        Navigation_Activity.sortedVoiceMediaFiles.get(pos).media_files.get(i).checked = true;
                    }
                    VoiceNotes_Fragment.adapter.notifyChildItemRangeChanged(pos, 0, Navigation_Activity.sortedVoiceMediaFiles.get(pos).media_files.size());

                } else {
                    int pos = getParentAdapterPosition();
                    Navigation_Activity.sortedVoiceMediaFiles.get(pos).isChecked = false;
                    for (int i = 0; i < Navigation_Activity.sortedVoiceMediaFiles.get(pos).media_files.size(); i++) {
                        Navigation_Activity.sortedVoiceMediaFiles.get(pos).media_files.get(i).checked = false;
                    }
                    VoiceNotes_Fragment.adapter.notifyChildItemRangeChanged(pos, 0, Navigation_Activity.sortedVoiceMediaFiles.get(pos).media_files.size());
                }
            }
        });

    }

    @Override
    public void onExpansionToggled(boolean expanded) {
        super.onExpansionToggled(expanded);
        if (expanded)
            btn_expand_toggle.setImageResource(R.drawable.up_arrows);
        else
            btn_expand_toggle.setImageResource(R.drawable.down_arrows);
    }

    public void bind(final Sorted_Media_Files sorted_media_file) {
        header_title.setText(sorted_media_file.name);
        StorageSize mStorageSize = StorageUtil.convertStorageSize(sorted_media_file.size);
        header_size.setText(String.format("%.2f", mStorageSize.value) + " " + mStorageSize.suffix);
        header_checkbox.setChecked(sorted_media_file.isChecked);
        if (sorted_media_file.isInitiallyExpanded())
            btn_expand_toggle.setImageResource(R.drawable.down_arrows);
        else btn_expand_toggle.setImageResource(R.drawable.up_arrows);

    }

}
