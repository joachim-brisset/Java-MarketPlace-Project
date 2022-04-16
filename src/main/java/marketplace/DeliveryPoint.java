package main.java.marketplace;

public class DeliveryPoint {
	private Adress adress;

	public DeliveryPoint(Adress adress) {
		this.adress = adress;
	}
	
	public Adress getAdress() {
		return adress;
	}

	public void setAdress(Adress adress) {
		this.adress = adress;
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
