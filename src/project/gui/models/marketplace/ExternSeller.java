package project.gui.models.marketplace;

import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.ObservableMap;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import project.gui.Main;
import project.gui.models.utils.Address;
import project.gui.models.utils.Addressable;

import java.text.ParseException;
import java.util.HashMap;
import java.util.Optional;

public class ExternSeller extends Seller implements Addressable {

    private ObservableList<Product> products = FXCollections.observableArrayList();
    private ObservableList<SelledProduct> selledProducts = FXCollections.observableArrayList();
    private final ObjectProperty<Address> address = new SimpleObjectProperty<>();

    public ExternSeller(int id, int idAsSeller, String firstName, String lastName, String name, Address address) {
        super(id, idAsSeller, firstName, lastName, name);
        this.address.set(address);
    }

    @Override
    protected ObservableList<Product> getProductsList() {
        return products;
    }

    @Override
    protected ObservableList<SelledProduct> getSelledProductsList() {
        return selledProducts;
    }

    public Address getAddress() {
        return address.get();
    }
    public ObjectProperty<Address> addressProperty() {
        return address;
    }
    public void setAddress(Address address) {
        this.address.set(address);
    }


    /* =========== move code elsewhere =============*/

    private static final XSSFSheet sheet = Main.workbook.getSheet("extern");

    private static final int ADDRESS_FIELD = 0;

    private static HashMap<ExternSeller,Integer> idByExternSeller = new HashMap<>();
    private static int counterID = 0;

    /** load externSeller with ID */
    public static ExternSeller load(int externsellerid, int idAsSeller, String fn, String ln, String name) {
        Row row = Optional.ofNullable(sheet.getRow(externsellerid)).orElseGet( () -> sheet.createRow(externsellerid));
        String addressStr = Optional.ofNullable(row.getCell(ADDRESS_FIELD)).orElseGet( () -> row.createCell(ADDRESS_FIELD)).getStringCellValue();

        Address address;
        try {
            address = new Address(addressStr);
        } catch (ParseException e) { System.err.println("could not parse string to address object. Setting default one"); address = null; }

        if (counterID < externsellerid) counterID = externsellerid +1;

        ExternSeller extern = new ExternSeller(externsellerid, idAsSeller, fn, ln, name, address);
        idByExternSeller.put(extern, externsellerid);
        return extern;
    }

    public static void unload(ExternSeller extern) {
        Row row = Optional.ofNullable(sheet.getRow(idByExternSeller.get(extern))).orElseGet( () -> sheet.createRow(idByExternSeller.get(extern)));
        Optional.ofNullable(row.getCell(ADDRESS_FIELD)).orElseGet( () -> row.createCell(ADDRESS_FIELD)).setCellValue(Address.stringSerialize(extern.getAddress()));
    }
}
