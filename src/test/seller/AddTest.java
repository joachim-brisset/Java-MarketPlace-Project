package test.seller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Modality;
import javafx.stage.Stage;
import project.gui.Main;
import project.gui.controllers.seller.AddController;
import project.gui.models.marketplace.Product;
import project.gui.models.marketplace.SelledProduct;
import test.TestController;

public class AddTest extends TestController {

    @Override
    public void test(Stage stage) throws Exception {
    	Stage newStage = new Stage();
    	newStage.initOwner(stage);
    	newStage.initModality(Modality.WINDOW_MODAL);
    	newStage.setResizable(false);

        ObservableList<Product> products = FXCollections.observableArrayList();
        products.add(new Product("#001", "lit", "lit" ));
        products.add(new Product("#002", "lamp", "une lamp"));


        FXMLLoader loader = new FXMLLoader(getClass().getClassLoader().getResource(Main.VIEW_FOLDER + "seller/add.fxml"));
        newStage.setScene(new Scene(loader.load()));

        SelledProduct selledProduct = new SelledProduct();

        AddController controller = loader.getController();
        controller.setCurrentStage(newStage);
        controller.setSelledProduct(selledProduct);
        controller.setProduct(products);

        newStage.showAndWait();
        
        if (controller.isAddClicked()) System.out.println("ok");
    }
}
