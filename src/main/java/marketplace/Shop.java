package marketplace; 
import java.util.Date; 
import java.util.Scanner; 


public class Shop{ 
	private SelledProduct [] selledProducts;
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
		selledProducts = new SelledProduct[size];
		for(int i = 0; i < size; i++ ) { 
			selledProducts[i] = createNewSelledProduct(); 
		} 
		return selledProducts; 
	} 
	
	public void addToProduct() {
		for (int x=0 ; x<selledProducts.length; x++ ) {
			if (selledProducts[x]==null) {
				selledProducts[x] = createNewSelledProduct();
				x = selledProducts.length;
			}
		}
	}   
	
	public void printAll() {
		for (int x = 0; x< selledProducts.length; x++) {
			if (selledProducts[x] != null) {
				System.out.println("Numero:" + (x+1)+"Nom"+selledProducts[x].getProduct().getName()+"stock:"+selledProducts[x].getStock());
			}
		}
	}
	
	public void buyProduct() {
		int sellproductsnumber = -1 , quantity = 0;
		while ( sellproductsnumber<0 || selledProducts[sellproductsnumber]== null  ) {
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
		double expenses = selledProducts[sellproductsnumber].getPrix()*quantity;
	}
	
	public void result() {
		int income = 0;
		int expenses = 0;   
		System.out.println(income-expenses+ "Euro");
	}
	
	public void sellProduct() {
		int sellproductsnumber = -1 , quantity = 0;
		while ( sellproductsnumber<0 || selledProducts[sellproductsnumber]== null  ) {
			System.out.println("Numero du produit vendue: ");
			sellproductsnumber = input.nextInt()-1;
			input.nextLine();
		}
		
		System.out.println();
		
		while ( quantity < 1 || selledProducts[sellproductsnumber].getStock()<quantity) {
			System.out.println("Nombre d'article Vendue:");
			quantity = input.nextInt();
			
			if (quantity>selledProducts[sellproductsnumber].getStock()) {
				System.out.println("la quantité en vente est plus grande que la quantité en stock ");
				System.out.println();
			}
		}
		
		selledProducts[sellproductsnumber].setStock(selledProducts[sellproductsnumber].getStock()- quantity);
		double income = selledProducts[sellproductsnumber].getPrix()*quantity;
	}
} 