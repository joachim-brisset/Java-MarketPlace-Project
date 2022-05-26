package project;

import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import project.marketplace.*;

import java.io.FileInputStream;
import java.io.IOException;
import java.text.ParseException;
import java.time.Duration;
import java.util.*;
import java.util.Map.Entry;
import java.util.stream.Collectors;

public class App {

    public static XSSFWorkbook workook;
    private static final Scanner scan = new Scanner(System.in);
    private static Account account;

    /**
     * Show menu while answer is not valid
     * @param runnable Runnable containing the menu to print;
     * @param min minimun integer answer allowed
     * @param max maximum integer answer allowed
     * @return the answer
     * TODO: review - bug some time
     */
    private static int menuInt(Runnable runnable, int min, int max) {
        int choice = -1;
        while(choice == -1) {
            runnable.run();

            String line = scan.nextLine();
            try {
                choice = Integer.parseInt(line);
            } catch (NumberFormatException e) {
                choice = -1;
                System.out.println("Erreur nous n'avons pas comprit votre réponse veuillez entrez un chiffre!");
                continue;
            }

            if(choice < min || choice > max) {
                System.out.println("Veuillez entrer un nombre entre " + min + " et " + max);
                choice = -1;
            }
        }
        return choice;
    }

    public static void main(String[] args) throws IOException {
        if(args.length != 1) {
            System.out.println("Le programme doit prendre le path (sans espace dedans) du fichier excel en 1er argument");
            System.exit(-1);
        }
        System.out.println(args[0]);
        FileInputStream fis = new FileInputStream(args[0]);
        workook = new XSSFWorkbook(fis);
        fis.close();

        try {
            Product.load();
            Seller.load();
            SelledProduct.load();
            Contract.load();
        } catch (ParseException e) {
            System.err.println("Un erreur est survenue lors du chargement des données");
            e.printStackTrace();
            System.exit(-1);
        }

        startMenu();
    }

    private static void startMenu() {
        System.out.println("_____/\\\\\\\\\\\\\\\\\\_________________________________________________________________________________");
        System.out.println(" ___/\\\\\\\\\\\\\\\\\\\\\\\\\\_______________________________________________________________________________");
        System.out.println("  _/\\\\\\/////////\\\\\\______________________________________________________________________________");
        System.out.println("   _\\/\\\\\\_______\\/\\\\\\____/\\\\\\\\\\__/\\\\\\\\\\____/\\\\\\\\\\\\\\\\\\_____/\\\\\\\\\\\\\\\\\\\\\\_____/\\\\\\\\\\_____/\\\\/\\\\\\\\\\\\___");
        System.out.println("    _\\/\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\__/\\\\\\///\\\\\\\\\\///\\\\\\_\\////////\\\\\\___\\///////\\\\\\/____/\\\\\\///\\\\\\__\\/\\\\\\////\\\\\\__");
        System.out.println("     _\\/\\\\\\/////////\\\\\\_\\/\\\\\\_\\//\\\\\\__\\/\\\\\\___/\\\\\\\\\\\\\\\\\\\\_______/\\\\\\/_____/\\\\\\__\\//\\\\\\_\\/\\\\\\__\\//\\\\\\_");
        System.out.println("      _\\/\\\\\\_______\\/\\\\\\_\\/\\\\\\__\\/\\\\\\__\\/\\\\\\__/\\\\\\/////\\\\\\_____/\\\\\\/______\\//\\\\\\__/\\\\\\__\\/\\\\\\___\\/\\\\\\_");
        System.out.println("       _\\/\\\\\\_______\\/\\\\\\_\\/\\\\\\__\\/\\\\\\__\\/\\\\\\_\\//\\\\\\\\\\\\\\\\/\\\\__/\\\\\\\\\\\\\\\\\\\\\\__\\///\\\\\\\\\\/___\\/\\\\\\___\\/\\\\\\_");
        System.out.println("        _\\///________\\///__\\///___\\///___\\///___\\////////\\//__\\///////////_____\\/////_____\\///____\\///__");
        System.out.println();

        account = new Account();

        while(true) {
            account.setConnected(false);
            int action = menuInt(() -> {
                System.out.println("Bievenue ! Veuillez-vous identifier ou vous enregister :");
                System.out.println("\t-> 0. quitter");
                System.out.println("\t-> 1. s'identifier");
                System.out.println("\t-> 2. s'enregistrer");
            }, 0, 2);

            switch (action) {
                case 0: System.exit(0); break;
                case 1: signInMenu(); break;
                case 2: signUpMenu(); break;
                default: throw new IllegalArgumentException();
            }

            if ( account.getUser() instanceof Admin) {
                adminMenu();
            } else if ( account.getUser() instanceof Seller) {
                sellerMenu();
            } else if (account.getUser() instanceof Buyer) {
                buyerMenu();
            }

            account.setLogin("");
            account.setPass("");
            account.setConnected(false);
        }
    }

