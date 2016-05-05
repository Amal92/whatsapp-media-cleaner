package com.amal.whatsapp.Fragments;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.amal.whatsapp.Activities.Navigation_Activity;
import com.amal.whatsapp.Adapters.Image.expandableRecyclerViewAdapter;
import com.amal.whatsapp.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class ImageFiles_Fragment extends Fragment {

    public static expandableRecyclerViewAdapter adapter;
    /* ExpandableListView expandableListView;
     ExpandableListViewAdapter adapter;*/
    RecyclerView mRecyclerView;
    LinearLayoutManager mLayoutManager;


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

        mRecyclerView = (RecyclerView) view.findViewById(R.id.recyclerView);
        mLayoutManager = new LinearLayoutManager(getActivity());
        mRecyclerView.setLayoutManager(mLayoutManager);
        adapter = new expandableRecyclerViewAdapter(getActivity(), Navigation_Activity.sortedImageMediaFiles);
        mRecyclerView.setAdapter(adapter);

        return view;
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
