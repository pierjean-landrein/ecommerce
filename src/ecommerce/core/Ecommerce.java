package ecommerce.core;

// Java Core API
import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.sql.*;

// My Exceptions
import ecommerce.DBService.DBService;
import ecommerce.exceptions.*;

public class Ecommerce {
	protected List<Produit> catalogue;
	protected List<Client> clients;

	public Ecommerce(){
		catalogue = new ArrayList<Produit>();
		clients = new ArrayList<Client>();
		chargerClients();
		chargerProduits();
	}

	private void chargerClients() {
		DBService dbService = DBService.getInstance();
		ResultSet rs = dbService.executeSelect("SELECT id,email,nom,prenom FROM Client");
		try{
			while(rs.next()){
				String id = rs.getString("id");
				String email = rs.getString("email");
				String nom = rs.getString("nom");
				String prenom = rs.getString("prenom");

				try{
					Client client = new Client(nom,prenom,email);
					clients.add(client);
				}catch (Exception e){}
			}
		}catch(SQLException e){
			e.printStackTrace();
		}
	}

	private void chargerProduits(){
		DBService dbService = DBService.getInstance();
		ResultSet rs = dbService.executeSelect("SELECT idProduit,nom,prix FROM Produit");
		try{
			while(rs.next()){
				String id = rs.getString("idProduit");
				String nom = rs.getString("nom");
				float prix = rs.getFloat("prix");

				try{
					Produit produit = new Produit(nom,prix);
					catalogue.add(produit);
				}catch (Exception e){}
			}
		}catch(SQLException e){
			e.printStackTrace();
		}
	}

	public List<Produit> getCatalogue(){
		return catalogue;
	}

	public  List<Client> getCLients(){
		return clients;
	}


	public void ajouteClient(Client client) throws ClientNullException {
		if(client == null) throw new ClientNullException();
		client.enregistrerDB();
		clients.add(client);
	}

	public void ajouteProduit(Produit produit) throws ProduitNullException{
		if(produit == null) throw new ProduitNullException();
		produit.enregistrerDB();
		catalogue.add(produit);
	}

	public Client chercherClient(String id) throws NotFoundClientException{
		for(Client client : clients){
			if(client.getId().equals(id)){
				return client;
			}
		}
		throw new NotFoundClientException();
	}

	public Produit chercherProduit(String id){
		Produit produitRecherche = null;

		for(Produit produit : catalogue){
			if(produit.getId().equals(id)){
				produitRecherche = produit;
			}
		}
		return produitRecherche;
	}

}