    private static void signUpMenu() {
        //TODO: MEDIUM implement - signUp
        System.out.println("Not implemented yet");
    }
    private static void signInMenu() {
        while (!account.isConnected()) {
            int action = menuInt(() -> {
                System.out.println("\nVeuillez a present vous connecter :");
                System.out.println("\t-> 0. quitter");
                System.out.println("\t-> 1. Modifier votre identifiant. (Actuellement : " + account.getLogin() + " )");
                System.out.println("\t-> 2. Modifier votre password. (Actuellement : " + account.getPass() + " )");
                System.out.println("\t-> 3. Se connecter !");
            }, 0, 3);

            boolean abandon = false;
            switch (action) {
                case 0: return;
                case 1:
                    System.out.print("Entrez le nouveau identifiant : ");
                    account.setLogin(scan.nextLine());
                    break;
                case 2:
                    System.out.print("Entrez le nouveau password :");
                    account.setPass(scan.nextLine());
                    break;
                case 3:
                    account.signIn();
                    break;
                default: throw new IllegalArgumentException();
            };
        }
    }

    private static void buyerMenu() {
        while(true) {
            System.out.println("\nBievenue dans le menu acheteur !");
            int action = menuInt(() -> {
                System.out.println("Veuillez choisir une action :");
                System.out.println("\t->0. Quitter");
                System.out.println("\t->1. Afficher les produits");
                System.out.println("\t->2. Ajouter au panier");
                System.out.println("\t->3. Afficher panier");
                System.out.println("\t->4. Valider panier");
                System.out.println("\t->5. Afficher vos commandes");
            }, 0, 5);

            switch (action) {
                case 0: return;
                case 1: afficherProduitMenu(); break;
                case 2: ajouterAuPanier(); break;
                case 3: afficherPanier(); break;
                case 4: validerPanier(); break;
                case 5: afficherCommande(); break;
            }
        }
    }


