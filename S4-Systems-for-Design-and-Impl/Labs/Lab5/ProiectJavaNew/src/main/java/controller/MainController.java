package controller;

import domain.Reservation;
import domain.ReservationManager;
import domain.Seat;
import domain.Trip;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import service.Service;
import utils.SeatReservation;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.StreamSupport;

public class MainController {

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

    private Trip tripForReservation;
    private ReservationManager loggedUser;
    private Service service;
    private Stage stage;

    public void setService(Service service) {
        this.service = service;
        initModel();
    }

    public void setStage(Stage stage) {
        this.stage = stage;
    }

    public void setLoggedUser(ReservationManager loggedUser) {
        this.loggedUser = loggedUser;
    }

    @FXML
    public void initialize() {
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

    private void initModel() {
        Iterable<Trip> tripsIterable = service.getAllTrips();
        List<Trip> tripsList = new ArrayList<>();
        tripsIterable.forEach(tripsList::add);
        trips.setAll(tripsList);
        managerNameText.setText(loggedUser.getName());
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
                service.addReservation(selectedTrip.getId(), selectedSeats, clientNameField.getText());
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
        Iterable<Trip> tripsIterable = service.getAllTrips();
        Trip selectedTrip = StreamSupport.stream(tripsIterable.spliterator(), false)
                .filter(trip -> trip.getDestination().equals(destination) && trip.getDateDeparture().toLocalDate().equals(date) && trip.getDateDeparture().toLocalTime().equals(time))
                .findFirst()
                .orElse(null);
        tripForReservation = selectedTrip;

        // If the trip was found, we loop through the reservations and create the seat image views
        if (selectedTrip != null) {
            List<Reservation> reservations = service.getReservationsByTrip(selectedTrip.getId());
            List<SeatReservation> seatsList = new ArrayList<>();

            // Loop through the 18 seats and check if they are reserved
            for (int seatNumber = 1; seatNumber <= 18; seatNumber++) {
                boolean isReserved = false;
                String clientName = "-";
                for (Reservation reservation: reservations) {
                    List<Seat> seats = service.getSeatsByReservation(reservation.getId());
                    for (Seat seat: seats) {
                        if (seat.getSeatNumber() == seatNumber) {
                            isReserved = true;
                            clientName = reservation.getClientName();
                            break;
                        }
                    }
                    if (isReserved)
                        break;
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