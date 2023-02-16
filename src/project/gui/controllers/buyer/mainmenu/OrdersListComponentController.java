package project.gui.controllers.buyer.mainmenu;

import java.io.IOException;

import javafx.beans.property.ListProperty;
import javafx.beans.property.SimpleListProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.ListView;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Modality;
import javafx.stage.Stage;
import project.gui.Main;
import project.gui.controllers.buyer.orderexplorer.OrdersExplorerSceneController;
import project.gui.models.marketplace.Order;

public class OrdersListComponentController {
	
	@FXML private ListView<Order> listOrderContainer;

	private ListProperty<Order> orders = new SimpleListProperty<>(FXCollections.observableArrayList());
	
	public OrdersListComponentController() { }
	
	@FXML private void handleSeeMore(ActionEvent event) {
		try {
			FXMLLoader loader = new FXMLLoader(getClass().getClassLoader().getResource(Main.VIEW_FOLDER + "buyer/orderexplorer/OrdersExplorer_Scene.fxml"));
			AnchorPane root = (AnchorPane) loader.load();
			OrdersExplorerSceneController controller = loader.getController();
			controller.setOrderList(orders.get());

			Stage currentStage = (Stage) ((Node) event.getSource()).getScene().getWindow();
			Stage newStage = new Stage();

			newStage.initModality(Modality.WINDOW_MODAL);
			newStage.initOwner(currentStage);
			newStage.setScene(new Scene(root));
			newStage.setResizable(false);
			newStage.showAndWait();
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
	}

	public ObservableList<Order> getOrders() {
		return orders.get();
	}
	public ListProperty<Order> ordersProperty() {
		return orders;
	}
	public void setOrders(ObservableList<Order> orders) {
		this.orders.set(orders);
	}
}