    private static void afficherProduitMenu() {
        Map<Integer, SelledProduct> selledProducts = SelledProduct.getSelledProducts();
        if(selledProducts.isEmpty()) {
            System.out.println("Il n'y a pas de produit en vente !");
        } else {
            //TODO: LOW - create table with library 'java-ascii-table'
            for (Entry<Integer, SelledProduct> entry : selledProducts.entrySet()) {
                Contract contract = Contract.getContractList().get(entry.getValue().getSeller());

                if(contract == null || !contract.isValid()) continue;
                System.out.println(entry.getKey() + "\t"
                        + entry.getValue().getProduct().getDesignation() + "\t"
                        + entry.getValue().getProduct().getDescription() + "\t"
                        + entry.getValue().getSeller().getName() + "\t"
                        + entry.getValue().getPrice() + "€\t"
                        + entry.getValue().getShippingPrice() + "€\t"
                        + entry.getValue().getShippingTime().toDays() + "j");
            }
        }
    }
    private static void ajouterAuPanier() {
        System.out.print("Afficher les produits ? (Y/N)");
        String show = scan.nextLine();
        if(show.toLowerCase().equals("y")) afficherProduitMenu();

        try {
            System.out.print("Entrez l'id du produit a acheter : ");
            int productid = scan.nextInt();
            SelledProduct selledProduct = SelledProduct.getSelledProducts().get(productid);
            if (selledProduct == null) {
                System.out.println("l'id " + productid + " n'existe pas !");
                return;
            } else if(!Contract.getContractList().get(selledProduct.getSeller()).isValid()) {
                System.out.println("Le contrat reliant la marketplace et le vendeur de ce produit n'est plus valide");
                return;
            }

            System.out.print("Entrez le nombre de produit a acheter");
            int quantity = scan.nextInt();

            Buyer buyer = (Buyer) account.getUser();
            buyer.getCart().editProduct(selledProduct, quantity);

        }catch (InputMismatchException e) {
            System.out.println("Entrez invalide !");
            return;
        }
    }
    private static void editerProduitPanier() {
        Buyer buyer = (Buyer) account.getUser();

        try {
            System.out.print("Entrez l'id du produit a editer : ");
            int productid = scan.nextInt();
            SelledProduct selledProduct = SelledProduct.getSelledProducts().get(productid);
            if (selledProduct == null) {
                System.out.println("l'id " + productid + " n'existe pas !");
                return;
            }  else if(!Contract.getContractList().get(selledProduct.getSeller()).isValid()) {
                System.out.println("Le contrat reliant la marketplace et le vendeur de ce produit n'est plus valide");
                buyer.getCart().removeProduct(selledProduct);
                return;
            }

            System.out.print("Entrez le nombre de produit a acheter");
            int quantity = scan.nextInt();

            buyer.getCart().editProduct(selledProduct, quantity);

        }catch (InputMismatchException e) {
            System.out.println("Entrez invalide !");
            return;
        }
    }
    private static void supprimerProduitPanier() {
        try {
            System.out.print("Entrez l'id ud produit a supprimer : ");
            int productid = scan.nextInt();
            SelledProduct selledProduct = SelledProduct.getSelledProducts().get(productid);
            if (selledProduct == null) {
                System.out.println("l'id " + productid + " n'existe pas !");
                return;
            }

            Buyer buyer = (Buyer) account.getUser();
            buyer.getCart().removeProduct(selledProduct);

        }catch (InputMismatchException e) {
            System.out.println("Entrez invalide !");
            return;
        }
    }

    private static void afficherPanier() {

        Cart cart = ((Buyer) account.getUser()).getCart();
        if(cart.getProductList().isEmpty()) {
            System.out.println("Vous n'avez pas de produit dans le panier !");
        } else {
            while(true) {
                System.out.println("Votre panier : :");
                for (Entry<SelledProduct, Integer> entry : cart.getProductList().entrySet()) {
                    System.out.println(entry.getKey().getId() + "\t"
                            + entry.getValue() + "x " + entry.getKey().getProduct().getDesignation() + "\t"
                            + entry.getKey().getProduct().getDescription() + "\t"
                            + entry.getKey().getSeller().getName() + "\t"
                            + entry.getKey().getPrice() + "€\t"
                            + entry.getKey().getShippingPrice() + "€\t"
                            + entry.getKey().getShippingTime().toDays() + "j");
                }
                System.out.println("Prix total : " + cart.getPrice());

                int action = menuInt(() -> {
                    System.out.println("\n Choissier une action :");
                    System.out.println("\t->0. Quitter");
                    System.out.println("\t->1. ajouter un produit");
                    System.out.println("\t->2. editer un produit");
                    System.out.println("\t->3. supprimer un produit");
                }, 0, 3);

                switch (action) {
                    case 0: return;
                    case 1: ajouterAuPanier(); break;
                    case 2: editerProduitPanier(); break;
                    case 3: supprimerProduitPanier(); break;
                }
            }
        }
    }
    private static void validerPanier() {
        Buyer buyer = ((Buyer) account.getUser());
        buyer.getCart().validateCart();
        System.out.println("\n Vous avez validé votre panier !");
    }
    private static void afficherCommande() {

        System.out.println("Not implemented yet");

        Buyer buyer = ((Buyer) account.getUser());
        System.out.println("Vos commandes : ");
        for (Order order : buyer.getOrdersList()) {
            System.out.println("\tCommande n°" + order.getId() + " " + order.getDeliveryPoint() + " " + order.getDeliveryStatus().name());
            for (Entry<SelledProduct, Integer> entry : order.getOrderedProductList().entrySet()) {
                System.out.println("\t\t" + entry.getValue() + "x " + entry.getKey().getProduct().getDesignation() + " " + entry.getKey().getShippingTime().toHours() + "H");
            }
        }
    }

