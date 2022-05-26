package project.marketplace;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import project.App;

import java.text.DateFormat;
import java.text.ParseException;
import java.util.*;

/**
 * An object that allow ExternSeller to sell product on the marketplace
 */
public class Contract {

    protected static HashMap<ExternSeller, Contract> contractBySeller = new HashMap<>();
    public static Map<ExternSeller, Contract> getContractList() {
        return Collections.unmodifiableMap(contractBySeller);
    }

    private static int counter = 0;

    /**
     * load all contract in marketplace from xlsx file in sheet 'contract' with header in this order : ID, ADMIN_ID, SELLER_ID, COMMISSION, EXPIRATION_DATE
     * @implSpec Seller::load must be executed before
     * @return the number of contract loaded
     * @throws ParseException cannot read date in database
     */
    public static int load() throws ParseException {
        XSSFSheet sheet = App.workook.getSheet("contract");
        if (sheet == null) throw new IllegalStateException("a 'contract' named sheet must exist");

        Iterator<Row> rowIt = sheet.rowIterator();
        rowIt.next(); //skip header

        int count = 0;
        while(rowIt.hasNext()) {
            Row row = rowIt.next();

            try {
                Cell cell;
                cell = row.getCell(0);
                int id = (int) cell.getNumericCellValue();

                cell = row.getCell(1);
                int adminid = (int) cell.getNumericCellValue();
                Admin admin = (Admin) Seller.getSellers().get(adminid);
                if (admin == null) throw new IllegalStateException("admin must not be null");

                cell = row.getCell(2);
                int sellerid = (int) cell.getNumericCellValue();
                ExternSeller seller = (ExternSeller) Seller.getSellers().get(sellerid);
                if (seller == null) throw new IllegalStateException("seller must not be null");

                cell = row.getCell(3);
                double commission = cell.getNumericCellValue();

                cell = row.getCell(4);
                Date date = DateFormat.getInstance().parse(cell.getStringCellValue());

                Contract contract = new Contract(id, admin, seller, commission, date);
                contractBySeller.put(seller, contract);

                if (counter < id) counter = id + 1;
            }catch (NullPointerException e) {
                System.out.println("Could not read line ");
                continue;
            }
        }
        return count;
    }

    private int id = 0;

    private Admin admin;
    private ExternSeller seller;
    private double commission;
    private Date expirationDate;

    /**
     * Constructor for internal use
     * */
    private Contract(int id, Admin admin, ExternSeller seller, double commission, Date expirationDate) {
        this.admin = admin;
        this.seller = seller;
        this.commission = commission;
        this.expirationDate = expirationDate;
    }

    /**
     * Default constructor set commission to 0.01 (1%) and expirationDate to 1 year later;
     * @param admin the admin that create the contract
     * @param seller the seller concerned by the contract
     */
    protected Contract(Admin admin, ExternSeller seller) {
        this.admin = admin;
        this.seller = seller;
        this.commission = 0.01;

        Calendar calendar = Calendar.getInstance();
        calendar.setTime(new Date());
        calendar.add(Calendar.YEAR, 1);

        this.expirationDate = calendar.getTime();
    }

    /**
     * register contract on the marketplace
     */
    protected void registerContract() {
        this.id = id++;
        contractBySeller.put(this.seller, this);
    }

    /**
     * check whether contract is valid or not
     * @return true if the contract is valid, so the seller can sell product, and false otherwise
     */
    public boolean isValid() {
        return expirationDate.after(new Date());
    }

    public Admin getAdmin() {
        return admin;
    }
    protected void setAdmin(Admin admin) {
        this.admin = admin;
    }
    public ExternSeller getSeller() {
        return seller;
    }
    protected void setSeller(ExternSeller seller) {
        this.seller = seller;
    }
    public double getCommission() {
        return commission;
    }
    public void setCommission(double commission) {
        if(commission < 0) throw new IllegalArgumentException("commission must not be negative");
        this.commission = commission;
    }
    public Date getExpirationDate() {
        return expirationDate;
    }
    public void setExpirationDate(Date expirationDate) {
        this.expirationDate = expirationDate;
    }
}
