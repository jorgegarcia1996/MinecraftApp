package com.jgm.minecraftapp.fragment;


import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.jgm.minecraftapp.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class BlocksFragment extends Fragment {


    public BlocksFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_blocks, container, false);
    }

}
