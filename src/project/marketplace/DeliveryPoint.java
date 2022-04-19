package project.marketplace;

public class DeliveryPoint { 
	private Address address; 
 
	
	public DeliveryPoint(Address adress) { 
		this.address = adress; 
	} 
	 
	public Address getAdress() { 
		return address; 
	} 
 
	public void setAdress(Address address) { 
		this.address = address; 
	} 
	 
	public void delivered (Order order){   
		if(order.getDelivered() == 1) { 
			System.out.println("Order delivered");  
		} 
		if(order.getDelivered() == 0) { 
			System.out.println("Order not delivered"); 
		} 
	} 
} 
