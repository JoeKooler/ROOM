package com.example.yoons.room;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

/**
 * Created by yoons on 28/03/2018.
 */

public class DeviceStatusFragment extends Fragment {
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Declare your first fragment here
        return inflater.inflate(R.layout.device_setup_fragment_layout, container, false);
    }
}
