package com.example.bizbot;

import javafx.fxml.Initializable;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;

import java.net.URL;
import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.time.LocalDateTime;
import java.util.Objects;
import java.util.ResourceBundle;

public class CardProductController implements Initializable {
    @FXML
    private Button card_addBtn;

    @FXML
    private AnchorPane card_form;

    @FXML
    private ImageView card_image;

    @FXML
    private Label card_pname;

    @FXML
    private Label card_price;

    @FXML
    private Spinner<Integer> card_spinner;
    private productData prodData;
    private Image image;
    private String prodId;
    private String prod_image;
    private String prod_date;
    private Connection connect;
    private PreparedStatement psmt;
    private ResultSet rset;
    private Alert alert;
    private SpinnerValueFactory<Integer> spin;
    private int quantity;
    private String prod_id;
    private double totalP;
    private double pr;

    public void setData(productData prodData){
        this.prodData = prodData;
        prod_id = prodData.getProduct_id();
        prod_date = String.valueOf(prodData.getDate());
        prod_image = prodData.getImage();
        card_pname.setText(prodData.getProduct_name());
        card_price.setText(String.valueOf(prodData.getPrice()));
        String path = prodData.getImage();
        image = new Image(path, 205,80, false, true);
        card_image.setImage(image);
        pr = prodData.getPrice();
    }

    public void setQuantity(){
        spin = new SpinnerValueFactory.IntegerSpinnerValueFactory(0, 100, 0);
        card_spinner.setValueFactory(spin);
    }

    public void addBtn(){
        mainDashboard mform = new mainDashboard();
        mform.customerID();
        quantity = card_spinner.getValue();
        String check = "";
        String check_available_query = "select status from product where product_id = '"
                + prod_id+ "'";
        connect = DatabaseConnectivity.connectDb();
        try{
            psmt = connect.prepareStatement(check_available_query);
            rset = psmt.executeQuery();
            if(rset.next()){
                check = rset.getString("status");
            }
            if(!Objects.equals(check, "Available") || quantity == 0){
                alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Error message");
                alert.setHeaderText(null);
                alert.setContentText("Item not available");
                alert.showAndWait();
            }
            else{
                int check_stock = 0;
                String check_stock_query = "select stock from product where product_id='"
                        +prod_id+"'";
                psmt = connect.prepareStatement(check_stock_query);
                rset = psmt.executeQuery();
                if(rset.next()){
                    check_stock = rset.getInt("stock");
                }
                if(check_stock< quantity){
                    alert = new Alert(Alert.AlertType.ERROR);
                    alert.setTitle("Error message");
                    alert.setHeaderText(null);
                    alert.setContentText("Item not available. Out of Stock");
                    alert.showAndWait();
                }
                else{
                    String insert_query = "insert into customer"+
                            "(customer_id,product_name,quantity, price, date, em_username) "
                            +"values(?,?,?,?,?,?)";
                    psmt = connect.prepareStatement(insert_query);
                    psmt.setString(1, String.valueOf(data.cID));
                    psmt.setString(2,card_pname.getText());
                    psmt.setString(3,String.valueOf(quantity));
                    totalP = quantity * pr;
                    psmt.setString(4,String.valueOf(totalP));
                    LocalDateTime currentDate = LocalDateTime.now();
                    psmt.setString(5,String.valueOf(currentDate));
                    psmt.setString(6,data.username);
                    psmt.executeUpdate();
                    int updated_stock = check_stock-quantity;
                    prod_image = prod_image.replace("\\", "\\\\");
                    String update_stock_query = "update product set product_name = '"
                            +card_pname.getText()+"',price = "+pr
                            +", status = '"+ check +"', stock = "+ updated_stock+", date = '" +prod_date+"', image = '"+
                            prod_image+"' where product_id = '"+prod_id+"'" ;
                    psmt = connect.prepareStatement(update_stock_query);
                    psmt.executeUpdate();
                    alert = new Alert(Alert.AlertType.INFORMATION);
                    alert.setTitle("Information message");
                    alert.setHeaderText(null);
                    alert.setContentText("Successfully added");
                    alert.showAndWait();
                }

            }
        }
        catch (Exception e){
            System.out.println("Error:"+ e);
        }
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
            setQuantity();
    }
}
