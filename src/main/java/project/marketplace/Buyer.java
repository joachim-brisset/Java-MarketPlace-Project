package project.marketplace;

import project.User;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Buyer extends User {

    private int buyerID;
    private Cart cart;
    private ArrayList<Order> orders = new ArrayList<Order>();

    private boolean subscribed;

    public Buyer(int buyerID) {
        this.buyerID = buyerID;
        cart = new Cart(this);
    }

    public Buyer(int buyerID, Cart cart, ArrayList<Order> orders) {
        this.buyerID = buyerID;
        this.cart = cart;
        this.orders = orders;
    }

    public static Buyer load(int userid) {
        //TODO: implement important
        return new Buyer(0);
    }

    public Buyer addProductToCart(SelledProduct selledProduct, int quantity) {
        getCart().addProduct(selledProduct, quantity);
        return this;
    }


    public int getBuyerID() {
        return buyerID;
    }
    private void setBuyerID(int buyerID) {
        this.buyerID = buyerID;
    }
    public Cart getCart() {
        return cart;
    }
    public void setCart(Cart cart) {
        this.cart = cart;
    }
    protected ArrayList<Order> getOrders() {return orders;}
    protected void setOrders(ArrayList<Order> orders) {
        this.orders = orders;
    }
    public boolean isSubscribed() {
        return subscribed;
    }
    public void setSubscribed(boolean subscribed) {
        this.subscribed = subscribed;
    }

    public List<Order> getOrdersList() {
        return Collections.unmodifiableList(orders);
    }
}
