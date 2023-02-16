package project.gui.controllers.common;

import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import project.gui.models.utils.Address;

import java.util.ArrayList;
import java.util.stream.Collectors;

public class EditAddressController {

    @FXML private TextField inputStreetNumber;
    @FXML private TextField inputStreetName;
    @FXML private TextField inputCityName;
    @FXML private TextField inputCP;
    @FXML private TextField inputCountryName;
    @FXML private TextArea inputAdditionalInfo;

    private int streetNumber;
    private String streetName;
    private String cityName;
    private int postalCode;
    private String countryName;
    private String additionalsInfo;

    private Stage currentStage;
    private final ObjectProperty<Address> address = new SimpleObjectProperty<>();
    private boolean okClicked = false;

    public EditAddressController() {
    }

    @FXML private void initialize() {
        address.addListener( (obj) -> {
            if (address.get().isEmpty()) return;

            inputStreetNumber.textProperty().set(String.valueOf(address.get().getStreetNumber()));
            inputStreetName.textProperty().set(String.valueOf(address.get().getStreetName()));
            inputCityName.textProperty().set(String.valueOf(address.get().getCityName()));
            inputCP.textProperty().set(String.valueOf(address.get().getPostalCode()));
            inputCountryName.textProperty().set(String.valueOf(address.get().getCountryName()));
            inputAdditionalInfo.textProperty().set(String.valueOf(address.get().getAdditionalInfos()));
        });
    }

    @FXML private void handleValidate() {

        if (validInput()) {
            address.get().setStreetNumber(streetNumber);
            address.get().setStreetName(streetName);
            address.get().setCityName(cityName);
            address.get().setPostalCode(postalCode);
            address.get().setCountryName(countryName);
            address.get().setAdditionalInfos(additionalsInfo);
            
            okClicked = true;
            currentStage.close();
        }
    }

    private boolean validInput() {
        ArrayList<String> errorMsg = new ArrayList<>();

        try {
            streetNumber = Integer.parseInt(inputStreetNumber.getText());
            if (streetNumber <= 0) errorMsg.add("Veuillez entrez un numero de rue superieur a 0 \n");
        } catch (NumberFormatException e) { errorMsg.add("Veuillez entrez un nombre pour le nnumero de rue ! \n"); }

        streetName = inputStreetName.getText();
        if (inputStreetName.getText() == null || inputStreetName.getText().isBlank()) errorMsg.add("Veuillez entrez un nom de rue ! \n");
        cityName = inputCityName.getText();
        if (inputCityName.getText() == null || inputCityName.getText().isBlank()) errorMsg.add("Veuillez entrer un nom de ville ! \n");

        try {
            postalCode = Integer.parseInt(inputCP.getText());
            if (postalCode <= 0) errorMsg.add("Veuillez entrez un code postal superieur a 0 ! \n");
        } catch (NumberFormatException e) { errorMsg.add("Veuillez entrez un nombre pour le codePostal ! \n"); }

        countryName = inputCountryName.getText();
        if (inputCountryName.getText() == null || inputCountryName.getText().isBlank()) errorMsg.add("Veuillez entrer un nom de pays ! \n");

        additionalsInfo = inputAdditionalInfo.getText();

        if (errorMsg.isEmpty()) return true;

        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.initOwner(currentStage);
        alert.setTitle("Invalid Fields");
        alert.setHeaderText("Please correct invalid fields");
        alert.setContentText(errorMsg.stream().collect(Collectors.joining()));

        alert.showAndWait();
        return false;
    }

    @FXML private void handleCancel() { currentStage.close(); }



    public void setCurrentStage(Stage currentStage) {
        this.currentStage = currentStage;
    }
    
    public boolean isOkClicked() { return okClicked; }

    public Address getAddress() {
        return address.get();
    }
    public ObjectProperty<Address> addressProperty() {
        return address;
    }
    public void setAddress(Address address) {
        this.address.set(address);
    }
}
