package com.example.yoons.room.Fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import com.example.yoons.room.Adapter.DeviceRecyclerViewAdapter;
import com.example.yoons.room.R;
import com.example.yoons.room.ValueClass.Device;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by yoons on 28/03/2018.
 */

public class SelectDeviceFragment extends Fragment {

    private RecyclerView recyclerView;
    private List<Device> deviceList;
    private DeviceRecyclerViewAdapter adapter;

    private DatabaseReference databaseReference;
    private FirebaseDatabase firebaseDatabase;

    private void updateList()
    {
        firebaseDatabase = FirebaseDatabase.getInstance();
        databaseReference = firebaseDatabase.getReference("MyDevice");

        databaseReference.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                deviceList.add(dataSnapshot.getValue(Device.class));
                adapter.notifyDataSetChanged();
            }

            @Override
            public void onChildChanged(DataSnapshot dataSnapshot, String s)
            {
                Device device = dataSnapshot.getValue(Device.class);

                int index = getItemIndex(device);

                deviceList.set(index,device);
                adapter.notifyItemChanged(index);
            }

            @Override
            public void onChildRemoved(DataSnapshot dataSnapshot)
            {
                Device device = dataSnapshot.getValue(Device.class);

                int index = getItemIndex(device);

                deviceList.remove(index);
                adapter.notifyItemRemoved(index);
            }

            @Override
            public void onChildMoved(DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }

    private int getItemIndex(Device device)
    {
        int index = -1;

        for(int i=0;i<deviceList.size();i++)
        {
            if(deviceList.get(i).getType().equals(device.getType()))
            {
                index = i;
                break;
            }
        }
        return index;
    }

    @Override
    public boolean onContextItemSelected(MenuItem item)
    {
        switch (item.getItemId())
        {
            case 0:
                break;
            case 1:
                break;
        }

        return super.onContextItemSelected(item);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState)
    {
        View view = inflater.inflate(R.layout.device_select_fragment_layout, container, false);
        deviceList = new ArrayList<>();

        recyclerView = view.findViewById(R.id.listViewinSelectDevice);
        recyclerView.setHasFixedSize(true);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this.getContext());
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);

        recyclerView.setLayoutManager(linearLayoutManager);

        adapter = new DeviceRecyclerViewAdapter(deviceList);
        recyclerView.setAdapter(adapter);

        updateList();

        return view;
    }

}
