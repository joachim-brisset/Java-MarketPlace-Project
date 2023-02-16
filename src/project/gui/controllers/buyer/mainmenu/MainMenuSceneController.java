package project.gui.controllers.buyer.mainmenu;

import java.io.IOException;

import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.value.ObservableValue;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import project.gui.Main;
import project.gui.models.marketplace.Buyer;
import project.gui.models.marketplace.Cart;

/**
 * @implNote must specify a buyer through setBuyer()
 */
public class MainMenuSceneController {
	
	@FXML private GridPane gridContainer;
	
	private AnchorPane orders;
	private OrdersListComponentController ordersController;
	private AnchorPane cart;
	private CartComponentController cartController;
	
	private ObjectProperty<Buyer> buyer = new SimpleObjectProperty<>();

	/** Constructor */
	public MainMenuSceneController() {
		try {
			FXMLLoader ordersLoader = new FXMLLoader();
			ordersLoader.setLocation(getClass().getClassLoader().getResource(Main.VIEW_FOLDER + "buyer/mainmenu/OrdersList_Component.fxml"));
			orders = ordersLoader.load();
			ordersController = ordersLoader.getController();

			FXMLLoader cartLoader = new FXMLLoader();
			cartLoader.setLocation(getClass().getClassLoader().getResource(Main.VIEW_FOLDER + "buyer/mainmenu/Cart_Component.fxml"));
			cart = cartLoader.load();
			cartController = cartLoader.getController();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	/** Code block executed after FXML load and injection */
	@FXML private void initialize() {

		buyer.addListener( (obj) -> {
			cartController.setCart(buyer.get() == null ? new Cart(null,null) : buyer.get().getCart());
			ordersController.setOrders(buyer.get() == null ? null : buyer.get().getOrders());
		});

		gridContainer.add(orders, 1, 0);
		gridContainer.add(cart, 0,0);
	}

	public Buyer getBuyer() {
		return buyer.get();
	}
	public ObjectProperty<Buyer> buyerProperty() {
		return buyer;
	}
	public void setBuyer(Buyer buyer) {
		this.buyer.set(buyer);
	}
}
