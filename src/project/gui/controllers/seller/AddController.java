package project.gui.controllers.seller;


import javafx.beans.property.ListProperty;
import javafx.beans.property.SimpleListProperty;
import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.util.StringConverter;
import project.gui.Main;
import project.gui.models.marketplace.Product;
import project.gui.models.marketplace.SelledProduct;

import java.io.IOException;
import java.time.Duration;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;

public class AddController {

    @FXML private TextField inputPrice;
    @FXML private TextField inputStock;
    @FXML private TextField inputShipTime;
    @FXML private ChoiceBox<Product> inputProduct;
    @FXML private TextField inputShipPrice;

    private SelledProduct selledProduct;
    private final ListProperty<Product> product = new SimpleListProperty<>();

    private boolean addClicked = false;
    private Stage currentStage;

    public AddController() { }

    @FXML private void initialize() {
        product.addListener((ListChangeListener<? super Product>) (obj) -> {
        	
        	Random rand = new Random();

            HashMap<String, Product> productByDesignation = new HashMap<>();
            product.forEach( product1 -> productByDesignation.put(product1 == null ? "Product " + rand.nextInt() : product1.getDesignation(), product1));

            inputProduct.setConverter(new StringConverter<>() {
                @Override
                public String toString(Product product) {
                    return product == null ? "Selectionner un produit" : product.getDesignation();
                }

                @Override
                public Product fromString(String s) {
                    return productByDesignation.get(s);
                }
            });
            inputProduct.setItems(product.get());
        });
    }

    @FXML private void handleCancel() {
        currentStage.close();
    }

    @FXML private void handleValidate() {
        if(isInputValid()) {
            addClicked = true;
            currentStage.close();
        }
    }

    private boolean isInputValid() {
        //Bind inputs to selledProduct properties ?
        List<String> errorMsg = new ArrayList<>();

        selledProduct.setProduct(inputProduct.getValue());
        if (selledProduct.getProduct() == null) errorMsg.add("Veuillez choisir un produit dans la list ! \n");

        try {
            selledProduct.setPrice(Integer.parseInt(inputPrice.getText()));
        } catch (NumberFormatException e) { errorMsg.add("Veuillez entrer un nombre comme prix ! \n");}

        try {
            //TODO: parse stock input (ex: k, M...)
            selledProduct.setStock(Integer.parseInt(inputStock.getText()));
        } catch (NumberFormatException e) { errorMsg.add("Veuillez entrer un nombre comme stock ! \n");}

        try {
            selledProduct.setShippingPrice(Integer.parseInt(inputShipPrice.getText()));
        } catch (NumberFormatException e) { errorMsg.add("Veuillez entrer un nombre comme cout de livraison ! \n");}

        try {
            //TODO: Parse duration input
            selledProduct.setShippingTime(Duration.ofDays(Integer.parseInt(inputShipTime.getText())));
        } catch (NumberFormatException e) { errorMsg.add("Veuillez entrer un nombre comme prix ! \n");}

        if (errorMsg.isEmpty()) return true;

        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.initOwner(currentStage);
        alert.setTitle("Invalid Fields");
        alert.setHeaderText("Please correct invalid fields");
        alert.setContentText(errorMsg.stream().collect(Collectors.joining()));

        alert.showAndWait();
        return false;
    }

    @FXML private void handleAddProduct(ActionEvent event) {
        try {
            Stage currentStage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            Stage newStage = new Stage();

            FXMLLoader loader = new FXMLLoader(getClass().getClassLoader().getResource(Main.VIEW_FOLDER + "seller/addType.fxml"));
            BorderPane root = (BorderPane) loader.load();
            AddTypeController controller = loader.getController();

            Product product1 = new Product();
            controller.setProduct(product1);
            controller.setReference(Product.getProducts().values().stream().map(Product::getReference).collect(Collectors.toList()));
            controller.setCurrentStage(newStage);

            newStage.initModality(Modality.WINDOW_MODAL);
            newStage.initOwner(currentStage);
            newStage.setScene(new Scene(root));
            newStage.setResizable(false);
            newStage.showAndWait();

            if(!controller.isAddClicked()) return;
            product.add(product1);
            inputProduct.setValue(product1);
        } catch (IOException e) { throw new RuntimeException(e); }
    }

    public void setSelledProduct(SelledProduct selledProduct) {
        this.selledProduct = selledProduct;
    }

    public ListProperty<Product> productProperty() {
        return product;
    }
    public void setProduct(ObservableList<Product> product) {
        this.product.set(product);
    }

    public boolean isAddClicked() {
        return addClicked;
    }

    public void setCurrentStage(Stage currentStage) {
        this.currentStage = currentStage;
    }
}
