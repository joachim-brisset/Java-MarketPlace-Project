package project;

import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import project.marketplace.*;

import java.io.FileInputStream;
import java.io.IOException;
import java.time.Duration;
import java.util.*;
import java.util.Map.Entry;

public class App {

    public static XSSFWorkbook workook;
    private static final Scanner scan = new Scanner(System.in);
    private static Account account;

    //todo: review - bug some time
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
        FileInputStream fis = new FileInputStream("H:\\Desktop\\database.xlsx");
        workook = new XSSFWorkbook(fis);
        fis.close();

        Product.load();
        Seller.load();
        SelledProduct.load();

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
            int action = menuInt(() -> {
                System.out.println("Bievenue ! Veuillez-vous identifier ou vous enregister :");
                System.out.println("\t-> 0. quitter");
                System.out.println("\t-> 1. s'identifier");
                System.out.println("\t-> 2. s'enregistrer");
            }, 0, 2);

            //java 14 switch expression
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
        }
    }

    private static boolean signUpMenu() {
        //TODO: implement
        System.out.println("Not implemented yet");
        return false;
    }
    private static boolean signInMenu() {
        while (!account.isConnected()) {
            int action = menuInt(() -> {
                System.out.println("\nVeuillez a present vous connecter :");
                System.out.println("\t-> 0. quitter");
                System.out.println("\t-> 1. Modifier votre identifiant. (Actuellement : " + account.getLogin() + " )");
                System.out.println("\t-> 2. Modifier votre password. (Actuellement : " + account.getPass() + " )");
                System.out.println("\t-> 3. Se connecter !");
            }, 0, 3);

            boolean abondon = false;
            switch (action) {
                case 0: abondon = true; break;
                case 1:
                    System.out.print("Entrez le nouveau identifiant : ");
                    account.setLogin(scan.nextLine());
                    abondon = false;
                    break;
                case 2:
                    System.out.print("Entrez le nouveau password :");
                    account.setPass(scan.nextLine());
                    abondon = false;
                    break;
                case 3:
                    account.signIn();
                    abondon = false;
                    break;
                default: throw new IllegalArgumentException();
            };
            if (abondon) return false;
        }
        return true;
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
                System.out.println("\t->3. Valider panier");
            }, 0, 4);

            switch (action) {
                case 0: account.setConnected(false); return;
                case 1: afficherProduitMenu(); break;
                case 2: ajouterAuPanier(); break;
                case 3: afficherPanier(); break;
                case 4: validerPanier(); break;
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
            System.out.print("Entrez l'id ud produit a acheter : ");
            int productid = scan.nextInt();
            SelledProduct selledProduct = SelledProduct.getSelledProducts().get(productid);
            if (selledProduct == null) {
                System.out.println("l'id " + productid + " n'existe pas !");
                return;
            }

            System.out.print("Entrez le nombre de produit a acherter");
            int quantity = scan.nextInt();

            Buyer buyer = (Buyer) account.getUser();
            buyer.getCart().editProduct(selledProduct, quantity);

        }catch (InputMismatchException e) {
            System.out.println("Entrez invalide !");
            return;
        }
    }
    private static void editerProduitPanier() {
        try {
            System.out.print("Entrez l'id ud produit a editer : ");
            int productid = scan.nextInt();
            SelledProduct selledProduct = SelledProduct.getSelledProducts().get(productid);
            if (selledProduct == null) {
                System.out.println("l'id " + productid + " n'existe pas !");
                return;
            }

            System.out.print("Entrez le nombre de produit a acherter");
            int quantity = scan.nextInt();

            Buyer buyer = (Buyer) account.getUser();
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
                //TODO: price do not include shippingPrice

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
        //TODO:
    }

    private static void sellerMenu() {
        System.out.println("\nBievenue dans le menu vendeur !");
        menuInt(() -> {
            System.out.println("\t->1. Voir les produits vendu");
            System.out.println("\t->2. Ajouter un produit");
            System.out.println("\t->3. Voir les produits vendu");
        }, 0,5);
    }
    private static void adminMenu() {
        System.out.println("\nBievenue dans le menu admin !");
        //TODO:
    }

    static void test() {
        //TODO: Contract


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
*/

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

    }
}
