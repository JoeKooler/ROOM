package com.example.yoons.room;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.HashMap;


/**
 * Created by yoons on 28/03/2018.
 */

public class MainRemoteFragment extends Fragment {

    DatabaseReference currentReference,mydeviceReference;
    FirebaseDatabase firebaseDatabase;
    private String powerStatus;
    private String setRemoteUpDownReference;
    private int uniqueValue;
    private int horizontal;
    private int vertical;
    private int menu;
    private int mode;
    private int ok;
    private int power;
    private int source;

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.recent_device_fragment_layout, container, false);
        Bundle bundle = getArguments();

        final String deviceType = bundle.getString("deviceType");
        String deviceBrand = bundle.getString("deviceBrand");
        String deviceVersion = bundle.getString("deviceVersion");
        final String deviceUID = bundle.getString("deviceUID");

        TextView deviceBrandinRemote = view.findViewById(R.id.deviceBrandinRemote);
        TextView deviceTypeinRemote = view.findViewById(R.id.deviceTypeinRemote);
        final TextView powerStatusTextView = view.findViewById(R.id.powerStatusinRemote);
        final TextView uniqueStatus = view.findViewById(R.id.otherStatusinRemote);

        TextView menuButton = view.findViewById(R.id.menuButtoninRemote);
        TextView videoButton = view.findViewById(R.id.videoButtoninRemote);
        TextView okButton = view.findViewById(R.id.okButtoninRemote);

        ImageView powerButton = view.findViewById(R.id.powerButtoninRemote);
        ImageView upButton = view.findViewById(R.id.upButtoninRemote);
        ImageView downButton = view.findViewById(R.id.downButtoninRemote);
        ImageView leftButton = view.findViewById(R.id.leftButtoninRemote);
        ImageView rightButton = view.findViewById(R.id.rightButtoninRemote);

        firebaseDatabase = FirebaseDatabase.getInstance();
        currentReference = firebaseDatabase.getReference("TempUnit").child("CurrentRemote");
        mydeviceReference = firebaseDatabase.getReference("MyDevice");

        currentReference.child("Device").child("Type").setValue(deviceType);
        currentReference.child("Device").child("Brand").setValue(deviceBrand);
        currentReference.child("Device").child("Version").setValue(deviceVersion);

        setRemoteUpDownReference = "Vertical";

        powerButton.setColorFilter(Color.WHITE);
        upButton.setColorFilter(Color.WHITE);
        downButton.setColorFilter(Color.WHITE);
        leftButton.setColorFilter(Color.WHITE);
        rightButton.setColorFilter(Color.WHITE);





        deviceBrandinRemote.setText(deviceBrand);
        deviceTypeinRemote.setText(deviceType);

        mydeviceReference.child(deviceUID).child("Status").child("Power").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot)
            {
                powerStatus = dataSnapshot.getValue(String.class);
                powerStatusTextView.setText("Power: " + powerStatus);
                System.out.println(powerStatus);
            }

            @Override
            public void onCancelled(DatabaseError databaseError)
            {

            }
        });

        currentReference.child("RemoteCount").orderByKey().startAt("Horizontal").endAt("Vertical").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for(DataSnapshot child : dataSnapshot.getChildren()) {
                    HashMap<Integer, Integer> value = (HashMap<Integer, Integer>) child.getValue();
                    horizontal = value.get("Horizontal");
                    menu = value.get("Menu");
                    mode = value.get("Mode");
                    ok = value.get("OK");
                    power = value.get("Power");
                    source = value.get("Source");
                    vertical = value.get("Vertical");
                } }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

        uniqueStatus.setText("");

        if (deviceType.equals("Television")||deviceType.equals("Airconditioner")){
            mydeviceReference.child(deviceUID).child("Status").child(setRemoteUpDownReference).addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                    uniqueValue = dataSnapshot.getValue(int.class);

                    switch (deviceType) {
                        case "Airconditioner":
                            uniqueStatus.setText(uniqueValue+"°C");
                            break;
                        case "Television":
                            uniqueStatus.setText(uniqueValue+"");
                            break;
                    }
                }


                @Override
                public void onCancelled(DatabaseError databaseError) {

                }
            });
        }

        powerButton.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                currentReference.child("Status").child("Power").setValue(power++);
            }
        });

        upButton.setOnClickListener(new View.OnClickListener()
        {
            int upValue;

            @Override
            public void onClick(View v)
            {
                currentReference.child("Status").child(setRemoteUpDownReference).setValue(vertical++);
                mydeviceReference.child(deviceUID).child("Status").child(setRemoteUpDownReference).addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        if(deviceType.equals("Television")||deviceType.equals("Airconditioner"))
                        {
                            upValue = dataSnapshot.getValue(int.class);
                            upValue++;
                            System.out.println(deviceType+":"+upValue);
                            if(deviceType.equals("Airconditioner") &&upValue>=30) {
                                System.out.println(upValue);
                                mydeviceReference.child(deviceUID).child("Status").child(setRemoteUpDownReference).setValue(30);
                            }
                            else if(deviceType.equals("Television") &&upValue>=100) {
                                mydeviceReference.child(deviceUID).child("Status").child(setRemoteUpDownReference).setValue(100);
                            }
                            else
                            {
                                mydeviceReference.child(deviceUID).child("Status").child(setRemoteUpDownReference).setValue(upValue);
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

        downButton.setOnClickListener(new View.OnClickListener()
        {
            int downValue;
            @Override
            public void onClick(View v)
            {

                currentReference.child("Status").child(setRemoteUpDownReference).setValue(vertical--);
                mydeviceReference.child(deviceUID).child("Status").child(setRemoteUpDownReference).addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        if(deviceType.equals("Television")||deviceType.equals("Airconditioner"))
                        {
                            downValue = dataSnapshot.getValue(int.class);
                            downValue--;
                            System.out.println(deviceType+":"+downValue);
                            if(deviceType.equals("Airconditioner") &&downValue<=15) {
                                System.out.println(downValue);
                                mydeviceReference.child(deviceUID).child("Status").child(setRemoteUpDownReference).setValue(15);
                            }
                            else if(deviceType.equals("Television") &&downValue<=0) {
                                mydeviceReference.child(deviceUID).child("Status").child(setRemoteUpDownReference).setValue(0);
                            }
                            else
                            {
                                mydeviceReference.child(deviceUID).child("Status").child(setRemoteUpDownReference).setValue(downValue);
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
            public void onClick(View v)
            {
                if(deviceType.equals("Television")){
                    currentReference.child("Status").child("Volume").setValue(horizontal++);
                    mydeviceReference.child(deviceUID).child("Status").child("Volume").addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(DataSnapshot dataSnapshot)
                        {
                            upValue = dataSnapshot.getValue(int.class);
                            upValue++;
                            System.out.println(deviceType+":"+upValue);
                            if(upValue>=100) {
                                mydeviceReference.child(deviceUID).child("Status").child("Volume").setValue(100);
                            }
                            else
                            {
                                mydeviceReference.child(deviceUID).child("Status").child("Volume").setValue(upValue);
                            }
                        }

                        @Override
                        public void onCancelled(DatabaseError databaseError) {

                        }
                    });
                }
                else
                    System.out.println("Nope LuL");
            }
        });

        leftButton.setOnClickListener(new View.OnClickListener()
        {
            int downValue;
            @Override
            public void onClick(View v)
            {
                if(deviceType.equals("Television")){
                    currentReference.child("Status").child("Volume").setValue(horizontal--);
                    mydeviceReference.child(deviceUID).child("Status").child("Volume").addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(DataSnapshot dataSnapshot) {
                            downValue = dataSnapshot.getValue(int.class);
                            downValue--;
                            System.out.println(deviceType+":"+downValue);
                            if(downValue<=0){
                                System.out.println(downValue);
                                mydeviceReference.child(deviceUID).child("Status").child("Volume").setValue(0);
                            }
                            else
                            {
                                mydeviceReference.child(deviceUID).child("Status").child("Volume").setValue(downValue);
                            }
                        }

                        @Override
                        public void onCancelled(DatabaseError databaseError) {

                        }
                    });
                }
                else
                    System.out.println("Nope LuL");
            }});

        okButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                currentReference.child("Status").child("OK").setValue(ok++);
            }
        });

        menuButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                currentReference.child("Status").child("Menu").setValue(menu++);
            }
        });

        videoButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                currentReference.child("Status").child("Source").setValue(source++);
            }
        });

    // Declare your first fragment here
        return view;
    }
}
