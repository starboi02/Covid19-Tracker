package com.example.covid_19tracker;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentStatePagerAdapter;

public class PagerAdapter extends FragmentStatePagerAdapter {

    int mNoOfTabs;
//    private final Bundle fragmentBundle;

    public PagerAdapter(FragmentManager fm, int NumberOfTabs)
    {
        super(fm);
//        fragmentBundle=data;
        this.mNoOfTabs = NumberOfTabs;
    }


    @Override
    public Fragment getItem(int position) {
        switch(position)
        {

            case 0:
                NationalCases nationalCases = new NationalCases();
                return nationalCases;
            case 1:
                NationalStats nationalStats = new NationalStats();
//                nationalStats.setArguments(this.fragmentBundle);
                return nationalStats;
            default:
                return null;
        }
    }

    @Override
    public int getCount() {
        return mNoOfTabs;
    }
}
