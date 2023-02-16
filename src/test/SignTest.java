package test;

import javafx.stage.Stage;
import project.gui.controllers.sign.SignManager;
import project.gui.models.Account;

public class SignTest extends TestController{

	@Override
	public void test(Stage stage) throws Exception {

		Account account = new Account(0, "", "", false, null);

		SignManager manager = new SignManager(account, stage);
		manager.showStage();
	}
}
