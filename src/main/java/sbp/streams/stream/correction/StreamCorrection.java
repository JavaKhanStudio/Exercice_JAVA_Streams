package sbp.streams.stream.correction;


import sbp.streams.stream.enums.SexeEnum;
import sbp.streams.stream.model.Animal;
import sbp.streams.stream.model.Personne;

import javax.swing.*;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicReference;
import java.util.stream.Collectors;

import static sbp.streams.stream.BanqueDeDonnees.people;
import static sbp.streams.stream.enums.AnimalTypeEnum.CHAT;
import static sbp.streams.stream.enums.AnimalTypeEnum.CHIEN;

public class StreamCorrection {

    // Bienvenue dans ce puzzle
    // Chaque element de ce dernier peut être résolu en un seul stream
    // faite set la variable a l'aide de ce stream pour voir si votre réponse est juste
    public static void codePourPuzzle() {

        // Exemple Trouver le nombre de personne majeur dans la liste
        long nombrePerssoneMajeur = -1 ;
        nombrePerssoneMajeur =
                people.stream()
                        .filter(personne -> personne.getAge() >= 18)
                        .count() ;

        // Retourner tous les d'homme
        List<Personne> listeHomme = Collections.emptyList();
        listeHomme =
                people.stream()
                        .filter(personne -> personne.getSexe() == SexeEnum.MALE)
                        .collect(Collectors.toList());

        // Trouver le nombre d'inconnu mineur
        long nombreInconnuMineur = -1 ;
        nombreInconnuMineur =
                people.stream()
                        .filter(personne -> personne.getSexe() == SexeEnum.INCONNU)
                        .filter(personne -> personne.getAge() < 18)
                        .count() ;



        // Trouver La somme total de l'argent gagnier par les femmes majeurs
        long argentFemmeMajeur = -1 ;
        argentFemmeMajeur =
                people.stream()
                        .filter(personne -> personne.getSexe() == SexeEnum.FEMELLE)
                        .filter(personne -> personne.getAge() >= 18)
                        .mapToInt(Personne::getSalaire)
                        .sum() ;

        // Trouver la plus vieille personne dans la liste
        Personne vieux = null ;
        vieux =
                people.stream()
                        .max((o1, o2) -> o1.getAge() - o2.getAge())
                        .orElse(null);


        // En gardant uniquement les personnes majeurs,
        // D'un sexe defini (pas d'inconnu)
        // diviser la liste par un map diviser par genre
        Map<SexeEnum, List<Personne>> persons = new HashMap<>() ; ;
        persons =
                people.stream()
                        .filter(personne -> personne.getSexe() != SexeEnum.INCONNU)
                        .collect(Collectors.groupingBy(Personne::getSexe));

        // Organiser, en ordre croissant, les hommes de moins de 40 ans par salaire.
        // Retourner uniquement les salaires
        int[] salaireHommeOrdreCroissant = new int[0] ;
        salaireHommeOrdreCroissant =
                people.stream()
                        .filter(personne -> personne.getSexe() == SexeEnum.MALE)
                        .filter(personne -> personne.getAge() < 40)
                        .mapToInt(Personne::getSalaire)
                        .sorted()
                        .toArray();

        // Trouver tous les animaux de compagnie
        List<Animal> animaux = Collections.emptyList();
        animaux =
                people.stream()
                .map(Personne::getAnimaux)
                .flatMap(List::stream)
                .collect(Collectors.toList()) ;


        // Trouver le total des lengths de tous les noms de tous les animaux
        // Appartement à des femmes majeur
        // Pour les animaux autre que des chats ou des chiens
        long animalNameLength = -1 ;
        animalNameLength =
            people.stream()
                    .filter(personne -> personne.getSexe() == SexeEnum.FEMELLE)
                    .filter(personne -> personne.getAge() >= 18)
                    .map(Personne::getAnimaux)
                    .flatMap(List::stream)
                    .filter(animal -> animal.getAnimalType() != CHAT && animal.getAnimalType() != CHIEN)
                    .mapToInt(animal -> animal.getNom().length())
                    .sum() ;

        // Parfois une liste c'est plus simple
        AtomicReference<Integer> total = new AtomicReference<>(0);
        for(Personne person : people) {
            if(person.getAge() >= 18 && person.getSexe() == SexeEnum.FEMELLE) {
                person.getAnimaux().forEach(
                        animal -> {
                            if(animal.getAnimalType() != CHIEN && animal.getAnimalType() != CHAT) {
                                total.updateAndGet(v -> v + animal.getNom().length());
                            }
                        }
                );
            }
        }



        // Difficile
        // Obtenir, en un seul stream
        // Une map des salaires moyen par sexe
        // Des gens ayant un chat de type FEMELLE
        // en ignorant les duplications
        // Autant des animaux de compagnie que des personnes
        Map<SexeEnum, Double> salaireMoyens = new HashMap<SexeEnum, Double>(); ;
        salaireMoyens =
                people.stream()
                        .distinct()
                        .filter(personne ->
                                personne.getAnimaux()
                                        .stream()
                                        .distinct()
                                        .filter(animal -> animal.getSexeEnum() == SexeEnum.MALE)
                                        .anyMatch(animal -> animal.getAnimalType() == CHAT)
                        )
                        .collect(Collectors.groupingBy(Personne::getSexe, Collectors.averagingInt(Personne::getSalaire)));


        moulinetteDeVerification(nombrePerssoneMajeur, listeHomme, nombreInconnuMineur, argentFemmeMajeur, vieux, animaux, animalNameLength,
                persons.size(),salaireHommeOrdreCroissant, salaireMoyens) ;


    }

