import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;




public class Contrat {
	 private int num;
	 /*type_Date date_D�but 
	  * type_Date date_Fin
	  */
	 private Vendeur vendeur;
	 
	 Contrat(int num /* date_d�but date_fin*/)
	 {
		 this.num=num;
	 }

	@Override
	public String toString() {
		return "Contrat [num=" + num + "]";
	}
	 

}
