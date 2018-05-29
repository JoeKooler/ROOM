package com.example.yoons.room.Adapter;

import android.support.v7.widget.RecyclerView;
import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;


import com.example.yoons.room.R;
import com.example.yoons.room.ValueClass.Device;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.List;

/**
 * Created by yoons on 14/11/2017.
 */

public class DeviceRecyclerViewAdapter extends RecyclerView.Adapter<DeviceRecyclerViewAdapter.SelectDeviceRecyclerViewHolder>
{
    List<Device> deviceList;
    FirebaseDatabase firebaseDatabase;
    DatabaseReference currentReference,myDeviceDatabase,tempDeviceDatabase;

    public DeviceRecyclerViewAdapter(List<Device> deviceList){
        this.deviceList = deviceList;
    }

    @Override
    public SelectDeviceRecyclerViewHolder onCreateViewHolder(ViewGroup parent, int viewType)
    {
        return new SelectDeviceRecyclerViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.device_recyclerview_items_layout,parent,false));
    }

    @Override
    public void onBindViewHolder(final SelectDeviceRecyclerViewHolder holder, int position)
    {
        final Device device = deviceList.get(position);

        holder.deviceBrand.setText(device.getBrand());
        holder.deviceVersion.setText(device.getVersion());
        switch (device.getType()){
            case "Television":
                holder.deviceType.setImageResource(R.drawable.tvicon);
                break;
            case "AirConditioner":
                holder.deviceType.setImageResource(R.drawable.airicon);
                break;
            case "Projector":
                holder.deviceType.setImageResource(R.drawable.projectoricon);
                break;
        }

        holder.itemView.setOnCreateContextMenuListener(new View.OnCreateContextMenuListener()
        {
            @Override
            public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo)
            {
                menu.add(holder.getAdapterPosition(),0,0,"delete").setOnMenuItemClickListener(new MenuItem.OnMenuItemClickListener() {
                    @Override
                    public boolean onMenuItemClick(MenuItem menuItem) {
                        firebaseDatabase = FirebaseDatabase.getInstance();
                        myDeviceDatabase = firebaseDatabase.getReference("MyDevice").child(device.getUID());
                        myDeviceDatabase.removeValue();
                        return true;
                    }
                });

                menu.getItem(0);
            }

        });

        holder.homeScreenLayout.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                firebaseDatabase = FirebaseDatabase.getInstance();
                currentReference = firebaseDatabase.getReference("TempUnit").child("CurrentRemote");
                currentReference.child("Device").child("Type").setValue(device.getType());
                currentReference.child("Device").child("Brand").setValue(device.getBrand());
                currentReference.child("Device").child("Version").setValue(device.getVersion());
                currentReference.child("Device").child("UID").setValue(device.getUID());
                Toast.makeText(v.getContext(),"Remote Set!",Toast.LENGTH_SHORT).show();
            }
        });
    }


    @Override
    public int getItemCount()
    {
        return deviceList.size();
    }

    class SelectDeviceRecyclerViewHolder extends RecyclerView.ViewHolder
    {
        TextView deviceBrand;
        TextView deviceVersion;
        ImageView deviceType;
        LinearLayout homeScreenLayout;

        public SelectDeviceRecyclerViewHolder(View itemView)
        {
            super(itemView);

            deviceBrand = itemView.findViewById(R.id.deviceBrandInRecyclerView);
            deviceVersion = itemView.findViewById(R.id.deviceVersionInRecyclerView);
            deviceType = itemView.findViewById(R.id.deviceTypeInRecyclerView);
            homeScreenLayout = itemView.findViewById(R.id.device_recycler_layout);
        }
    }
}
