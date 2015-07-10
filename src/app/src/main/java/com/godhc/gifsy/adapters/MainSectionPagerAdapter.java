package com.godhc.gifsy.adapters;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.godhc.gifsy.fragments.FavouriteFragment;
import com.godhc.gifsy.fragments.PopularFragment;

public class MainSectionPagerAdapter extends FragmentPagerAdapter {

    public MainSectionPagerAdapter(FragmentManager fm) {
        super(fm);
    }

    @Override
    public Fragment getItem(int position) {
        switch (position) {
            case 0:
                return new PopularFragment();
            case 1:
            default:
                return new FavouriteFragment();
        }
    }

    @Override
    public int getCount() {
        return 2;
    }

    @Override
    public CharSequence getPageTitle(int position) {

        switch (position) {
            case 0:
                return "Popular";
            case 1:
            default:
                return "Favourite";
        }
    }
}
