package test.seller;

import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Modality;
import javafx.stage.Stage;
import project.gui.Main;
import project.gui.controllers.seller.EditController;
import project.gui.models.marketplace.SelledProduct;
import test.TestController;

import java.time.Duration;

public class EditTest extends TestController {

    @Override
    public void test(Stage stage) throws Exception {
        Stage newStage = new Stage();
        newStage.initOwner(stage);
        newStage.initModality(Modality.WINDOW_MODAL);
        newStage.setResizable(false);


        FXMLLoader loader = new FXMLLoader(getClass().getClassLoader().getResource(Main.VIEW_FOLDER + "seller/edit.fxml"));
        newStage.setScene(new Scene(loader.load()));

        SelledProduct selledProduct = new SelledProduct(null, null, -1, 85000, 199.99, 0.99, Duration.ofDays(1), 5);

        EditController controller = loader.getController();
        controller.setSelledProduct(selledProduct);
        controller.setCurrentStage(newStage);

        newStage.showAndWait();

        if (controller.isOkClicked()) System.out.println(selledProduct);
    }
}
