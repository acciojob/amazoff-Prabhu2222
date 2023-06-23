package com.driver;

import org.springframework.stereotype.Repository;

import java.util.*;

@Repository
public class OrderRepository {
  HashMap<String ,Order> orderDb=new HashMap<>();
  HashMap<String,DeliveryPartner> deliveryPartnerDb=new HashMap<>();
  HashMap<String,String> orderPartnerPairDb=new HashMap<>();




    public void addOrder(Order order) {
      orderDb.put(order.getId(), order);
    }

  public void addPartner(String partnerId) {
      DeliveryPartner dp=new DeliveryPartner(partnerId);
      deliveryPartnerDb.put(partnerId,dp);
  }

    public void addOrderPartnerPair(String orderId, String partnerId) {
      //incrementing the count of orders corresponding to the partner id
        if(orderDb.containsKey(orderId) && deliveryPartnerDb.containsKey(partnerId)){
            orderPartnerPairDb.put(orderId,partnerId);
            DeliveryPartner dp1=deliveryPartnerDb.get(partnerId);
            dp1.setNumberOfOrders(dp1.getNumberOfOrders()+1);
        }

    }

  public Order getOrderById(String orderId) {
      return orderDb.get(orderId);
  }

  public DeliveryPartner getPartnerById(String partnerId) {
      return deliveryPartnerDb.get(partnerId);
  }

  public Integer getOrderCountByPartnerId(String partnerId) {
        if(deliveryPartnerDb.containsKey(partnerId)){
            return deliveryPartnerDb.get(partnerId).getNumberOfOrders();
        }
        return 0;

  }

    public List<Order> getOrdersByPartnerId(String partnerId) {
      List<Order> list=new ArrayList<>();
      if(!deliveryPartnerDb.containsKey(partnerId)){
         return list;
      }
      for(String orderId:orderPartnerPairDb.keySet()){
        if(orderPartnerPairDb.get(orderId).equals(partnerId)){
          list.add(orderDb.get(orderId));
        }
      }
      return list;
    }

  public List<String> getAllOrders() {
      List<String> list=new ArrayList<>();
      for(String ele:orderDb.keySet()){
         list.add(orderDb.get(ele).toString());
      }
      return list;
  }

  public Integer getCountOfUnassignedOrders() {
        return orderDb.size()-orderPartnerPairDb.size();
  }

    public Integer getOrdersLeftAfterGivenTimeByPartnerId(int time, String partnerId) {
        int count=0;
        for(String ele:orderPartnerPairDb.keySet()){
            if(orderPartnerPairDb.get(ele).equals(partnerId)){
                int deliveryTime=orderDb.get(ele).getDeliveryTime();
                if(deliveryTime>time) count++;
            }
        }
        return Integer.valueOf(count);


    }

    public int getLastDeliveryTimeByPartnerId(String partnerId) {
        int time=0;
        for(String orderId:orderPartnerPairDb.keySet()){
            if(orderPartnerPairDb.get(orderId).equals(partnerId)){
                int deliveryTime=orderDb.get(orderId).getDeliveryTime();
                if(deliveryTime>time)
                    time=deliveryTime;
            }
        }
        return time;
    }

    public void deletePartnerById(String partnerId) {
        deliveryPartnerDb.remove(partnerId);
        
//        for(String orderId:orderPartnerPairDb.keySet()){
//            if(orderPartnerPairDb.get(orderId).equals(partnerId)){
//                orderPartnerPairDb.remove(orderId);
//            }
//        }


        Set<String> toBeRemoved=new HashSet<>();
        for(String orderId:orderPartnerPairDb.keySet()){
            if(orderPartnerPairDb.get(orderId).equals(partnerId)){
                toBeRemoved.add(orderId);
            }
        }
      for(String ele:toBeRemoved){
          orderPartnerPairDb.remove(ele);
      }


    }

    public void deleteOrderById(String orderId) {
        orderDb.remove(orderId);
//        for(String oid:orderPartnerPairDb.keySet()){
//            if(oid.equals(orderId)){
//                String pid=orderPartnerPairDb.get(oid);
//                DeliveryPartner dp=deliveryPartnerDb.get(pid);
//                dp.setNumberOfOrders(dp.getNumberOfOrders()-1);
//                orderPartnerPairDb.remove(oid);
//            }
//        }
//
        Set<String> toBeRemoved=new HashSet<>();


        for(String oid:orderPartnerPairDb.keySet()){
            if(oid.equals(orderId)){
                toBeRemoved.add(oid);
                if(orderPartnerPairDb.get(oid)!=null){
                    String pid=orderPartnerPairDb.get(oid);
                    DeliveryPartner dp=deliveryPartnerDb.get(pid);
                    dp.setNumberOfOrders((dp.getNumberOfOrders()-1));
                }
            }
        }
        for(String ele:toBeRemoved){
            orderPartnerPairDb.remove(ele);
        }
    }
}
