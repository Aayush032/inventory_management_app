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
    @FXML
    private ComboBox<?> combo_sortBy;
    @FXML
    private AnchorPane order_display;
    @FXML
    private AnchorPane customer_display;
    @FXML
    private AnchorPane report_display;

    private String[] statusList = {"Available", "Unavailable"};
    private String[] sortbyList = {"Alphabet", "Order-Date", "Location"};
    public void inventoryStatus(){
        //This is to add list items to the combo-box of the inventory section
        List<String> Inv_status = new ArrayList<>();
        for(String data: statusList){
            Inv_status.add(data);
        }
        ObservableList listData = FXCollections.observableArrayList(Inv_status);
        combo_status.setItems(listData);

    }
    public void sortby(){
        //this is to add list to the combo-box of the order section
        List<String> sort_by = new ArrayList<>();
        for(String data: sortbyList){
            sort_by.add(data);
        }
        ObservableList SortData = FXCollections.observableArrayList(sort_by);
        combo_sortBy.setItems(SortData);
    }
    @Override
    public void initialize(URL location, ResourceBundle resources){
        inventoryStatus();
        sortby();
    }
    public void switchToDashboard(ActionEvent event){
        //this is to switch from inventory to dashboard page
        if(event.getSource() == id_dashboard){
            //when dashboard button is pressed, that anchor pane is set to visible and other sections are made invisible
            dashboard_display.setVisible(true);
            inventory_display.setVisible(false);
            order_display.setVisible(false);
            customer_display.setVisible(false);
            report_display.setVisible(false);
        }
    }
    public void switchToInventory(ActionEvent event){
        //this is to switch from dashboard to inventory page
        if(event.getSource() == id_inventory){
            //when inventory button is pressed, that anchor pane is set to visible and other sections are made invisible
            inventory_display.setVisible(true);
            dashboard_display.setVisible(false);
            order_display.setVisible(false);
            customer_display.setVisible(false);
            report_display.setVisible(false);
        }
    }
    public void switchToOrderList(ActionEvent event){
        //this is to switch to order section
        if(event.getSource()== id_order){
            //when order button is pressed, that anchor pane is set to visible and other sections are made invisible
            order_display.setVisible(true);
            inventory_display.setVisible(false);
            dashboard_display.setVisible(false);
            customer_display.setVisible(false);
            report_display.setVisible(false);
        }
    }
    public void switchToCustomer(ActionEvent event){
        customer_display.setVisible(true);
        order_display.setVisible(false);
        inventory_display.setVisible(false);
        dashboard_display.setVisible(false);
        report_display.setVisible(false);
    }
    public void switchToReport(ActionEvent event){
        report_display.setVisible(true);
        customer_display.setVisible(false);
        order_display.setVisible(false);
        inventory_display.setVisible(false);
        dashboard_display.setVisible(false);
    }
}
