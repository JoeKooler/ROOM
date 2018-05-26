package com.example.yoons.room;

import android.app.FragmentTransaction;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;


import java.util.List;

/**
 * Created by yoons on 14/11/2017.
 */

public class DeviceRecyclerViewAdapter extends RecyclerView.Adapter<DeviceRecyclerViewAdapter.HomeRecyclerViewHolder>
{
    List<Device> deviceList;

    public DeviceRecyclerViewAdapter(List<Device> deviceList){
        this.deviceList = deviceList;
    }

    @Override
    public HomeRecyclerViewHolder onCreateViewHolder(ViewGroup parent, int viewType)
    {
        return new HomeRecyclerViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.device_recyclerview_items_layout,parent,false));
    }

    @Override
    public void onBindViewHolder(final HomeRecyclerViewHolder holder, int position)
    {
        final Device device = deviceList.get(position);


        holder.deviceBrand.setText(device.getDeviceBrand());
        holder.deviceVersion.setText(device.getDeviceVersion());
        switch (device.getDeviceType())
        {
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
                menu.add(holder.getAdapterPosition(),0,0,"edit");
                menu.add(holder.getAdapterPosition(),1,0,"delete");

                menu.getItem(1);
            }

        });

        holder.homeScreenLayout.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                MainRemoteFragment mainRemoteFragment = new MainRemoteFragment();
                android.support.v4.app.FragmentTransaction fragmentTransaction = mainRemoteFragment.getActivity().getSupportFragmentManager().beginTransaction();
                Bundle bundle = new Bundle();
                bundle.putString("deviceType", device.getDeviceType());
                bundle.putString("deviceBrand", device.getDeviceBrand());
                bundle.putString("deviceVersion", device.getDeviceVersion());
                bundle.putString("deviceUID",device.getUID());
                //fragmentTransaction.replace()
                fragmentTransaction.commit();
            }
        });
    }


    @Override
    public int getItemCount()
    {
        return deviceList.size();
    }

    class HomeRecyclerViewHolder extends RecyclerView.ViewHolder
    {
        TextView deviceBrand;
        TextView deviceVersion;
        ImageView deviceType;
        LinearLayout homeScreenLayout;


        public HomeRecyclerViewHolder(View itemView)
        {
            super(itemView);

            deviceBrand = itemView.findViewById(R.id.deviceBrandInRecyclerView);
            deviceVersion = itemView.findViewById(R.id.deviceVersionInRecyclerView);
            deviceType = itemView.findViewById(R.id.deviceTypeInRecyclerView);
            homeScreenLayout = itemView.findViewById(R.id.device_recycler_layout);
        }
    }
}
