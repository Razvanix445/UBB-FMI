package transport.client.gui;

import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import transport.model.*;
import transport.services.ITransportObserver;
import transport.services.ITransportServices;
import transport.services.TransportException;
import java.net.URL;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.ResourceBundle;

public class MainController implements Initializable, ITransportObserver {

    private ObservableList<SeatReservation> seats = FXCollections.observableArrayList();
    @FXML
    private TableView<SeatReservation> seatsTableView;
    @FXML
    private TableColumn<Reservation, String> clientNameColumn;
    @FXML
    private TableColumn<Seat, Long> seatNumberColumn;

    @FXML
    private Text managerNameText;
    @FXML
    private TextField destinationField;
    @FXML
    private DatePicker datePicker;
    @FXML
    private TextField hourField;
    @FXML
    private Button searchButton;
    @FXML
    private TextField clientNameField;

    @FXML
    private VBox firstVBox;
    @FXML
    private VBox secondVBox;
    @FXML
    private VBox thirdVBox;
    @FXML
    private VBox fourthVBox;

    private ObservableList<Trip> trips = FXCollections.observableArrayList();
    @FXML
    private TableView<Trip> tableView;
    @FXML
    private TableColumn<Trip, String> destinationColumn;
    @FXML
    private TableColumn<Trip, LocalDateTime> departureTimeColumn;
    @FXML
    private TableColumn<Trip, Long> noOfAvailableSeatsColumn;

    private ITransportServices server;
    private ReservationManager loggedUser;
    private Trip tripForReservation;
    //private Service service;
    private Stage stage;

    public MainController() {
        System.out.println("MainController constructor");
    }

    public MainController(ITransportServices server) {
        this.server = server;
        System.out.println("MainController constructor with server param");
    }

    public void setServer(ITransportServices server) {
        this.server = server;
        //initModel();
    }

    public void setStage(Stage stage) {
        this.stage = stage;
    }

    public void setLoggedUser(ReservationManager loggedUser) {
        this.loggedUser = loggedUser;
        initModel();
    }

    @FXML
    public void initialize(URL url, ResourceBundle rb) {
        destinationColumn.setCellValueFactory(new PropertyValueFactory<Trip, String>("destination"));
        departureTimeColumn.setCellValueFactory(new PropertyValueFactory<Trip, LocalDateTime>("dateDeparture"));
        noOfAvailableSeatsColumn.setCellValueFactory(new PropertyValueFactory<Trip, Long>("noOfAvailableSeats"));

        tableView.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
        tableView.setItems(trips);
        tableView.getSelectionModel().selectedItemProperty().addListener((observable, oldSelection, newSelection) -> {
            if (newSelection != null) {
                Trip selectedTrip = tableView.getSelectionModel().getSelectedItem();
                destinationField.setText(selectedTrip.getDestination());
                datePicker.setValue(selectedTrip.getDateDeparture().toLocalDate());
                hourField.setText(selectedTrip.getDateDeparture().toLocalTime().toString());
            }
        });

        seatNumberColumn.setCellValueFactory(new PropertyValueFactory<Seat, Long>("seatNumber"));
        clientNameColumn.setCellValueFactory(new PropertyValueFactory<Reservation, String>("clientName"));

        seatsTableView.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
        seatsTableView.setItems(seats);
    }

    public void login(String username, String password) throws TransportException {
        ReservationManager reservationManager = new ReservationManager(username, password);
        server.login(reservationManager, this);
        loggedUser = reservationManager;
        System.out.println("Authentication successful (MainController).");
    }

    public void handleLogout(ActionEvent actionEvent) {
        logout();
        ((Node)(actionEvent.getSource())).getScene().getWindow().hide();
    }

    void logout() {
        try {
            server.logout(loggedUser, this);
            System.out.println("Logout successful (MainController).");
        } catch (TransportException e) {
            System.out.println("Logout failed (MainController). " + e);
        }
    }

    private void initModel() {
        Trip[] tripsIterable = null;
        try {
            tripsIterable = server.getAllTrips();
            trips.setAll(tripsIterable);
        } catch (TransportException e) {
            throw new RuntimeException(e);
        }
        managerNameText.setText(loggedUser.getName());
    }

    public void reservationAdded(Reservation reservation) throws TransportException {
        Platform.runLater(() -> {
            System.out.println("Reservation added: " + reservation);
            initModel();
        });
    }

    @FXML
    private void handleReserve() {
        Trip selectedTrip = tripForReservation;
        if (selectedTrip != null) {
            List<Long> selectedSeats = new ArrayList<>();
            List<VBox> vBoxList = List.of(firstVBox, secondVBox, thirdVBox, fourthVBox);
            for (VBox vBox: vBoxList) {
                for (Node node: vBox.getChildren()) {
                    if (node instanceof ImageView) {
                        ImageView imageView = (ImageView) node;
                        int seatNumber = (int) imageView.getUserData();
                        if (isSeatSelected(imageView)) {
                            selectedSeats.add((long) seatNumber);
                        }
                    }
                }
            }
            if (!selectedSeats.isEmpty()) {
                Trip trip = null;
                try {
                    trip = server.searchTripById(selectedTrip.getId());
                } catch (TransportException e) {
                    System.out.println("Error getting trip (MainController)." + e);
                    throw new RuntimeException(e);
                }
                Reservation newReservation = new Reservation(trip, selectedSeats, clientNameField.getText());
                try {
                    server.addReservation(newReservation);
                } catch (TransportException e) {
                    System.out.println("Error adding reservation (MainController).");
                    throw new RuntimeException(e);
                }
                handleSearch();
            } else {
                System.out.println("No seats selected.");
            }
        } else {
            System.out.println("No trip selected.");
        }
        initModel();
    }