    private static void sellerMenu() {
        while(true) {
            System.out.println("\nBienvenue dans le menu vendeur !");
            int action = menuInt(() -> {
                System.out.println("\t->0. Quitter");
                System.out.println("\t->1. Voir les produits vendu");
                System.out.println("\t->2. Voir les resultats");
            }, 0, 2);

            switch (action) {
                case 0: return;
                case 1: afficherProduitVendu(); break;
                case 2: afficherVendeurResultat(); break;
            }
        }
    }

    private static void afficherVendeurResultat() {
        ExternSeller seller = ((ExternSeller) account.getUser());
        System.out.println("vos ventes :");
        double selledProfit = 0;
        for( SelledProduct selledProduct : seller.getProductList().values()) {
            double profit = selledProduct.getSelledCount() * selledProduct.getPrice();
            System.out.println("\t" + selledProduct.getSelledCount() + "x +" + selledProduct.getProduct().getDesignation() + " @ " + selledProduct.getPrice()  + "€ ==> " +  profit  + "€");
            selledProfit += profit;
        }
        System.out.println("Total profit : " + selledProfit + "€");

        System.out.print("\n Taper <ENTREE> pour continuer... ");
        scan.nextLine();
    }
    private static void afficherProduitVendu() {
        while(true) {
            Map<Product, SelledProduct> products = ((Seller) account.getUser()).getProductList();
            if (products.isEmpty()) {
                System.out.println("\nVous ne vendez pas encore de produit");
            } else {
                System.out.println("\nVos produits vendus :");
                for (Entry<Product, SelledProduct> entry : products.entrySet()) {
                    System.out.println(entry.getKey().getId() + "\t" + entry.getKey().getDesignation() + "\t" + entry.getKey().getDescription());
                }
            }

            int action = menuInt(() -> {
                System.out.println("\nVeuillez choisir une action : ");
                System.out.println("\t->0. Quitter");
                System.out.println("\t->1. Ajouter un produit");
                System.out.println("\t->2. Modifier un produit");
                System.out.println("\t->3. supprimer un produit");
            }, 0, 3);

            switch (action) {
                case 0: return;
                case 1: ajouterProduitVendu(); break;
                case 2: modifierProduitVendu(); break;
                case 3: supprimerProduitVendu(); break;
            }
        }
    }

