import java.io.FileInputStream; 
import java.util.Date; 
import java.util.Iterator; 
import java.util.Scanner;

import marketplace.Product;
import marketplace.SelledProduct;
import marketplace.Shop; 

 

public class Main { 
 
	public static void main(String[] args) { 
		selection(); 
		Shop shop = new Shop(); 
		SelledProduct[] selledProducts = shop.createNewSelledProducts(2); 
		for( SelledProduct product: selledProducts) {
			System.out.println(product.getProduct().getName() + " " + product.getPrix() + " " + product.getSelledCount());
		}
	} 
	 
	static void test1() { 
		/*FileInputStream excelFile = new FileInputStream(""); 
		XSSFWorkbook workbook = new XSSFWorkbook(excelFile); 
		 
		XSSFSheet sheet = workbook.getSheetAt(0); 
		Iterator<Row> rowIt = sheet.rowIterator(); 
		 
		while(rowIt.hasNext()) { 
			Row row = rowIt.next(); 
			String name = row.getCell(0).getStringCellValue(); 
		}*/ 
		 
		 
		@SuppressWarnings("deprecation") 
		SelledProduct product = new SelledProduct( 30l, new Date(1000*60*20), 15, false, 2, 50, new Product(1234,"article1","aaaa" )); 
		 
		System.out.println(product.getProduct().getName()); 
	} 
		 
	 
 
	public static int selection() { 
		int result = 0; 
		Scanner scan = new Scanner(System.in); 
		while (result<1 || result>6) { 
			System.out.println("************SELECTION*********"); 
			System.out.println("1 pour ajouter un nouvel article"); 
			System.out.println("2 pour acheter un article"); 
			System.out.println("3 pour vendre un article"); 
			System.out.println("4 pour affciher les articles"); 
			System.out.println("5 pour afficher le resultat"); 
			System.out.println("6 pour fermer le programme"); 
			System.out.println("votre selection:"); 
			result = scan.nextInt(); 
			System.out.println(); 
		} 
		 
		return result;	 
	} 
 
	public static void runSelledProduct() { 
		switch (selection()) { 
		    case 1: 
		    	break;  
		    case 2:  
		    	break; 
		    case 3:  
		    	break; 
		    case 4:  
		    	break; 
		    case 5: 
		    	break; 
		    case 6: 
		    	break;	 
		} 
	}	 
} 