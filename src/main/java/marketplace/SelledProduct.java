package marketplace;

import java.util.Date; 
 
public class SelledProduct{ 
	private long stock;  
	private Date  shippingtime; 
	private double prix; 
	private boolean externproduct; 
	private double totalCommission; 
	private long selledCount;
	private Product product;  
	 
	public SelledProduct(long stock, Date shippingtime, double prix, boolean externproduct, double totalCommission, long selledCount) { 
		this.stock= stock;  
		this.shippingtime= shippingtime; 
		this.prix= prix;  
		this.externproduct= externproduct;  
		this.totalCommission= totalCommission; 
		this.selledCount= selledCount; 
	} 
 
	public SelledProduct(Product product, double prix, long selledCount) {
		// TODO Auto-generated constructor stub
		this.product= product;
		this.prix= prix;
		this.selledCount=selledCount;
		
		
	}

	public SelledProduct(long l, Date date, double i, boolean b, double j, int k, Product product) {
		// TODO Auto-generated constructor stub
		this(l,date,i,b,j,k);
		this.product=product;
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
 
	public double getTotalCommission  () { 
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

	public Product getProduct() {
		// TODO Auto-generated method stub
		return product;
	}

	@Override
	public String toString() {
		return "SelledProduct [stock=" + stock + ", shippingtime=" + shippingtime + ", prix=" + prix
				+ ", externproduct=" + externproduct + ", totalCommission=" + totalCommission + ", selledCount="
				+ selledCount + ", product=" + product + "]";
	} 
	 
	 
	}