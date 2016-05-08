package com.amal.whatsapp.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.TextView;

import com.amal.whatsapp.R;

import java.util.ArrayList;

/**
 * Created by amal on 08/05/16.
 */
public class ListAdapter extends BaseAdapter {

    private Context mContext;
    private LayoutInflater mLayoutInflater;
    private ArrayList<String> items = new ArrayList<>();

    public ListAdapter(Context mContext, ArrayList<String> items) {
        this.mContext = mContext;
        this.items = items;
    }

    @Override
    public int getCount() {
        return items.size();
    }

    @Override
    public Object getItem(int position) {
        return items.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder;

        if (mLayoutInflater == null)
            mLayoutInflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        if (convertView == null) {
            convertView = mLayoutInflater.inflate(R.layout.single_row, null);
            holder = new ViewHolder();
            holder.count = (TextView) convertView.findViewById(R.id.count_text);
            holder.type = (TextView) convertView.findViewById(R.id.type_text);
            convertView.setTag(holder);
        } else
            holder = (ViewHolder) convertView.getTag();

        holder.count.setText(items.get(position));
        switch (position){
            case 0:
                holder.type.setText("Images");
                break;
            case 1:
                holder.type.setText("Videos");
                break;
            case 2:
                holder.type.setText("Audios");
                break;
            case 3:
                holder.type.setText("Voices");
                break;
        }

        return convertView;
    }

    public static class ViewHolder {
        TextView count, type;
    }
}
