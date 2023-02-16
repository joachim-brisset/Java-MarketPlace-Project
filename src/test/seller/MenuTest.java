package test.seller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;
import project.gui.Main;
import project.gui.controllers.seller.MenuController;
import project.gui.models.marketplace.Product;
import project.gui.models.marketplace.SelledProduct;
import test.TestController;

import java.time.Duration;

public class MenuTest extends TestController {
    @Override
    public void test(Stage stage) throws Exception {
    	
    	Product p1 = new Product("#001", "lampe", "lamp");
    	Product p2 = new Product("#002", "lit", "lit");
    	Product.getProducts().put(0, p1);
    	Product.getProducts().put(1, p2);

        ObservableList<SelledProduct> selledProducts = FXCollections.observableArrayList(
                new SelledProduct(p1, null, 0, 50, 5, 1, Duration.ZERO, 12),
                new SelledProduct(p2, null, 0, 50, 1.5, 1, Duration.ZERO, 32)
        );

        FXMLLoader loader = new FXMLLoader(getClass().getClassLoader().getResource(Main.VIEW_FOLDER + "seller/menu.fxml"));
        stage.setScene(new Scene(loader.load()));

        MenuController controller = loader.getController();
        //controller.setSeller();
        

        stage.show();
    }
}
