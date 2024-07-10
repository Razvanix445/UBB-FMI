package com.example.comenzirestauranteexamen.domain;

import java.time.LocalDateTime;
import java.util.List;

public class Order extends Entity<Long> {

    protected Table table;
    protected List<MenuItem> menuItems;
    protected LocalDateTime date;
    protected OrderStatus status;

    public Order(Long id, Table table, List<MenuItem> menuItems, LocalDateTime date, OrderStatus status) {
        super(id);
        this.table = table;
        this.menuItems = menuItems;
        this.date = date;
        this.status = status;
    }

    public Table getTable() {
        return table;
    }

    public void setTable(Table table) {
        this.table = table;
    }

    public List<MenuItem> getMenuItems() {
        return menuItems;
    }

    public void setMenuItems(List<MenuItem> menuItems) {
        this.menuItems = menuItems;
    }

    public LocalDateTime getDate() {
        return date;
    }

    public void setDate(LocalDateTime date) {
        this.date = date;
    }

    public OrderStatus getStatus() {
        return status;
    }

    public void setStatus(OrderStatus status) {
        this.status = status;
    }

    @Override
    public boolean equals(Object obj) {
        return super.equals(obj);
    }

    @Override
    public int hashCode() {
        return super.hashCode();
    }

    @Override
    public String toString() {
        return "Order{" +
                "id=" + id +
                ", table=" + table +
                ", menuItems=" + menuItems +
                ", date=" + date +
                ", status=" + status +
                '}';
    }
}
