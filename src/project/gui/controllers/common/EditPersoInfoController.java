package project.gui.controllers.common;

import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Modality;
import javafx.stage.Stage;
import project.gui.Main;
import project.gui.models.Account;
import project.gui.models.marketplace.Buyer;
import project.gui.models.utils.Address;
import project.gui.models.utils.Addressable;
import project.gui.models.utils.User;

import java.util.ArrayList;
import java.util.stream.Collectors;

public class EditPersoInfoController {

    @FXML private Label lblAddress1Line;
    @FXML private Label lblAddress2Line;
    @FXML private Label lblAddress3Line;
    @FXML private TextField inputFirstName;
    @FXML private TextField inputLastName;
    @FXML private CheckBox checkSubscribe;
    @FXML private Button butEditAddress;
    @FXML private Button butCancel;

    private Stage currentStage;
    private boolean okClicked;

    private final ObjectProperty<Account> account = new SimpleObjectProperty<>();
    private final ObjectProperty<Address> address = new SimpleObjectProperty<>();
    		
    public EditPersoInfoController() {
    }

    @FXML private void initialize() {
        account.addListener( (obj) -> {
            User user = account.get().getUser();

            if (user != null) {
            	
                inputFirstName.setText(user.getFirstName());
                inputLastName.setText(user.getLastName());

                if (user instanceof Buyer buyer) {
                    checkSubscribe.setSelected(buyer.isSubscribed());
                    checkSubscribe.setDisable(false);
                } else {
                    checkSubscribe.setDisable(true);
                }

                if (user instanceof Addressable addressable) {
                    butEditAddress.setDisable(false);

                    address.addListener( (obj2) -> {
                        if (address.get() == null || address.get().isEmpty()) {
                            lblAddress1Line.setText("No address available !");
                        } else {
                            lblAddress1Line.setText(address.get().getStreetNumber() + " " +address.get().getStreetName() + "," );
                            lblAddress2Line.setText(address.get().getPostalCode() + " " +address.get().getCityName() + "," + address.get().getCountryName() +";");
                            lblAddress3Line.setText(address.get().getAdditionalInfos());
                        }
                    });
                    address.set(addressable.getAddress());

                } else {
                    butEditAddress.setDisable(true);
                    lblAddress1Line.setText("Address disabled");
                    lblAddress2Line.setText("");
                    lblAddress3Line.setText("");
                }
            }
        });
    }

    @FXML public void handleEditAddress(ActionEvent actionEvent) {
        try {
            Stage newStage = new Stage();
            newStage.initOwner(currentStage);
            newStage.initModality(Modality.WINDOW_MODAL);
            newStage.setResizable(false);

            FXMLLoader loader = new FXMLLoader(getClass().getClassLoader().getResource(Main.VIEW_FOLDER + "common/EditAdresse.fxml"));
            newStage.setScene(new Scene(loader.load()));

            EditAddressController controller = loader.getController();
            Address adr = address.get() != null ? new Address(address.get()) : new Address();
            controller.setAddress(adr);
            controller.setCurrentStage(newStage);

            newStage.showAndWait();
            if (controller.isOkClicked()) address.set(adr);
        } catch (Exception e) { throw new RuntimeException(e); }
    }

    @FXML public void handleEditPassword(ActionEvent actionEvent) {
        try {
            Stage newStage = new Stage();
            newStage.initOwner(currentStage);
            newStage.initModality(Modality.WINDOW_MODAL);
            newStage.setResizable(false);

            FXMLLoader loader = new FXMLLoader(getClass().getClassLoader().getResource(Main.VIEW_FOLDER + "common/Motdepasse.fxml"));
            newStage.setScene(new Scene(loader.load()));

            MotdepasseController controller = loader.getController();
            controller.setDialogStage(newStage);
            controller.setAccount(account.get());

            newStage.showAndWait();
        } catch (Exception e) { throw new RuntimeException(e); }
    }

    @FXML public void handleValidate(ActionEvent actionEvent) {
        if (isInputValid()) {
        	ObjectProperty<User> user = account.get().userProperty();
        	
            user.get().setFirstName(inputFirstName.getText());
            user.get().setLastName(inputLastName.getText());
            if (user.get() instanceof Buyer) ((Buyer) user.get()).setSubscribed(checkSubscribe.isSelected());
            if(user.get() instanceof Addressable) ((Addressable)user.get()).setAddress(address.get());
            okClicked = true;
            currentStage.close();
        }
    }

    @FXML public void handleCancel(ActionEvent event) {
        currentStage.close();
    }

    private boolean isInputValid() {
        ArrayList<String> errorMsg = new ArrayList<>();
        if (inputFirstName.getText() == null || inputFirstName.getText().isBlank()) errorMsg.add("Veuillez entrer votre prenom ! \n");
        if (inputLastName.getText() == null || inputLastName.getText().isBlank()) errorMsg.add("Veuillez entrer votre nom ! \n");
        if (account.get().getUser() instanceof Addressable && address.get() == null)
            errorMsg.add("Veuillez saisir une address ! \n");

        if (errorMsg.isEmpty()) return true;

        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.initOwner(currentStage);
        alert.setTitle("Invalid Fields");
        alert.setHeaderText("Please correct invalid fields");
        alert.setContentText(errorMsg.stream().collect(Collectors.joining()));

        alert.showAndWait();
        return false;
    }


    public void setCurrentStage(Stage currentStage) {
        this.currentStage = currentStage;
    }

    public boolean isOkClicked() {
        return okClicked;
    }

    public void setAccount(Account account) {
        this.account.set(account);
    }
}