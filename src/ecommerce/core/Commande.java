package ecommerce.core;
import java.time.LocalDate;

public class Commande {
	protected Panier panier;
	protected String nomCommande;
	protected LocalDate dateCommande;
	protected EtatCommande etatCommande;

	public Commande(String nom){
		this.nomCommande = nom;
		etatCommande = EtatCommande.EN_ATTENTE;
	}

	public boolean valider(){
		if(!panier.estVide()){
			dateCommande = LocalDate.now();
			return true;
		}
		return false;
	}

}
