﻿namespace TransportNetworking.transport.network.jsonprotocol
{
    public enum RequestType
    {
        LOGIN, 
        SEARCH_RESERVATION_MANAGER_BY_NAME, 
        GET_ALL_TRIPS, 
        SEARCH_TRIP_BY_ID, 
        NEW_RESERVATION, 
        GET_RESERVATIONS_BY_TRIP, 
        GET_SEATS_BY_RESERVATION, 
        GET_SEATS_BY_TRIP, 
        LOGOUT
    }
}