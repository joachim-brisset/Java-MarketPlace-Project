package test.buyer;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;
import project.gui.Main;
import project.gui.controllers.buyer.orderexplorer.OrdersExplorerSceneController;
import project.gui.models.delivery.DeliveryStatus;
import project.gui.models.marketplace.Order;
import test.TestController;

import java.util.Date;

public class OrderExplorerTest extends TestController {

    public void test(Stage stage) throws Exception {

        ObservableList<Order> orderList = FXCollections.observableArrayList();
        orderList.addAll(
                new Order( null, DeliveryStatus.WAITING, null, null, new Date()).register(),
                new Order( null, DeliveryStatus.DELIVERED, null, null, new Date()).register()
        );

        FXMLLoader loader = new FXMLLoader(getClass().getClassLoader().getResource(Main.VIEW_FOLDER + "buyer/orderexplorer/OrdersExplorer_Scene.fxml"));
        stage.setScene(new Scene(loader.load()));
        stage.show();

        OrdersExplorerSceneController controller = loader.getController();
        controller.setOrderList(orderList);
    }
}
