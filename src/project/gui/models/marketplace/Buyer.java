package project.gui.models.marketplace;

import javafx.beans.Observable;
import javafx.beans.property.*;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import project.gui.Main;
import project.gui.models.utils.Address;
import project.gui.models.utils.Addressable;
import project.gui.models.utils.User;
import project.utils.NoEntryXSLXException;

import java.io.InvalidObjectException;
import java.text.ParseException;
import java.util.*;

public class Buyer extends User implements Addressable {

    private int id;
    private final ObjectProperty<Cart> cart = new SimpleObjectProperty<>(new Cart(null, null));
    private ObservableList<Order> orders = FXCollections.observableArrayList(
            item -> new Observable[] {
                item.buyerProperty(),
                item.getCartedProducts(),
                item.creationDateProperty(),
                item.deliveryStatusProperty(),
                item.deliveryPointProperty()
    });
    private final BooleanProperty subscribed = new SimpleBooleanProperty(false);
    private final ObjectProperty<Address> address = new SimpleObjectProperty<>(null);

    public Buyer(int id, String firstname, String lastname, Cart cart, List<Order> orders, boolean subscribed, Address address) {
        super(firstname, lastname);

        this.id = id;
        if (cart != null) this.cart.set(cart);
        if (orders != null) this.orders.addAll(orders);
        this.subscribed.set(subscribed);
        this.address.set(address);
        
        this.getCart().setOwner(this);
    }

    public int getId() {
        return id;
    }

    public Cart getCart() {
        return cart.get();
    }
    public ObjectProperty<Cart> cartProperty() {
        return cart;
    }
    public void setCart(Cart cart) {
        this.cart.set(cart);
    }

    public ObservableList<Order> getOrders() {
        return orders;
    }
    public void setOrders(ObservableList<Order> orders) {
        this.orders = orders;
    }

    public boolean isSubscribed() {
        return subscribed.get();
    }
    public BooleanProperty subscribedProperty() {
        return subscribed;
    }
    public void setSubscribed(boolean subscribed) {
        this.subscribed.set(subscribed);
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

    @Override
    public String toString() {
        return "Buyer{" +
                "id=" + id +
                ", cart=" + cart +
                ", orders=" + orders +
                ", subscribed=" + subscribed +
                ", address=" + address +
                '}' + super.toString();
    }



    /* move code elsewhere */

    private static final XSSFSheet sheet = Main.workbook == null ? null : Main.workbook.getSheet("buyer");

    // Database column/fields correspondence
    public static final int FIRSTNAME_FIELD = 0;
    public static final int LASTNAME_FIELD = 1;
    public static final int SUBSCRIBE_FIELD = 2;
    public static final int ADDRESS_FIELD = 3;
    public static final int CART_FIELD = 4;

    // map for linking Object after
    public static final HashMap<Integer, Buyer> buyerById = new HashMap<>();
    public static final HashMap<Buyer, String> stringCartByBuyer = new HashMap<>();

    private static int counterID = 0;

    public static int load() {
        Iterator<Row> rowIt = sheet.rowIterator();
        rowIt.next(); //skip header

        int count = 0;
        while(rowIt.hasNext()) {
            Row row = rowIt.next();

            //TODO: reference Cell should never be null, make something to ensure that
            int id = row.getRowNum();

            String firstname = row.getCell(FIRSTNAME_FIELD).getStringCellValue();
            String lastname = row.getCell(LASTNAME_FIELD).getStringCellValue();
            boolean subsribed = Optional.ofNullable(row.getCell(SUBSCRIBE_FIELD)).orElseGet( () -> row.createCell(SUBSCRIBE_FIELD)).getBooleanCellValue();
            String addressStr = Optional.ofNullable(row.getCell(ADDRESS_FIELD)).orElseGet( () -> row.createCell(ADDRESS_FIELD)).getStringCellValue();
            Address address = null;
            try {
                address = new Address(addressStr);
            } catch (ParseException e) {
                System.err.println("could not parse string to address object. Setting default one"); address = new Address(0, null, null, 0, null, null);
            }
            String stringCart = Optional.ofNullable(row.getCell(CART_FIELD)).orElseGet(() -> row.createCell(CART_FIELD)).getStringCellValue();

            Buyer buyer = new Buyer(id, firstname, lastname, null, null, subsribed, address);
            if(counterID <= id) counterID = id +1;

            buyerById.put(id, buyer);
            stringCartByBuyer.put(buyer, stringCart);

            count++;
        }
        return count;
    }

    public static void linkModels()  {
        for (Map.Entry<Buyer, String> entry : stringCartByBuyer.entrySet()) {
            Cart cart = null;
            try {
                cart = new Cart(null, CartedProduct.unstringSerialize(entry.getValue()));
            } catch (ParseException e) { System.err.println("Could not parse string to cart objecct. setting default"); cart = new Cart(null, null); }

            cart.setOwner(entry.getKey());
            entry.getKey().setCart(cart);
        }
    }

    /** load a buyer user from XLSX file
     * @param userid
     * @return a buyer object
     * @throws NoEntryXSLXException when no entry ine XLSX file has been found
     * TODO: load cart;
     */
    public static Buyer load(int userid) throws NoEntryXSLXException {
        Row row = sheet.getRow(userid);
        if(row == null) throw new NoEntryXSLXException("No buyer with id " + userid + " found");

        String firstName = row.getCell(FIRSTNAME_FIELD) == null ? "" : row.getCell(FIRSTNAME_FIELD).getStringCellValue();
        String lastName = row.getCell(LASTNAME_FIELD) == null ? "" : row.getCell(LASTNAME_FIELD).getStringCellValue();
        boolean subscribe = row.getCell(SUBSCRIBE_FIELD) != null && row.getCell(SUBSCRIBE_FIELD).getBooleanCellValue();
        String addressStr = row.getCell(ADDRESS_FIELD) == null ? "" : row.getCell(ADDRESS_FIELD).getStringCellValue();
        Address address = null;
        try {
            address = new Address(addressStr);
        } catch (ParseException e) {
            System.err.println("could not parse string to address object. Setting default one"); address = new Address(0, null, null, 0, null, null);
        }

        return new Buyer(userid, firstName, lastName, null, null , subscribe, address);
    }

    /** unload data in XSSFSheet
     * @throws InvalidObjectException when trying to unload an invalid object
     * TODO: cart
     */
    public void unload() {
        Row row = sheet.getRow(this.getId());
        if (row == null) row = sheet.createRow(this.getId());

        Optional.ofNullable(row.getCell(FIRSTNAME_FIELD)).orElse(row.createCell(FIRSTNAME_FIELD)).setCellValue(this.getFirstName());
        Optional.ofNullable(row.getCell(LASTNAME_FIELD)).orElse(row.createCell(LASTNAME_FIELD)).setCellValue(this.getLastName());
        Optional.ofNullable(row.getCell(SUBSCRIBE_FIELD)).orElse(row.createCell(SUBSCRIBE_FIELD)).setCellValue(this.subscribed.get());
        Optional.ofNullable(row.getCell(ADDRESS_FIELD)).orElse(row.createCell(ADDRESS_FIELD)).setCellValue(Address.stringSerialize(this.getAddress()));
        Optional.ofNullable(row.getCell(CART_FIELD)).orElse(row.createCell(CART_FIELD)).setCellValue(CartedProduct.stringSerialize(this.cart.get().getCartedProducts()));
    }

    public static void unloadAll() {
        for(Buyer buyer : buyerById.values()) {
            buyer.unload();
        }
    }
}


