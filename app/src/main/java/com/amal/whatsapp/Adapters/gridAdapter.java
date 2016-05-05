package com.amal.whatsapp.Adapters;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.ImageView;

import com.amal.whatsapp.Pojo.Media_File;
import com.amal.whatsapp.R;
import com.bumptech.glide.Glide;

import java.util.ArrayList;

/**
 * Created by amal on 03/05/16.
 */
public class gridAdapter extends BaseAdapter {

    ArrayList<Media_File> media_files;
    Context context;
    private LayoutInflater inflater;

    public static class ViewHolder {
        ImageView image;
        CheckBox checkBox;
    }

    public gridAdapter(Context context, ArrayList<Media_File> media_files) {
        this.context = context;
        this.media_files = media_files;
    }

    @Override
    public int getCount() {
        Log.d("GridAdapter",media_files.size()+"");
        return media_files.size();
    }

    @Override
    public Object getItem(int position) {

        return media_files.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder;

        if (inflater == null)
            inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        if (convertView == null) {
            convertView = inflater.inflate(R.layout.single_column, null);
            holder = new ViewHolder();
            holder.image = (ImageView) convertView.findViewById(R.id.child_image);
            holder.checkBox = (CheckBox) convertView.findViewById(R.id.child_checkbox);
            convertView.setTag(holder);
        } else
            holder = (ViewHolder) convertView.getTag();

        holder.checkBox.setSelected(media_files.get(position).checked);

        Glide.with(context).load(media_files.get(position).file).into(holder.image);

        return convertView;
    }
}
