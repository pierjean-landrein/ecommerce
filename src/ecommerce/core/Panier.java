package ecommerce.core;

import ecommerce.DBService.DBService;
import ecommerce.exceptions.ProduitNullException;
import ecommerce.util.DateUtils;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class Panier {
	protected float prixPanier;
	protected String id;
	protected List<ProduitPanier> produits;
	protected LocalDateTime date;

	public Panier(){
		prixPanier = 0.f;
		produits = new ArrayList<>();
		this.date = LocalDateTime.now();
		this.id = UUID.randomUUID().toString();
		chargerPanier();
	}

	public void ajouterProduit(Produit produit) throws ProduitNullException{
		if(produit == null) throw new ProduitNullException();
		prixPanier += produit.getPrix();
		try{
			produits.add(new ProduitPanier(produit));
		}catch (Exception e){
			e.printStackTrace();
		}
	}

	public void supprimerProduit(Produit produit) throws ProduitNullException{
		if(produit == null) throw new ProduitNullException();
		produits.remove(produit);
	}

	public String toString(){
		String str = "Produits : \n";
		for(Produit produit : produits){
			str += produit;
		}
		str += "prix total : " + prixPanier + "\n";
		return str;
	}

	public void chargerPanier(){
		/*try{
			DBService dbService = DBService.getInstance();
			ResultSet rs = dbService.executeSelect()
			while(rs.next()){
				//rs.getString(1,"")
			}

		}catch(SQLException e){
			e.printStackTrace();
		}*/
	}

	public void enregistrerDB(String idClient){
		DBService instance = DBService.getInstance();
		try {
			PreparedStatement preparedStatement = instance.createPreparedStatement("INSERT into Panier(`id`, `idClient`,`date`) values (?,?,?)");
			preparedStatement.setString(1, id);
			preparedStatement.setString(2, idClient);
			preparedStatement.setDate(3, DateUtils.asSqlDate(date));
			preparedStatement.execute();
		}catch(SQLException e){
			e.printStackTrace();
		}
		for(ProduitPanier produit : produits){
			produit.enregistrerDB(id);
		}
	}

	public void vider(){
		produits.clear();
	}

	public int getNombreArticle(){
		return produits.size();
	}

	public float getPrixPanier(){
		return prixPanier;
	}

	boolean estVide(){
		return produits.isEmpty();
	}
}
