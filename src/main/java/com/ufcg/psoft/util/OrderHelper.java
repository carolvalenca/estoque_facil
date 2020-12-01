package com.ufcg.psoft.util;

import org.springframework.data.domain.Sort;

import java.util.ArrayList;
import java.util.List;

public class OrderHelper {


    public static List<Sort.Order> getSortingOrders(String[] sort){
        List<Sort.Order> orders = new ArrayList<>();
        if(sort[0].contains(",")){
            for(String sortingOrder : sort){
                String[] columnAndDirection = sortingOrder.split(",");
                String column= columnAndDirection[0];
                String direction = columnAndDirection[1];
                orders.add(new Sort.Order(getSortDirection(direction),column));
            }
        }
        else{
            orders.add(new Sort.Order(getSortDirection(sort[1]), sort[0]));
        }

        return orders;
    }

    private static Sort.Direction getSortDirection(String direction){
        if(direction.equalsIgnoreCase("ASC")){
            return Sort.Direction.ASC;
        }
        else if (direction.equalsIgnoreCase("DESC")){
            return Sort.Direction.DESC;
        }

        return Sort.Direction.ASC;
    }
}