    private static void ajouterProduitVendu() {
        Seller seller = (Seller) account.getUser();
        Map<Product, SelledProduct> products = seller.getProductList();

        System.out.println("\nLes produits que vous pouvez vendre :");
        for(Product product : Product.getProducts().values()) {
            if(!products.containsKey(product)) {
                System.out.println(product.getId() + "\t" + product.getDesignation() + "\t" + product.getDescription());
            }
        }

        System.out.print("Voulez vendre un produit qui n'existe pas encore ? (Y/N) : ");
        boolean exist = scan.nextLine().toLowerCase().equals("n");

        Product product;
        try {
            if (!exist) {
                System.out.print("\nEntrez la ref du produit a vendre : ");
                String ref = scan.nextLine();

                System.out.print("Entrez la designation du produit a vendre : ");
                String designation = scan.nextLine();

                System.out.print("Entrez la description du produit a vendre : ");
                String description = scan.nextLine();
                product = new Product(ref, designation, description);
            } else {
                System.out.print("\nEntrez l'id du produit a vendre : ");
                int productid = scan.nextInt();
                product = Product.getProducts().get(productid);
                if (product == null) {
                    System.out.println("L'id " + productid + " n'existe pas");
                    return;
                } else if (products.containsKey(product)) {
                    System.out.println("Vous vendez deja le produit !");
                    return;
                }
            }

            System.out.print("Entrez le prix du produit a vendre : ");
            int price = scan.nextInt();

            System.out.print("Entrez le prix de livraison du produit a vendre : ");
            int shipprice = scan.nextInt();

            System.out.print("Entrez le temp de livraison en jours du produit a vendre : ");
            int days = scan.nextInt();

            System.out.print("Entrez le stock du produit a vendre : ");
            int stock = scan.nextInt();

            seller.registerNewSelledProduct(
                    product,
                    new SelledProduct(stock, price, shipprice, Duration.ofDays(days))
            );

        } catch (InputMismatchException e) {
            System.out.println("Valeur invalide");
            return;
        }


    }
    private static void modifierProduitVendu() {
        Map<Product, SelledProduct> products = ((Seller) account.getUser()).getProductList();
        System.out.println("\nVos produits en vente : ");
        for (Entry<Product, SelledProduct> entry : products.entrySet()) {
            System.out.println(entry.getKey().getId() + "\t" + entry.getKey().getDesignation() + "\t" + entry.getKey().getDescription() + "\tx" + entry.getValue().getStock() + "\t" + entry.getValue().getShippingTime() + "j\t" + entry.getValue().getShippingPrice() + "€\t" + entry.getValue().getPrice());
        }
        try {
            System.out.print("Entrez l'id du produit a modifier : ");
            int id = scan.nextInt();
            Product product = Product.getProducts().get(id);
            if (product == null) {
                System.out.println("L'id " + id + " n'existe pas");
                return;
            } else if (!products.containsKey(product)) {
                System.out.println("Vous ne vendez pas ce produit");
                return;
            }
            SelledProduct selledProduct = products.get(product);

            System.out.print("Entrez le nouveau stock du produit a modifier (negatif pour rien modifier) : ");
            int stock = scan.nextInt();
            if (stock > 0) selledProduct.setStock(stock);

            System.out.print("Entrez le nouveau temp de livraison a modifier (negatif pour rien modifier) : ");
            int days = scan.nextInt();
            if (days > 0) selledProduct.setShippingTime(Duration.ofDays(days));

            System.out.print("Entrez le nouveau prix du produit a modifier (negatif pour rien modifier) : ");
            int price = scan.nextInt();
            if (price > 0) selledProduct.setPrice(price);

            System.out.print("Entrez le nouveau prix de livraison du produit a modifier (negatif pour rien modifier) : ");
            int shipprice = scan.nextInt();
            if (shipprice > 0) selledProduct.setShippingPrice(shipprice);

            System.out.println(selledProduct.getProduct().getId() + "\t" + selledProduct.getProduct().getDesignation() + "\t" + selledProduct.getProduct().getDescription() + "\tx" + selledProduct.getStock() + "\t" + selledProduct.getShippingTime() + "j\t" + selledProduct.getShippingPrice() + "€\t" + selledProduct.getPrice());
        } catch (InputMismatchException e) {
            System.out.println("Valeur invalide");
            return;
        }
    }
    private static void supprimerProduitVendu() {
        Map<Product, SelledProduct> products = ((Seller) account.getUser()).getProductList();

        System.out.println("\nVos produits vendus :");
        for (Entry<Product, SelledProduct> entry : products.entrySet()) {
            System.out.println(entry.getKey().getId() + "\t" + entry.getKey().getDesignation() + "\t" + entry.getKey().getDescription());
        }
        try {
            System.out.print("Entrez l'id du produit a supprimer : ");
            int id = scan.nextInt();
            Product product = Product.getProducts().get(id);
            if (product == null) {
                System.out.println("L'id " + id + " n'existe pas");
                return;
            } else if (!products.containsKey(product)) {
                System.out.println("Vous ne vendez pas ce produit");
                return;
            }
            SelledProduct selledProduct = products.get(product);

            selledProduct.unregister();
        } catch (InputMismatchException e) {
            System.out.println("Valeur invalide");
            return;
        }
    }

