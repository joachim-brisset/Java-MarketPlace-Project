package project.gui.controllers.common;

import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import project.gui.models.Account;

public class MotdepasseController {

    @FXML
    private TextField Ancien;
    @FXML
    private TextField Nouveau;
    @FXML
    private TextField Nouveau1;


    private ObjectProperty<Account> account = new SimpleObjectProperty<>();
   
    private Stage dialogStage;
    private boolean validerClicked = false;

    public MotdepasseController() { }

    /**
     * Initializes the controller class. This method is automatically called
     * after the fxml file has been loaded.
     */
    @FXML private void initialize() { }

    /**
     * Called when the user clicks ok.
     */
    @FXML private void handleValider() {
        if (isInputValid()) {
            account.get().setPass(Nouveau.getText());

            validerClicked = true;
            dialogStage.close();
        } else {
            Ancien.setText("");
            Nouveau.setText("");
            Nouveau1.setText("");
        }
    }

	/**
     * Called when the user clicks cancel.
     */
    @FXML private void handleAnnuler() {
        dialogStage.close();
    }

    /**
     * Validates the user input in the text fields.
     *
     * @return true if the input is valid
     */
    private boolean isInputValid() {
        String errorMessage = "";

        if (Ancien.getText() == null || Ancien.getText().length() == 0) {
        	errorMessage += "Ancien mot de passe vide !\n";
        } else {
	        if (!Ancien.getText().equals(account.get().getPass())) {
	    		errorMessage += "Ancien mot de passe invalide !\n";
	        }
        }
            
        if (Nouveau.getText() == null || Nouveau.getText().length() == 0) {
            errorMessage += "Nouveau mot de passe vide !\n";
        }
        if (Nouveau1.getText() == null || Nouveau1.getText().length() == 0) {
            errorMessage += "Confirmation mot de passe vide \n";
        } else {
        	if ( !Nouveau1.getText().equals(Nouveau.getText())) {
        		errorMessage += "Les nouveaux mot de passe sont différents";
        	}
        }

        if (errorMessage.length() == 0) return true;

        // Show the error message.
        Alert alert = new Alert(AlertType.ERROR);
        alert.initOwner(dialogStage);
        alert.setTitle("Invalid Fields");
        alert.setHeaderText("Please correct invalid fields");
        alert.setContentText(errorMessage);

        alert.showAndWait();

        return false;
    }

	/**
     * Returns true if the user clicked OK, false otherwise.
     *
     * @return
     */
    public boolean isValiderClicked() {
        return validerClicked;
    }

    /**
     * Sets the stage of this dialog.
     *
     * @param dialogStage
     */
    public void setDialogStage(Stage dialogStage) {
        this.dialogStage = dialogStage;
    }

    public Account getAccount() {
        return account.get();
    }
    public ObjectProperty<Account> accountProperty() {
        return account;
    }
    public void setAccount(Account account) {
        this.account.set(account);
    }
}
