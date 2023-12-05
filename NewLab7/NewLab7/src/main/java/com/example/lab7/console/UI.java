package com.example.lab7.console;

import com.example.lab7.domain.Utilizator;
import com.example.lab7.domain.UtilizatorDatePair;
import com.example.lab7.exceptions.*;
import com.example.lab7.service.Service;
import com.example.lab7.validators.ValidationException;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.Scanner;
import java.util.concurrent.atomic.AtomicInteger;

import static com.example.lab7.controller.ConvertorLuna.convertorLuna;

public class UI {

    static final int ADAUGARE_UTILIZATOR = 1;
    static final int STERGERE_UTILIZATOR = 2;
    static final int MODIFICARE_UTILIZATOR =3;
    static final int CAUTA_UTILIZATOR = 4;
    static final int AFISARE_UTILIZATORI = 5;
    static final int ADAUGARE_PRIETEN = 6;
    static final int STERGERE_PRIETEN = 7;
    static final int AFISARE_PRIETENII = 8;
    static final int AFISARE_TOATE_COMUNITATILE = 9;
    static final int AFISARE_NUMAR_COMUNITATI = 10;
    static final int AFISARE_CEA_MAI_SOCIABILA_COMUNITATE = 11;
    static final int AFISARE_PRIETENI_UTILIZATOR = 12;
    static final int IESIRE_APLICATIE = 0;

    private static Scanner scanner = new Scanner(System.in);

    public static void ui(Service service) {

//        Utilizator u1 = new Utilizator("Vasile", "Bogdan"); service.adaugaUtilizator(u1.getPrenume(), u1.getNume());
//        Utilizator u2 = new Utilizator("Andrei", "Ioan"); service.adaugaUtilizator(u2.getPrenume(), u2.getNume());
//        Utilizator u3 = new Utilizator("Ion", "Petre"); service.adaugaUtilizator(u3.getPrenume(), u3.getNume());
//        Utilizator u4 = new Utilizator("George", "Pop"); service.adaugaUtilizator(u4.getPrenume(), u4.getNume());
//        Utilizator u5 = new Utilizator("Cristian", "Muresan"); service.adaugaUtilizator(u5.getPrenume(), u5.getNume());
//        Utilizator u6 = new Utilizator("Ionel", "Pop"); service.adaugaUtilizator(u6.getPrenume(), u6.getNume());
//        Utilizator u7 = new Utilizator("Violeta", "Ciorpan"); service.adaugaUtilizator(u7.getPrenume(), u7.getNume());
//        Utilizator u8 = new Utilizator("Victor", "Bene"); service.adaugaUtilizator(u8.getPrenume(), u8.getNume());
//        Utilizator u9 = new Utilizator("Cosmin", "Muresan"); service.adaugaUtilizator(u9.getPrenume(), u9.getNume());
//        Utilizator u10 = new Utilizator("Zoltan", "Nagy"); service.adaugaUtilizator(u10.getPrenume(), u10.getNume());
//        Utilizator u11 = new Utilizator("Maria", "Vasilescu"); service.adaugaUtilizator(u11.getPrenume(), u11.getNume());
//        service.adaugaPrietenie(8L, 4L);
//        service.adaugaPrietenie(5L, 1L);
//        service.adaugaPrietenie(5L, 2L);
//        service.adaugaPrietenie(2L, 6L);
//        service.adaugaPrietenie(3L, 5L);
//        service.adaugaPrietenie(4L, 7L);
//        service.adaugaPrietenie(10L, 11L);

        while (true) {
            try {
                afisareMeniu();
                int comanda = scanner.nextInt();
                scanner.nextLine();

                switch (comanda) {
                    case ADAUGARE_UTILIZATOR:
                        adaugare_utilizator(service);
                        break;

                    case STERGERE_UTILIZATOR:
                        stergere_utilizator(service);
                        break;

                    case MODIFICARE_UTILIZATOR:
                        modificare_utilizator(service);
                        break;

                    case CAUTA_UTILIZATOR:
                        cautare_utilizator(service);
                        break;

                    case AFISARE_UTILIZATORI:
                        afisare_utilizatori(service);
                        break;

                    case ADAUGARE_PRIETEN:
                        adaugare_prieten(service);
                        break;

                    case STERGERE_PRIETEN:
                        stergere_prieten(service);
                        break;

                    case AFISARE_PRIETENII:
                        afisare_prietenii(service);
                        break;

                    case AFISARE_TOATE_COMUNITATILE:
                        afisare_toate_comunitatile(service);
                        break;

                    case AFISARE_NUMAR_COMUNITATI:
                        afisare_numar_comunitati(service);
                        break;

                    case AFISARE_CEA_MAI_SOCIABILA_COMUNITATE:
                        afisare_cea_mai_sociabila_comunitate(service);
                        break;

                    case AFISARE_PRIETENI_UTILIZATOR:
                        afisare_prieteni_utilizator_creati_in_luna_specifica(service);
                        break;

                    case IESIRE_APLICATIE:
                        iesire_aplicatie();
                        break;
                    default:
                        System.out.println("Optiune invalida! Alege o optiune valida din meniu!");
                }
            } catch (ValidationException | EntitateNull | IDuriIdenticeException | PrietenieExistentaException |
                     PrietenieInexistentaException | UtilizatorInexistentException e) {
                System.out.println(e.getMessage());
            }
        }
    }

