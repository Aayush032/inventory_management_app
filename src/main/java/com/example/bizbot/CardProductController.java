package com.example.bizbot;

import javafx.fxml.Initializable;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.Spinner;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;

import java.net.URL;
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
    private Spinner<?> card_spinner;
    private productData prodData;
    private Image image;
    public void setData(productData prodData){
        this.prodData = prodData;
        card_pname.setText(prodData.getProduct_name());
        card_price.setText(String.valueOf(prodData.getPrice()));
        String path = prodData.getImage();
        image = new Image(path, 205,80, false, true);
        card_image.setImage(image);
    }


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

    }
}
