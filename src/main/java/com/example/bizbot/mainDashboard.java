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
import javafx.scene.image.Image;
import javafx.scene.layout.AnchorPane;
import javafx.scene.image.ImageView;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import java.io.File;
import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.ResourceBundle;

public class mainDashboard implements Initializable {
    @FXML
    private Button btn_add;

    @FXML
    private Button btn_clear;

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
    private Image image;
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
    //to merge all the data
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
    //To show data in our table
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
    //Action of add button in the inventory
    public void inventoryAddBtn(){
        if(text_pID.getText().isEmpty() || text_pName.getText().isEmpty() || text_price.getText().isEmpty() ||
                text_stock.getText().isEmpty() || combo_status.getSelectionModel().getSelectedItem() == null || data.path == null){
            alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error message");
            alert.setHeaderText(null);
            alert.setContentText("Please fill all the blank fields");
            alert.showAndWait();
        }
        else{
            String check_prodId_query = "select product_id from product where product_id = '"+text_pID.getText()+"'";
            connect = DatabaseConnectivity.connectDb();
            try{
                smt = connect.createStatement();
                rset = smt.executeQuery(check_prodId_query);
                if(rset.next()){
                    alert = new Alert(Alert.AlertType.ERROR);
                    alert.setTitle("Error message");
                    alert.setHeaderText(null);
                    alert.setContentText(text_pID.getText()+" is already taken");
                    alert.showAndWait();
                }
                else{
                    String insert_data_query = "insert into product"+"(product_id, product_name, stock, status, price, image, date)"+
                            "values (?,?,?,?,?,?,?)";
                    psmt = connect.prepareStatement(insert_data_query);
                    psmt.setString(1,text_pID.getText());
                    psmt.setString(2,text_pName.getText());
                    psmt.setString(3,text_stock.getText());
                    psmt.setString(4,(String)combo_status.getSelectionModel().getSelectedItem());
                    psmt.setString(5,text_price.getText());
                    String path = data.path;
                    path = path.replace("\\","\\\\");
                    psmt.setString(6,path);
                    LocalDateTime currentDate = LocalDateTime.now();
                    psmt.setString(7, String.valueOf(currentDate));
                    psmt.executeUpdate();
                    alert = new Alert(Alert.AlertType.INFORMATION);
                    alert.setTitle("Information message");
                    alert.setHeaderText(null);
                    alert.setContentText("Successfully added ");
                    alert.showAndWait();
                    inventoryShowData();
                    inventoryClearBtn();

                }
            }
            catch (Exception e){
                System.out.println("Error :"+e);
            }
        }
    }
    //to clear the entered data after pressing the clear button
    public void inventoryClearBtn(){
        text_pID.setText("");
        text_pName.setText("");
        text_price.setText("");
        text_stock.setText("");
        data.path = "";
        combo_status.getSelectionModel().clearSelection();
        text_image.setImage(null);
    }

    //Action for import button in the inventory
    public void inventoryImportBtn(){
        FileChooser open_file_path = new FileChooser();
        open_file_path.getExtensionFilters().add(new FileChooser.ExtensionFilter("Open Image file", "*png", "*jpg"));
        File file = open_file_path.showOpenDialog(dashboard_form.getScene().getWindow());
        if(file != null){
            data.path = file.getAbsolutePath();
            // here 180 is the desired width, 155 is the desired height, false is a boolean indicating whether to preserve the aspect ratio, true is a boolean indicating whether to enable smooth scaling
            image = new Image(file.toURI().toString(), 165, 145, false, true );
            text_image.setImage(image);
        }
    }
    @Override
    public void initialize(URL location, ResourceBundle resources){
        displayUsername();
        inventoryStatus();
        sortby();
        inventoryShowData();
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
