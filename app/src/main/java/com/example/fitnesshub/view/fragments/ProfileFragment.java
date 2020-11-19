package com.example.fitnesshub.view.fragments;

import android.app.DatePickerDialog;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.fitnesshub.databinding.FragmentProfileBinding;
import com.example.fitnesshub.view.adapters.FavoriteAdapter;
import com.example.fitnesshub.viewModel.FavouritesRoutinesViewModel;
import com.example.fitnesshub.viewModel.RoutinesViewModel;
import com.example.fitnesshub.viewModel.UserViewModel;

import java.util.ArrayList;

public class ProfileFragment extends Fragment {

    private FavouritesRoutinesViewModel favRoutinesViewModel;
    private UserViewModel userViewModel;

    private FragmentProfileBinding binding;

    private FavoriteAdapter favoriteAdapter;

    private RecyclerView favoriteCardsList;

    private TextView username;
    private TextView fullName;
    private TextView phone;
    private TextView birthdate;
    private ImageView profilePic;

    private Spinner spinner;
    private TextView mDisplayDate;
    private DatePickerDialog.OnDateSetListener mDateSetListener;

    public ProfileFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        binding = FragmentProfileBinding.inflate(getLayoutInflater());
        View view = binding.getRoot();

        username = binding.userName;
        fullName = binding.fullName;
        phone = binding.phone;
        birthdate = binding.birthDate;
        profilePic = binding.profileImage;

        favoriteCardsList = binding.favRecycler;

        return view;
    }


    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        favRoutinesViewModel = new ViewModelProvider(getActivity()).get(FavouritesRoutinesViewModel.class);
        favRoutinesViewModel.updateData();

        favoriteAdapter = new FavoriteAdapter(new ArrayList<>(),new ViewModelProvider(getActivity()).get(RoutinesViewModel.class));

        userViewModel = new ViewModelProvider(getActivity()).get(UserViewModel.class);

        favoriteCardsList.setLayoutManager(new LinearLayoutManager(getContext()));
        favoriteCardsList.setAdapter(favoriteAdapter);

        seedProfile();
    }


    private void seedProfile() {
        userViewModel.getUserInfo().observe(getViewLifecycleOwner(), userInfo -> {
            if (userInfo != null) {
                binding.setUserInfo(userInfo);
                if (!userInfo.getAvatarUrl().equals("")) {
                    Glide.with(binding.getRoot()).load(userInfo.getAvatarUrl()).into(binding.profileImage);
                }
            }
        });

        favRoutinesViewModel.getFavouriteRoutines().observe(getViewLifecycleOwner(), favourites -> {
            if (favourites != null) {
                favoriteCardsList.setVisibility(View.VISIBLE);
                favoriteAdapter.updateFavoriteList(favourites);
            }
        });
    }

}