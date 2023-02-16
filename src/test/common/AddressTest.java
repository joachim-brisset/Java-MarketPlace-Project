package test.common;

import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Modality;
import javafx.stage.Stage;
import project.gui.Main;
import project.gui.controllers.common.EditAddressController;
import project.gui.models.marketplace.Buyer;
import project.gui.models.utils.Address;
import project.gui.models.utils.Addressable;
import test.TestController;

public class AddressTest extends TestController {

    @Override
    public void test(Stage stage) throws Exception {
        stage.show();

        Stage newStage = new Stage();
        newStage.initOwner(stage);
        newStage.initModality(Modality.WINDOW_MODAL);

        FXMLLoader loader = new FXMLLoader(getClass().getClassLoader().getResource(Main.VIEW_FOLDER + "common/EditAdresse.fxml"));
        newStage.setScene(new Scene(loader.load()));

        Addressable account = new Buyer(0, "", "",null, null, false,
                new Address(15, "boulevard du port", "Cergy", 95000, "France", "appart 532")
        );

        EditAddressController controller = loader.getController();
        controller.setAddress(account.getAddress());
        controller.setCurrentStage(newStage);

        newStage.showAndWait();

        if(controller.isOkClicked()) System.out.println(account.getAddress().getStringAddress());
    }
}
