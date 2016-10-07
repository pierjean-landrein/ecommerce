package ecommerce.core;

import ecommerce.DBService.DBService;
import ecommerce.exceptions.ErreurPrixProduitException;
import ecommerce.exceptions.ProduitNoNameException;
import ecommerce.util.DateUtils;

import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.time.LocalDate;

public class ProduitPanier extends Produit {

	protected int quantite;
	
	public ProduitPanier(Produit produit) throws ErreurPrixProduitException, ProduitNoNameException {
		super(produit.nom, produit.prix);
		this.quantite = 1;
	}

	public String toString(){
		String str = super.toString() + " / " + quantite;
		return str;
	}

	public void enregistrerDB(String idPanier) {
		DBService instance = DBService.getInstance();
		try {
			if(quantite == 1){
				PreparedStatement preparedStatement = instance.createPreparedStatement("INSERT into ProduitPanier(`idPanier`, `idProduit`,`quantite`) values (?,?,?)");
				preparedStatement.setString(1, idPanier);
				preparedStatement.setString(2, id);
				preparedStatement.setInt(3, quantite);
				preparedStatement.execute();
			}
			else{
				PreparedStatement preparedStatement = instance.createPreparedStatement("UPDATE ProduitPanier SET quantite = ? WHERE idPanier = ? AND idProduit = ? )");
				preparedStatement.setInt(1, quantite);
				preparedStatement.setString(2, idPanier);
				preparedStatement.setString(3, id);
				preparedStatement.execute();
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
}

