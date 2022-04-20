import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Date; 
import java.util.Iterator; 
import java.util.Scanner;

import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import marketplace.Product;
import marketplace.SelledProduct;
import marketplace.Shop; 

 

public class Main { 
 
	public static void main(String[] args) { 
		selection(); 
		Shop shop = new Shop(); 
		SelledProduct[] selledProducts = shop.createNewSelledProducts(2); 
		for( SelledProduct product: selledProducts) {
			System.out.println("Nom du produit:" + " " + product.getProduct().getName() + " " + "Prix:" + " " + product.getPrix() + " " + "SelledCount:" + " "+ product.getSelledCount());
		}
		
		try {
			ecrire(selledProducts);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			
			
		}
		shop.addToProduct();
		shop.printAll();
		shop.result();
		shop.printAll();
		shop.buyProduct();
		shop.printAll();
		shop.result();
		shop.printAll();
		
		while (true) {
			runShop();
		}
	} 
	 
	static void ecrire(SelledProduct[] selledProducts) throws IOException { 
		String excelPath = "H:\\Documents\\Projet Java GIT\\Book1.xlsx";
		
		FileInputStream excelFile = new FileInputStream(excelPath); 
		XSSFWorkbook workbook = new XSSFWorkbook(excelFile);
		excelFile.close();
		 
		XSSFSheet sheet = workbook.getSheetAt(0);
		
		 
		/*Iterator<Row> rowIt = sheet.rowIterator(); */
		 
		/*while(rowIt.hasNext()) { 
			Row row = rowIt.next(); 
			String name = row.getCell(0).getStringCellValue(); 
		}*/
		 
		 
		@SuppressWarnings("deprecation") 
		/*SelledProduct product = new SelledProduct( 30l, new Date(1000*60*20), 15, false, 2, 50, new Product(1234,"article1","aaaa" )); 
		 
		System.out.println(product.getProduct().getName()); */

		int counter = 0;
		for (SelledProduct selledproduct: selledProducts) {
			XSSFRow xssfRow = sheet.getRow(counter);
			if (xssfRow == null) xssfRow = sheet.createRow(counter++);
			
			XSSFCell cell = xssfRow.getCell(0);
			if (cell == null) cell = xssfRow.createCell(0);
			cell.setBlank();
			cell.setCellValue(selledproduct.getPrix());
		}
		
		FileOutputStream fos = new FileOutputStream(excelPath);
		sheet.getWorkbook().write(fos);
		fos.close();
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
 
	public static void runShop() { 
		Shop shop= new Shop();
		switch (selection()) { 
		    case 1:
		    	shop.addToProduct();
		    	break;  
		    case 2:
		    	shop.buyProduct();
		    	break; 
		    case 3:
		    	shop.sellProduct();
		    	break; 
		    case 4:
		    	shop.printAll();
		    	break; 
		    case 5:
		    	shop.result();
		    	break; 
		    case 6:
		    	System.exit(0);
		    	break;	 
		} 
	}	 
} 