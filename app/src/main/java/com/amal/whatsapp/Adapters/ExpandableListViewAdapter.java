package com.amal.whatsapp.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.TextView;

import com.amal.whatsapp.Pojo.Media_File;
import com.amal.whatsapp.Pojo.Sorted_Media_Files;
import com.amal.whatsapp.R;

import java.util.ArrayList;

/**
 * Created by amal on 02/05/16.
 */
public class ExpandableListViewAdapter extends BaseExpandableListAdapter {

    private Context context;
    private ArrayList<Sorted_Media_Files> sortedMediaFiles;

    public ExpandableListViewAdapter(Context context, ArrayList<Sorted_Media_Files> sortedMediaFiles) {
        this.context = context;
        this.sortedMediaFiles = sortedMediaFiles;
    }

    @Override
    public int getGroupCount() {
        return sortedMediaFiles.size();
    }

    @Override
    public int getChildrenCount(int groupPosition) {
        return sortedMediaFiles.get(groupPosition).media_files.size();
    }

    @Override
    public Object getGroup(int groupPosition) {
        return sortedMediaFiles.get(groupPosition);
    }

    @Override
    public Object getChild(int groupPosition, int childPosition) {
        return sortedMediaFiles.get(groupPosition).media_files.get(childPosition);
    }

    @Override
    public long getGroupId(int groupPosition) {
        return groupPosition;
    }

    @Override
    public long getChildId(int groupPosition, int childPosition) {
        return childPosition;
    }

    @Override
    public boolean hasStableIds() {
        return false;
    }

    @Override
    public View getGroupView(int groupPosition, boolean isExpanded, View convertView, ViewGroup parent) {

        Sorted_Media_Files sorted_media_file = (Sorted_Media_Files) getGroup(groupPosition);
        if (convertView == null) {
            LayoutInflater inflater = (LayoutInflater) this.context
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.list_header, null);
        }

        TextView header_text = (TextView) convertView.findViewById(R.id.header_title);
       ImageView toggle_btn = (ImageView) convertView.findViewById(R.id.btn_expand_toggle);
        CheckBox header_checkbox = (CheckBox) convertView.findViewById(R.id.header_checkbox);

        header_text.setText(sorted_media_file.name);
        toggle_btn.setImageResource(R.drawable.up_arrows);
        header_checkbox.setSelected(false);

        return convertView;
    }

    @Override
    public View getChildView(int groupPosition, int childPosition, boolean isLastChild, View convertView, ViewGroup parent) {

      //  ArrayList<Media_File> media_files = (ArrayList<Media_File>) getChild(groupPosition, childPosition);
      //  Sorted_Media_Files sorted_media_file = (Sorted_Media_Files) getGroup(groupPosition);
        Media_File media_file = (Media_File) getChild(groupPosition, childPosition);
        if (convertView == null) {
            LayoutInflater inflater = (LayoutInflater) this.context
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.list_child, null);

        }
        /*GridView child_grid = (GridView) convertView.findViewById(R.id.child_grid);
        gridAdapter gridadater = new gridAdapter(context, sorted_media_file.media_files);
        child_grid.setAdapter(gridadater);
        gridadater.notifyDataSetChanged();*/
      //  TextView textView = (TextView) convertView.findViewById(R.id.child_text);
      //  textView.setText(media_file.file.getName());

        return convertView;
    }

    @Override
    public boolean isChildSelectable(int groupPosition, int childPosition) {
        return true;
    }
}
