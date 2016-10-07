package ecommerce.core;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;
import java.util.UUID;

import ecommerce.DBService.DBService;
import ecommerce.exceptions.*;

public class Produit {
	
	protected String nom;
	protected String description;
	protected String url;
	protected String categorie;
	protected String id;
	protected List<String> images;
	protected float prix;
	protected boolean disponible;
	
	public Produit(String nom,float prix) throws ProduitNoNameException, ErreurPrixProduitException{
		if(nom == null || nom.isEmpty()){
			throw new ProduitNoNameException();
		}else if(prix <= 0.f){
			throw new ErreurPrixProduitException();
		}
		this.nom = nom;
		this.prix = prix;
		this.id = UUID.randomUUID().toString();
		this.disponible = true;
	}

	public void enregistrerDB(){
		DBService instance = DBService.getInstance();
		try {
			PreparedStatement preparedStatement = instance.createPreparedStatement("INSERT into Produit(`id`, `nom`,`prix`) values (?,?,?)");
			preparedStatement.setString(1,id);
			preparedStatement.setString(2,nom);
			preparedStatement.setFloat(3,prix);
			preparedStatement.execute();
		} catch (SQLException e){
			e.printStackTrace();
		}
	}

	public float getPrix() {
		return prix;
	}
	
	public void setDisponible(boolean disponible){
		this.disponible = disponible;
	}

	public String getId() {
		return id;
	}

	public String toString(){
    String str = String.format("%s / %s / %.2f", id, nom, prix);
		return str;
	}
}
