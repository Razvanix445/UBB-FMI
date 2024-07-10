using System.Collections.Generic;
using Transport.Model.transport.model;
using TransportModel.transport.model;

namespace TransportNetworking.transport.network.jsonprotocol
{
    public static class JsonProtocolUtils
    {
        public static Response CreateNewReservationResponse(Reservation reservation)
        {
            return new Response
            {
                Type = ResponseType.NEW_RESERVATION,
                Reservation = reservation
            };
        }

        public static Response CreateOkResponse()
        {
            return new Response
            {
                Type = ResponseType.OK
            };
        }

        public static Response CreateErrorResponse(string errorMessage)
        {
            return new Response
            {
                Type = ResponseType.ERROR,
                ErrorMessage = errorMessage
            };
        }

        public static Response CreateGetAllTripsResponse(Trip[] trips)
        {
            return new Response
            {
                Type = ResponseType.GET_ALL_TRIPS,
                Trips = trips
            };
        }
        
        public static Response CreateGetSeatsByTripResponse(List<Seat> seats)
        {
            return new Response
            {
                Type = ResponseType.GET_SEATS_BY_TRIP,
                Seats = seats
            };
        }

        public static Response CreateSearchTripByIdResponse(Trip trip)
        {
            return new Response
            {
                Type = ResponseType.SEARCH_TRIP_BY_ID,
                Trip = trip
            };
        }
        
        public static Response CreateGetReservationsByTripResponse(List<Reservation> reservations)
        {
            return new Response
            {
                Type = ResponseType.GET_RESERVATIONS_BY_TRIP,
                Reservations = reservations
            };
        }
        
        public static Response CreateGetSeatsByReservationResponse(List<Seat> seats)
        {
            return new Response
            {
                Type = ResponseType.GET_SEATS_BY_RESERVATION,
                Seats = seats
            };
        }

        public static Request CreateLoginRequest(ReservationManager reservationManager)
        {
            return new Request
            {
                Type = RequestType.LOGIN,
                ReservationManager = reservationManager
            };
        }

        public static Request CreateSearchReservationManagerByNameRequest(string name)
        {
            return new Request
            {
                Type = RequestType.SEARCH_RESERVATION_MANAGER_BY_NAME,
                String = name
            };
        }

        public static Request CreateGetAllTripsRequest()
        {
            return new Request
            {
                Type = RequestType.GET_ALL_TRIPS
            };
        }

        public static Request CreateSearchTripByIdRequest(long id)
        {
            return new Request
            {
                Type = RequestType.SEARCH_TRIP_BY_ID,
                Long = id
            };
        }

        public static Request CreateAddReservationRequest(Reservation reservation)
        {
            return new Request
            {
                Type = RequestType.NEW_RESERVATION,
                Reservation = reservation
            };
        }

        public static Request CreateGetReservationsByTripRequest(Trip trip)
        {
            return new Request
            {
                Type = RequestType.GET_RESERVATIONS_BY_TRIP,
                Trip = trip
            };
        }

        public static Request CreateGetSeatsByReservationRequest(long reservationId)
        {
            return new Request
            {
                Type = RequestType.GET_SEATS_BY_RESERVATION,
                Long = reservationId
            };
        }

        public static Request CreateGetSeatsByTripRequest(Trip trip)
        {
            return new Request
            {
                Type = RequestType.GET_SEATS_BY_TRIP,
                Trip = trip
            };
        }

        public static Request CreateLogoutRequest(ReservationManager reservationManager)
        {
            return new Request
            {
                Type = RequestType.LOGOUT,
                ReservationManager = reservationManager
            };
        }
    }
}