package marketplace; 
import java.util.Date; 
import java.util.Scanner; 


public class Shop{ 
	private SelledProduct [] sellproducts;
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
			System.out.println("Nom du produit:"+ " " + product.getName()+" "+"prix:"+" "+ prix+" "+ "nombre de ventes:" +" "+ selledCount ) ; 
			 
		return new SelledProduct(product, prix, selledCount); 
	} 
	
	public SelledProduct[] createNewSelledProducts(int size) { 
		sellproducts = new SelledProduct[size];
		for(int i = 0; i < size; i++ ) { 
			sellproducts[i] = createNewSelledProduct(); 
		} 
		return sellproducts; 
	} 
	
	public void addToProduct() {
		for (int x=0 ; x<sellproducts.length; x++ ) {
			if (sellproducts[x]==null) {
				sellproducts[x] = createNewSelledProduct();
				x = sellproducts.length;
			}
		}
	}   
	
	public void printAll() {
		for (int x = 0; x<sellproducts.length; x++) {
			if (sellproducts[x] != null) {
				System.out.println("Numero:" + (x+1)+"Nom"+sellproducts[x].getProduct().getName()+"stock:"+sellproducts[x].getStock());
			}
		}
	}
	
	public void buyProduct() {
		int sellproductsnumber = -1 , quantity = 0;
		while ( sellproductsnumber<0 || sellproducts[sellproductsnumber]== null  ) {
			System.out.println("Numero du produit acheté: ");
			sellproductsnumber = input.nextInt()-1;
			input.nextLine();
		}
		
		System.out.println();
		
		while ( quantity < 1) {
			System.out.println("Nombre d'article acheter:");
			quantity = input.nextInt();
			input.nextLine();
		}
		
		/*sellproducts[sellproductsnumber].getProduct().setName(null)()[sellproducts[sellproductsnumber].getName()+ quantity);*/
		double expenses = sellproducts[sellproductsnumber].getPrix()*quantity;
	}
	
	public void result() {
		int income = 0;
		int expenses = 0;   
		System.out.println(income-expenses+ "Euro");
	}
	
	public void sellProduct() {
		int sellproductsnumber = -1 , quantity = 0;
		while ( sellproductsnumber<0 || sellproducts[sellproductsnumber]== null  ) {
			System.out.println("Numero du produit vendue: ");
			sellproductsnumber = input.nextInt()-1;
			input.nextLine();
		}
		
		System.out.println();
		
		while ( quantity < 1 || sellproducts[sellproductsnumber].getStock()<quantity) {
			System.out.println("Nombre d'article Vendue:");
			quantity = input.nextInt();
			
			if (quantity>sellproducts[sellproductsnumber].getStock()) {
				System.out.println("la quantité en vente est plus grande que la quantité en stock ");
				System.out.println();
			}
		}
		
		sellproducts[sellproductsnumber].setStock(sellproducts[sellproductsnumber].getStock()- quantity);
		double income = sellproducts[sellproductsnumber].getPrix()*quantity;
	}
} 