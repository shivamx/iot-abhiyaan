package com.iot.rest.controller;

import com.iot.rest.service.DustbinService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class DustbinController {

    @Autowired DustbinService dustbinService;

    @RequestMapping("/get/dustbin")
    @GetMapping
    public String getDustbins() {
        return dustbinService.getDustbins();
    }

    @RequestMapping("/get/dustbins")
    @GetMapping
    public String getDustbinsByPincode(@RequestParam("pincode") Integer pincode) {
        return "pincodeDataCallback("+dustbinService.getDeployedDustbinByPincode(pincode)+")";
    }

    @RequestMapping("/pincode/list")
    @GetMapping
    public String getPincodeList() {
        return "callback("+dustbinService.getPincodeList().toString()+")";
    }

}
