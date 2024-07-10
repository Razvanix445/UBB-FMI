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
                type = ResponseType.NEW_RESERVATION,
                reservation = reservation
            };
        }

        public static Response CreateOkResponse()
        {
            return new Response
            {
                type = ResponseType.OK
            };
        }

        public static Response CreateErrorResponse(string errorMessage)
        {
            return new Response
            {
                type = ResponseType.ERROR,
                errorMessage = errorMessage
            };
        }

        public static Response CreateGetAllTripsResponse(Trip[] trips)
        {
            return new Response
            {
                type = ResponseType.GET_ALL_TRIPS,
                trips = trips
            };
        }
        
        public static Response CreateGetSeatsByTripResponse(List<Seat> seats)
        {
            return new Response
            {
                type = ResponseType.GET_SEATS_BY_TRIP,
                seats = seats
            };
        }

        public static Response CreateSearchTripByIdResponse(Trip trip)
        {
            return new Response
            {
                type = ResponseType.SEARCH_TRIP_BY_ID,
                trip = trip
            };
        }
        
        public static Response CreateGetReservationsByTripResponse(List<Reservation> reservations)
        {
            return new Response
            {
                type = ResponseType.GET_RESERVATIONS_BY_TRIP,
                reservations = reservations
            };
        }
        
        public static Response CreateGetSeatsByReservationResponse(List<Seat> seats)
        {
            return new Response
            {
                type = ResponseType.GET_SEATS_BY_RESERVATION,
                seats = seats
            };
        }

        public static Request CreateLoginRequest(ReservationManager reservationManager)
        {
            return new Request
            {
                type = RequestType.LOGIN,
                reservationManager = reservationManager
            };
        }

        public static Request CreateSearchReservationManagerByNameRequest(string name)
        {
            return new Request
            {
                type = RequestType.SEARCH_RESERVATION_MANAGER_BY_NAME,
                stringR = name
            };
        }

        public static Request CreateGetAllTripsRequest()
        {
            return new Request
            {
                type = RequestType.GET_ALL_TRIPS
            };
        }

        public static Request CreateSearchTripByIdRequest(long id)
        {
            return new Request
            {
                type = RequestType.SEARCH_TRIP_BY_ID,
                aLong = id
            };
        }

        public static Request CreateAddReservationRequest(Reservation reservation)
        {
            return new Request
            {
                type = RequestType.NEW_RESERVATION,
                reservation = reservation
            };
        }

        public static Request CreateGetReservationsByTripRequest(Trip trip)
        {
            return new Request
            {
                type = RequestType.GET_RESERVATIONS_BY_TRIP,
                trip = trip
            };
        }

        public static Request CreateGetSeatsByReservationRequest(long reservationId)
        {
            return new Request
            {
                type = RequestType.GET_SEATS_BY_RESERVATION,
                aLong = reservationId
            };
        }

        public static Request CreateGetSeatsByTripRequest(Trip trip)
        {
            return new Request
            {
                type = RequestType.GET_SEATS_BY_TRIP,
                trip = trip
            };
        }

        public static Request CreateLogoutRequest(ReservationManager reservationManager)
        {
            return new Request
            {
                type = RequestType.LOGOUT,
                reservationManager = reservationManager
            };
        }
    }
}