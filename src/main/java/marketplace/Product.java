package main.java.marketplace;
public class Product{
	private long iD; 
	private String name;
	private String description;
	
	public Product(long iD, String name, String description) {
		super();
		this.iD = iD;
		this.name = name;
		this.description = description;
	}

	public long getID() {
		return iD;
	}

	public void setID(long iD) {
		this.iD = iD;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	} 
	
	
	
}