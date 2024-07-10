using System;
using System.Collections.Concurrent;
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
    public class TransportServicesJsonProxy : ITransportServices
    {
        private string host;
        private int port;

        private ITransportObserver client;

        private StreamReader input;
        private StreamWriter output;
        private Socket connection;

        private BlockingCollection<Response> qresponses;
        private volatile bool finished;
        public TransportServicesJsonProxy(string host, int port)
        {
            this.host = host;
            this.port = port;
            qresponses = new BlockingCollection<Response>();
        }

        public ReservationManager Login(ReservationManager reservationManager, ITransportObserver client)
        {
            InitializeConnection();

            Request req = JsonProtocolUtils.CreateLoginRequest(reservationManager);
            SendRequest(req);
            Response response = ReadResponse();
            if (response.type == ResponseType.OK)
            {
                this.client = client;
                return reservationManager;
            }
            if (response.type == ResponseType.ERROR)
            {
                string err = response.errorMessage;
                CloseConnection();
                throw new TransportException(err);
            }
            return response.ReservationManager;
        }

        public Reservation AddReservation(Reservation reservation)
        {
            Request req = JsonProtocolUtils.CreateAddReservationRequest(reservation);
            SendRequest(req);
            Response response = ReadResponse();
            if (response.type == ResponseType.ERROR)
            {
                string err = response.errorMessage;
                throw new TransportException(err);
            }
            return reservation;
        }

        public ReservationManager Logout(ReservationManager reservationManager, ITransportObserver client)
        {
            Request req = JsonProtocolUtils.CreateLogoutRequest(reservationManager);
            SendRequest(req);
            Response response = ReadResponse();
            if (response.type == ResponseType.ERROR)
            {
                string err = response.errorMessage;
                throw new TransportException(err);
            }
            return reservationManager;
        }

        public Trip[] GetAllTrips()
        {
            Request req = JsonProtocolUtils.CreateGetAllTripsRequest();
            SendRequest(req);
            Response response = ReadResponse();
            if (response.type == ResponseType.ERROR)
            {
                string err = response.errorMessage;
                throw new TransportException(err);
            }
            return response.trips;
        }

        public Trip SearchTripById(long id)
        {
            Request req = JsonProtocolUtils.CreateSearchTripByIdRequest(id);
            SendRequest(req);
            Response response = ReadResponse();
            if (response.type == ResponseType.ERROR)
            {
                string err = response.errorMessage;
                throw new TransportException(err);
            }
            return response.trip;
        }

        public ReservationManager SearchReservationManagerByName(string name)
        {
            Request req = JsonProtocolUtils.CreateSearchReservationManagerByNameRequest(name);
            SendRequest(req);
            Response response = ReadResponse();
            if (response.type == ResponseType.ERROR)
            {
                string err = response.errorMessage;
                throw new TransportException(err);
            }
            return response.ReservationManager;
        }

        public List<Reservation> GetReservationsByTrip(Trip trip)
        {
            Request req = JsonProtocolUtils.CreateGetReservationsByTripRequest(trip);
            SendRequest(req);
            Response response = ReadResponse();
            if (response.type == ResponseType.ERROR)
            {
                string err = response.errorMessage;
                throw new TransportException(err);
            }
            return response.reservations;
        }

        public List<Seat> GetSeatsByReservation(long reservationId)
        {
            Request req = JsonProtocolUtils.CreateGetSeatsByReservationRequest(reservationId);
            SendRequest(req);
            Response response = ReadResponse();
            if (response.type == ResponseType.ERROR)
            {
                string err = response.errorMessage;
                throw new TransportException(err);
            }
            return response.seats;
        }

        public List<Seat> GetSeatsByTrip(Trip trip)
        {
            Request req = JsonProtocolUtils.CreateGetSeatsByTripRequest(trip);
            SendRequest(req);
            Response response = ReadResponse();
            if (response.type == ResponseType.ERROR)
            {
                string err = response.errorMessage;
                throw new TransportException(err);
            }
            return response.seats;
        }

        public void CloseConnection()
        {
            finished = true;
            try
            {
                input.Close();
                output.Close();
                connection.Close();
                client = null;
            }
            catch (Exception e)
            {
                Console.WriteLine("Error " + e);
            }
        }

        private void SendRequest(Request request)
        {
            var options = new JsonSerializerOptions
            {
                Converters = { new JsonStringEnumConverter() }
            };
            string requestJson = JsonSerializer.Serialize(request, options);
            Console.WriteLine("Sending request " + requestJson);
            try
            {
                output.WriteLine(requestJson);
                output.Flush();
            }
            catch (Exception e)
            {
                throw new TransportException("Error sending object " + e);
            }
        }

        private Response ReadResponse()
        {
            Response response = null;
            try
            {
                response = qresponses.Take();
            }
            catch (InvalidOperationException e)
            {
                throw new TransportException("Error reading response " + e);
            }
            if (response == null)
            {
                throw new TransportException("Received null response from server.");
            }
            return response;
        }

        private void InitializeConnection()
        {
            try
            {
                connection = new Socket(AddressFamily.InterNetwork, SocketType.Stream, ProtocolType.Tcp);
                connection.Connect(host, port);
                NetworkStream networkStream = new NetworkStream(connection);
                output = new StreamWriter(networkStream);
                input = new StreamReader(networkStream);
                finished = false;
                StartReader();
            }
            catch (IOException e)
            {
                throw new TransportException("Error initializing connection " + e);
            }
        }

        private void StartReader()
        {
            Thread tw = new Thread(new ReaderThread(this).Run);
            tw.Start();
        }

        private void HandleUpdate(Response response)
        {
            if (response.type == ResponseType.NEW_RESERVATION)
            {
                Reservation reservation = response.reservation;
                try
                {
                    client.ReservationAdded(reservation);
                }
                catch (TransportException e)
                {
                    Console.WriteLine(e.StackTrace);
                }
            }
        }

        private bool IsUpdate(Response response)
        {
            return response.type == ResponseType.NEW_RESERVATION;
        }

        private class ReaderThread
        {
            private TransportServicesJsonProxy outerClass;
            public ReaderThread(TransportServicesJsonProxy outerClass)
            {
                this.outerClass = outerClass;
            }
            public void Run()
            {
                while (!outerClass.finished)
                {
                    try
                    {
                        string responseLine = outerClass.input.ReadLine();
                        if (responseLine == null)
                            break;
                        Console.WriteLine("Received response " + responseLine);
                        Response response = JsonSerializer.Deserialize<Response>(responseLine);
                        if (response == null)
                        {
                            Console.WriteLine("Invalid response from the server (Proxy)!");
                            continue;
                        }
                        if (outerClass.IsUpdate(response))
                        {
                            outerClass.HandleUpdate(response);
                        }
                        else
                        {
                            try
                            {
                                outerClass.qresponses.Add(response);
                            }
                            catch (InvalidOperationException e)
                            {
                                Console.WriteLine(e.StackTrace);
                            }
                        }
                    }
                    catch (IOException e)
                    {
                        Console.WriteLine("Reading error " + e);
                    }
                }
            }
        }
    }
}