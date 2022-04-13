package main.java.marketplace;

import java.util.Date;

public class SelledProduct{
	private long stock; 
	private Date  shippingtime;
	private double prix;
	private boolean externproduct;
	private double totalCommission;
	private long selledCount; 
	
	public SelledProduct(long stock, Date shippingtime, double prix, boolean externproduct, double totalCommission, long selledCount) {
		this.stock= stock; 
		this.shippingtime= shippingtime;
		this.prix= prix; 
		this.externproduct= externproduct; 
		this.totalCommission= totalCommission;
		this.selledCount= selledCount;
	}

	public long getStock() {
		return stock;
	}

	public void setStock(long stock) {
		this.stock = stock;
	}

	public Date getShippingtime() {
		return shippingtime;
	}

	public void setShippingtime(Date shippingtime) {
		this.shippingtime = shippingtime;
	}

	public double getPrix() {
		return prix;
	}

	public void setPrix(double prix) {
		this.prix = prix;
	}

	public boolean isExternproduct() {
		return externproduct;
	}

	public void setExternproduct(boolean externproduct) {
		this.externproduct = externproduct;
	}

	public double getTotalCommission extends double SellContract() {
		return totalCommission;
	}

	public void setTotalCommission(double totalCommission) {
		this.totalCommission = totalCommission;
	}

	public long getSelledCount() {
		return selledCount;
	}

	public void setSelledCount(long selledCount) {
		this.selledCount = selledCount;
	}
	
	
	}