import controller.LoginController;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import repository.database.ReservationDBRepository;
import repository.database.ReservationManagerDBRepository;
import repository.database.SeatDBRepository;
import repository.database.TripDBRepository;
import repository.interfaces.IReservationManagerRepository;
import repository.interfaces.IReservationRepository;
import repository.interfaces.ISeatRepository;
import repository.interfaces.ITripRepository;
import service.Service;

import java.io.FileReader;
import java.io.IOException;
import java.util.Properties;

/*
Problema 4.
O firma de transport are mai multe oficii prin tara unde clientii pot suna si pot rezerva locuri pentru diferite
destinatii. Firma foloseste un sistem soft pentru gestiunea rezervarilor. Persoana de la oficiu foloseste o aplicatie
desktop cu urmatoarele functionalitati:
1. Login. Dupa autentificarea cu succes, o noua fereastra se deschide in care sunt afisate toate cursele
(destinatia, data si ora plecarii si numarul de locuri disponibile). Pentru o anumita destinatie pot exista
mai multe curse.
2. Cautare. Dupa autentificarea cu succes, persoana de la oficiu poate cauta o anumita cursa introducand
destinatia, data si ora plecarii. Aplicatia va afisa in alta lista/alt tabel toate locurile pentru cursa
respectiva: numarul locului si numele clientului care a rezervat locul respectiv (daca locul este deja
rezervat). Daca un loc nu este rezervat se va afisa '-'. Fiecare autocar are 18 locuri pentru clienti.
3. Rezervare. Persoana de la oficiu poate rezerva locuri pentru clienti la o anumita cursa. Cand se face o
rezervare se introduce numele clientului si numarul de locuri pe care doreste sa le rezerve. Dupa ce s-a
facut o rezervare, toate persoanele de la celelalte oficii vad lista actualizata a curselor si a numarului de
locuri disponibile. De asemenea numele clientului apare la rezultatul afisat la cerinta 2.
4. Logout
* */
public class Main extends Application {

    private Service service;

    @Override
    public void start(Stage primaryStage) {

        Properties props = new Properties();
        try {
            props.load(new FileReader("bd.config"));

            IReservationManagerRepository reservationManagerRepository = new ReservationManagerDBRepository(props);
            IReservationRepository reservationRepository = new ReservationDBRepository(props);
            ITripRepository tripRepository = new TripDBRepository(props);
            ISeatRepository seatRepository = new SeatDBRepository(props);
            service = new Service(reservationManagerRepository, reservationRepository, tripRepository, seatRepository);

            initView(primaryStage);
            primaryStage.setTitle("Hello!");
            primaryStage.setWidth(800);
            primaryStage.show();

        } catch (IOException e) {
            System.out.println("Cannot find bd.config or open the window " + e);
        }
    }

    public static void main(String[] args) {
        launch(args);
    }

    private void initView(Stage primaryStage) throws IOException {
        FXMLLoader loginLoader = new FXMLLoader();
        loginLoader.setLocation(getClass().getResource("/views/login-view.fxml"));
        AnchorPane loginLayout = loginLoader.load();
        primaryStage.setScene(new Scene(loginLayout));

        LoginController loginController = loginLoader.getController();
        loginController.setService(service);
        loginController.setStage(primaryStage);
    }
}