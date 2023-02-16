package test.common;

import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Modality;
import javafx.stage.Stage;
import project.gui.Main;
import project.gui.controllers.common.EditPersoInfoController;
import project.gui.models.Account;
import project.gui.models.marketplace.Admin;
import project.gui.models.marketplace.Buyer;
import project.gui.models.utils.User;
import test.TestController;

public class EditPersoInfoTest extends TestController {

    @Override
    public void test(Stage stage) throws Exception {
        stage.show();

        Stage newStage = new Stage();
        newStage.initOwner(stage);
        newStage.initModality(Modality.WINDOW_MODAL);

        FXMLLoader loader = new FXMLLoader(getClass().getClassLoader().getResource(Main.VIEW_FOLDER + "common/EditPersoInfo.fxml"));
        newStage.setScene(new Scene(loader.load()));

        EditPersoInfoController controller = loader.getController();
        //User user = new Buyer(0, null, null, null, null, false, null);
        User user = new Admin(0,0, "", "");

        controller.setAccount(new Account(0, "", "", false, user));
        controller.setCurrentStage(newStage);

        newStage.showAndWait();

       if(controller.isOkClicked()) System.out.println("test");

    }
}