    private static void iesire_aplicatie() {
        System.out.println("^_^");
        System.exit(0);
    }

    private static void afisare_prieteni_utilizator_creati_in_luna_specifica(Service service) {
        System.out.println("Introduceti ID-ul utilizatorului: ");
        Long utilizatorID = scanner.nextLong();
        System.out.println("Introduceti luna anului (1-12): ");
        int luna;
        do {
            luna = scanner.nextInt();
            if (luna > 12 || luna < 1) System.out.println("Introduceti o luna valida! (1-12): ");
        } while (luna > 12 || luna < 1);
        int finalLuna = luna;
        String lunaString = convertorLuna(finalLuna);

        List<UtilizatorDatePair> prieteniiInLunaSpecifica = service.getPrieteniDataPentruUtilizatorInLunaSpecifica(utilizatorID, finalLuna);

        if (prieteniiInLunaSpecifica.isEmpty()) {
            System.out.println("Utilizatorul nu s-a imprietenit cu nimeni in luna " + lunaString + "!");
            return;
        }
        System.out.println("Prietenii cu care s-a imprietenit utilizatorul in luna " + lunaString + " sunt: ");
        System.out.println("==============================================================");
        System.out.println("Nume Prieten | Prenume Prieten | Data de la care sunt prieteni");
        prieteniiInLunaSpecifica.forEach(pereche -> {
            Utilizator prieten = pereche.getUtilizator();
            LocalDateTime date = pereche.getDate();
            String formatAfisare = String.format("%-12s | %-15s | %s", prieten.getNume(), prieten.getPrenume(), date);
            System.out.println(formatAfisare);
        });
    }

    private static void afisare_numar_comunitati(Service service) {
        System.out.println("Numar comunitati: " + service.getNumarComunitati());
    }

    private static void afisare_cea_mai_sociabila_comunitate(Service service) {
        List<Utilizator> ceaMaiSociabilaComunitate = service.ceaMaiSociabilaComunitate();
        System.out.println("Cea mai sociabila comunitate este alcatuita din: ");
        ceaMaiSociabilaComunitate.forEach(utilizatorSociabil -> {
            System.out.println(utilizatorSociabil.getNume() + " " + utilizatorSociabil.getPrenume() + ", ID: " +
                    utilizatorSociabil.getId());
        });
    }

    private static void afisare_toate_comunitatile(Service service) {
        List<List<Utilizator>> comunitati = service.getAllComunitati();
        AtomicInteger contor = new AtomicInteger();

        comunitati.forEach(comunitateCurenta -> {
            System.out.println("Comunitatea " + contor.incrementAndGet() + " este formata din:");
            comunitateCurenta.forEach(utilizatorDinComunitate -> {
                System.out.println(utilizatorDinComunitate.getNume() + " " +
                        utilizatorDinComunitate.getPrenume() + " ID: " + utilizatorDinComunitate.getId());
            });
            System.out.println();
        });
    }

    private static void afisare_prietenii(Service service) {
        System.out.println("Prieteniile:");
        Map<Utilizator, List<Utilizator>> utilizatoriCuPrietenii = service.getAllUtilizatoriCuPrietenii();
        for (Map.Entry<Utilizator, List<Utilizator>> entry: utilizatoriCuPrietenii.entrySet()) {
            Utilizator utilizator = entry.getKey();
            List<Utilizator> prieteni = entry.getValue();
            System.out.println("Utilizatorul " + utilizator.getNume() + " " + utilizator.getPrenume() +
                    " cu ID-ul " + utilizator.getId() + " are " + prieteni.size() + " prieteni:");
            if (prieteni != null) {
                for (Utilizator prieten: prieteni)
                    System.out.println(prieten.getNume() + " " + prieten.getPrenume() + ", ID: " + prieten.getId());
            }
        }
    }

    private static void stergere_prieten(Service service) {
        System.out.println("Introduceti ID-ul utilizatorului: ");
        Long idUtilizator2 = scanner.nextLong();
        System.out.println("Introduceti ID-ul prietenului: ");
        Long idPrieten2 = scanner.nextLong();

        service.stergePrietenie(idUtilizator2, idPrieten2);
        System.out.println("Prieten sters cu succes!");
    }

