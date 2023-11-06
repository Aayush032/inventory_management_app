package com.example.bizbot;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;

import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
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
    private TableColumn<productData,String > column_date;

    @FXML
    private TableColumn<productData,String > column_pID;

    @FXML
    private TableColumn<productData,String > column_pName;

    @FXML
    private TableColumn<productData,String > column_price;

    @FXML
    private TableColumn<productData,String > column_status;

    @FXML
    private TableColumn<productData,String > column_stock;

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
    private Button id_signout;

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
    private TableView<productData> inventory_tableview;
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
    @FXML
    private Label dash_username;
    private Alert alert;
    private Connection connect;
    private PreparedStatement psmt;
    private Statement smt;
    private ResultSet rset;

    private String[] statusList = {"Available", "Unavailable"};
    private String[] sortbyList = {"Alphabet", "Order-Date", "Location"};
    //method to display the active username in the menu
    public void displayUsername(){
        String user = data.username;
        user = user.substring(0,1).toUpperCase() + user.substring(1);
        dash_username.setText(user);
    }
    //method to sign out for the current user
    public void signOutBtn(){
        try{
            alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setTitle("Confirmation Message");
            alert.setHeaderText(null);
            alert.setContentText("Are you sure you want to log out?");
            Optional<ButtonType> option = alert.showAndWait();
            if(option.get().equals(ButtonType.OK)){
                //To hide the main form
                id_signout.getScene().getWindow().hide();
                //To move back to login menu signing out the current user
                Parent root = FXMLLoader.load(getClass().getResource("hello-view.fxml"));
                Stage stage = new Stage();
                Scene scene = new Scene(root);
                stage.setTitle("Bizbot Login");
                stage.setScene(scene);
                stage.show();
            }
        }
        catch (Exception e){
            System.out.println("Error"+e);
        }
    }
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
    public ObservableList<productData> inventoryData(){
        ObservableList<productData> data_list = FXCollections.observableArrayList();
        String select_product_query = "select * from product";
        connect = DatabaseConnectivity.connectDb();
        try{
            psmt = connect.prepareStatement(select_product_query);
            rset = psmt.executeQuery();
            productData prod_data;
            while(rset.next()){
                prod_data = new productData(rset.getInt("id"), rset.getString("product_id"),
                        rset.getString("product_name"), rset.getDouble("price"),
                        rset.getString("status"), rset.getString("image"),
                        rset.getDate("date"),rset.getInt("stock"));
                data_list.add(prod_data);
            }
        }
        catch (Exception e){
            System.out.println("Error:"+e);
        }
        return data_list;
    }
    private ObservableList<productData> inventoryListData;
    public void inventoryShowData(){
        inventoryListData = inventoryData();
        column_pID.setCellValueFactory(new PropertyValueFactory<>("product_id"));
        column_pName.setCellValueFactory(new PropertyValueFactory<>("product_name"));
        column_stock.setCellValueFactory(new PropertyValueFactory<>("stock"));
        column_status.setCellValueFactory(new PropertyValueFactory<>("status"));
        column_price.setCellValueFactory(new PropertyValueFactory<>("price"));
        column_date.setCellValueFactory(new PropertyValueFactory<>("date"));
        inventory_tableview.setItems(inventoryListData);
    }
    @Override
    public void initialize(URL location, ResourceBundle resources){
        displayUsername();
        inventoryStatus();
        sortby();
        inventoryData();
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