    private static void adminMenu() {
        while(true) {
            System.out.println("\nBievenue dans le menu admin !");
            int action = menuInt(() -> {
                System.out.println("\t->0. Quitter");
                System.out.println("\t->1. Voir les produits vendu");
                System.out.println("\t->2. Voir les resultats");
                System.out.println("\t->3. Gerer les contracts");
            }, 0, 3);

            switch (action) {
                case 0: return;
                case 1: afficherProduitVendu(); break;
                case 2: afficherAdminResultat(); break;
                case 3: gererContrat();
            }
        }
    }

    private static void afficherAdminResultat() {
        Admin admin = ((Admin) account.getUser());

        System.out.println("\nLes resultat des ventes !");
        System.out.println("Vos vente :");
        double selledProfit = 0;
        for (SelledProduct selledProduct : admin.getProductList().values()) {
            double profit = selledProduct.getSelledCount() * selledProduct.getPrice();
            System.out.println("\t" + selledProduct.getSelledCount() + "x +" + selledProduct.getProduct().getDesignation() + " @ " + selledProduct.getPrice()  + "€ ==> " +  profit  + "€");
            selledProfit += profit;
        }
        System.out.println("Profit vente : " + selledProfit + "€");
        System.out.println("\nVos commissions : ");
        double commissionProfit = 0;
        for (Seller seller :  Seller.getSellers().values()) {
            if (seller instanceof ExternSeller) {
                Contract contract = Contract.getContractList().get(seller);
                if(contract != null) {
                    for (SelledProduct selledProduct : seller.getProductList().values()) {
                        double profit = contract.getCommission() * selledProduct.getPrice() * selledProduct.getSelledCount();
                        System.out.println("\t" + seller.getName() + "(" + seller.getId() + ") " + selledProduct.getSelledCount() + "x " + selledProduct.getProduct().getDesignation() + " @ " + selledProduct.getPrice() + "€ : " + contract.getCommission() * 100 + "% ==> " + profit + "€");
                        commissionProfit += profit;
                    }
                }
            }
        }
        System.out.println("Commission profit : " + commissionProfit + "€");
        System.out.println("Total profit : " + (commissionProfit + selledProfit) + "€");

        System.out.print("\n Taper <ENTREE> pour continuer... ");
        scan.nextLine();
    }
    private static void gererContrat() {
        while (true) {
            Map<ExternSeller, Contract> contractBySeller = Contract.getContractList();

            if (contractBySeller.isEmpty()) {
                System.out.println("\nAucun contrat n'a encore été signé !");
            } else {
                System.out.println("\nLes contrat signé : ");
                for (Entry<ExternSeller, Contract> entry : contractBySeller.entrySet()) {
                    System.out.println("\tMarketPlace <-<#>-> " + entry.getValue().getSeller().getName() + "(" + entry.getKey().getId() + ")  @  " + entry.getValue().getCommission()*100 + "% / " + entry.getValue().getExpirationDate() + "\t(" + (entry.getValue().isValid() ? "VALID" : "INVALID") + ")");
                }

                int action = menuInt(() -> {
                    System.out.println("\nVeuillez choisir une action");
                    System.out.println("\t->0. Quitter");
                    System.out.println("\t->1. Ajouter un contat");
                    System.out.println("\t->2. Modifier un contrat");
                    System.out.println("\t->3. Supprimer un contrat");
                },0,3);

                switch (action) {
                    case 0: return;
                    case 1: ajouterContrat(); break;
                    case 2: modifierContrat(); break;
                    case 3: supprimerContrat(); break;
                }
            }
        }
    }

