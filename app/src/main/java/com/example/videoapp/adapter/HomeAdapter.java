package com.example.videoapp.adapter;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

import java.util.ArrayList;

public class HomeAdapter extends FragmentPagerAdapter {

    private String[] mTitles;

    private ArrayList<Fragment> mFragments;


    public HomeAdapter(FragmentManager fm, String[] titles, ArrayList<Fragment> fragments) {
        super(fm);
        this.mTitles = titles;
        this.mFragments = fragments;
    }



    @Override
    public Fragment getItem(int position) {
        return mFragments.get(position);
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return mTitles[position];
    }

    @Override
    public int getCount() {
        return mFragments.size();
    }
}
