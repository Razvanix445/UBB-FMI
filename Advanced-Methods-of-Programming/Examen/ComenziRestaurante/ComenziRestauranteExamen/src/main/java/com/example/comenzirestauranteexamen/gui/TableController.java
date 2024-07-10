package com.example.comenzirestauranteexamen.gui;

import com.example.comenzirestauranteexamen.domain.MenuItem;
import com.example.comenzirestauranteexamen.domain.Order;
import com.example.comenzirestauranteexamen.domain.OrderStatus;
import com.example.comenzirestauranteexamen.domain.Table;
import com.example.comenzirestauranteexamen.service.Service;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TitledPane;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import static javafx.scene.control.SelectionMode.MULTIPLE;

public class TableController {

    private Service service;
    private Stage stage;
    private Table table;
    private ObservableList<MenuItem> selectedMenuItems = FXCollections.observableArrayList();

    @FXML
    private VBox menuContainer;

    @FXML
    private Text tableNumber;

    public void setService(Service service) {
        this.service = service;
    }

    public void setStage(Stage stage) {
        this.stage = stage;
    }

    public void setTable(Table table) {
        this.table = table;
        tableNumber.setText("Table " + table.getId());
        Map<String, List<MenuItem>> menuByCategory = service.getMenuItemsByCategory();
        displayMenu(menuByCategory);
    }

    private void displayMenu(Map<String, List<MenuItem>> menuByCategory) {
        for (Map.Entry<String, List<MenuItem>> entry : menuByCategory.entrySet()) {
            String category = entry.getKey();
            List<MenuItem> menuItems = entry.getValue();

            TableView<MenuItem> tableView = new TableView<>();
            TableColumn<MenuItem, String> itemColumn = new TableColumn<>("Item");
            TableColumn<MenuItem, Float> priceColumn = new TableColumn<>("Price");
            TableColumn<MenuItem, String> currencyColumn = new TableColumn<>("Currency");

            itemColumn.setCellValueFactory(new PropertyValueFactory<>("item"));
            priceColumn.setCellValueFactory(new PropertyValueFactory<>("price"));
            currencyColumn.setCellValueFactory(new PropertyValueFactory<>("currency"));

            tableView.getColumns().addAll(itemColumn, priceColumn, currencyColumn);
            tableView.getItems().addAll(menuItems);
            tableView.getSelectionModel().setSelectionMode(MULTIPLE);

            TitledPane titledPane = new TitledPane(category, tableView);
            titledPane.setCollapsible(false);

            menuContainer.getChildren().add(titledPane);
        }
    }

    @FXML
    private void onPlaceOrderButtonClick() {
        List<MenuItem> selectedItems = new ArrayList<>();

        for (Node titledPaneNode : menuContainer.getChildren()) {
            if (titledPaneNode instanceof TitledPane) {
                TitledPane titledPane = (TitledPane) titledPaneNode;
                TableView<MenuItem> tableView = (TableView<MenuItem>) titledPane.getContent();
                selectedItems.addAll(tableView.getSelectionModel().getSelectedItems());
            }
        }

        if (!selectedItems.isEmpty()) {
            Order order = new Order(null, table, new ArrayList<>(selectedItems), LocalDateTime.now(), OrderStatus.PLACED);
            service.addOrder(order);
            selectedItems.clear();
        }
    }
}
