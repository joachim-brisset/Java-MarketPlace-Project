package project.gui.models.marketplace;

import javafx.beans.property.DoubleProperty;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleObjectProperty;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import project.gui.Main;
import project.utils.DateUtils;

import java.text.ParseException;
import java.util.*;

public class Contract {

    private final ObjectProperty<Admin> contractor = new SimpleObjectProperty<>();
    private final ObjectProperty<ExternSeller> contractant = new SimpleObjectProperty<>();
    private final DoubleProperty commissionFee = new SimpleDoubleProperty();
    private final ObjectProperty<Date> expirationDate = new SimpleObjectProperty<>();

    public Contract(Admin contractor, ExternSeller contractant, double commissionFee, Date expirationDate) {
        this.contractor.set(contractor);
        this.contractant.set(contractant);
        this.commissionFee.set(commissionFee);
        this.expirationDate.set(expirationDate);
    }

    public Admin getContractor() {
        return contractor.get();
    }
    public ObjectProperty<Admin> contractorProperty() {
        return contractor;
    }
    public void setContractor(Admin contractor) {
        this.contractor.set(contractor);
    }

    public ExternSeller getContractant() {
        return contractant.get();
    }
    public ObjectProperty<ExternSeller> contractantProperty() {
        return contractant;
    }
    public void setContractant(ExternSeller contractant) {
        this.contractant.set(contractant);
    }

    public double getCommissionFee() {
        return commissionFee.get();
    }
    public DoubleProperty commissionFeeProperty() {
        return commissionFee;
    }
    public void setCommissionFee(double commissionFee) {
        this.commissionFee.set(commissionFee);
    }

    public Date getExpirationDate() {
        return expirationDate.get();
    }
    public ObjectProperty<Date> expirationDateProperty() {
        return expirationDate;
    }
    public void setExpirationDate(Date expirationDate) {
        this.expirationDate.set(expirationDate);
    }



    /* ================ move code elesewhere =============== */


    // Database column/fields correspondence
    private static final int ADMIN_ID_FIELD = 0;
    private static final int SELLER_ID_FIELD = 1;
    private static final int COMMISSION_FIELD = 2;
    private static final int EXPIRATION_DATE_FIELD = 3;

    //map for linking Object after.
    public static final HashMap<Integer, Contract> contractById = new HashMap<>();
    public static final HashMap<Contract, Integer> adminIdByContract = new HashMap<>();
    public static final HashMap<Contract,Integer> sellerIdByContract= new HashMap<>();

    /**  registered contract are stored here */
    public static HashMap<ExternSeller, Contract> contractBySeller = new HashMap<>();

    private static int counterID = 0;
    private static final XSSFSheet sheet = Main.workbook.getSheet("contract");


    /**
     * load all contract in marketplace from XLSX file in sheet 'contract' with header in this order : ID, ADMIN_ID, SELLER_ID, COMMISSION, EXPIRATION_DATE
     * @implSpec Seller::load must be executed before
     * @return the number of contract loaded
     * @throws ParseException cannot read date in database
     */
    public static int load() {
        Iterator<Row> rowIt = sheet.rowIterator();
        rowIt.next(); //skip header

        int count = 0;
        while(rowIt.hasNext()) {
            Row row = rowIt.next();

            int id = row.getRowNum();

            int adminid, sellerid;
            try {
                adminid = (int) row.getCell(ADMIN_ID_FIELD).getNumericCellValue();
                sellerid = (int) row.getCell(SELLER_ID_FIELD).getNumericCellValue();
            } catch (NullPointerException e) {
                System.err.println("Contract:" + id + " -> unable to read line ..."); continue;
            }

            double commission = row.getCell(COMMISSION_FIELD).getNumericCellValue();

            Date date;
            try {
                date = DateUtils.DATE_FORMATER.parse(row.getCell(EXPIRATION_DATE_FIELD).getStringCellValue());
            } catch (ParseException e) { throw new RuntimeException("Invalide expiration date for contract's id " + id ); }

            Contract contract = new Contract(id, null, null, commission, date);

            contractById.put(id, contract);
            adminIdByContract.put(contract, adminid);
            sellerIdByContract.put(contract, sellerid);

            if (counterID < id) counterID = id + 1;
            count++;
        }
        return count;
    }

    /**
     * using object/id map, link SelledProduct object with object associated
     */
    public static void linkModels() {
        for (Contract contract : contractBySeller.values()) {
            Admin admin = (Admin) Seller.getSellers().get(adminIdByContract.get(contract));
            if (admin == null) throw new IllegalStateException("admin must not be null");

            ExternSeller seller = (ExternSeller) Seller.getSellers().get(sellerIdByContract.get(contract));
            if (seller == null) throw new IllegalStateException("seller must not be null");

            contractBySeller.put(seller, contract);
        }
    }

    /**
     * unload data in XLSX model Object
     */
    public static void unloadAll() {
        for (Contract contract : contractBySeller.values() ) {
            Row row = Optional.ofNullable(sheet.getRow(contract.id)).orElseGet(() -> sheet.createRow(contract.id));
            Optional.ofNullable(row.getCell(ADMIN_ID_FIELD)).orElseGet( () -> row.createCell(ADMIN_ID_FIELD)).setCellValue(contract.getContractor().getId());
            Optional.ofNullable(row.getCell(SELLER_ID_FIELD)).orElseGet( () -> row.createCell(SELLER_ID_FIELD)).setCellValue(contract.getContractant().getId());
            Optional.ofNullable(row.getCell(COMMISSION_FIELD)).orElseGet( () -> row.createCell(COMMISSION_FIELD)).setCellValue(contract.getCommissionFee());
            Optional.ofNullable(row.getCell(EXPIRATION_DATE_FIELD)).orElseGet( () -> row.createCell(EXPIRATION_DATE_FIELD)).setCellValue(DateUtils.DATE_FORMATER.format(contract.getExpirationDate()));
        }
    }


    private int id;


    private Contract(int id, Admin contractor, ExternSeller contractant, double commissionFee, Date expirationDate) {
        this(contractor,contractant, commissionFee,expirationDate);
        this.id = id;
    }

    public boolean register() {
        if (contractBySeller.containsKey(this.contractant)) return false;

        if (id == 0) this.id = counterID++;
        contractBySeller.put(getContractant(), this);
        return true;
    }


    public void unregister() {
        contractBySeller.remove(getContractant());
    }

    public boolean isContractValid() {
        return getExpirationDate().after(new Date());
    }
}
