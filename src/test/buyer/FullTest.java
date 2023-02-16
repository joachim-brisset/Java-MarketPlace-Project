package test.buyer;

import java.time.Duration;
import java.util.ArrayList;
import java.util.Arrays;

import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import project.gui.Main;
import project.gui.controllers.buyer.mainmenu.MainMenuSceneController;
import project.gui.models.marketplace.*;
import test.TestController;

public class FullTest extends TestController {

	@Override
	public void test(Stage stage) throws Exception {
		FXMLLoader loader = new FXMLLoader();
		loader.setLocation(getClass().getClassLoader().getResource(Main.VIEW_FOLDER + "buyer/mainmenu/MainMenu_Scene.fxml"));
		AnchorPane root = loader.load();
		MainMenuSceneController controller = loader.getController();
		
		stage.setScene(new Scene(root, 800, 500));
		stage.show();
		
		Seller s1 = new ExternSeller(0,0, "sellerfn", "sellerln", "seller1", null);
		Seller s2 = new ExternSeller(1,1, "sellerfn", "sellerln", "seller2", null);
		
		Product p1 = new Product("#AF52092M", "Lampe", "une lampe");
		Product p2 = new Product("#AG65592M", "Lit", "une lit");
		
		SelledProduct sp1 = new SelledProduct(p1, s1, 0, 46845, 15, 0.5, Duration.ZERO, 0);
		SelledProduct sp2 = new SelledProduct(p2, s2, 0, 46845, 499, 20, Duration.ZERO, 0);
		
		SelledProduct.getSelledProductList().addAll(sp1, sp2);

		Buyer buyer = new Buyer(0, "", "",
				new Cart(null, new ArrayList<CartedProduct>(Arrays.asList(new CartedProduct(sp1, 7)))),
				new ArrayList<>(),false, null);
		buyer.getCart().setOwner(buyer);


		controller.setBuyer(buyer);
	}

}