    @FXML
    private void handleSearch() {
        firstVBox.getChildren().clear();
        secondVBox.getChildren().clear();
        thirdVBox.getChildren().clear();
        fourthVBox.getChildren().clear();

        Image steeringWheelImage = new Image(getClass().getResource("/icons/SteeringWheel.png").toExternalForm());
        ImageView imageView = new ImageView(steeringWheelImage);
        imageView.setFitWidth(50);
        imageView.setFitHeight(50);
        imageView.setUserData(0);
        firstVBox.getChildren().add(imageView);

        Image emptyImage = new Image(getClass().getResource("/icons/SteeringWheel.png").toExternalForm());
        ImageView emptyImageView = new ImageView(emptyImage);
        emptyImageView.setFitWidth(50);
        emptyImageView.setFitHeight(50);
        emptyImageView.setVisible(false);
        emptyImageView.setUserData(0);
        secondVBox.getChildren().add(emptyImageView);

        // Take the data from the fields and search for the trip
        String destination = destinationField.getText();
        LocalDate date = datePicker.getValue();
        LocalTime time = LocalTime.parse(hourField.getText());
        Trip[] tripsIterable = null;
        try {
            tripsIterable = server.getAllTrips();
        } catch (TransportException e) {
            System.out.println("Error getting trips (MainController)." + e);
            throw new RuntimeException(e);
        }
        Trip selectedTrip = Arrays.stream(tripsIterable)
                .filter(trip -> trip.getDestination().equals(destination) && trip.getDateDeparture().toLocalDate().equals(date) && trip.getDateDeparture().toLocalTime().equals(time))
                .findFirst()
                .orElse(null);
        tripForReservation = selectedTrip;

        // If the trip was found, we loop through the reservations and create the seat image views
        if (selectedTrip != null) {
            List<Seat> seats = null;
            try {
                seats = server.getSeatsByTrip(selectedTrip);
            } catch (TransportException e) {
                System.out.println("Error getting seats (MainController)." + e);
                throw new RuntimeException(e);
            }
            List<SeatReservation> seatsList = new ArrayList<>();

            // Loop through the 18 seats and check if they are reserved
            for (int seatNumber = 1; seatNumber <= 18; seatNumber++) {
                boolean isReserved = false;
                String clientName = "-";
                for (Seat seat: seats) {
                    if (seat.getSeatNumber() == seatNumber) {
                        isReserved = true;
                        clientName = seat.getReservation().getClientName();
                        break;
                    }
                }
                seatsList.add(new SeatReservation((long) seatNumber, clientName));
                createSeatImageView(seatNumber, isReserved);
            }
            seatsTableView.getItems().setAll(seatsList);
        }
    }

    private void createSeatImageView(int seatNumber, boolean isReserved) {
        Image seatImage = isReserved ? new Image(getClass().getResource("/icons/ReservedSeat.png").toExternalForm()) :
                new Image(getClass().getResource("/icons/AvailableSeat.png").toExternalForm());
        ImageView imageView = new ImageView(seatImage);
        imageView.setFitWidth(50);
        imageView.setFitHeight(50);
        imageView.setUserData(seatNumber);
        imageView.setOnMouseClicked(event -> handleImageClick(imageView, seatNumber, isReserved));
        imageView.setPickOnBounds(true);

        if (seatNumber >= 1 && seatNumber <= 4) {
            firstVBox.getChildren().add(imageView);
        } else if (seatNumber >= 5 && seatNumber <= 8) {
            secondVBox.getChildren().add(imageView);
        } else if (seatNumber >= 9 && seatNumber <= 13) {
            thirdVBox.getChildren().add(imageView);
        } else if (seatNumber >= 14 && seatNumber <= 18) {
            fourthVBox.getChildren().add(imageView);
        }
    }

    private void handleImageClick(ImageView imageView, int seatNumber, boolean isReserved) {
        if (!isReserved) {
            if (isSeatSelected(imageView)) {
                Image availableImage = new Image(getClass().getResource("/icons/AvailableSeat.png").toExternalForm());
                imageView.setImage(availableImage);
                System.out.println("Seat " + seatNumber + " was deselected.");
            } else {
                Image availableImage = new Image(getClass().getResource("/icons/SelectedSeat.png").toExternalForm());
                imageView.setImage(availableImage);
                System.out.println("Seat " + seatNumber + " was selected.");
            }
        } else {
            System.out.println("Seat " + seatNumber + " is already reserved.");
        }
    }

    private boolean isSeatSelected(ImageView imageView) {
        Image selectedImage = new Image(getClass().getResource("/icons/SelectedSeat.png").toExternalForm());
        return imageView.getImage().getUrl().equals(selectedImage.getUrl());
    }

    @FXML
    private void handleExit() {
        Stage stage = (Stage) searchButton.getScene().getWindow();
        stage.close();
    }
}