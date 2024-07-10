package com.example.comenzirestauranteexamen.service;

import com.example.comenzirestauranteexamen.domain.MenuItem;
import com.example.comenzirestauranteexamen.domain.Order;
import com.example.comenzirestauranteexamen.domain.OrderStatus;
import com.example.comenzirestauranteexamen.domain.Table;
import com.example.comenzirestauranteexamen.events.ChangeEventType;
import com.example.comenzirestauranteexamen.events.OrderChangeEvent;
import com.example.comenzirestauranteexamen.observer.Observer;
import com.example.comenzirestauranteexamen.repository.Repository;
import com.example.comenzirestauranteexamen.observer.Observable;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

public class Service implements Observable<OrderChangeEvent> {

    private List<Observer> observers = new ArrayList<>();

    private Repository<Long, Table> tablesRepository;
    private Repository<Long, MenuItem> menuItemsRepository;
    private Repository<Long, Order> ordersRepository;

    public Service(Repository<Long, Table> tablesRepository, Repository<Long, MenuItem> menuItemsRepository, Repository<Long, Order> ordersRepository) {
        this.tablesRepository = tablesRepository;
        this.menuItemsRepository = menuItemsRepository;
        this.ordersRepository = ordersRepository;
    }

    public Iterable<Table> getAllTables() {
        return tablesRepository.findAll();
    }

    public Iterable<MenuItem> getAllMenuItems() {
        return menuItemsRepository.findAll();
    }

    public Iterable<Order> getAllOrders() {
        return ordersRepository.findAll();
    }

    public void addOrder(Order order) {
        ordersRepository.save(order);
        OrderChangeEvent event = new OrderChangeEvent(ChangeEventType.ADD, order);
        notifyObservers(event);
    }

    public void addTable(Table table) {
        tablesRepository.save(table);
    }

    public void addMenuItem(MenuItem menuItem) {
        menuItemsRepository.save(menuItem);
    }

    public void deleteOrder(Long id) {
        ordersRepository.delete(id);
    }

    public void deleteTable(Long id) {
        tablesRepository.delete(id);
    }

    public void deleteMenuItem(Long id) {
        menuItemsRepository.delete(id);
    }

    public Map<String, List<MenuItem>> getMenuItemsByCategory() {
        Iterable<MenuItem> menuItems = menuItemsRepository.findAll();
        List<MenuItem> menuItemsList = StreamSupport.stream(menuItems.spliterator(), false).toList();
        return menuItemsList.stream().collect(Collectors.groupingBy(MenuItem::getCategory));
    }

    public Iterable<Order> getAllPlacedOrders() {
        Iterable<Order> orders = ordersRepository.findAll();
        List<Order> ordersList = StreamSupport.stream(orders.spliterator(), false).toList();
        return ordersList.stream().filter(order -> order.getStatus().equals(OrderStatus.PLACED)).toList();
    }


    @Override
    public void addObserver(Observer<OrderChangeEvent> e) {
        observers.add(e);
    }

    @Override
    public void removeObserver(Observer<OrderChangeEvent> e) {
        observers.remove(e);
    }

    @Override
    public void notifyObservers(OrderChangeEvent event) {
        for (Observer<OrderChangeEvent> observer: observers) {
            observer.update(event);
        }
    }
}