    private static void adaugare_prieten(Service service) {
        System.out.println("Introduceti ID-ul utilizatorului: ");
        Long idUtilizator = scanner.nextLong();
        System.out.println("Introduceti ID-ul prietenului: ");
        Long idPrieten = scanner.nextLong();

        service.adaugaPrietenie(idUtilizator, idPrieten);
        System.out.println("Prieten adaugat cu succes!");
    }

    private static void afisare_utilizatori(Service service) {
        System.out.println("Utilizatorii inscrisi in reteaua de socializare sunt:\n");
        Iterable<Utilizator> utilizatori = service.getAllUtilizatori();

        utilizatori.forEach(utilizatorDeAfisat -> {
            System.out.println(utilizatorDeAfisat.getNume() + " " + utilizatorDeAfisat.getPrenume() + " ID: " + utilizatorDeAfisat.getId());
        });
    }

    private static void cautare_utilizator(Service service) {
        System.out.println("Introduceti ID-ul utilizatorului cautat: ");
        Long idUtilizatorDeCautat = scanner.nextLong();
        Utilizator utilizator = service.cautaUtilizator(idUtilizatorDeCautat);

        System.out.println("Utilizatorul cautat este: ");
        System.out.println(utilizator.getNume() + " " + utilizator.getPrenume() + "\nPrieteni:");
        service.getPrieteniUtilizator(utilizator.getId()).forEach(prieten -> {
            System.out.println(prieten.getNume() + " " + prieten.getPrenume() + ", ID: " + prieten.getId());
        });
    }

    private static void modificare_utilizator(Service service) {
        System.out.println("Introduceti noul prenume al utilizatorului: ");
        String prenumeNou = scanner.nextLine();
        System.out.println("Introduceti noul nume al utilizatorului: ");
        String numeNou = scanner.nextLine();
        System.out.println("Introduceti ID-ul utilizatorului care se doreste modificat: ");
        Long id = scanner.nextLong();
        System.out.println("Introduceti noul username al utilizatorului: ");
        String usernameNou = scanner.nextLine();
        System.out.println("Introduceti noua parola a utilizatorului: ");
        String parolaNoua = scanner.nextLine();
        System.out.println("Introduceti noul email al utilizatorului: ");
        String emailNou = scanner.nextLine();

        service.modificaUtilizator(new Utilizator(prenumeNou, numeNou, usernameNou, parolaNoua, emailNou));
        System.out.println("Utilizator modificat cu succes: " + numeNou + " " + prenumeNou + " ID: " + id);
    }

    private static void stergere_utilizator(Service service) {
        System.out.println("Introduceti ID-ul utilizatorului pentru stergere: ");
        Long idUtilizatorDeSters = scanner.nextLong();
        Utilizator utilizator = service.cautaUtilizator(idUtilizatorDeSters);
        scanner.nextLine();
        service.stergeUtilizator(utilizator);

        System.out.println("Utilizator sters cu succes!");
    }

    private static void adaugare_utilizator(Service service) {
        System.out.println("Introduceti prenumele utilizatorului: ");
        String prenume = scanner.nextLine();
        System.out.println("Introduceti numele utilizatorului: ");
        String nume = scanner.nextLine();
        System.out.println("Introduceti username-ul utilizatorului: ");
        String username = scanner.nextLine();
        System.out.println("Introduceti parola utilizatorului: ");
        String parola = scanner.nextLine();
        System.out.println("Introduceti email-ul utilizatorului: ");
        String email = scanner.nextLine();

        service.adaugaUtilizator(new Utilizator(prenume, nume, username, parola, email));

        System.out.println("Utilizator adaugat cu succes: " + nume + " " + prenume);
    }

    private static void afisareMeniu() {
        System.out.println("   Meniu:");
        System.out.println(ADAUGARE_UTILIZATOR + ". Adauga utilizator");
        System.out.println(STERGERE_UTILIZATOR + ". Sterge utilizator");
        System.out.println(MODIFICARE_UTILIZATOR + ". Modifica utilizator");
        System.out.println(CAUTA_UTILIZATOR + ". Cauta utilizator");
        System.out.println(AFISARE_UTILIZATORI + ". Afiseaza toti utilizatorii");
        System.out.println(ADAUGARE_PRIETEN + ". Adauga prieten");
        System.out.println(STERGERE_PRIETEN + ". Sterge prieten");
        System.out.println(AFISARE_PRIETENII + ". Afiseaza toate prieteniile");
        System.out.println(AFISARE_TOATE_COMUNITATILE + ". Afiseaza toate comunitatile");
        System.out.println(AFISARE_NUMAR_COMUNITATI + ".Afiseaza numarul de comunitati");
        System.out.println(AFISARE_CEA_MAI_SOCIABILA_COMUNITATE + ".Afiseaza cea mai sociabila comunitate");
        System.out.println(AFISARE_PRIETENI_UTILIZATOR + ".Afiseaza toti prietenii unui anumit utilizator dintr-o anumita luna");
        System.out.println(IESIRE_APLICATIE + ". Iesire");
    }
}