    private static void ajouterContrat() {
        //TODO: LOW messy code - rewrite
        List<Seller> sellers = Seller.getSellers().values().stream().filter(seller -> seller instanceof ExternSeller && !Contract.getContractList().containsKey(seller)  ).collect(Collectors.toList());
        if(sellers.isEmpty()) {
            System.out.println("\nAucun vendeur a contractualisé");
            return;
        }
        System.out.println("\nLes vendeur non contractualisé : ");
        for (Seller seller : sellers) {
            System.out.println("\t" + seller.getId() + " " + seller.getName());
        }

        try {
            System.out.print("Entrez l'id du vendeur a contractualisé : ");
            int sellerid = scan.nextInt();
            Seller seller = ExternSeller.getSellers().get(sellerid);
            if(seller == null || seller instanceof Admin || Contract.getContractList().containsKey(seller)) {
                System.out.println("Vous ne pouvez pas contractualisé avec ce vendeur");
                return;
            }
            ExternSeller extern = (ExternSeller) seller;

            /* TODO: LOW customize contract
            System.out.println("Entrez la commission en taux ([0-1[) du contrat : ");
            double commission = scan.nextDouble();
            if(commission < 0 || commission >= 1) {
                System.out.println("Commission invalide");
            }
            */

            ((Admin) account.getUser()).createContract(extern);
        } catch (InputMismatchException e) {
            System.out.println("Valeur invalide");
            return;
        }
    }
    private static void modifierContrat() {
        try {
            System.out.println("Entrez l'id du vendeur dont vous voulez modifier le contrat : ");
            int sellerid = scan.nextInt();
            Seller seller = Seller.getSellers().get(sellerid);
            if (seller == null) {
                System.out.println("L'id " + sellerid + " n'existe pas");
                return;
            } else if (!Contract.getContractList().containsKey(seller)) {
                System.out.println("Ce vendeur n'a pas de contrat");
                return;
            }
            Contract contract = Contract.getContractList().get(seller);
            System.out.println("\nVous avez selectionner le contrat suivant : ");
            System.out.println("\tMarketPlace <-<#>-> " + contract.getSeller().getName() + "(" + contract.getSeller().getId() + ")  @  " + contract.getCommission() * 100 + "% / " + contract.getExpirationDate() + "\t(" + (contract.isValid() ? "VALID" : "INVALID") + ")");

            System.out.println("\nCommission actuelle : " + contract.getCommission());
            System.out.println("Entrez la nouvelle commission en taux ([0,1[) du coutrat (negatif pour rien modifier) : ");
            double commission = scan.nextDouble();
            if (commission >= 0 && commission < 1) contract.setCommission(commission);

            System.out.println("Date d'expiration actuelle : " + contract.getExpirationDate());
            System.out.println("Entrez de combien de jour vous voulez modifier le contrat : ");
            int date = scan.nextInt();
            Calendar calendar = Calendar.getInstance();
            calendar.setTime(contract.getExpirationDate());
            calendar.add(Calendar.DAY_OF_YEAR, date);
            contract.setExpirationDate(calendar.getTime());

            System.out.println("Le nouveau contrat : ");
            System.out.println("\tMarketPlace <-<#>-> " + contract.getSeller().getName() + "(" + contract.getSeller().getId() + ")  @  " + contract.getCommission() * 100 + "% / " + contract.getExpirationDate() + "\t(" + (contract.isValid() ? "VALID" : "INVALID") + ")");

        } catch (InputMismatchException e) {
            System.out.println("Valeur invalide");
            return;
        }
    }
    private static void supprimerContrat() {
        //TODO: MEDIUM
        System.out.println("Not implemented yet");
    }

