package com.amal.whatsapp.Pojo;

import com.bignerdranch.expandablerecyclerview.Model.ParentListItem;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by amal on 29/04/16.
 */
public class Sorted_Media_Files implements ParentListItem {
    public String name;
    public long size;
    public ArrayList<Media_File> media_files = new ArrayList<>();
    public boolean isChecked;
    public boolean isInitiallyExpanded = false;

    @Override
    public List<?> getChildItemList() {

        return media_files;
    }

    @Override
    public boolean isInitiallyExpanded() {
        return isInitiallyExpanded;
    }
}
