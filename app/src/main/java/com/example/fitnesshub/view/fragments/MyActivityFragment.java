package com.example.fitnesshub.view.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

import androidx.fragment.app.Fragment;

import com.example.fitnesshub.R;
import com.example.fitnesshub.databinding.FragmentMyActivityBinding;

public class MyActivityFragment extends Fragment implements AdapterView.OnItemSelectedListener{


    private FragmentMyActivityBinding binding;

    private Spinner sortSpinner;
    private Spinner orderSpinner;

    public MyActivityFragment() {
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentMyActivityBinding.inflate(getLayoutInflater());

        View view = binding.getRoot();


        sortSpinner = view.findViewById(R.id.sortActivitySpinner);
        ArrayAdapter<CharSequence> sortActivityAdapter = ArrayAdapter.createFromResource(getActivity(),R.array.sort,android.R.layout.simple_spinner_item);
        sortActivityAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        sortSpinner.setAdapter(sortActivityAdapter);
        sortSpinner.setOnItemSelectedListener(this);


        orderSpinner = view.findViewById(R.id.orderActivitySpinner);
        ArrayAdapter<CharSequence> orderActivityAdapter = ArrayAdapter.createFromResource(getActivity(),R.array.order,android.R.layout.simple_spinner_item);
        orderActivityAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        orderSpinner.setAdapter(orderActivityAdapter);
        orderSpinner.setOnItemSelectedListener(this);

        return view;
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        String text = parent.getItemAtPosition(position).toString();

    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }
}
