package com.example.yoons.room.Adapter;

import android.content.Context;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.yoons.room.Fragment.DeviceRemoteSelectFragment;
import com.example.yoons.room.Fragment.SelectBrandFragment;
import com.example.yoons.room.R;

import java.util.List;

public class SelectBrandRecyclerViewAdapter extends RecyclerView.Adapter<SelectBrandRecyclerViewAdapter.SelectBrandRecyclerViewHolder>{
    List<String> deviceBrandList;
    String deviceType;
    Context context;
    SelectBrandFragment selectBrandFragment;

    public SelectBrandRecyclerViewAdapter(Context context,List<String> deviceBrandList,String deviceType,SelectBrandFragment selectBrandFragment){
        this.deviceBrandList = deviceBrandList;
        this.deviceType = deviceType;
        this.context = context;
        this.selectBrandFragment = selectBrandFragment;
    }

    @Override
    public SelectBrandRecyclerViewHolder onCreateViewHolder(ViewGroup parent, int viewType){
        return new SelectBrandRecyclerViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.select_brand_recycler_layout,parent,false));
    }

    @Override
    public void onBindViewHolder(final SelectBrandRecyclerViewAdapter.SelectBrandRecyclerViewHolder holder, int position){
        final String deviceBrandPos = deviceBrandList.get(position);

        holder.deviceBrand.setText(deviceBrandPos);

        holder.deviceBrandSelectLayout.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                android.support.v4.app.Fragment remoteSelectFragment = new DeviceRemoteSelectFragment();
                Bundle bundle = new Bundle();
                bundle.putString("deviceType",deviceType);
                bundle.putString("deviceBrand",deviceBrandPos);
                remoteSelectFragment.setArguments(bundle);
                android.support.v4.app.FragmentTransaction transaction = selectBrandFragment.getActivity().getSupportFragmentManager().beginTransaction();
                transaction.replace(R.id.content,remoteSelectFragment);
                //transaction.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
                //transaction.addToBackStack(null);
                transaction.commit();


            }
        });
    }

    @Override
    public int getItemCount()
    {
        return deviceBrandList.size();
    }

    class SelectBrandRecyclerViewHolder extends RecyclerView.ViewHolder{
        TextView deviceBrand;
        LinearLayout deviceBrandSelectLayout;

        public SelectBrandRecyclerViewHolder(View itemView){
            super(itemView);
            deviceBrand = itemView.findViewById(R.id.deviceBrand);
            deviceBrandSelectLayout = itemView.findViewById(R.id.select_brand_layout);
        }
    }
}