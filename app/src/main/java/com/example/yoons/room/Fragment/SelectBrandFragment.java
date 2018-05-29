package com.example.yoons.room.Fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.example.yoons.room.ValueClass.DeviceBrandSelector;
import com.example.yoons.room.R;
import com.example.yoons.room.Adapter.SelectBrandRecyclerViewAdapter;

import java.util.ArrayList;
import java.util.List;

public class SelectBrandFragment extends Fragment {

    private RecyclerView recyclerView;
    private List<String> deviceBrandList;
    private SelectBrandRecyclerViewAdapter adapter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
    View view = inflater.inflate(R.layout.fragment_select_brand, container, false);

    Bundle bundle = getArguments();
    String deviceType = bundle.getString("deviceType");

    deviceBrandList = new ArrayList<>();
    recyclerView = view.findViewById(R.id.listViewinSelectBrand);
    recyclerView.setHasFixedSize(true);

    ImageView backButtonInSelectBrand = view.findViewById(R.id.backButtonInSelectBrand);
        backButtonInSelectBrand.setOnClickListener(new View.OnClickListener()
    {
        @Override
        public void onClick(View v) {
        FragmentManager fragmentManager = getFragmentManager();
        FragmentTransaction transaction = fragmentManager.beginTransaction();
        transaction.replace(R.id.content,new DeviceSetupFragment()).commit();

    }
    });

    LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext());
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);

        recyclerView.setLayoutManager(linearLayoutManager);

        switch (deviceType){
        case "Television":
            adapter = new SelectBrandRecyclerViewAdapter(getContext(),DeviceBrandSelector.getTVList(),deviceType,this);
            break;
        case "AirConditioner":
            adapter = new SelectBrandRecyclerViewAdapter(getContext(),DeviceBrandSelector.getAirConditionerList(),deviceType,this);
            break;
        case "Projector":
            adapter = new SelectBrandRecyclerViewAdapter(getContext(),DeviceBrandSelector.getProjectorList(),deviceType,this);
            break;
    }

        recyclerView.setAdapter(adapter);
        return view;
    }

}

