package project.marketplace;

public class RelayPoint extends DeliveryPoint { 
	private String name; 
	 
	public RelayPoint (Adress adress, String name) { 
		super(adress); 
		this.name = name; 
	} 
 
	public String getName() { 
		return name; 
	} 
 
	public void setName(String name) { 
		this.name = name; 
	} 
	 
	public void received(Order order) { 
		if(order.getReceived() == 1) { 
			System.out.println("Order received");  
		} 
		if(order.getReceived() == 0) { 
			System.out.println("Order not received"); 
		} 
	} 
} 