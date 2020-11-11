package com.example.fitnesshub.view.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

import com.example.fitnesshub.R;
import com.example.fitnesshub.databinding.FragmentMyActivityBinding;
import com.google.android.material.tabs.TabLayout;

public class MyActivityFragment extends Fragment implements AdapterView.OnItemSelectedListener {


    private TabLayout tabs;
    private TabLayout.OnTabSelectedListener listener;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_my_activity, container, false);
        tabs = view.findViewById(R.id.myActivityTabs);

        createListener();
        tabs.addOnTabSelectedListener(listener);
        Spinner sortSpinner = view.findViewById(R.id.sortActivitySpinner);
        ArrayAdapter<CharSequence> sortActivityAdapter = ArrayAdapter.createFromResource(getActivity(), R.array.sort, android.R.layout.simple_spinner_item);
        sortActivityAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        sortSpinner.setAdapter(sortActivityAdapter);
        sortSpinner.setOnItemSelectedListener(this);
        Spinner orderSpinner = view.findViewById(R.id.orderActivitySpinner);
        ArrayAdapter<CharSequence> orderActivityAdapter = ArrayAdapter.createFromResource(getActivity(), R.array.order, android.R.layout.simple_spinner_item);
        orderActivityAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        orderSpinner.setAdapter(orderActivityAdapter);
        orderSpinner.setOnItemSelectedListener(this);


        return view;
    }

    private void createListener() {
        listener = new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                switch (tab.getPosition()) {
                    case 0:
                        Navigation.findNavController(getView()).navigate(R.id.action_historyFragment_to_myRoutinesFragment);
                        break;

                    case 1:
                        Navigation.findNavController(getView()).navigate(R.id.action_myRoutinesFragment_to_historyFragment);
                        break;
                }
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        };
    }

    // perform setOnTabSelectedListener event on TabLayout

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
