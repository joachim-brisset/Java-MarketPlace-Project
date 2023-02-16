package project.gui.models;

import javafx.beans.property.*;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import project.gui.Main;
import project.gui.models.marketplace.Buyer;
import project.gui.models.marketplace.Seller;
import project.gui.models.utils.User;

import java.util.Iterator;

import org.apache.poi.ss.usermodel.Row;
import project.utils.NotImplementedException;

public class Account {

    private IntegerProperty id = new SimpleIntegerProperty();
    private StringProperty login = new SimpleStringProperty();
    private StringProperty pass = new SimpleStringProperty();
    private boolean connected;

    private ObjectProperty<User> user = new SimpleObjectProperty<>();

    public Account(int id, String login, String pass, boolean connected, User user) {
        this.id.set(id);
        this.login.set(login);
        this.pass.set(pass);
        this.connected = connected;
        this.user.set(user);
    }

    public int getId() {
        return id.get();
    }
    public IntegerProperty idProperty() {
        return id;
    }
    public void setId(int id) {
        this.id.set(id);
    }

    public String getLogin() {
        return login.get();
    }
    public StringProperty loginProperty() {
        return login;
    }
    public void setLogin(String login) {
        this.login.set(login);
    }

    public String getPass() {
        return pass.get();
    }
    public StringProperty passProperty() {
        return pass;
    }
    public void setPass(String pass) {
        this.pass.set(pass);
    }

    public boolean isConnected() {
        return connected;
    }
    public void setConnected(boolean connected) {
        this.connected = connected;
    }

    public User getUser() {
        return user.get();
    }
    public ObjectProperty<User> userProperty() {
        return user;
    }
    public void setUser(User user) {
        this.user.set(user);
    }

    @Override
    public String toString() {
        return "Account{" +
                "id=" + id +
                ", login=" + login +
                ", pass=" + pass +
                ", connected=" + connected +
                ", user=" + user +
                '}';
    }


    // move this code elsewhere

    private static final XSSFSheet sheet = Main.workbook == null ? null : Main.workbook.getSheet("account");

    public static final int LOGIN_FIELD = 0;
    public static final int PASSWORD_FIELD = 1;
    public static final int ACCOUNT_TYPE_FIELD = 2;
    public static final int USER_ID_FIELD = 3;

    public enum  AccountType { ADMIN, SELLER, BUYER, DELIVERER }

    public void signIn() throws AccountNotFound {
        Iterator<Row> rowIt = sheet.rowIterator();
        rowIt.next(); //ignore header

        while (rowIt.hasNext()) {
            Row row = rowIt.next();

            if (row.getCell(LOGIN_FIELD).getStringCellValue().equals(login.get()) && row.getCell(PASSWORD_FIELD).getStringCellValue().equals(pass.get())) {
                id.set(row.getRowNum());
                connected = true;

                int userid = (int) row.getCell(USER_ID_FIELD).getNumericCellValue();

                switch (AccountType.valueOf(row.getCell(ACCOUNT_TYPE_FIELD).getStringCellValue())) {
                    case ADMIN:
                    case SELLER: user.set(Seller.getSellers().get(userid));
                        break;
                    case BUYER: try { user.set(Buyer.buyerById.get(userid)); } catch (Exception e) { throw new RuntimeException(e); }
                        break;
                    case DELIVERER: throw new NotImplementedException();
                }
                System.out.println(user);
                return;
            }
        }
        throw new AccountNotFound();
    }

    public void signUp() throws AccountAlreadyExist {
        Iterator<Row> rowIt = sheet.rowIterator();
        rowIt.next();

        while (rowIt.hasNext()) {
            Row row = rowIt.next();
            if (row.getCell(LOGIN_FIELD).getStringCellValue().equals(login.get())) throw new AccountAlreadyExist();
        }

        Row row = sheet.createRow(sheet.getLastRowNum());
        this.id.set(row.getRowNum());
        this.connected = true;

        row.createCell(LOGIN_FIELD).setCellValue(this.login.get());
        row.createCell(PASSWORD_FIELD).setCellValue(this.pass.get());
        row.createCell(ACCOUNT_TYPE_FIELD).setCellValue("BUYER");
    }

    public void unload() {
        if (isConnected()) {
            //TODO: user.unload();

            Row row = sheet.getRow(this.id.get());
            row.getCell(LOGIN_FIELD).setCellValue(this.login.get());
            row.getCell(PASSWORD_FIELD).setCellValue(this.pass.get());


        }
    }

    public class AccountAlreadyExist extends Exception {}
    public class AccountNotFound extends Exception {}
}
