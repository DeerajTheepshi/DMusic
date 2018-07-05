package com.example.android.dmusic.Fragments;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

public class pagerAdapter extends FragmentPagerAdapter {

    String[] tabTitles = new String[]{"TRACKS","ARTISTS","FAVOURITES"};

    public pagerAdapter(FragmentManager fm) {
        super(fm);
    }

    @Override
    public Fragment getItem(int position) {
        if (position == 0) {
            return new mainFragment();
        } else if(position==1){
            return new ArtistFragment();
        }else {
            return new faviFragment();
        }
    }

    @Override
    public CharSequence getPageTitle(int position) {
        // Generate title based on item position
        return tabTitles[position];
    }

    @Override
    public int getCount() {
        return 3;
    }
}
