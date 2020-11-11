package com.example.fitnesshub.view.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;

import com.example.fitnesshub.R;
import com.example.fitnesshub.databinding.FragmentMyActivityBinding;

public class MyActivityFragment extends Fragment {

    public MyActivityFragment() {
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_my_activity, container, false);
    }

}
