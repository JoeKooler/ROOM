package com.example.yoons.room.Adapter;

import android.content.Context;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;


import com.example.yoons.room.Fragment.DeviceSetupFragment;
import com.example.yoons.room.Fragment.SelectBrandFragment;
import com.example.yoons.room.R;

import java.util.List;
/**
 * Created by JoeKooler on 14/11/2017.
 */

public class SetupRecyclerViewAdapter extends RecyclerView.Adapter<SetupRecyclerViewAdapter.SetupRecyclerViewHolder>
{
    List<String> deviceTypeList;

    private Context context;

    DeviceSetupFragment deviceSetupFragment;



    public SetupRecyclerViewAdapter(Context context, List<String> deviceTypeList, DeviceSetupFragment deviceSetupFragment)
    {
        this.context = context;
        this.deviceTypeList = deviceTypeList;
        this.deviceSetupFragment = deviceSetupFragment;
    }

    @Override
    public SetupRecyclerViewHolder onCreateViewHolder(ViewGroup parent, int viewType)
    {
        return new SetupRecyclerViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.setup_recycle_layout,parent,false));
    }

    @Override
    public void onBindViewHolder(final SetupRecyclerViewHolder holder, int position)
    {
        final String deviceType = deviceTypeList.get(position);

        holder.deviceType.setText(deviceType);
        switch (deviceType){
            case "Television":
                holder.deviceTypeImage.setImageResource(R.drawable.tvimg);
                break;
            case "AirConditioner":
                holder.deviceTypeImage.setImageResource(R.drawable.airimg);
                break;
            case "Projector":
                holder.deviceTypeImage.setImageResource(R.drawable.projectimg);
                break;
        }

        holder.setupScreenLayout.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                android.support.v4.app.Fragment selectBrandFragment = new SelectBrandFragment();
                Bundle bundle = new Bundle();
                bundle.putString("deviceType",deviceType);
                selectBrandFragment.setArguments(bundle);
                android.support.v4.app.FragmentTransaction transaction = deviceSetupFragment.getActivity().getSupportFragmentManager().beginTransaction();
                transaction.replace(R.id.content,selectBrandFragment);
                //transaction.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
                //transaction.addToBackStack(null);
                transaction.commit();
            }
        });


    }

    @Override
    public int getItemCount()
    {
        return deviceTypeList.size();
    }

    class SetupRecyclerViewHolder extends RecyclerView.ViewHolder
    {
        TextView deviceType;
        ImageView deviceTypeImage;
        LinearLayout setupScreenLayout;

        public SetupRecyclerViewHolder(View itemView)
        {
            super(itemView);

            deviceType = itemView.findViewById(R.id.deviceTypeInFirstSetup);
            deviceTypeImage = itemView.findViewById(R.id.typeImageInSetup);
            setupScreenLayout = itemView.findViewById(R.id.setup_recycler_layout);
        }
    }
}
