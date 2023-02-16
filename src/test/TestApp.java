package test;

import javafx.application.Application;
import javafx.stage.Stage;
import test.buyer.FullTest;
import test.buyer.OrderExplorerTest;
import test.buyer.ProductExplorerTest;
import test.common.AddressTest;
import test.common.EditPersoInfoTest;
import test.common.TestPassword;
import test.seller.AddTest;
import test.seller.EditTest;
import test.seller.MenuTest;

public class TestApp extends Application {

	@Override
	public void start(Stage stage) throws Exception {
		/* ======== Buyer ======== */
		TestController fullTest = new FullTest();
		fullTest.test(stage);
		
		//TestController test1 = new OrderExplorerTest();
		//test1.test(stage);
		
		//TestController test2 = new ProductExplorerTest();
		//test2.test(stage);

		/* ========= common ======== */
		//TestController test3 = new TestPassword();
		//test3.test(stage);

		//TestController test4 = new AddressTest();
		//test4.test(stage);

		//TestController test5 = new EditPersoInfoTest();
		//test5.test(stage);

		//TestController test6 = new SignTest();
		//test6.test(stage);

		//TestController test7 = new AddTest();
		//test7.test(stage);

		//TestController test8 = new MenuTest();
		//test8.test(stage);
		
		//TestController test9 = new EditTest();
		//test9.test(stage);
		
		//System.out.println(DeliveryStatus.DELIVERED + " " + DeliveryStatus.DELIVERED.name() + " " + DeliveryStatus.DELIVERED.toString());
	}

	public static void main(String[] args) {
		launch(args);
	}

}
