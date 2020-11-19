package com.example.fitnesshub.view.fragments;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.viewpager2.widget.ViewPager2;

import com.example.fitnesshub.R;
import com.example.fitnesshub.view.activities.MainActivity;
import com.example.fitnesshub.view.adapters.TabsAdapter;
import com.google.android.material.tabs.TabLayout;
import com.google.android.material.tabs.TabLayoutMediator;

public class MyActivityFragment extends Fragment implements AdapterView.OnItemSelectedListener {


    private TabLayout tabs;
    private TabLayout.OnTabSelectedListener listener;

    private View view;
    private ViewPager2 viewPager;

    private FragmentActivity myContext;

    @Override
    public void onAttach(@NonNull Context context) {
        myContext = (FragmentActivity) context;
        super.onAttach(context);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_my_activity, container, false);

        tabs = view.findViewById(R.id.myActivityTabs);
        viewPager = view.findViewById(R.id.viewPager);

        TabsAdapter tabsAdapter = new TabsAdapter(this);

        viewPager.setAdapter(tabsAdapter);

        ((MainActivity) getActivity()).setNavigationVisibility(true);

        tabs.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                viewPager.setCurrentItem(tab.getPosition());
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });

        new TabLayoutMediator(tabs, viewPager, (tab, position) -> {
            switch (position) {
                case 0:
                    tab.setText("MY ROUTINES");
                    break;
                case 1:
                    tab.setText("HISTORY");
                    break;
            }
        }).attach();


        return view;
    }


    @Override
    public void onDestroy() {
        super.onDestroy();
        tabs.removeOnTabSelectedListener(listener);
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        String text = parent.getItemAtPosition(position).toString();
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }
}
