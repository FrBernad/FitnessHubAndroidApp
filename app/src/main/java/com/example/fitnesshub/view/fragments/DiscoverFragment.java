package com.example.fitnesshub.view.fragments;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.fitnesshub.R;
import com.example.fitnesshub.databinding.FragmentDiscoverBinding;

public class DiscoverFragment extends Fragment {

    FragmentDiscoverBinding binding;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,Bundle savedInstanceState) {
        binding = FragmentDiscoverBinding.inflate(getLayoutInflater());
        return inflater.inflate(R.layout.fragment_discover, container, false);
    }
}