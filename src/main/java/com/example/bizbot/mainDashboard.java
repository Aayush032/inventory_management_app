package com.example.bizbot;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.layout.AnchorPane;
import javafx.scene.image.ImageView;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

public class mainDashboard implements Initializable {
    @FXML
    private Button btn_add;

    @FXML
    private Button btn_cleaar;

    @FXML
    private Button btn_delete;

    @FXML
    private Button btn_import;

    @FXML
    private Button btn_update;

    @FXML
    private TableColumn<?, ?> column_pID;

    @FXML
    private TableColumn<?, ?> column_pName;

    @FXML
    private TableColumn<?, ?> column_price;

    @FXML
    private TableColumn<?, ?> column_status;

    @FXML
    private TableColumn<?, ?> column_stock;

    @FXML
    private AnchorPane dashboard_form;

    @FXML
    private Button id_customer;

    @FXML
    private Button id_dashboard;

    @FXML
    private Button id_inventory;

    @FXML
    private Button id_order;

    @FXML
    private Button id_report;

    @FXML
    private AnchorPane inventory_display;

    @FXML
    private AnchorPane inventory_form;
    @FXML
    private ImageView text_image;

    @FXML
    private TextField text_pID;

    @FXML
    private TextField text_pName;
    @FXML
    private AnchorPane dashboard_display;

    @FXML
    private TextField text_price;

    @FXML
    private TextField text_stock;

    @FXML
    private TableView<?> inventory_tableview;
    @FXML
    private ComboBox<?> combo_status;

    private String[] statusList = {"Available", "Unavailable"};
    public void inventoryStatus(){
        //This is to add list items to the combo-box
        List<String> Inv_status = new ArrayList<>();
        for(String data: statusList){
            Inv_status.add(data);
        }
        ObservableList listData = FXCollections.observableArrayList(Inv_status);
        combo_status.setItems(listData);

    }
    @Override
    public void initialize(URL location, ResourceBundle resources){
        inventoryStatus();
    }
    public void switchFromDashboardtoInventory(ActionEvent event){
        //this is to switch from dashboard to inventory page
        if(event.getSource() == id_inventory){
            inventory_display.setVisible(true);
            dashboard_display.setVisible(false);
        }
        //this is to switch from inventory to dashboard page
        if(event.getSource() == id_dashboard){
            dashboard_display.setVisible(true);
            inventory_display.setVisible(false);
        }

    }
}
