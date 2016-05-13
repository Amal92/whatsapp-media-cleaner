package com.amal.whatsyclean.Adapters.Audio;

import android.content.Context;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.amal.whatsyclean.Pojo.Media_File;
import com.amal.whatsyclean.Pojo.Sorted_Media_Files;
import com.amal.whatsyclean.R;
import com.amal.whatsyclean.ViewHolders.Image.Audio.HeaderViewHolder;
import com.amal.whatsyclean.ViewHolders.Image.Audio.childViewHolder;
import com.bignerdranch.expandablerecyclerview.ExpandableRecyclerAdapter;
import com.bignerdranch.expandablerecyclerview.Model.ParentListItem;

import java.util.List;

/**
 * Created by amal on 04/05/16.
 */
public class audioExpandableRecyclerViewAdapter extends ExpandableRecyclerAdapter<HeaderViewHolder, childViewHolder> {

    private LayoutInflater mInflator;
    private Context mContext;

    /**
     * Primary constructor. Sets up {@link #mParentItemList} and {@link #mItemList}.
     * <p>
     * Changes to {@link #mParentItemList} should be made through add/remove methods in
     * {@link ExpandableRecyclerAdapter}
     *
     * @param parentItemList List of all {@link ParentListItem} objects to be
     *                       displayed in the RecyclerView that this
     *                       adapter is linked to
     */
    public audioExpandableRecyclerViewAdapter(Context mContext, @NonNull List<? extends ParentListItem> parentItemList) {
        super(parentItemList);
        this.mContext = mContext;
        mInflator = LayoutInflater.from(mContext);
    }

    @Override
    public HeaderViewHolder onCreateParentViewHolder(ViewGroup parentViewGroup) {
        View headerView = mInflator.inflate(R.layout.list_header, parentViewGroup, false);
        return new HeaderViewHolder(headerView);
    }

    @Override
    public childViewHolder onCreateChildViewHolder(ViewGroup childViewGroup) {
        View childView = mInflator.inflate(R.layout.list_child, childViewGroup, false);
        return new childViewHolder(childView);
    }

    @Override
    public void onBindParentViewHolder(HeaderViewHolder parentViewHolder, int position, ParentListItem parentListItem) {
        Sorted_Media_Files sorted_media_file = (Sorted_Media_Files) parentListItem;
        parentViewHolder.bind(sorted_media_file);
    }

    @Override
    public void onBindChildViewHolder(childViewHolder childViewHolder, int position, Object childListItem) {
        Media_File media_file = (Media_File) childListItem;
        childViewHolder.bind(mContext, media_file);
    }
}
