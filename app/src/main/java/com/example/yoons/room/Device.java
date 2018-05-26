package com.example.yoons.room;
/**
 * Created by yoons on 15/04/2018.
 */


public class Device {
    String deviceType;
    String deviceBrand;
    String deviceVersion;
    String UID;

    public Device() {}

    public Device(String deviceType, String deviceBrand, String deviceVersion) {
        this.deviceType = deviceType;
        this.deviceBrand = deviceBrand;
        this.deviceVersion = deviceVersion;
    }

    public Device(String deviceType, String deviceBrand, String deviceVersion, String UID) {
        this.deviceType = deviceType;
        this.deviceBrand = deviceBrand;
        this.deviceVersion = deviceVersion;
        this.UID = UID;
    }

    public String getDeviceType() {
        return deviceType;
    }

    public void setDeviceType(String deviceType) {
        this.deviceType = deviceType;
    }

    public String getDeviceBrand() {
        return deviceBrand;
    }

    public void setDeviceBrand(String deviceBrand) {
        this.deviceBrand = deviceBrand;
    }

    public String getDeviceVersion() {
        return deviceVersion;
    }

    public void setDeviceVersion(String deviceVersion) {
        this.deviceVersion = deviceVersion;
    }

    public String getUID() {
        return UID;
    }

    public void setUID(String UID) {
        this.UID = UID;
    }

    public int getImageUrl(){
        switch (deviceType) {
            case "Television":
                return R.drawable.tvicon;
            case "AirConditioner":
            default:
                return R.drawable.airicon;
            case "Projector":
                return R.drawable.projectoricon;
        }
    }

}
