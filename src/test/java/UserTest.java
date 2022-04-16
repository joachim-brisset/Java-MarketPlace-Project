import marketplace.buyer.IndividualBuyer;
import marketplace.User;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.URISyntaxException;
public class UserTest {

    public static void main(String[] args) {
        try {
            UserTest.test1();
            UserTest.test2();
            UserTest.test3();
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    static void test1() throws IOException, URISyntaxException, InvalidFormatException {
        User user = new IndividualBuyer();
        User.userDatabaseFile = UserTest.class.getClassLoader().getResource("userDatabase.xlsx").toURI().getPath();
        boolean logged = user.login("login2", "password2");

        System.out.println("login test :  " + (logged && user.getId() == 1? "YES" : "NO"));
    }

    static void test2() throws IOException, URISyntaxException, InvalidFormatException {

        User user = new IndividualBuyer();
        User.userDatabaseFile = UserTest.class.getClassLoader().getResource("userDatabase.xlsx").toURI().getPath();
        user.login("login2", "password2");
        boolean check1 = user.getFirstName().equals("");
        user.setFirstName("firstName");
        User user2 = new IndividualBuyer();
        user2.login("login2", "password2");
        boolean check2 = user2.getFirstName().equals("firstName");
        System.out.println("change value test : " + (check2 && check2 ? "YES" : "NO"));
        user2.setFirstName("");
    }

    static void test3() throws IOException, InvalidFormatException {
        User user = new IndividualBuyer();
        System.out.println("already signed up test : " + (user.signUp("login1", "") ? "NO" : "YES"));
        System.out.println("sign up test : " + (user.signUp("login3", "password3") ? "YES" : "NO"));

        FileInputStream fis = new FileInputStream(User.userDatabaseFile);
        XSSFWorkbook workbook = new XSSFWorkbook(fis);
        XSSFSheet sheet = workbook.getSheetAt(0);
        sheet.removeRow(sheet.getRow(sheet.getLastRowNum()));
        FileOutputStream fos = new FileOutputStream(User.userDatabaseFile);
        workbook.write(fos);
        fos.close();
    }
}
