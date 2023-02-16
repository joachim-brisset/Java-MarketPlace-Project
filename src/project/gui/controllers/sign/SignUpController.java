package project.gui.controllers.sign;

import java.io.IOException;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import project.gui.models.Account;

public class SignUpController {

	@FXML private TextField inputLogin;
	@FXML private PasswordField inputPass1;
	@FXML private PasswordField inputPass2;

	private Stage stage;
	private SignManager manager;
	private Account account;

	@FXML private void handleSwitch(ActionEvent event) throws IOException {
		manager.toggleSignMethod();
	}

	@FXML private void handleValidate(ActionEvent event) throws IOException {
		account.setLogin(inputLogin.getText());

		if (inputPass1.getText().equals(inputPass2.getText())) {
			account.setPass(inputPass1.getText());

			try {
				account.signUp();
			} catch (Account.AccountAlreadyExist e) {
				Alert alert = new Alert(Alert.AlertType.ERROR);
				alert.initOwner(stage);
				alert.setTitle("Invalid Fields");
				alert.setHeaderText("Please correct invalid fields");
				alert.setContentText("An account with this login already exist");

				alert.showAndWait();
			}

			if (account.isConnected()) stage.close();
		} else {
			Alert alert = new Alert(Alert.AlertType.ERROR);
			alert.initOwner(stage);
			alert.setTitle("Invalid Fields");
			alert.setHeaderText("Please correct invalid fields");
			alert.setContentText("You haven't entered the same password");

			alert.showAndWait();
		}
	}

	public void setStage(Stage stage) {
		this.stage = stage;
	}

	public void setAccount(Account account) {
		this.account = account;
	}

	public void setManager(SignManager manager) {
		this.manager = manager;
	}
}
