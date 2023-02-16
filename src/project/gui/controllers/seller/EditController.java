package project.gui.controllers.seller;

import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import project.gui.models.marketplace.SelledProduct;

import java.time.Duration;
import java.util.ArrayList;
import java.util.stream.Collectors;

public class EditController {

    @FXML private TextField inputPrice;
    @FXML private TextField inputStock;
    @FXML private TextField inputShipPrice;
    @FXML private TextField inputShipTime;

    private double price;
    private int stock;
    private double shipPrice;
    private int shipTime;

    private ObjectProperty<SelledProduct> selledProduct = new SimpleObjectProperty<>();
    private Stage currentStage;
    private boolean okClicked = false;

    public EditController() { }

    @FXML private void initialize() {
        selledProduct.addListener( (obj) -> {
            inputPrice.setText(String.valueOf(selledProduct.get().getPrice()));
            inputStock.setText(String.valueOf(selledProduct.get().getStock()));
            inputShipPrice.setText(String.valueOf(selledProduct.get().getShippingPrice()));
            inputShipTime.setText(String.valueOf(selledProduct.get().getShippingTime().toDays()));
        });
    }

    @FXML private void handleCancel(ActionEvent actionEvent) {
        currentStage.close();
    }

    @FXML private void handleValidate(ActionEvent actionEvent) {
        if(isInputValid()) {
            selledProduct.get().setPrice(price);
            selledProduct.get().setStock(stock);
            selledProduct.get().setShippingPrice(shipPrice);
            selledProduct.get().setShippingTime(Duration.ofDays(shipTime));

            okClicked = true;
            currentStage.close();
        }
    }

    private boolean isInputValid() {
        ArrayList<String> errorMsg = new ArrayList<>();

        try {
            price = Double.parseDouble(inputPrice.getText());
        } catch (NumberFormatException e) { errorMsg.add("Veuilez entrez un prix valide (double) ! \n"); }
        if (price <= 0) errorMsg.add("Veuillez entrez un prix superieur a 0 ! \n");

        try {
            stock = Integer.parseInt(inputStock.getText());
        } catch (NumberFormatException e) { errorMsg.add("Veuilez entrez un stock valide (nombre entier) ! \n"); }
        if (stock < 0) errorMsg.add("Veuillez entrez un stock superieur ou egale a 0 ! \n");

        try {
            shipPrice = Double.parseDouble(inputShipPrice.getText());
        } catch (NumberFormatException e) { errorMsg.add("Veuilez entrez un prix de livraison valide (double) ! \n"); }
        if (shipPrice <= 0) errorMsg.add("Veuillez entrez un price superieur a 0 ! \n");

        try {
            shipTime = Integer.parseInt(inputShipTime.getText());
        } catch (NumberFormatException e) { errorMsg.add("Veuilez entrez un stock valide (nombre entier) ! \n"); }
        if (shipTime <= 0) errorMsg.add("Veuillez entrez un stock superieur a 0 ! \n");

        if (errorMsg.isEmpty()) return true;

        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.initOwner(currentStage);
        alert.setTitle("Invalid Fields");
        alert.setHeaderText("Please correct invalid fields");
        alert.setContentText(errorMsg.stream().collect(Collectors.joining()));

        alert.showAndWait();
        return false;
    }

    public ObjectProperty<SelledProduct> selledProductProperty() {
        return selledProduct;
    }
    public void setSelledProduct(SelledProduct selledProduct) {
        this.selledProduct.set(selledProduct);
    }

    public void setCurrentStage(Stage currentStage) {
        this.currentStage = currentStage;
    }

    public boolean isOkClicked() {
        return okClicked;
    }
}
