package com.iot.rest.service;

import java.util.List;

public interface DustbinService {

    String getDustbins();

    String getDeployedDustbinByPincode(Integer pincode);

    List<String> getPincodeList();
}
