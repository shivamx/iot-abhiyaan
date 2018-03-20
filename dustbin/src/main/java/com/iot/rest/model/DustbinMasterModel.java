package com.iot.rest.model;


public class DustbinMasterModel {

    private String  deviceId;

    private String lat;

    private String longitude;

    private String pincode;

    private String area;

    private String level;

    public String getLevel() {
        return level;
    }

    public void setLevel(String level) {
        this.level = level;
    }

    public String getDeviceId() {
        return deviceId;
    }

    public void setDeviceId(String deviceId) {
        this.deviceId = deviceId;
    }

    public String getLat() {
        return lat;
    }

    public void setLat(String lat) {
        this.lat = lat;
    }

    public String getLongitude() {
        return longitude;
    }

    public void setLongitude(String longitude) {
        this.longitude = longitude;
    }

    public String getPincode() {
        return pincode;
    }

    public void setPincode(String pincode) {
        this.pincode = pincode;
    }

    public String getArea() {
        return area;
    }

    public void setArea(String area) {
        this.area = area;
    }

    @Override
    public String
    toString() {
        return "DustbinMasterModel{" +
                "deviceId='" + deviceId + '\'' +
                ", lat='" + lat + '\'' +
                ", longitude='" + longitude + '\'' +
                ", pincode=" + pincode +
                ", area='" + area + '\'' +
                ", level='" + level + '\'' +
                '}';
    }
}
