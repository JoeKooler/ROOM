package com.example.yoons.room.Fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.yoons.room.R;
import com.example.yoons.room.Adapter.SetupRecyclerViewAdapter;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by yoons on 28/03/2018.
 */

public class DeviceSetupFragment extends Fragment {

    private RecyclerView recyclerView;
    private List<String> deviceTypeList;
    private SetupRecyclerViewAdapter adapter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_setup_screen, container, false);
        deviceTypeList = new ArrayList<>();
        recyclerView = view.findViewById(R.id.listViewinSetup);
        recyclerView.setHasFixedSize(true);

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext());
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);

        recyclerView.setLayoutManager(linearLayoutManager);

        deviceTypeList.add("Television");
        deviceTypeList.add("AirConditioner");
        deviceTypeList.add("Projector");

        adapter = new SetupRecyclerViewAdapter(getContext(),deviceTypeList,this);
        recyclerView.setAdapter(adapter);
        return view;
}
}
