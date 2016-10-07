

CREATE TABLE `ecommerce`.`Client` (
  `id` VARCHAR(36) NOT NULL,
  `email` VARCHAR(255) NOT NULL,
  `nom` VARCHAR(255) NOT NULL,
  `prenom` VARCHAR(255) NOT NULL,
  PRIMARY KEY (`id`));

CREATE TABLE `ecommerce`.`Produit` (
  `id` VARCHAR(36) NOT NULL,
  `nom` VARCHAR(255) NOT NULL,
  `prixUnitaire` FLOAT NOT NULL,
  PRIMARY KEY (`id`));

CREATE TABLE `ecommerce`.`Panier` (
  `id` VARCHAR(36) NOT NULL,
  `idClient` VARCHAR(36) NOT NULL,
  `date` DATETIME NOT NULL,
  PRIMARY KEY (`id`),
  INDEX `fk_Panier_1_idx` (`idClient` ASC),
  CONSTRAINT `fk_Panier_1`
    FOREIGN KEY (`idClient`)
    REFERENCES `ecommerce`.`Client` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION);

CREATE TABLE `ecommerce`.`ProduitPanier` (
  `idPanier` VARCHAR(36) NOT NULL,
  `idProduit` VARCHAR(36) NOT NULL,
  `quantite` INT NULL,
  PRIMARY KEY (`idPanier`, `idProduit`),
  INDEX `fk_ProduitAjouteAuPanier_Produit_idx` (`idProduit` ASC),
  CONSTRAINT `fk_ProduitAjouteAuPanier_Panier`
    FOREIGN KEY (`idPanier`)
    REFERENCES `ecommerce`.`Panier` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_ProduitAjouteAuPanier_Produit`
    FOREIGN KEY (`idProduit`)
    REFERENCES `ecommerce`.`Produit` (`id`)
    ON DELETE NO ACTION
ON UPDATE NO ACTION);

-- SELECT * from Panier 
-- INNER JOIN ProduitPanier pp ON pp.idPanier = panier.id
-- INNER JOIN Produit produit ON produit.id = pp.idProduit
-- where panier.id = ""