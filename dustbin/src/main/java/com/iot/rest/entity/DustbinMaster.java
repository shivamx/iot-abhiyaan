package com.iot.rest.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

@Table(name = "dustbin_master")
@Entity
public class DustbinMaster {

    @Column(name = "device_id")
    private String deviceId;

    @Column(name = "lat")
    private String lat;

    @Column(name = "longitude")
    private String longitude;

    @Column(name = "area")
    private String area;


    @Override
    public String toString() {
        return "DustbinMaster{" +
                "deviceId='" + deviceId + '\'' +
                ", lat='" + lat + '\'' +
                ", longitude='" + longitude + '\'' +
                ", area='" + area + '\'' +
                '}';
    }

}
