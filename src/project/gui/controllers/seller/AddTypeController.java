package project.gui.controllers.seller;

import javafx.collections.transformation.TransformationList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import project.gui.models.marketplace.Product;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class AddTypeController {

    @FXML private TextField inputRef;
    @FXML private TextField inputDesi;
    @FXML private TextField inputDesc;

    private List<String> reference = Product.getProductList().stream().map( Product::getReference ).collect(Collectors.toList());

    private Product product;
    private boolean addClicked = false;
    private Stage currentStage;

    public AddTypeController() { }

    @FXML private void initialize() { }

    @FXML private void handleAdd(ActionEvent actionEvent) {
        if (isInputValid()) {
            addClicked = true;
            currentStage.close();
        }
    }

    private boolean isInputValid() {
        ArrayList<String> errorMsg = new ArrayList<>();

        product.setReference(inputRef.getText());
        if(inputRef.getText().isBlank()) errorMsg.add("Veuillez saisir une reference ! \n");
        if(inputRef.getText() != null && reference.contains(inputRef.getText())) errorMsg.add("La reference exist deja ! \n");

        product.setDesignation(inputDesi.getText());
        if(inputDesi.getText().isBlank()) errorMsg.add("Veuillez saisir une reference ! \n");
        product.setDescription(inputDesc.getText());
        if(inputDesc.getText().isBlank()) errorMsg.add("Veuillez saisir une reference ! \n");

        if (errorMsg.isEmpty()) return true;

        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.initOwner(currentStage);
        alert.setTitle("Invalid Fields");
        alert.setHeaderText("Please correct invalid fields");
        alert.setContentText(errorMsg.stream().collect(Collectors.joining()));

        alert.showAndWait();
        return false;
    }

    @FXML private void handleCancel(ActionEvent actionEvent) {
        currentStage.close();
    }

    public void setProduct(Product product) {
        this.product = product;
    }
    public boolean isAddClicked() {
		return addClicked;
    }

    public void setReference(List<String> reference) {
        this.reference = reference;
    }

    public void setCurrentStage(Stage currentStage) {
        this.currentStage = currentStage;
    }
}
