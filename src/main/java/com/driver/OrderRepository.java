package com.driver;

import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
@Repository
public class OrderRepository {
    Map<String,Order> orderDb=new HashMap<>();
    Map<String,DeliveryPartner> deliveryPartnerDb=new HashMap<>();
    Map<String,String> orderPartnerDb=new HashMap<>();
    Map<String, List<String>> partnerOrderDb=new HashMap<>();

    public void addOrder(Order order) {
        orderDb.put(order.getId(),order);
    }

    public void addPartner(String partnerId) {
        deliveryPartnerDb.put(partnerId,new DeliveryPartner(partnerId));
    }

    public void addOrderPartnerPair(String orderId, String partnerId) {
        if(orderDb.containsKey(orderId) && deliveryPartnerDb.containsKey(partnerId)){
            orderPartnerDb.put(orderId,partnerId);
            partnerOrderDb.putIfAbsent(partnerId,new ArrayList<String>());
            partnerOrderDb.get(partnerId).add(orderId);
            List<String> list=partnerOrderDb.get(partnerId);

            DeliveryPartner dp=new DeliveryPartner(partnerId);
            dp.setNumberOfOrders(list.size());
        }
    }

    public Order getOrderById(String orderId) {
        return orderDb.get(orderId);
    }

    public DeliveryPartner getPartnerById(String partnerId) {
        return deliveryPartnerDb.get(partnerId);
    }

    public Integer getOrderCountByPartnerId(String partnerId) {
        return partnerOrderDb.get(partnerId).size();
    }

    public List<String> getOrdersByPartnerId(String partnerId) {
      return partnerOrderDb.get(partnerId);
    }

    public List<String> getAllOrders() {
        List<String> list=new ArrayList<>();
        for(String id:orderDb.keySet()){
            list.add(id);
        }
        return list;
    }

    public Integer getCountOfUnassignedOrders() {
        return orderDb.size()-orderPartnerDb.size();
    }

    public Integer getOrdersLeftAfterGivenTimeByPartnerId(int timeValue, String partnerId) {
        int count=0;
        for(String id:partnerOrderDb.get(partnerId)){
            int orderTime=orderDb.get(id).getDeliveryTime();
            if(orderTime>timeValue) count++;
        }
        return count;
    }

    public int getLastDeliveryTimeByPartnerId(String partnerId) {
        int maxTime=0;
        for(String id:partnerOrderDb.get(partnerId)){
            int time=orderDb.get(id).getDeliveryTime();
            if(time>maxTime) maxTime=time;
        }
        return maxTime;
    }

    public void deletePartnerById(String partnerId) {
        deliveryPartnerDb.remove(partnerId);
        List<String> orderList=partnerOrderDb.get(partnerId);
        partnerOrderDb.remove(partnerId);

        for(String ele:orderList){
            orderPartnerDb.remove(ele);
        }

    }

    public void deleteOrderById(String orderId) {
        orderDb.remove(orderId);
        String pid=orderPartnerDb.get(orderId);
        orderPartnerDb.remove(orderId);

        partnerOrderDb.get(pid).remove(orderId);
        deliveryPartnerDb.get(pid).setNumberOfOrders(partnerOrderDb.get(pid).size());
    }
}