    public static void moulinetteDeVerification(long nombrePerssoneMajeur,List<Personne> listeHomme, long nombreInconnuMineur, long argentFemmeMajeur,Personne vieux,List<Animal> animaux,long animalNameLength,
                                                long persons,int[] salaireHommeOrdreCroissant,Map<SexeEnum, Double> salaireMoyens) {

        System.out.println(salaireMoyens);


        String fullMessage = "";

        fullMessage += "=== Résultat des streams ===\n";
        fullMessage += "- " + generateMessage("nombrePerssoneMajeur",
                nombrePerssoneMajeur,
                nombrePerssoneMajeur == 44,
                nombrePerssoneMajeur == -1 || nombrePerssoneMajeur == 0);
        fullMessage += "- " + generateMessage("listeHomme",
                listeHomme,
                listeHomme.size() == 18,
                listeHomme.isEmpty());
        fullMessage += "- " + generateMessage("nombreInconnuMineur",
                nombreInconnuMineur,
                nombreInconnuMineur == 14,
                nombreInconnuMineur == -1 || nombreInconnuMineur == 0);

        fullMessage += "- " + generateMessage("argentFemmeMajeur",
                argentFemmeMajeur,
                argentFemmeMajeur == 1196000,
                argentFemmeMajeur == -1 || argentFemmeMajeur == 0);

        fullMessage += "- " + generateMessage("vieux",
                vieux != null,
                vieux!= null && vieux.getNom().equals("George"),
                vieux == null);

        fullMessage += "- " + generateMessage("animaux",
                animaux,
                animaux.size() == 49,
                animaux.isEmpty());

        fullMessage += "- " + generateMessage("animalNameLength",
                animalNameLength,
                animalNameLength == 20,
                animalNameLength == -1 || animalNameLength == 0);

        fullMessage += "- " + generateMessage("persons",
                persons,
                persons == 2,
                persons == 0);

        fullMessage += "- " + generateMessage("salaireHommeOrdreCroissant",
                salaireHommeOrdreCroissant,
                salaireHommeOrdreCroissant.length == 15 && salaireHommeOrdreCroissant[0] == 0 && salaireHommeOrdreCroissant[14] == 72000,
                salaireHommeOrdreCroissant.length == 0);



        fullMessage += "- " + generateMessage("salaireMoyens",
                salaireMoyens,
                salaireMoyens.size() == 3 && salaireMoyens.get(SexeEnum.INCONNU) == 7000 && salaireMoyens.get(SexeEnum.MALE) > 42665 && salaireMoyens.get(SexeEnum.FEMELLE) == 61500,
                animaux.isEmpty());

        JOptionPane.showMessageDialog(null, fullMessage);
    }

    // Method to generate messages
    private static String generateMessage(String variableName, Object variableValue, boolean isCorrect, boolean isNotTried) {
        if(isNotTried) {
            return String.format("ℹ Vous n'avez pas encore essayé **%s**.\n", variableName) ;
        } else if (isCorrect) {
            return String.format("✔ Vous avez raison ! La réponse pour **%s** est correcte.\n", variableName) ;
        } else {
            return String.format("❌ Il semble que la réponse pour **%s** soit incorrecte, essayez encore.\n", variableName);
        }
    }


    public static void main(String[] args) {
        try {
            codePourPuzzle() ;
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Vous sembler avoir brisé quelque chose... allez voir la console !", "Erreur", JOptionPane.ERROR_MESSAGE);
            e.printStackTrace();
        }
    }

}
