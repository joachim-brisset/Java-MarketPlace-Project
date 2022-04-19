package project;

import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFSheet;

import project.App;
import project.User;
import project.marketplace.Buyer;
import project.marketplace.Seller;

import java.util.Iterator;

public class Account {

    private static final XSSFSheet sheet = App.workook.getSheet("user");

    private boolean connected = false;
    private int id = -1;
    private String login = "";
    private String pass = "";
    private User user;

    public void signIn() {
        Iterator<Row> rowIt = sheet.rowIterator();
        rowIt.next();

        while (rowIt.hasNext()) {
            Row row = rowIt.next();
            if (row.getCell(1).getStringCellValue().equals(login) && row.getCell(2).getStringCellValue().equals(pass)) {
                id = (int) row.getCell(0).getNumericCellValue();
                connected = true;

                int userid = (int) row.getCell(4).getNumericCellValue();

                switch (row.getCell(3).getStringCellValue()) {
                    case "ADMIN", "SELLER": user = Seller.getSellers().get(userid); break;
                    case "BUYER": user = Buyer.load(userid); break;
                }
            }
        }
    }

    public void signUp() {

    }

    public int getId() {
        return id;
    }
    public boolean isConnected() {
        return connected;
    }
    public void setConnected(boolean connected) {
        this.connected = connected;
    }
    public String getLogin() {
        return login;
    }
    public void setLogin(String login) {
        this.login = login;
    }
    public String getPass() {
        return pass;
    }
    public void setPass(String pass) {
        this.pass = pass;
    }
    public User getUser() {
        return user;
    }
}
