package com.amal.whatsapp.ViewHolders.Image;

import android.view.View;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.amal.whatsapp.Activities.Navigation_Activity;
import com.amal.whatsapp.Fragments.ImageFiles_Fragment;
import com.amal.whatsapp.Pojo.Sorted_Media_Files;
import com.amal.whatsapp.R;
import com.bignerdranch.expandablerecyclerview.ViewHolder.ParentViewHolder;

/**
 * Created by amal on 04/05/16.
 */
public class HeaderViewHolder extends ParentViewHolder {

    private TextView header_title;
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
        header_checkbox = (CheckBox) itemView.findViewById(R.id.header_checkbox);
        header_checkbox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    int pos = getAdapterPosition();
                    for (int i = 0; i < Navigation_Activity.sortedImageMediaFiles.get(pos).media_files.size(); i++) {
                        Navigation_Activity.sortedImageMediaFiles.get(pos).media_files.get(i).checked = true;
                    }
                    ImageFiles_Fragment.adapter.notifyChildItemRangeChanged(pos,0,Navigation_Activity.sortedImageMediaFiles.get(pos).media_files.size());

                }else {
                    int pos = getAdapterPosition();
                    for (int i = 0; i < Navigation_Activity.sortedImageMediaFiles.get(pos).media_files.size(); i++) {
                        Navigation_Activity.sortedImageMediaFiles.get(pos).media_files.get(i).checked = false;
                    }
                    ImageFiles_Fragment.adapter.notifyChildItemRangeChanged(pos,0,Navigation_Activity.sortedImageMediaFiles.get(pos).media_files.size());

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
        header_checkbox.setSelected(sorted_media_file.isChecked);
        if (sorted_media_file.isInitiallyExpanded())
            btn_expand_toggle.setImageResource(R.drawable.down_arrows);
        else btn_expand_toggle.setImageResource(R.drawable.up_arrows);

    }

}
