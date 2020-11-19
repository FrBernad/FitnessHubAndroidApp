package com.example.fitnesshub.view.adapters;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.viewpager2.adapter.FragmentStateAdapter;

import com.example.fitnesshub.view.fragments.HistoryFragment;
import com.example.fitnesshub.view.fragments.MyRoutinesFragment;

public class TabsAdapter extends FragmentStateAdapter {
    public TabsAdapter(@NonNull Fragment fragment) {
        super(fragment);
    }

    @NonNull
    @Override
    public Fragment createFragment(int position) {
        switch (position) {
            case 0:
                return new MyRoutinesFragment();
            case 1:
                return new HistoryFragment();
            default:
                return null;
        }
    }

    @Override
    public int getItemCount() {
        return 2;
    }
}
