package com.example.yoons.room.Fragment;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.graphics.Color;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.yoons.room.ValueClass.Device;
import com.example.yoons.room.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;


/**
 * Created by yoons on 28/03/2018.
 */

public class MainRemoteFragment extends Fragment {

    DatabaseReference currentReference, myDeviceReference;
    FirebaseDatabase firebaseDatabase;
    private String powerStatus;
    private String setRemoteVerticalReference;
    private String setRemoteHorizontalReference;
    private String deviceType;
    private String deviceBrand;
    private String deviceVersion;
    private String deviceUID;
    private int uniqueValue;
    private int horizontal = 0;
    private int vertical = 0;
    private int menu = 0;
    private int mode = 0;
    private int ok = 0;
    private int power = 0;
    private int source = 0;

    public void setCountFunction(final String countType){
        currentReference.child("RemoteCount").child(countType).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                switch (countType){
                    case "OK" :{ok = dataSnapshot.getValue(int.class); break;}
                    case "Power" :{power = dataSnapshot.getValue(int.class); break;}
                    case "Source" :{source = dataSnapshot.getValue(int.class); break;}
                    case "Menu" :{menu = dataSnapshot.getValue(int.class); break;}
                    case "Mode" :{mode = dataSnapshot.getValue(int.class); break;}
                    case "Vertical" :{vertical = dataSnapshot.getValue(int.class); break;}
                    case "Horizontal" :{horizontal = dataSnapshot.getValue(int.class); break;}
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.remote_layout, container, false);

        final TextView deviceBrandInRemote = view.findViewById(R.id.deviceBrandInRemote);
        final TextView deviceTypeInRemote = view.findViewById(R.id.deviceTypeInRemote);
        final TextView powerStatusTextView = view.findViewById(R.id.powerStatusInRemote);
        final TextView uniqueStatus = view.findViewById(R.id.uniqueStatusInRemote);

        TextView menuButton = view.findViewById(R.id.menuButtonInRemote);
        TextView videoButton = view.findViewById(R.id.videoButtonInRemote);
        TextView okButton = view.findViewById(R.id.okButtonInRemote);

        ImageView powerButton = view.findViewById(R.id.powerButtonInRemote);
        ImageView upButton = view.findViewById(R.id.upButtonInRemote);
        ImageView downButton = view.findViewById(R.id.downButtonInRemote);
        ImageView leftButton = view.findViewById(R.id.leftButtonInRemote);
        ImageView rightButton = view.findViewById(R.id.rightButtonInRemote);

        firebaseDatabase = FirebaseDatabase.getInstance();
        currentReference = firebaseDatabase.getReference("TempUnit").child("CurrentRemote");
        myDeviceReference = firebaseDatabase.getReference("MyDevice");

        powerButton.setColorFilter(Color.WHITE);
        upButton.setColorFilter(Color.WHITE);
        downButton.setColorFilter(Color.WHITE);
        leftButton.setColorFilter(Color.WHITE);
        rightButton.setColorFilter(Color.WHITE);

        currentReference.child("Device").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                Device device = dataSnapshot.getValue(Device.class);
                deviceType = device.getType();
                deviceBrand = device.getBrand();
                deviceVersion = device.getVersion();
                deviceUID = device.getUID();
                deviceBrandInRemote.setText(device.getBrand());
                deviceTypeInRemote.setText(device.getType());
                switch (device.getType()){
                    case "AirConditioner":{
                        setRemoteVerticalReference = "Temperature";setRemoteHorizontalReference = "Fan"; break; }
                    case "Television": {
                        setRemoteVerticalReference = "Channel";setRemoteHorizontalReference = "Volume"; break;}
                }
                myDeviceReference.child(deviceUID).child("Status").child("Power").addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        powerStatus = dataSnapshot.getValue(String.class);
                        powerStatusTextView.setText(powerStatus);
                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {

                    }
                });

                myDeviceReference.child(deviceUID).child("Status").child(setRemoteVerticalReference).addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        uniqueValue = dataSnapshot.getValue(int.class);
                        switch (deviceType){
                            case "AirConditioner":{uniqueStatus.setText(""+uniqueValue+"°C"); break;}
                            case "Television" : {uniqueStatus.setText(""+uniqueValue); break;}
                        }
                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {

                    }
                });

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
            }
        });

        upButton.setOnClickListener(new View.OnClickListener(){
            int upValue;
            @Override
            public void onClick(View v){
                currentReference.child("RemoteCount").child("Vertical").setValue(++vertical);
                setCountFunction("Vertical");
                myDeviceReference.child(deviceUID).child("Status").child(setRemoteVerticalReference).addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        if(deviceType.equals("Television")||deviceType.equals("AirConditioner"))
                        {
                            upValue = dataSnapshot.getValue(int.class);
                            upValue++;
                            System.out.println(deviceType+":"+upValue);
                            if(deviceType.equals("AirConditioner") &&upValue>=30) {
                                System.out.println(upValue);
                                myDeviceReference.child(deviceUID).child("Status").child(setRemoteVerticalReference).setValue(30);
                            }
                            else if(deviceType.equals("Television") &&upValue>=100) {
                                myDeviceReference.child(deviceUID).child("Status").child(setRemoteVerticalReference).setValue(100);
                            }
                            else{
                                myDeviceReference.child(deviceUID).child("Status").child(setRemoteVerticalReference).setValue(upValue);
                            }
                        }
                        else
                            System.out.println("Nope LuL");
                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {
                    }
                });
            }
        });

        downButton.setOnClickListener(new View.OnClickListener(){
            int downValue;
            @Override
            public void onClick(View v){
                currentReference.child("RemoteCount").child("Vertical").setValue(--vertical);
                setCountFunction("Vertical");
                myDeviceReference.child(deviceUID).child("Status").child(setRemoteVerticalReference).addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        if(deviceType.equals("Television")||deviceType.equals("AirConditioner"))
                        {
                            downValue = dataSnapshot.getValue(int.class);
                            downValue--;
                            //Log.e(TAG,"Down down down " +downValue);
                            System.out.println(deviceType+":"+downValue);
                            if(deviceType.equals("AirConditioner") &&downValue<15) {
                                myDeviceReference.child(deviceUID).child("Status").child(setRemoteVerticalReference).setValue(15);
                            }
                            else if(deviceType.equals("Television") &&downValue<0) {
                                myDeviceReference.child(deviceUID).child("Status").child(setRemoteVerticalReference).setValue(0);
                                //Log.e(TAG,"Elif Triggered!!");
                            }
                            else{
                                myDeviceReference.child(deviceUID).child("Status").child(setRemoteVerticalReference).setValue(downValue);
                                //Log.e(TAG,"Else phew!!");
                            }
                        }
                        else
                            System.out.println("Nope LuL");
                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {

                    }
                });
            }
        });

        rightButton.setOnClickListener(new View.OnClickListener()
        {
            int upValue;
            @Override
            public void onClick(View v){
                currentReference.child("RemoteCount").child("Horizontal").setValue(++horizontal);
                setCountFunction("Horizontal");
                myDeviceReference.child(deviceUID).child("Status").child(setRemoteHorizontalReference).addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        if(deviceType.equals("Television")||deviceType.equals("AirConditioner")){
                            upValue = dataSnapshot.getValue(int.class);
                            upValue++;
                            System.out.println(deviceType+":"+upValue);
                            if(deviceType.equals("AirConditioner")&& upValue>=3){
                                myDeviceReference.child(deviceUID).child("Status").child(setRemoteHorizontalReference).setValue(3);
                            }
                            else if(deviceType.equals("Television")&&upValue>=100){
                                myDeviceReference.child(deviceUID).child("Status").child(setRemoteHorizontalReference).setValue(100);
                            }
                            else
                                myDeviceReference.child(deviceUID).child("Status").child(setRemoteHorizontalReference).setValue(upValue);
                        }
                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {

                    }
                });
            }
        });

        leftButton.setOnClickListener(new View.OnClickListener()
        {
            int downValue;
            @Override
            public void onClick(View v){
                currentReference.child("RemoteCount").child("Horizontal").setValue(--horizontal);
                setCountFunction("Horizontal");
                myDeviceReference.child(deviceUID).child("Status").child(setRemoteHorizontalReference).addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        if(deviceType.equals("Television")||deviceType.equals("AirConditioner")){
                            downValue = dataSnapshot.getValue(int.class);
                            downValue--;
                            System.out.println(deviceType+":"+downValue);
                            if(deviceType.equals("AirConditioner")&& downValue<=1){
                                myDeviceReference.child(deviceUID).child("Status").child(setRemoteHorizontalReference).setValue(1);
                            }
                            else if(deviceType.equals("Television")&&downValue<=0){
                                myDeviceReference.child(deviceUID).child("Status").child(setRemoteHorizontalReference).setValue(0);
                            }
                            else
                                myDeviceReference.child(deviceUID).child("Status").child(setRemoteHorizontalReference).setValue(downValue);
                        }
                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {

                    }
                });
            }
        });

        menuButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                currentReference.child("RemoteCount").child("Menu").setValue(menu++);
                setCountFunction("OK");
            }
        });

        videoButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                currentReference.child("RemoteCount").child("Source").setValue(source++);
                setCountFunction("Source");
            }
        });

        powerButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                currentReference.child("RemoteCount").child("Power").setValue(power++);
                setCountFunction("Power");
                if(powerStatus.equals("On"))
                    myDeviceReference.child(deviceUID).child("Status").child("Power").setValue("Off");
                else
                    myDeviceReference.child(deviceUID).child("Status").child("Power").setValue("On");
            }
        });

        okButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                currentReference.child("RemoteCount").child("OK").setValue(ok++);
                setCountFunction("OK");
            }
        });
        // Declare your first fragment here
        return view;
    }
}
