package project.gui.controllers.buyer.orderexplorer;

import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import project.gui.models.marketplace.Order;
import project.utils.DateUtils;

public class OrdersExplorerSceneController {

	@FXML private Label lblOrderID;
	@FXML private Label lblOrderCreationDate;
	@FXML private Label lblOrderStatus;
	@FXML private Label lblOrderPrice;
	
	@FXML private ListView<Order> listOrdersContainer;

	private ObservableList<Order> orderList;

	public OrdersExplorerSceneController() {}

	@FXML private void initialize() {
		//bind ListView in gui to ObservableList in logic code and set custom display
		listOrdersContainer.setItems(orderList);
		listOrdersContainer.setCellFactory(x -> new ListCell<Order>() {
			@Override
			protected void updateItem(Order order, boolean empty) {
				super.updateItem(order, empty);
				setText( empty || order == null ? null : "Commande N°"+order.getReference());
			}
		});

		//on click print info the selected one
		listOrdersContainer.getSelectionModel().selectedItemProperty().addListener(
				(observable, oldValue, newValue) -> printOrderInfo(newValue)
		);
	}

	private void printOrderInfo(Order order) {
		lblOrderID.setText(order.getReference());
		lblOrderCreationDate.setText(DateUtils.DATE_FORMATER.format(order.getCreationDate()));
		lblOrderStatus.setText(order.getDeliveryStatus().name());
		lblOrderPrice.setText("TODO");

		//TODO productList
	}

	public ObservableList<Order> getOrderList() {
		return orderList;
	}

	public void setOrderList(ObservableList<Order> orderList) {
		this.orderList = orderList;
		listOrdersContainer.setItems(orderList);
	}
}
