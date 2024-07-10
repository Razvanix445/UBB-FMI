using System;
using System.Collections.Generic;
using System.IO;
using System.Net.Sockets;
using System.Text.Json;
using System.Text.Json.Serialization;
using System.Threading;
using Transport.Model.transport.model;
using TransportModel.transport.model;
using TransportServices.transport.services;

namespace TransportNetworking.transport.network.jsonprotocol
{
    public class TransportClientJsonWorker : ITransportObserver
    {
        private ITransportServices server;
        private Socket connection;

        private StreamReader input;
        private StreamWriter output;
        private JsonSerializerOptions options;
        private volatile bool connected;

        public TransportClientJsonWorker(ITransportServices server, Socket connection)
        {
            this.server = server;
            this.connection = connection;
            options = new JsonSerializerOptions
            {
                Converters = { new JsonStringEnumConverter() }
            };
            try
            {
                NetworkStream networkStream = new NetworkStream(this.connection);
                output = new StreamWriter(networkStream);
                input = new StreamReader(networkStream);
                connected = true;
            }
            catch (IOException e)
            {
                Console.WriteLine(e.StackTrace);
            }
        }

        public void Run()
        {
            while (connected)
            {
                try
                {
                    string requestLine = input.ReadLine();
                    Request request = JsonSerializer.Deserialize<Request>(requestLine, options);
                    Response response = HandleRequest(request);
                    if (response != null)
                    {
                        SendResponse(response);
                    }
                }
                catch (IOException e)
                {
                    Console.WriteLine(e.StackTrace);
                }
                Thread.Sleep(1000);
            }
            try
            {
                input.Close();
                output.Close();
                connection.Close();
            }
            catch (IOException e)
            {
                Console.WriteLine(e.StackTrace);
            }
        }

        public void ReservationAdded(Reservation reservation)
        {
            Response response = JsonProtocolUtils.CreateNewReservationResponse(reservation);
            Console.WriteLine("Reservation added " + reservation);
            try
            {
                SendResponse(response);
            }
            catch (IOException e)
            {
                throw new TransportException("Sending error: " + e);
            }
        }

        private static Response okResponse = JsonProtocolUtils.CreateOkResponse();

        private Response HandleRequest(Request request)
        {
            Response response = null;
            if (request.Type == RequestType.LOGIN)
            {
                Console.WriteLine("Login request..." + request.Type);
                ReservationManager reservationManager = request.ReservationManager;
                try
                {
                    server.Login(reservationManager, this);
                    return okResponse;
                }
                catch (TransportException e)
                {
                    return JsonProtocolUtils.CreateErrorResponse(e.Message);
                }
            }
            if (request.Type == RequestType.LOGOUT)
            {
                Console.WriteLine("Logout request..." + request.Type);
                ReservationManager reservationManager = request.ReservationManager;
                try
                {
                    server.Logout(reservationManager, this);
                    connected = false;
                    return okResponse;
                }
                catch (TransportException e)
                {
                    return JsonProtocolUtils.CreateErrorResponse(e.Message);
                }
            }
            if (request.Type == RequestType.GET_ALL_TRIPS)
            {
                Console.WriteLine("Get all trips request..." + request.Type);
                try
                {
                    Trip[] trips = server.GetAllTrips();
                    Console.WriteLine("Got all trips from Worker HandleRequest: ");
                    foreach (Trip trip in trips)
                        Console.WriteLine(trip);
                    return JsonProtocolUtils.CreateGetAllTripsResponse(trips);
                }
                catch (TransportException e)
                {
                    return JsonProtocolUtils.CreateErrorResponse(e.Message);
                }
            }
            if (request.Type == RequestType.GET_RESERVATIONS_BY_TRIP)
            {
                Console.WriteLine("Get reservations by trip request..." + request.Type);
                if (request.Trip == null)
                    return JsonProtocolUtils.CreateErrorResponse("Trip in request is null");
                try
                {
                    List<Reservation> reservations = server.GetReservationsByTrip(request.Trip);
                    return JsonProtocolUtils.CreateGetReservationsByTripResponse(reservations);
                }
                catch (TransportException e)
                {
                    return JsonProtocolUtils.CreateErrorResponse(e.Message);
                }
            }
            if (request.Type == RequestType.GET_SEATS_BY_TRIP)
            {
                Console.WriteLine("Get seats by trip request..." + request.Type);
                if (request.Trip == null)
                    return JsonProtocolUtils.CreateErrorResponse("Trip in request is null");
                try
                {
                    List<Seat> seats = server.GetSeatsByTrip(request.Trip);
                    foreach (Seat seat in seats)
                        Console.WriteLine(seat);
                    return JsonProtocolUtils.CreateGetSeatsByTripResponse(seats);
                }
                catch (TransportException e)
                {
                    return JsonProtocolUtils.CreateErrorResponse(e.Message);
                }
            }
            if (request.Type == RequestType.SEARCH_TRIP_BY_ID)
            {
                Console.WriteLine("Search trip by id request..." + request.Type);
                if (request.Long == null)
                    return JsonProtocolUtils.CreateErrorResponse("Trip id in request is null");
                try
                {
                    Trip trip = server.SearchTripById(request.Long.Value);
                    return JsonProtocolUtils.CreateSearchTripByIdResponse(trip);
                }
                catch (TransportException e)
                {
                    return JsonProtocolUtils.CreateErrorResponse(e.Message);
                }
            }
            if (request.Type == RequestType.NEW_RESERVATION)
            {
                Console.WriteLine("Add reservation request..." + request.Type);
                Reservation reservation = request.Reservation;
                try
                {
                    Reservation reservationAdded = server.AddReservation(reservation);
                    return okResponse;
                }
                catch (TransportException e)
                {
                    return JsonProtocolUtils.CreateErrorResponse(e.Message);
                }
            }
            return response;
        }

        private void SendResponse(Response response)
        {
            string responseLine = JsonSerializer.Serialize(response, options);
            Console.WriteLine("Sending response " + responseLine);
            lock (output)
            {
                output.WriteLine(responseLine);
                output.Flush();
            }
        }
    }
}