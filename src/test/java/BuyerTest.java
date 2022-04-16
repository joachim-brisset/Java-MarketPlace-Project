import marketplace.User;
import marketplace.buyer.Buyer;
import marketplace.buyer.IndividualBuyer;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;

import java.io.IOException;
import java.net.URISyntaxException;

public class BuyerTest {

    public static void main(String[] args) {
        try {
            BuyerTest.test1();
        } catch (IOException | URISyntaxException e) {
            e.printStackTrace();
        } catch (InvalidFormatException e) {
            e.printStackTrace();
        }
    }

    static void test1() throws IOException, InvalidFormatException, URISyntaxException {
        User.userDatabaseFile = BuyerTest.class.getResource("userDatabase.xlsx").toURI().getPath();

        Buyer buyer = new IndividualBuyer();
        buyer.login("login2", "password2");
        System.out.println("subscription test : " + (buyer.isSubscribed() ? "YES" : "NO"));

    }
}
