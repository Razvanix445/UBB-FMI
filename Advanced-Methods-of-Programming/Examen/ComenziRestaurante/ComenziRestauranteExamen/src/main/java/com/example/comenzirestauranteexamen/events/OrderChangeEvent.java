package com.example.comenzirestauranteexamen.events;

import com.example.comenzirestauranteexamen.domain.Order;

public class OrderChangeEvent implements Event {

    private final ChangeEventType changeType;
    private final Order affectedOrder;

    public OrderChangeEvent(ChangeEventType changeType, Order affectedOrder) {
        this.changeType = changeType;
        this.affectedOrder = affectedOrder;
    }

    public ChangeEventType getChangeType() {
        return changeType;
    }

    public Order getAffectedOrder() {
        return affectedOrder;
    }
}
