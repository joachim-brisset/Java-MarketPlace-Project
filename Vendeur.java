
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;


public class Vendeur{
	
	private int cdVendeur;
	private Contrat contrat;
	
	Vendeur(int cdVendeur){
		this.cdVendeur=cdVendeur;
	}
	
	
	@Override
	public String toString() {
		return "Vendeur [cdVendeur=" + cdVendeur + "]";
	}

	public static void main(String[] args) {
		
		/* j'ai crée des variable mais normalement, elle vient des autre class, je ne sais pas comment faire */
		boolean accord=true;
		float prix_produit=5;
		int nombre_vendu=8;
		DateTimeFormatter date_Fin_Contrat = DateTimeFormatter.ofPattern("yyyy/MM/dd");
		
		
		sign_Contrat(accord);
		Chiffre_affaire(prix_produit,nombre_vendu);
		resilier(date_Fin_Contrat);
	}
	
	private static void sign_Contrat(boolean accord) {
		if (accord) {
			System.out.println("le contrat est signé");
		}
		else {
			System.out.println("le contrat n'est pas signé");
		}
		
	}
	private static void Chiffre_affaire(float prix_produit, int nombre_vendu)
	{
		float Resultat;
		Resultat=prix_produit*nombre_vendu;
		System.out.println(Resultat);
	}
	private static void resilier(DateTimeFormatter date_Fin_Contrat) {
		DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy/MM/dd");
		if (dtf==date_Fin_Contrat){
			System.out.println("le contrat est résilié");
		}
		
	}
	
}
