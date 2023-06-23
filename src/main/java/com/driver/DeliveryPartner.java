package com.driver;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import static com.fasterxml.jackson.databind.type.LogicalType.Map;

public class DeliveryPartner {

    private String id;
    private int numberOfOrders;

    public DeliveryPartner(String id) {
        this.id = id;
        this.numberOfOrders = 0;
    }

    public String getId() {
        return id;
    }

    public Integer getNumberOfOrders(){
        return numberOfOrders;
    }

    public void setNumberOfOrders(Integer numberOfOrders) {
        this.numberOfOrders = numberOfOrders;
    }


//    public static void main(String[] args) {
//        HashMap<String,String>hm=new HashMap<>();
//        hm.put("1","a");
//        hm.put("2","b");
//        hm.put("3","c");
//        hm.put("4","a");
//        hm.put("5","b");
//
//        for(String ele:hm.keySet()){
//            if(ele.equals("7"))
//                hm.remove(ele);
//        }
//
//        for(String ele: hm.keySet()){
//            System.out.println(ele+" "+hm.get(ele));
//        }
//    }
}