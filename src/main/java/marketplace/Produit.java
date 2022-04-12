package main.java.marketplace;

public class Produit{
	private String designation; 
	private String description;
	private double prix;
	private double id_produit;
	private double delai;
	
	public Produit(String designation, String descritpion, double prix, double id_produit, double delai ) {
		this.designation= designation; 
		this.description= description;
		this.prix= prix; 
		this.id_produit= id_produit; 
		this.delai= delai;
	}
	public String getdesignation() {
		return designation;
	}
	
	public void setdesignation(String designation) {
		this.designation= designation;
	}
	
	public  String getdescription() {
		return description;
	}
	
	public void setdescription(String description) {
		this.description = description;
	}
	
	public double getprix() {
		return prix;
		
	}
	
	public void setprix(double prix) {
		this.prix = prix;
	}
	
	public double getid_produit() {
		return id_produit;
		
	}
	
	public void setid_produit(double id_produit) {
		this.id_produit = id_produit;
	}
	
	public double getdelai() {
		return delai;
		
	}
	
	public void setdelai(double delai) {
		this.delai = delai;
	}
	
	}