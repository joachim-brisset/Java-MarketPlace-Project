package marketplace;

import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.*;
import java.util.Iterator;

public abstract class User {

    public static String userDatabaseFile = "database.csv";
    private static XSSFSheet sheet = null;

    public static int loginCol = 0;
    public static int passwordCol = 1;
    public static int firstNameCol = 2;
    public static int familyNameCol = 3;

    public static final int DEFAULT_ID = -1;

    /**
     * Initialize sheet if not and return it.
     * @return
     * @throws IOException
     */
    private static XSSFSheet getSheet() throws IOException, InvalidFormatException {
        if(sheet == null) {
            FileInputStream excelFile = new FileInputStream(userDatabaseFile);
            XSSFWorkbook workbook = new XSSFWorkbook(excelFile);

            excelFile.close();
            sheet = workbook.getSheetAt(0);
        }
        return sheet;
    }




    private int id = DEFAULT_ID;
    private boolean connected = false;

    private String firstName = null;
    private String familyName = null;


    /**
     * fetch a data associated to the User in the database
     * @param col column to get data from
     * @return the data as String
     * @throws IOException if the user is not connected
     */
    public String getUserData(int col) throws IOException, InvalidFormatException {
        if(!isConnected()) {
            //TODO: correct exception ?
            throw new RuntimeException("User not connected");
        }

        XSSFSheet sheet = getSheet();
        XSSFCell cell = sheet.getRow(id).getCell(col);
        return cell != null ? cell.getStringCellValue() : "";
    }

    public void setUserData(int col, String data) throws IOException, InvalidFormatException {
        if(!isConnected()) {
            //TODO: correct exception ?
            throw new RuntimeException("User not connected");
        }

        XSSFSheet sheet = getSheet();
        XSSFCell cell = sheet.getRow(id).getCell(col);
        if(cell == null) {
            cell = sheet.getRow(id).createCell(col);
        }
        cell.setCellValue(data);

        FileOutputStream out = new FileOutputStream(userDatabaseFile);
        sheet.getWorkbook().write(out);
        out.close();
    }

    public void loadData() throws IOException, InvalidFormatException {
        firstName = getUserData(firstNameCol);
        familyName = getUserData(familyNameCol);
    }


    /**
     * log user in
     * @param login identifier string
     * @param password password string
     * @return true if succesfully logged in and false otherwise
     * @throws IOException
     */
    public boolean login(String login, String password) throws IOException, InvalidFormatException {
        XSSFSheet sheet = getSheet();
        Iterator<Row> rowIt = sheet.rowIterator();

        while (rowIt.hasNext()) {
            Row row = rowIt.next();
            if (row.getCell(loginCol).getStringCellValue().equals(login) && row.getCell(passwordCol).getStringCellValue().equals(password)) {
                id = row.getRowNum();
                setConnected(true);

                loadData();
                return true;
            }
        }
        return false;
    }

    /**
     * register user
     * @param login identifier string
     * @param password password string
     * @return true if successfully register and false otherwise
     * @throws IOException
     * @implNote not implemented
     */
    public boolean signUp(String login, String password) throws IOException, InvalidFormatException {
        XSSFSheet sheet = getSheet();
        Iterator<Row> rowIt = sheet.rowIterator();

        while (rowIt.hasNext()) {
            Row row = rowIt.next();
            if (row.getCell(loginCol).getStringCellValue().equals(login)) return false;
        }

        id = sheet.createRow(sheet.getLastRowNum() +1).getRowNum();
        connected = true;
        setUserData(loginCol, login);
        setUserData(passwordCol, password);
        return true;
    }

    public boolean isConnected() {
        return connected;
    }
    private void setConnected(boolean connected) {
        this.connected = connected;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) throws IOException, InvalidFormatException {
        if (isConnected()) {
            setUserData(firstNameCol, firstName);
            this.firstName = firstName;
        }
    }

    public String getFamilyName() {
        return familyName;
    }

    public void setFamilyName(String familyName) throws IOException, InvalidFormatException {
        if (isConnected()) {
            setUserData(familyNameCol, familyName);
            this.familyName = familyName;
        }
    }

    public int getId() {
        return id;
    }
}
