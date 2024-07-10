package transport.network.jsonprotocol;

import transport.model.Reservation;
import transport.model.ReservationManager;
import transport.model.Seat;
import transport.model.Trip;

import java.util.List;

public class JsonProtocolUtils {
    public static Response createNewReservationResponse(Reservation reservation){
        Response resp=new Response();
        resp.setType(ResponseType.NEW_RESERVATION);
        resp.setReservation(reservation);
        return resp;
    }

    public static Response createOkResponse(){
        Response resp=new Response();
        resp.setType(ResponseType.OK);
        return resp;
    }

    public static Response createErrorResponse(String errorMessage){
        Response resp=new Response();
        resp.setType(ResponseType.ERROR);
        resp.setErrorMessage(errorMessage);
        return resp;
    }

    public static Response createGetAllTripsResponse(Trip[] trips){
        Response resp=new Response();
        resp.setType(ResponseType.GET_ALL_TRIPS);
        resp.setTrips(trips);
        return resp;
    }

    public static Response createGetSeatsByTripResponse(List<Seat> seats){
        Response resp=new Response();
        resp.setType(ResponseType.GET_SEATS_BY_TRIP);
        resp.setSeats(seats);
        return resp;
    }

    public static Response createSearchTripByIdResponse(Trip trip){
        Response resp=new Response();
        resp.setType(ResponseType.SEARCH_TRIP_BY_ID);
        resp.setTrip(trip);
        return resp;
    }

    public static Response createGetReservationsByTripResponse(List<Reservation> reservations){
        Response resp=new Response();
        resp.setType(ResponseType.GET_RESERVATIONS_BY_TRIP);
        resp.setReservations(reservations);
        return resp;
    }

    public static Response createGetSeatsByReservationResponse(List<Seat> seats){
        Response resp=new Response();
        resp.setType(ResponseType.GET_SEATS_BY_RESERVATION);
        resp.setSeats(seats);
        return resp;
    }

    public static Request createLoginRequest(ReservationManager reservationManager){
        Request req=new Request();
        req.setType(RequestType.LOGIN);
        req.setReservationManager(reservationManager);
        return req;
    }

    public static Request createSearchReservationManagerByNameRequest(String name){
        Request req=new Request();
        req.setType(RequestType.SEARCH_RESERVATION_MANAGER_BY_NAME);
        req.setStringR(name);
        return req;
    }

    public static Request createGetAllTripsRequest(){
        Request req=new Request();
        req.setType(RequestType.GET_ALL_TRIPS);
        return req;
    }

    public static Request createSearchTripByIdRequest(Long id){
        Request req=new Request();
        req.setType(RequestType.SEARCH_TRIP_BY_ID);
        req.setLong(id);
        return req;
    }

    public static Request createAddReservationRequest(Reservation reservation){
        Request req=new Request();
        req.setType(RequestType.NEW_RESERVATION);
        req.setReservation(reservation);
        return req;
    }

    public static Request createGetReservationsByTripRequest(Trip trip){
        Request req=new Request();
        req.setType(RequestType.GET_RESERVATIONS_BY_TRIP);
        req.setTrip(trip);
        return req;
    }

    public static Request createGetSeatsByReservationRequest(Long reservationId){
        Request req=new Request();
        req.setType(RequestType.GET_SEATS_BY_RESERVATION);
        req.setLong(reservationId);
        return req;
    }

    public static Request createGetSeatsByTripRequest(Trip trip){
        Request req=new Request();
        req.setType(RequestType.GET_SEATS_BY_TRIP);
        req.setTrip(trip);
        return req;
    }

    public static Request createLogoutRequest(ReservationManager reservationManager){
        Request req=new Request();
        req.setType(RequestType.LOGOUT);
        req.setReservationManager(reservationManager);
        return req;
    }
}
