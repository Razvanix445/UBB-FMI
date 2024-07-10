package ir.map.g222.console;

import ir.map.g222.domain.Utilizator;
import ir.map.g222.exceptions.*;
import ir.map.g222.service.Service;
import ir.map.g222.validators.ValidationException;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

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
    static final int IESIRE_APLICATIE = 0;

    private static Scanner scanner = new Scanner(System.in);

    public static void ui() {
        Service service = new Service();

        Utilizator u1 = new Utilizator("Vasile", "Bogdan"); service.adaugaUtilizator(u1.getPrenume(), u1.getNume());
        Utilizator u2 = new Utilizator("Andrei", "Ioan"); service.adaugaUtilizator(u2.getPrenume(), u2.getNume());
        Utilizator u3 = new Utilizator("Ion", "Petre"); service.adaugaUtilizator(u3.getPrenume(), u3.getNume());
        Utilizator u4 = new Utilizator("George", "Pop"); service.adaugaUtilizator(u4.getPrenume(), u4.getNume());
        Utilizator u5 = new Utilizator("Cristian", "Muresan"); service.adaugaUtilizator(u5.getPrenume(), u5.getNume());
        Utilizator u6 = new Utilizator("Ionel", "Pop"); service.adaugaUtilizator(u6.getPrenume(), u6.getNume());
        Utilizator u7 = new Utilizator("Violeta", "Ciorpan"); service.adaugaUtilizator(u7.getPrenume(), u7.getNume());
        Utilizator u8 = new Utilizator("Victor", "Bene"); service.adaugaUtilizator(u8.getPrenume(), u8.getNume());
        Utilizator u9 = new Utilizator("Cosmin", "Muresan"); service.adaugaUtilizator(u9.getPrenume(), u9.getNume());
        Utilizator u10 = new Utilizator("Zoltan", "Nagy"); service.adaugaUtilizator(u10.getPrenume(), u10.getNume());
        Utilizator u11 = new Utilizator("Maria", "Vasilescu"); service.adaugaUtilizator(u11.getPrenume(), u11.getNume());
        service.adaugaPrietenie(8L, 4L);
        service.adaugaPrietenie(5L, 1L);
        service.adaugaPrietenie(5L, 2L);
        service.adaugaPrietenie(2L, 6L);
        service.adaugaPrietenie(3L, 5L);
        service.adaugaPrietenie(4L, 7L);
        service.adaugaPrietenie(10L, 11L);

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

                    case IESIRE_APLICATIE:
                        iesire_aplicatie();
                        break;
                    default:
                        System.out.println("Optiune invalida! Alege o optiune valida din meniu!");
                }
            } catch (ValidationException e) {
                System.out.println(e.getMessage());
            } catch (UtilizatorInvalidException e) {
                System.out.println(e.getMessage());
            } catch (UtilizatorInexistentException e) {
                System.out.println(e.getMessage());
            } catch (PrietenieInexistentaException e) {
                System.out.println(e.getMessage());
            } catch (PrietenieExistentaException e) {
                System.out.println(e.getMessage());
            } catch (IDuriIdenticeException e) {
                System.out.println(e.getMessage());
            } catch (EntitateNull e) {
                System.out.println(e.getMessage());
            }
        }
    }

    private static void iesire_aplicatie() {
        System.out.println("^_^");
        System.exit(0);
    }

    private static void afisare_numar_comunitati(Service service) {
        System.out.println("Numar comunitati: " + service.getNumarComunitati());
    }

    private static void afisare_cea_mai_sociabila_comunitate(Service service) {
        List<Utilizator> ceaMaiSociabilaComunitate = service.ceaMaiSociabilaComunitate();
        System.out.println("Cea mai sociabila comunitate este alcatuita din: ");
        for (Utilizator utilizatorSociabil : ceaMaiSociabilaComunitate)
            System.out.println(utilizatorSociabil.getNume() + " " + utilizatorSociabil.getPrenume() + ", ID: " +
                    utilizatorSociabil.getId());
    }

    private static void afisare_toate_comunitatile(Service service) {
        List<List<Utilizator>> comunitati = service.getAllComunitati();
        int contor = 0;

        for (List<Utilizator> comunitateCurenta : comunitati) {
            System.out.println("Comunitatea " + ++contor + " este formata din:");
            for (Utilizator utilizatorDinComunitate : comunitateCurenta) {
                System.out.println(utilizatorDinComunitate.getNume() + " " +
                        utilizatorDinComunitate.getPrenume() + " ID: " + utilizatorDinComunitate.getId());
            }
            System.out.println();
        }
    }

    private static void afisare_prietenii(Service service) {
        System.out.println("Prieteniile:");
        for (Utilizator utilizator1 : service.getAllUtilizatori()) {
            System.out.println("Utilizatorul " + utilizator1.getNume() + " " + utilizator1.getPrenume() +
                    " cu ID-ul " + utilizator1.getId() + " are " + utilizator1.getPrieteni().size() + " prieteni:");
            if (utilizator1.getPrieteni() != null) {
                for (Utilizator prieten : utilizator1.getPrieteni()) {
                    System.out.println(prieten.getNume() + " " + prieten.getPrenume() + ", ID: " + prieten.getId());
                }
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

        for (Utilizator utilizatorDeAfisat : utilizatori)
            System.out.println(utilizatorDeAfisat.getNume() + " " + utilizatorDeAfisat.getPrenume() + " ID: " + utilizatorDeAfisat.getId());
    }

    private static void cautare_utilizator(Service service) {
        System.out.println("Introduceti ID-ul utilizatorului cautat: ");
        Long idUtilizatorDeCautat = scanner.nextLong();
        Utilizator utilizator = service.cautaUtilizator(idUtilizatorDeCautat);

        System.out.println("Utilizatorul cautat este: ");
        System.out.println(utilizator.getNume() + " " + utilizator.getPrenume() + "\nPrieteni:");
        for (Utilizator prieten : utilizator.getPrieteni()) {
            System.out.println(prieten.getNume() + " " + prieten.getPrenume() + ", ID: " + prieten.getId());
        }
    }

    private static void modificare_utilizator(Service service) {
        System.out.println("Introduceti noul prenume al utilizatorului: ");
        String prenumeNou = scanner.nextLine();
        System.out.println("Introduceti noul nume al utilizatorului: ");
        String numeNou = scanner.nextLine();
        System.out.println("Introduceti ID-ul utilizatorului care se doreste modificat: ");
        Long id = scanner.nextLong();

        service.modificaUtilizator(prenumeNou, numeNou, id);
        System.out.println("Utilizator modificat cu succes: " + numeNou + " " + prenumeNou + " ID: " + id);
    }

    private static void stergere_utilizator(Service service) {
        System.out.println("Introduceti ID-ul utilizatorului pentru stergere: ");
        Long idUtilizatorDeSters = scanner.nextLong();
        scanner.nextLine();
        service.stergeUtilizator(idUtilizatorDeSters);

        System.out.println("Utilizator sters cu succes!");
    }

    private static void adaugare_utilizator(Service service) {
        System.out.println("Introduceti prenumele utilizatorului: ");
        String prenume = scanner.nextLine();
        System.out.println("Introduceti numele utilizatorului: ");
        String nume = scanner.nextLine();

        service.adaugaUtilizator(prenume, nume);
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
        System.out.println(IESIRE_APLICATIE + ". Iesire");
    }
}
