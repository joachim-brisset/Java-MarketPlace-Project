
package project.gui.controllers.sign;

import java.io.IOException;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import project.gui.models.Account;

public class SignInController {

	@FXML private TextField inputLogin;
	@FXML private PasswordField inputPass;

	private Stage stage;
	private SignManager manager;
	private Account account;

	@FXML private void handleSwitch(ActionEvent event) throws IOException {
		manager.toggleSignMethod();
	}

	@FXML private void handleValidate(ActionEvent event) {
		account.setLogin(inputLogin.getText());
		account.setPass(inputPass.getText());

		try {
			account.signIn();
		} catch (Account.AccountNotFound e) {
			Alert alert = new Alert(Alert.AlertType.ERROR);
			alert.initOwner(stage);
			alert.setTitle("Invalid Fields");
			alert.setHeaderText("Please correct invalid fields");
			alert.setContentText("Account does not exist");

			alert.showAndWait();
		}

		if (account.isConnected()) stage.close();
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
