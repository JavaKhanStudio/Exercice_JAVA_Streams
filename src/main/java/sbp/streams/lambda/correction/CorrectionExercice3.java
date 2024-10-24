package sbp.streams.lambda.correction;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Function;
import java.util.function.Predicate;

public class CorrectionExercice3{


    public static void main(String[] args) {
        List<MonObjet> objets = new ArrayList<>();
        objets.add(new MonObjet("Banana", 5));
        objets.add(new MonObjet("Grape", 10));
        objets.add(new MonObjet("Apple", 2));
        objets.add(new MonObjet("Orange", 20));
        objets.add(new MonObjet("Melon", 1));

        // 1. Filtrer par entier avec une condition
        List<MonObjet> filtrés = filtrerParEntier(objets, valeur -> valeur > 5);
        System.out.println("Objets filtrés par valeur > 5 :");
        filtrés.forEach(System.out::println);

        // 2. Calculer la somme avec une lambda (somme des valeurs)
        int somme = calculerSomme(objets, MonObjet::getValeur);
        System.out.println("Somme des valeurs : " + somme);

        // 2.b Multiplication des valeurs
        int produit = calculerMultiplication(objets, MonObjet::getValeur);
        System.out.println("Produit des valeurs : " + produit);

        // 3. Modifier les Strings avec une lambda (par exemple, tout en majuscules)
        modifierString(objets, nom -> nom.toUpperCase());
        System.out.println("Objets avec noms modifiés :");
        objets.forEach(System.out::println);

        // 4. Vérifier si au moins un élément a une valeur spécifique
        boolean contient = contientValeur(objets, valeur -> valeur == 10);
        System.out.println("Contient un objet avec la valeur 10 ? " + contient);
    }

    // Méthode pour filtrer une liste selon une condition sur l'entier
    public static List<MonObjet> filtrerParEntier(List<MonObjet> objets, Predicate<Integer> condition) {
        List<MonObjet> resultats = new ArrayList<>();
        for (MonObjet obj : objets) {
            if (condition.test(obj.getValeur())) {
                resultats.add(obj);
            }
        }
        return resultats;
    }

    // Méthode pour calculer la somme
    public static int calculerSomme(List<MonObjet> objets, Function<MonObjet, Integer> fonction) {
        int somme = 0;
        for (MonObjet obj : objets) {
            somme += fonction.apply(obj);
        }
        return somme;
    }

    // Méthode pour calculer la multiplication
    public static int calculerMultiplication(List<MonObjet> objets, Function<MonObjet, Integer> fonction) {
        int produit = 1;
        for (MonObjet obj : objets) {
            produit *= fonction.apply(obj);
        }
        return produit;
    }

    // Méthode pour modifier les strings de la liste
    public static void modifierString(List<MonObjet> objets, Function<String, String> fonction) {
        for (MonObjet obj : objets) {
            obj.setNom(fonction.apply(obj.getNom()));
        }
    }

    // Méthode pour vérifier si un élément satisfait une condition
    public static boolean contientValeur(List<MonObjet> objets, Predicate<Integer> condition) {
        for (MonObjet obj : objets) {
            if (condition.test(obj.getValeur())) {
                return true;
            }
        }
        return false;
    }



}
