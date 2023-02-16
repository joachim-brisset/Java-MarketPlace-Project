package test.buyer;

import java.time.Duration;
import java.util.ArrayList;
import java.util.Arrays;

import javafx.collections.FXCollections;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;
import project.gui.Main;
import project.gui.controllers.buyer.productexplorer.AddProductSceneController;
import project.gui.models.marketplace.ExternSeller;
import project.gui.models.marketplace.Product;
import project.gui.models.marketplace.SelledProduct;
import project.gui.models.marketplace.Seller;
import test.TestController;

public class ProductExplorerTest extends TestController {
	
	private ArrayList<SelledProduct> products = new ArrayList<>(Arrays.asList(
			new SelledProduct(new Product("#AF52092M", "Lampe", "une lampe"), new ExternSeller(0, 0, "sellerfn", "sellerln", "seller1", null), 0, 46845, 15, 0.5, Duration.ZERO, 0),
			new SelledProduct(new Product("#AG65592M", "Lit", "une lit"), new ExternSeller(1,1, "sellerfn", "sellerln", "seller2", null), 0, 46845, 499, 20, Duration.ZERO, 0),
			new SelledProduct(new Product("#AG65592M", "Lit", "une lit"), new ExternSeller(2,2, "sellerfn", "sellerln", "seller2", null), 0, 46845, 499, 20, Duration.ZERO, 0),
			new SelledProduct(new Product("#AG65592M", "Lit", "une lit"), new ExternSeller(3,3, "sellerfn", "sellerln", "seller2", null), 0, 46845, 499, 20, Duration.ZERO, 0),
			new SelledProduct(new Product("#AG65592M", "Lit", "une lit"), new ExternSeller(4,4, "sellerfn", "sellerln", "seller2", null), 0, 46845, 499, 20, Duration.ZERO, 0)
	));

	@Override
	public void test(Stage stage) throws Exception {
		FXMLLoader loader = new FXMLLoader(getClass().getClassLoader().getResource(Main.VIEW_FOLDER + "buyer/productexplorer/ProductExplorer_Scene.fxml"));
        stage.setScene(new Scene(loader.load()));
        stage.show();

        AddProductSceneController controller = loader.getController();
        controller.setProducts(FXCollections.observableArrayList(products));
	}
}
