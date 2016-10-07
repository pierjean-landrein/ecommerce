package ecommerce.core;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.List;
import java.util.Scanner;

import ecommerce.exceptions.*;

public class Main {
  static Scanner br = new Scanner(System.in);
  static Ecommerce site = new Ecommerce();

  public static void menu(){
    System.out.println("[0] : Exit");
    System.out.println("[1] : Créer un client");
    System.out.println("[2] : Lister les produits");
    System.out.println("[3] : Ajouter un produit au panier");
    System.out.println("[4] : Détail d'un client");
    System.out.println("[5] : Lister les clients");
    System.out.println("[6] : Créer un produit");
    System.out.println("[7] : Afficher tout les clients");
  }

  private static void creerClient() {
    String id,nom,prenom,mail;

    try {
      System.out.println("Entrez le prenom : ");
      prenom = br.nextLine();

      System.out.println("Entrez le nom : ");
      nom = br.nextLine();

      System.out.println("Entrez le mail:");
      mail = br.nextLine();

      Client client = new Client(nom,prenom,mail);
      site.ajouteClient(client);
      client.enregistrerDB();

    }catch(Exception e){
      e.printStackTrace();
    }
  }

  private static void listerProduits() {
    final List<Produit> produits = site.getCatalogue();
    produits.forEach(System.out::println);
  }

  private static void detailClient(){
    System.out.println("Entrez l'id du client : ");
    String id = br.nextLine();
    try {
      System.out.println(site.chercherClient(id));
    } catch (NotFoundClientException e) {
      System.out.println("Id client incorrect !");
    }
  }

  private static void ajouterProduit(){
    String lecture;
    System.out.println("Entrez l'id du client auquel vous vouler ajouter un produit");
    lecture = br.nextLine();
    Client client;
    try{
      client = site.chercherClient(lecture);
      System.out.println("Entrez l'id du produit que vous vouler ajouter au panier");
      lecture = br.nextLine();
      client.ajouterProduitPanier(site.chercherProduit(lecture));
     }catch (NotFoundClientException e) {
      System.out.println("Ce client n'existe pas !");
    }
  }

  private static void creerProduit(){
    String nom,id;
    float prix;

    try{
      System.out.println("Entrez le nom : ");
      nom = br.nextLine();

      System.out.println("Entrez le prix : ");
      prix = new Float(br.nextLine()).floatValue();

      site.ajouteProduit(new Produit(nom,prix));
    }catch(Exception e){
      e.printStackTrace();
    }
  }

  private static void listerClients(){
    List<Client> clients = site.getCLients();
    clients.forEach(System.out::println);
  }

  public static void main(String[] args){
    String commande;
    while(true){
      menu();
      commande = br.nextLine();
      switch(commande){
        case "0":
          System.exit(0);
        case "1":
          creerClient();
          break;
        case "2":
          listerProduits();
          break;
        case "3":
          ajouterProduit();
          break;
        case "4":
          detailClient();
          break;
        case "5":
          listerClients();
          break;
        case "6":
          creerProduit();
          break;
        case "7":
          listerClients();
          break;
      }
    }
  }
}
