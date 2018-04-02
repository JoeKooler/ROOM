package com.example.yoons.room;

/**
 * Created by yoons on 28/03/2018.
 */

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import java.util.List;

public class SectionPagerAdapter extends FragmentPagerAdapter {
    private List<Fragment> fragments;

    public SectionPagerAdapter(FragmentManager fm, List<Fragment> fragments) {
        super(fm);
        this.fragments = fragments;
    }

    @Override
    public Fragment getItem(int position) {
        return this.fragments.get(position);
    }

    @Override
    public int getCount() {
        return this.fragments.size();
    }

    @Override
    public CharSequence getPageTitle(int position) {
        switch (position) {
            case 0:
                return "Device Setup";
            case 1:
            default:
                return "Recent Device";
            case 2:
                return "Device Status";
        }
    }
}
