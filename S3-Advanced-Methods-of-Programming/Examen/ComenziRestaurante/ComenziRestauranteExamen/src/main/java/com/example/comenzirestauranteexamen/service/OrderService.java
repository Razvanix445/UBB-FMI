package com.example.comenzirestauranteexamen.service;

import com.example.comenzirestauranteexamen.domain.MenuItem;
import com.example.comenzirestauranteexamen.domain.Table;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class OrderService {

    private final Map<Table, List<MenuItem>> selectedMenuItems = new HashMap<>();

    public List<MenuItem> getSelectedMenuItems(Table table) {
        return selectedMenuItems.computeIfAbsent(table, k -> new ArrayList<>());
    }

    public void clearSelectedMenuItems(Table table) {
        selectedMenuItems.computeIfPresent(table, (k, v) -> new ArrayList<>());
    }

    public void addSelectedMenuItem(Table table, MenuItem menuItem) {
        selectedMenuItems.computeIfAbsent(table, k -> new ArrayList<>()).add(menuItem);
    }
}
