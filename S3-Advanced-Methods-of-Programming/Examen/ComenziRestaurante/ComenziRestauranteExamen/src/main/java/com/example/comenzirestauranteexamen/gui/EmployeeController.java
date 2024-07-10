package com.example.comenzirestauranteexamen.gui;

import com.example.comenzirestauranteexamen.domain.MenuItem;
import com.example.comenzirestauranteexamen.domain.Order;
import com.example.comenzirestauranteexamen.events.ChangeEventType;
import com.example.comenzirestauranteexamen.events.OrderChangeEvent;
import com.example.comenzirestauranteexamen.observer.Observer;
import com.example.comenzirestauranteexamen.service.Service;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;

import java.time.LocalDateTime;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

public class EmployeeController implements Observer<OrderChangeEvent> {

    private Service service;
    private Stage stage;

    @FXML
    private TableView<Order> placedOrdersTable;

    @FXML
    private TableColumn<Order, Long> orderIdColumn;

    @FXML
    private TableColumn<Order, LocalDateTime> orderDateColumn;

    @FXML
    private TableColumn<Order, String> orderedItemsColumn;

    @FXML
    private Label welcomeText;

    @FXML
    protected void onHelloButtonClick() {
        welcomeText.setText("Staff");
    }

    public void setService(Service service) {
        this.service = service;
        this.service.addObserver(this);
        initPlacedOrdersTable();
    }

    public void setStage(Stage stage) {
        this.stage = stage;
    }

    private void initPlacedOrdersTable() {
        orderIdColumn.setCellValueFactory(new PropertyValueFactory<>("id"));
        orderDateColumn.setCellValueFactory(new PropertyValueFactory<>("date"));
        orderedItemsColumn.setCellValueFactory(cellData -> {
            List<MenuItem> menuItems = cellData.getValue().getMenuItems();
            return new SimpleStringProperty(concatenateMenuItems(menuItems));
        });
        updatePlacedOrdersTable();
    }

    private void updatePlacedOrdersTable() {
        Iterable<Order> placedOrders = service.getAllOrders();
        ObservableList<Order> placedOrdersList = FXCollections.observableArrayList();
        for (Order order : placedOrders) {
            placedOrdersList.add(order);
        }

        placedOrdersList.sort(Comparator.comparing(Order::getDate));
        placedOrdersTable.setItems(placedOrdersList);
    }

    private String concatenateMenuItems(List<MenuItem> menuItems) {
        return menuItems.stream()
                .map(MenuItem::getItem)
                .collect(Collectors.joining(", "));
    }

    @Override
    public void update(OrderChangeEvent orderChangeEvent) {
        if (orderChangeEvent.getChangeType() == ChangeEventType.ADD) {
            updatePlacedOrdersTable();
        }
    }
}