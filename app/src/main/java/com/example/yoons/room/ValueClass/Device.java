package com.example.yoons.room.ValueClass;
/**
 * Created by yoons on 15/04/2018.
 */


public class Device {
    private String Type;
    private String Brand;
    private String Version;
    private String UID;

    public Device() {}

    public Device(String Type, String Brand, String Version) {
        this.Type = Type;
        this.Brand = Brand;
        this.Version = Version;
    }

    public Device(String Type, String Brand, String Version, String UID) {
        this.Type = Type;
        this.Brand = Brand;
        this.Version = Version;
        this.UID = UID;
    }

    public String getType() {
        return Type;
    }

    public void setType(String Type) {
        this.Type = Type;
    }

    public String getBrand() {
        return Brand;
    }

    public void setBrand(String Brand) {
        this.Brand = Brand;
    }

    public String getVersion() {
        return Version;
    }

    public void setVersion(String Version) {
        this.Version = Version;
    }

    public String getUID() {
        return UID;
    }

    public void setUID(String UID) {
        this.UID = UID;
    }


}
