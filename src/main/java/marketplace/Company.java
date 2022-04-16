package marketplace;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Company {
	
	private String name;

	public Company(String name) {
		super();
		this.name = name;
	}

	/**
	 * @return the name
	 */
	public String getName() {
		return name;
	}

	/**
	 * @param name the name to set
	 */
	public void setName(String name) {
		if (name.isEmpty()) throw new IllegalArgumentException("Company name must not be empty !");
		this.name = name;
	}
	
	private ArrayList<Parcel> pcl = new ArrayList<>();
	
	public List<Parcel> getpcl() {
		return Collections.unmodifiableList(pcl);
	}
	
	private ArrayList<Professionnal> pro = new ArrayList<>();
	
	public List<Professionnal> getpro() {
		return Collections.unmodifiableList(pro);
	}

}
