package test.common;

import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Modality;
import javafx.stage.Stage;
import project.gui.Main;
import project.gui.controllers.common.MotdepasseController;
import project.gui.models.Account;
import test.TestController;

public class TestPassword extends TestController {

    @Override
    public void test(Stage stage) throws Exception {
    	stage.show();
    	
    	Stage newStage = new Stage();
    	newStage.initOwner(stage);
    	newStage.initModality(Modality.WINDOW_MODAL);
    	
        FXMLLoader loader = new FXMLLoader(getClass().getClassLoader().getResource(Main.VIEW_FOLDER + "common/Motdepasse.fxml"));
        newStage.setScene(new Scene(loader.load()));

        Account account = new Account(0, "login", "pass", true, null);

        MotdepasseController controller = loader.getController();
        controller.setDialogStage(newStage);
        controller.setAccount(account);
        
        newStage.showAndWait();

        System.out.println(account.getPass());
    }
}
