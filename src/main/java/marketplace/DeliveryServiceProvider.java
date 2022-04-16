package marketplace;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class DeliveryServiceProvider<S> extends Company {
	
	private ArrayList<Deliverer> deliverers = new ArrayList<>();
	private ArrayList<Company> company = new ArrayList<>();
	private ArrayList<Vehicule> vehicules = new ArrayList<>();

	
	public DeliveryServiceProvider(String name) {
		super(name);
		// TODO Auto-generated constructor stub
	}
	
	public List<Company> getCompany() {
		return Collections.unmodifiableList(company);
	}
	
	public List<Deliverer> getDeliverers() {
		return Collections.unmodifiableList(deliverers);
	}
	
	public List<Vehicule> getVehicules() {
		return Collections.unmodifiableList(vehicules);
	}
	
	
}
