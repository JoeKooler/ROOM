package com.example.yoons.room;
/**
 * Created by Juned on 8/9/2017.
 */
import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

public class RecyclerViewAdapter extends RecyclerView.Adapter<RecyclerViewAdapter.ViewHolder> {

    Context context;
    List<Device> MainImageUploadInfoList;

    public RecyclerViewAdapter(Context context, List<Device> tempList) {

        this.MainImageUploadInfoList = tempList;

        this.context = context;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.device_recyclerview_items_layout, parent, false);

        ViewHolder viewHolder = new ViewHolder(view);

        return viewHolder;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {

        Device deviceDetails = MainImageUploadInfoList.get(position);

        holder.deviceType.setText(deviceDetails.getDeviceType());

        holder.deviceBrand.setText(deviceDetails.getDeviceBrand());

        //holder.deviceVersion.setText(deviceDetails.getDeviceVersion());



    }

    @Override
    public int getItemCount() {

        return MainImageUploadInfoList.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {

        public TextView deviceType;
        public TextView deviceBrand;
        public TextView deviceVersion;

        public ViewHolder(View itemView) {

            super(itemView);

            deviceType = itemView.findViewById(R.id.deviceTypeInRecyclerView);

            deviceBrand = itemView.findViewById(R.id.deviceBrandInRecyclerView);

            deviceVersion = itemView.findViewById(R.id.deviceVersionInRecyclerView);
        }
    }
}