package com.example.yoons.room.Activity;


import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.example.yoons.room.Fragment.DeviceSetupFragment;
import com.example.yoons.room.Fragment.MainRemoteFragment;
import com.example.yoons.room.R;
import com.example.yoons.room.Adapter.SectionPagerAdapter;
import com.example.yoons.room.Fragment.SelectDeviceFragment;

import java.util.List;
import java.util.Vector;

public class MainActivity extends AppCompatActivity
{
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.mainactivity_layout);
        // Initializing tab and pager view
        TabLayout tabLayout = findViewById(R.id.my_tab_layout);
        final ViewPager viewPager = findViewById(R.id.my_view_pager);

// Making new tabs and adding to tabLayout

        tabLayout.addTab(tabLayout.newTab().setText("Device Setup"));
        tabLayout.addTab(tabLayout.newTab().setText("Recent Device"));
        tabLayout.addTab(tabLayout.newTab().setText("Device Status"));

// Adding fragments to a list
        List<Fragment> fragments = new Vector<>();
        fragments.add(Fragment.instantiate(this, DeviceSetupFragment.class.getName()));
        fragments.add(Fragment.instantiate(this, MainRemoteFragment.class.getName()));
        fragments.add(Fragment.instantiate(this, SelectDeviceFragment.class.getName()));
// Attaching fragments into tabLayout with ViewPager
        viewPager.setAdapter(new SectionPagerAdapter(getSupportFragmentManager(), fragments));
        tabLayout.setupWithViewPager(viewPager);
    }
}
