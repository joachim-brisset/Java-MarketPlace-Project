package main.java.marketplace;

import java.sql.Date;
import java.util.HashMap;

public class Cart {
	private String name;
	private Date creationDate;
	private double price ;
	private DeliveryPoint deliveryPoint;
	private HashMap <SelledProduct,Integer> cartedProduct;
	
	public Cart(String name , Date creationDate , Double price, DeliveryPoint deliveryPoint, HashMap cartedProduct) {
		this.name = name;
		this.creationDate = creationDate;
		this.price = price;
		this.deliveryPoint = deliveryPoint;
		this.cartedProduct = cartedProduct;
	}
	
	
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public Date getCreationDate() {
		return creationDate;
	}
	public void setCreationDate(Date creationDate) {
		this.creationDate = creationDate;
	}
	public double getPrice() {
		return price;
	}
	public void setPrice(double price) {
		this.price = price;
	}
	public DeliveryPoint getDeliveryPoint() {
		return deliveryPoint;
	}
	public void setDeliveryPoint(DeliveryPoint deliveryPoint) {
		this.deliveryPoint = deliveryPoint;
	}
	
	public void addProduct(SelledProduct productName, int quantity) {
		cartedProduct.put(productName, quantity);
	}
	
	public void save() {
		//TODO//
	}
}
