package com.andy.smartparking.Adapter;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentStatePagerAdapter;

import com.andy.smartparking.Fragments.HomeFragment;
import com.andy.smartparking.Fragments.MapsFragment;
import com.andy.smartparking.Fragments.QRFragment;
import com.andy.smartparking.Fragments.SavedParking;

import org.jetbrains.annotations.NotNull;

public class ViewPagerAdapter extends FragmentStatePagerAdapter {

    public ViewPagerAdapter(@NonNull @NotNull FragmentManager fm, int behavior) {
        super(fm, behavior);
    }

    @NonNull
    @NotNull
    @Override
    public Fragment getItem(int position) {
        switch (position){
            case 0:
                return new HomeFragment();
            case 1:
                return new MapsFragment();
            case 2:
                return new SavedParking();
            case 3:
                return new QRFragment();
            default:
                return new HomeFragment();
        }

    }

    @Override
    public int getCount() {
        return 4;
    }
}
