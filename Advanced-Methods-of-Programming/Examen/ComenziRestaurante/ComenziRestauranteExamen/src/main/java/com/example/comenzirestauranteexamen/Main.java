package com.example.comenzirestauranteexamen;

import com.example.comenzirestauranteexamen.domain.MenuItem;
import com.example.comenzirestauranteexamen.domain.Order;
import com.example.comenzirestauranteexamen.domain.OrderStatus;
import com.example.comenzirestauranteexamen.domain.Table;
import com.example.comenzirestauranteexamen.repository.Repository;
import com.example.comenzirestauranteexamen.repository.RepositoryDBMenuItems;
import com.example.comenzirestauranteexamen.repository.RepositoryDBOrders;
import com.example.comenzirestauranteexamen.repository.RepositoryDBTables;
import com.example.comenzirestauranteexamen.service.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;

public class Main {

    public static void main(String[] args) {

        String url = "jdbc:postgresql://localhost:1234/comenzirestaurante";
        String username = "postgres";
        String password = "2003razvi";

        Repository<Long, Table> repoTables = new RepositoryDBTables(url, username, password);
        Repository<Long, MenuItem> repoMenuItems = new RepositoryDBMenuItems(url, username, password);
        Repository<Long, Order> repoOrders = new RepositoryDBOrders(url, username, password);
        Service service = new Service(repoTables, repoMenuItems, repoOrders);

        //Order order = new Order(25L, new Table(1L), null, LocalDateTime.now(), OrderStatus.PLACED);
        //repoOrders.save(order);

//        service.getAllTables().forEach(System.out::println);
//        System.out.println();
//        service.getAllMenuItems().forEach(System.out::println);
//        System.out.println();
//        service.getAllOrders().forEach(System.out::println);
    }
}


/*

 */