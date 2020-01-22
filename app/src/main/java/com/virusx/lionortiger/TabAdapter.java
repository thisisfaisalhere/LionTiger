package com.virusx.lionortiger;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

public class TabAdapter extends FragmentPagerAdapter {

    public TabAdapter(@NonNull FragmentManager fm, int behavior) {
        super(fm, behavior);
    }

    @NonNull
    @Override
    public Fragment getItem(int position) {
        switch (position) {
            case 0:
                EasyGameModeFragment easyGameModeFragment = new EasyGameModeFragment();
                return easyGameModeFragment;
            case 1:
                ModerateGameModeFragment moderateGameModeFragment = new ModerateGameModeFragment();
                return moderateGameModeFragment;
            default:
                return null;
        }
    }

    @Override
    public int getCount() {
        return 2;
    }

    @Nullable
    @Override
    public CharSequence getPageTitle(int position) {
        switch (position) {
            case 0:
                return "Easy";
            case 1:
                return "Moderate";
            default:
                return null;
        }
    }
}
