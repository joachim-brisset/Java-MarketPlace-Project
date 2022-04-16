package marketplace.buyer;

import marketplace.utils.Address;
import marketplace.Cart;
import marketplace.Order;
import marketplace.User;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public abstract class Buyer extends User {

    public static int subscribedCol = 4;
    public static int cartCol = 5;
    public static int orderCol = 6;

    private boolean subscribed = false;
    private Cart cart = null;
    private ArrayList<Order> orders = new ArrayList<>();

    public Buyer() {
        super();
    }

    @Override
    public void loadData() throws IOException, InvalidFormatException {
        super.loadData();
        subscribed = getUserData(subscribedCol) == "" ? false : true;
        cart = new Cart(getUserData(cartCol));
        //order = getUserData(orderCol);
        //TODO: get all order by id ?
    }

    public boolean subscribe() throws IOException, InvalidFormatException {
        if (isConnected() && !isSubscribed()) {
            setUserData(subscribedCol, "YES");
            subscribed = true;
            return true;
        }
        return false;
    }

    public boolean unsubscribe() throws IOException, InvalidFormatException {
        if(isConnected() && isSubscribed()) {
            setUserData(subscribedCol, "");
            subscribed = false;
            return true;
        }
        return false;
    }

    public abstract String getBuyerName();
    public abstract Address getBuyerAddress();

    public boolean addProductToCart() {
        //TODO: write function code when pull code
        return false;
    }

    public void validateCart() {
        //TODO
    }

    public boolean isSubscribed() {
        return subscribed;
    }

    public Cart getCart() {
        return cart;
    }

    public void setCart(Cart cart) {
        this.cart = cart;
    }

    public List<Order> getOrders() {
        return Collections.unmodifiableList(orders);
    }
}