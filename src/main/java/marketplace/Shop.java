package marketplace; 
import java.util.Date; 
import java.util.Scanner; 


public class Shop{ 
	private SelledProduct [] sellproducts= new SelledProduct[1000]; 
	static Scanner input= new Scanner(System.in); 
	 
	public SelledProduct createNewSelledProduct() { 
		double prix =0; 
		long selledCount =0; 
		Product product = new Product(0, null, null); 
		 
		 
			System.out.println("Nom du produit:"); 
			product.setName(input.nextLine()); 
			System.out.println("prix:"); 
			prix= input.nextDouble(); 
			System.out.println("nombre de ventes:"); 
			selledCount = (long) input.nextDouble(); 
			input.nextLine(); 
			System.out.println(); 
			 
		return new SelledProduct(product, prix, selledCount); 
	} 
	 
	public SelledProduct[] createNewSelledProducts(int size) { 
		for(int i = 0; i < size; i++ ) { 
			sellproducts[i] = createNewSelledProduct(); 
		} 
		return sellproducts; 
	} 
} 