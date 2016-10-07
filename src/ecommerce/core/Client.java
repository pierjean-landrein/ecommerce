package ecommerce.core;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import ecommerce.DBService.DBService;
import ecommerce.exceptions.*;
import ecommerce.exceptions.ProduitNullException;



public class Client {
	protected boolean isSupprime = false;
	protected String id;
	protected String nom;
	protected String prenom;
	protected String mail;
	protected String adressePostale;
	protected String telephone;
	protected String motDePasse;
	protected List<Commande> commandes;
	protected Panier panier;


	public Client(String nom,String prenom,String mail) throws MailInvalidException, MetierException{
		this.nom = nom;
		this.prenom = prenom;
		this.mail = mail;

		if(mail == null ||!mailIsValid()){
			throw new MailInvalidException();
		}
		if(nom == null || nom.isEmpty()){
			throw new MetierException("Nom invalide !");
		}
		if(prenom == null || prenom.isEmpty()){
			throw new MetierException("Prenom invalide !");
		}

		this.id = UUID.randomUUID().toString();
		this.nom = nom;
		this.prenom = prenom;
		this.mail = mail;
		this.commandes = new ArrayList<Commande>();
		this.panier = new Panier();
	}

	private boolean mailIsValid(){
		final String validationMailRegexp = "^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@" + "[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$";
		return mail.matches(validationMailRegexp);
	}

	public String getId(){
		return id;
	}

	public void setID(String id){
		this.id = id;
	}

	public String toString(){
		String str = String.format("%s / %s / %s", id, nom, prenom);
		str += panier;

		return str;
	}

	public void ajouterProduitPanier(Produit produit){
		try {
			panier.ajouterProduit(produit);
		} catch (ProduitNullException e) {
			e.printStackTrace();
		}
	}

	public void enregistrerDB(){
		DBService instance = DBService.getInstance();
		try {
			PreparedStatement preparedStatement = instance.createPreparedStatement("INSERT into Client(`id`, `nom`,`prenom`,`email`) values (?,?,?,?)");
			preparedStatement.setString(1,id);
			preparedStatement.setString(2,nom);
			preparedStatement.setString(3,prenom);
			preparedStatement.setString(4,mail);
			preparedStatement.execute();
		 } catch (SQLException e){
			e.printStackTrace();
		}
		panier.enregistrerDB(id);
	}
}