    static void test() {

        //test 1 -> load products
        System.out.println("TEST 1");
        Product.load();
        Product.getProducts().forEach((id, prod) -> System.out.println(id));
/*
        //test 2 -> create selledProduct
        System.out.println("TEST 2");
        Seller seller = new ExternSeller("test1");
        seller.register();
        seller.registerNewSelledProduct(
                Product.getProducts().get(0),
                new SelledProduct(50, 15, 1, Duration.ofDays(1))
        );
        seller.getProductList().forEach((prod, info) -> System.out.println(prod.getReference() + " at " + info.getPrice() + "$"));

        //test 3 -> remove product
        System.out.println("TEST 3");
        Seller seller2 = new ExternSeller("test2");
        seller2.register();
        seller2.registerNewSelledProduct(
                Product.getProducts().get(0),
                new SelledProduct(50,14,2,Duration.ofDays(2))
        );
        seller2.getProductList().forEach((prod, info) -> System.out.println(prod.getReference() + " at " + info.getPrice() + "$"));

        SelledProduct.getSelledProducts().forEach((id,info) -> System.out.println(info.getSeller().getName() + " sell " + info.getProduct().getDesignation() + " for " + info.getPrice()));

        Product.getProducts().get(0).getSellers().forEach((s,p) -> System.out.println(s.getName()));
        Product.getProducts().get(0).unregister();
        SelledProduct.getSelledProducts().forEach((id,info) -> System.out.println(info.getSeller().getName() + " sell " + info.getProduct().getDesignation() + " for " + info.getPrice()));

        //test 4 -> remove selledProduct
        System.out.println("TEST 4");
        Seller seller3 = new ExternSeller("test3");
        seller3.register();
        seller3.registerNewSelledProduct(
                new Product("DDD", "Jeux video", ""),
                new SelledProduct(15,12,0.5,Duration.ofDays(2))
        );
        seller3.getProductList().forEach((prod, info) -> System.out.println(prod.getReference() + " at " + info.getPrice() + "$"));

        seller3.unregister(seller3.getProductList().get(Product.getProducts().get(3)));
        seller3.getProductList().forEach((prod, info) -> System.out.println(prod.getReference() + " at " + info.getPrice() + "$"));

        Seller admin = new Admin();
        admin.registerNewSelledProduct(
                Product.getProducts().get(0),
                new SelledProduct(50,15,1,Duration.ofDays(1))
        );
        admin.registerNewSelledProduct(
                Product.getProducts().get(1),
                new SelledProduct(50,15,1,Duration.ofDays(1))
        );

        admin.getProductList().forEach((product,info) -> System.out.println(product.getDesignation() + " at " + info.getPrice()));

        Buyer buyer = new Buyer(0, new Cart(null), new ArrayList<Order>());
        buyer.addProductToCart(SelledProduct.getSelledProducts().get(0), 2 );
        buyer.addProductToCart(SelledProduct.getSelledProducts().get(1), 5);
        buyer.getCart().getProductList().forEach((product, quantity) -> System.out.println(quantity + "x " + product.getProduct().getDesignation() + " at " + product.getPrice()));

        buyer.getCart().editProduct(SelledProduct.getSelledProducts().get(1), 3);
        buyer.getCart().getProductList().forEach((product, quantity) -> System.out.println(quantity + "x " + product.getProduct().getDesignation() + " at " + product.getPrice()));

        buyer.getCart().removeProduct(SelledProduct.getSelledProducts().get(0));
        buyer.getCart().getProductList().forEach((product, quantity) -> System.out.println(quantity + "x " + product.getProduct().getDesignation() + " at " + product.getPrice()));
*/
    }
}
