package com.example.fitnesshub.view.fragments;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.denzcoskun.imageslider.ImageSlider;
import com.denzcoskun.imageslider.interfaces.ItemClickListener;
import com.denzcoskun.imageslider.models.SlideModel;
import com.example.fitnesshub.R;
import com.example.fitnesshub.databinding.FragmentHomeBinding;
import com.example.fitnesshub.view.activities.MainActivity;
import com.example.fitnesshub.viewModel.RoutinesViewModel;

import java.util.ArrayList;
import java.util.List;


public class HomeFragment extends Fragment {

    public HomeFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        FragmentHomeBinding binding = FragmentHomeBinding.inflate(getLayoutInflater());
        View view = binding.getRoot();

        ImageSlider imageSlider = view.findViewById(R.id.home_slider);

        List<SlideModel> slideModels = new ArrayList<>();

        slideModels.add(new SlideModel("https://images.unsplash.com/photo-1500468756762-a401b6f17b46?ixlib=rb-1.2.1&ixid=eyJhcHBfaWQiOjEyMDd9&auto=format&fit=crop&w=634&q=80", getString(R.string.PopularRoutinesSlide)));
        slideModels.add(new SlideModel("https://images.unsplash.com/photo-1560233026-ad254fa8da38?ixlib=rb-1.2.1&ixid=eyJhcHBfaWQiOjEyMDd9&auto=format&fit=crop&w=610&q=80", getString(R.string.FavoriteRoutinesSlide)));
        slideModels.add(new SlideModel("https://images.unsplash.com/photo-1567740034541-1ff8b618a370?ixlib=rb-1.2.1&ixid=eyJhcHBfaWQiOjEyMDd9&auto=format&fit=crop&w=634&q=80", getString(R.string.HistoryRoutinesSlide)));

        imageSlider.setImageList(slideModels, true);
        imageSlider.setItemClickListener(i -> {
            NavController navController = Navigation.findNavController(view);
            switch (i) {
                case 0:
                    new ViewModelProvider(getActivity()).get(RoutinesViewModel.class).setOrderById(1);
                    navController.navigate(HomeFragmentDirections.actionHomeFragmentToRoutinesFragment());
                    break;

                case 1:
                    navController.navigate(HomeFragmentDirections.actionHomeFragmentToMeFragment());
                    break;

                case 2:
                    navController.navigate(HomeFragmentDirections.actionHomeFragmentToMyActivityFragment());
                    break;
            }
        });

        ((MainActivity) getActivity()).setNavigationVisibility(true);

        return view;
    }

}