package com.example.yoons.room.Fragment;

import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.yoons.room.ValueClass.Device;
import com.example.yoons.room.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class DeviceRemoteSelectFragment extends Fragment {

    private String uniqueKey;
    private int patternNumber = 1;
    private int horizontal = 0;
    private int vertical = 0;
    private int menu = 0;
    private int mode = 0;
    private int ok = 0;
    private int power = 0;
    private int source = 0;


    private DatabaseReference maxPatternReference, tempRemoteReference, myDeviceReference;
    private FirebaseDatabase firebaseDatabase;

    private long maxPattern;

    public void setCountFunction(final String countType){
        tempRemoteReference.child("RemoteCount").child(countType).addValueEventListener(new ValueEventListener() {
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

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_remote_select, container, false);

        Bundle bundle = getArguments();
        final String deviceType = bundle.getString("deviceType");
        final String deviceBrand = bundle.getString("deviceBrand");

        ImageView backButtonInSelectBrand = view.findViewById(R.id.backButtonInSelectRemote);
        final ImageView selectRemoteNextButton = view.findViewById(R.id.selectRemoteNextButton);
        final ImageView selectRemoteBackButton = view.findViewById(R.id.selectRemoteBackButton);
        ImageView powerButton = view.findViewById(R.id.selectRemotePower);
        ImageView menuButton = view.findViewById(R.id.selectRemoteMenu);
        ImageView upButton = view.findViewById(R.id.selectRemoteVolumeUp);
        ImageView downButton = view.findViewById(R.id.selectRemoteVolumeDown);
        Button confirmRemoteSelectionButton = view.findViewById(R.id.confirmRemoteSelection);

        TextView deviceBrandTest = view.findViewById(R.id.deviceBrandinRemote);
        TextView deviceTypeTest = view.findViewById(R.id.deviceTypeinRemote);
        TextView downType = view.findViewById(R.id.selectRemoteVolumeDownText);
        final TextView upType = view.findViewById(R.id.selectRemoteVolumeUpText);
        final TextView versionNumberRemoteSelection = view.findViewById(R.id.versionNumberRemoteSelection);

        if(deviceType == "AirConditioner"){
            downType.setText("TEMP DOWN");
            upType.setText("TEMP UP");
        }

        firebaseDatabase = FirebaseDatabase.getInstance();

        tempRemoteReference = firebaseDatabase.getReference("TempUnit").child("CurrentRemote");
        tempRemoteReference.child("Device").child("Brand").setValue(deviceBrand);
        tempRemoteReference.child("Device").child("Type").setValue(deviceType);
        tempRemoteReference.child("Device").child("Version").setValue(""+patternNumber);

        // get reference to 'users' node
        maxPatternReference = firebaseDatabase.getReference("AllVersion").child(deviceType).child(deviceBrand);
        System.out.println(deviceType+""+deviceBrand);

        myDeviceReference = firebaseDatabase.getReference("MyDevice");

        maxPatternReference.addValueEventListener(new ValueEventListener(){
            @Override
            public void onDataChange(DataSnapshot dataSnapshot){
                maxPattern = dataSnapshot.getValue(long.class);
                versionNumberRemoteSelection.setText(patternNumber + "/" + maxPattern);
            }

            @Override
            public void onCancelled(DatabaseError databaseError){

            }
        });



        backButtonInSelectBrand.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                Fragment selectBrandFragment = new SelectBrandFragment();
                Bundle bundle2 = getArguments();
                String tempDeviceType = bundle2.getString("deviceType");
                bundle2.putString("deviceType",tempDeviceType);
                selectBrandFragment.setArguments(bundle2);
                FragmentManager fragmentManager = getFragmentManager();
                FragmentTransaction transaction = fragmentManager.beginTransaction();
                transaction.replace(R.id.content,selectBrandFragment).commit();

            }
        });

        deviceBrandTest.setText(deviceBrand);
        deviceTypeTest.setText(deviceType);

        if (patternNumber == 1)
            selectRemoteBackButton.setColorFilter(Color.WHITE);
        if (maxPattern == 1)
        {
            selectRemoteNextButton.setColorFilter(Color.WHITE);
            selectRemoteBackButton.setColorFilter(Color.WHITE);
        }

        selectRemoteNextButton.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                if(patternNumber<maxPattern){
                    patternNumber++;
                    versionNumberRemoteSelection.setText(patternNumber + "/" + maxPattern);
                    tempRemoteReference.child("Device").child("Version").setValue(patternNumber);
                    selectRemoteBackButton.setColorFilter(Color.BLACK);
                    if(patternNumber>=maxPattern)
                        selectRemoteNextButton.setColorFilter(Color.WHITE);
                }
            }

        });

        selectRemoteBackButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(patternNumber<=maxPattern && patternNumber > 1)
                {
                    patternNumber--;
                    versionNumberRemoteSelection.setText(patternNumber + "/" + maxPattern);
                    tempRemoteReference.child("Device").child("Version").setValue(patternNumber);
                    selectRemoteNextButton.setColorFilter(Color.BLACK);
                    if(patternNumber<=1)
                        selectRemoteBackButton.setColorFilter(Color.WHITE);
                }
            }
        });


        powerButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                tempRemoteReference.child("RemoteCount").child("Power").setValue(power++);
                setCountFunction("Power");
            }
        });

        menuButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                tempRemoteReference.child("RemoteCount").child("Menu").setValue(menu++);
                setCountFunction("OK");
            }
        });

        downButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                tempRemoteReference.child("RemoteCount").child("Vertical").setValue(--vertical);
                setCountFunction("Horizontal");
            }
        });

        upButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v){
                tempRemoteReference.child("RemoteCount").child("Vertical").setValue(++vertical);
                setCountFunction("Horizontal");
            }
        });

        confirmRemoteSelectionButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                uniqueKey = myDeviceReference.push().getKey();
                Device device = new Device(deviceType,deviceBrand,""+patternNumber,uniqueKey);
                myDeviceReference.child(uniqueKey).setValue(device);
                myDeviceReference.child(uniqueKey).child("Status").child("Power").setValue("Off");
                switch (deviceType){
                    case "AirConditioner":{
                        myDeviceReference.child(uniqueKey).child("Status").child("Temperature").setValue(25);
                        myDeviceReference.child(uniqueKey).child("Status").child("Mode").setValue("Freeze");
                        break;
                    }
                    case "Television":{
                        myDeviceReference.child(uniqueKey).child("Status").child("Volume").setValue(50);
                        myDeviceReference.child(uniqueKey).child("Status").child("Channel").setValue(1);
                        break;
                    }
                    case "Projector":{
                        break;
                    }
                }
                Toast.makeText(getContext(),"Setup Complete!",Toast.LENGTH_SHORT).show();
                getActivity().getSupportFragmentManager().popBackStack();
            }
        });

        return view;
    }
}
