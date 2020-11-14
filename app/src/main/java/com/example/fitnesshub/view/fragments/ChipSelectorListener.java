package com.example.fitnesshub.view.fragments;

import android.widget.RadioGroup;

import com.example.fitnesshub.R;
import com.example.fitnesshub.viewModel.RoutinesViewModel;
import com.google.android.material.chip.ChipGroup;

public class ChipSelectorListener implements ChipGroup.OnCheckedChangeListener {

    private RoutinesViewModel viewModel;

    public ChipSelectorListener(RoutinesViewModel viewModel) {
        this.viewModel = viewModel;
    }

    @Override
    public void onCheckedChanged(ChipGroup group, int checkedId) {
        Integer id = null;

        if (checkedId == R.id.rookie_chip) {
            id = 0;
        } else if (checkedId == R.id.beginner_chip) {
            id = 1;
        } else if (checkedId == R.id.intermediate_chip) {
            id = 2;
        } else if (checkedId == R.id.advanced_chip) {
            id = 3;
        } else if (group.getCheckedChipId() == checkedId) {
            id = -1;
        }
        viewModel.filterRoutines(id);
    }

}
