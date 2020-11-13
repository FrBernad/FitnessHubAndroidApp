package com.example.fitnesshub.view.fragments;

import android.app.DatePickerDialog;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
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
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.fitnesshub.R;
import com.example.fitnesshub.databinding.FragmentProfileBinding;
import com.example.fitnesshub.view.adapters.FavoriteAdapter;
import com.example.fitnesshub.viewModel.FavouritesRoutinesViewModel;
import com.example.fitnesshub.viewModel.UserViewModel;

import java.util.ArrayList;
import java.util.Calendar;

public class ProfileFragment extends Fragment implements AdapterView.OnItemSelectedListener {

    private FavouritesRoutinesViewModel favRoutinesViewModel;
    private UserViewModel userViewModel;

    private FragmentProfileBinding binding;

    private final FavoriteAdapter favoriteAdapter = new FavoriteAdapter(new ArrayList<>());

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

        setSpinner(view);

        mDateSetListener = (view12, year, month, dayOfMonth) -> {
            month = month + 1;
            String date = dayOfMonth + "/" + month + "/" + year;
            mDisplayDate.setText(date);
        };

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

        userViewModel = new ViewModelProvider(getActivity()).get(UserViewModel.class);

        favoriteCardsList.setLayoutManager(new LinearLayoutManager(getContext()));
        favoriteCardsList.setAdapter(favoriteAdapter);

        seedProfile();
    }


    private void seedProfile() {
        userViewModel.getUserInfo().observe(getViewLifecycleOwner(),userInfo -> {
            if(userInfo!=null){
                binding.setUserInfo(userInfo);
                if(!userInfo.getAvatarUrl().equals("")){
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

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        String text = parent.getItemAtPosition(position).toString();
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }

    private void setSpinner(View view) {
        spinner = view.findViewById(R.id.genderSpinner);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(getActivity(), R.array.genders, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);
        spinner.setOnItemSelectedListener(this);
        mDisplayDate = (TextView) view.findViewById(R.id.birthDate);
        mDisplayDate.setOnClickListener(view1 -> {
            Calendar cal = Calendar.getInstance();
            int year = cal.get(Calendar.YEAR);
            int month = cal.get(Calendar.MONTH);
            int day = cal.get(Calendar.DAY_OF_MONTH);
            DatePickerDialog dialog = new DatePickerDialog(getActivity(), android.R.style.Theme_Holo_Light_Dialog_MinWidth, mDateSetListener, year, month, day);
            dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
            dialog.show();

        });
    }

}