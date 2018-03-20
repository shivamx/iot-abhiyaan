package com.iot.rest.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.iot.rest.entity.DustbinMaster;
import com.iot.rest.model.DustbinMasterModel;
import net.bytebuddy.description.method.ParameterList;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.persistence.Query;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Objects;


@Service
public class DustbinsServiceImpl implements DustbinService{


    @Autowired SessionFactory sessionFactory;

    private Session getCurrentSession(){
        try {
           return sessionFactory.getCurrentSession();
        } catch (HibernateException e) {
            return sessionFactory.openSession();
        }
    }

    @Override
    public String getDustbins() {
        return getDustbinByDeviceId("iotabhiyaan1");
    }

    private String getDustbinByDeviceId(String id){

        return "data from server";
    }


    public String getDeployedDustbinByPincode(Integer pincode) {

        String jsonResult = "";

        List<Object[]> dustbins = getDustbinFromDb(pincode);

        List<DustbinMasterModel> dustbinList = parseResponse(dustbins);

        ObjectMapper objectMapper = new ObjectMapper();

        try {
            jsonResult = objectMapper.writeValueAsString(dustbinList);
        }
        catch (Exception e) {
            System.out.println("Exception while json conversion" + e);
        }

        return jsonResult;
    }

    @Override
    public List<String> getPincodeList() {
        return getPincodesFromDb();
    }

    private  List<Object[]> getDustbinFromDb(Integer pincode){

        List<Object[]> dustbinMasterList = new ArrayList<>();

                Session session = getCurrentSession();

        try {
            Query query = session.createNativeQuery("SELECT udt.device_id as device_id, level as level, lat, longitude, pincode, area FROM \n" +
                    "dustbin_updates udt\n" +
                    "inner join \n" +
                    "dustbin_master mst\n" +
                    "on udt.device_id=mst.device_id\n"+
                    "and pincode in (:pincode)\n" +
                    "ORDER BY udt.device_id ASC")
                    .setParameter("pincode", pincode);

             dustbinMasterList = (List<Object[]>) query.getResultList();

        }
        catch(Exception e) {
            System.out.println(e);
        }
        finally {
            session.close();
        }

        return dustbinMasterList;
    }

    private List<String> getPincodesFromDb(){

        List<String> resultPincode = new ArrayList<>();

        Session session = getCurrentSession();

        Query query = session.createNativeQuery("SELECT distinct pincode from dustbin_master");
        List<BigDecimal> pincodeList = (List<BigDecimal>) query.getResultList();

        session.close();

        for(BigDecimal pincode: pincodeList) {
                resultPincode.add(pincode.toString());
            }


            return resultPincode;
    }

    private  List<DustbinMasterModel> parseResponse( List<Object[]> dustbinMasterList) {

        List<DustbinMasterModel> dustbinMasterModels = new ArrayList<>();

        for(Object[] dustbinObject: dustbinMasterList) {

            DustbinMasterModel dustbinModel = new DustbinMasterModel();
            dustbinModel.setDeviceId(dustbinObject[0].toString());
            dustbinModel.setLevel(dustbinObject[1].toString());
            dustbinModel.setLat(dustbinObject[2].toString());
            dustbinModel.setLongitude(dustbinObject[3].toString());
            dustbinModel.setPincode(dustbinObject[4].toString());
            dustbinModel.setArea(dustbinObject[5].toString());

            dustbinMasterModels.add(dustbinModel);
        }

        return dustbinMasterModels;
    }

}
