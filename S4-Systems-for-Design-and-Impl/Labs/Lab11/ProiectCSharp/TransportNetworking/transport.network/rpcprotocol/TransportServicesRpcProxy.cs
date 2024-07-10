using System;
using System.Collections.Concurrent;
using System.Collections.Generic;
using System.IO;
using System.Net.Sockets;
using System.Runtime.Serialization.Formatters.Binary;
using System.Threading;
using Newtonsoft.Json;
using Transport.Model.transport.model;
using TransportModel.transport.model;
using TransportServices.transport.services;
using JsonSerializer = System.Text.Json.JsonSerializer;

namespace TransportNetworking.transport.network.rpcprotocol
{
    public class TransportServicesRpcProxy : ITransportServices
    {
        private string host;
        private int port;

        private ITransportObserver client;

        private StreamReader input;
        private StreamWriter output;
        private Socket connection;

        private BlockingCollection<Response> qresponses;
        private volatile bool finished;

        public TransportServicesRpcProxy(string host, int port)
        {
            this.host = host;
            this.port = port;
            qresponses = new BlockingCollection<Response>();
        }

        public ReservationManager Login(ReservationManager reservationManager, ITransportObserver client)
        {
            if (reservationManager == null)
            {
                Console.WriteLine("Null Reservation Manager in Proxy Login");
                return null;
            }
            InitializeConnection();
            Request req = new Request.Builder().Type(RequestType.LOGIN).Data(reservationManager).Build();
            SendRequest(req);
            Response response = ReadResponse();
            Console.WriteLine("Response Reservation Manager: " + response.Data);
            if (response.Type == ResponseType.OK)
            {
                this.client = client;
                return reservationManager;
            }

            if (response.Type == ResponseType.ERROR)
            {
                string err = response.Data.ToString();
                CloseConnection();
                throw new TransportException(err);
            }
            return null;
        }

        public ReservationManager SearchReservationManagerByName(string name)
        {
            Request req = new Request.Builder().Type(RequestType.SEARCH_RESERVATION_MANAGER_BY_NAME).Data(name).Build();
            SendRequest(req);
            Response response = ReadResponse();
            if (response.Type == ResponseType.ERROR)
            {
                string err = response.Data.ToString();
                throw new TransportException(err);
            }

            return (ReservationManager)response.Data;
        }

        public Trip[] GetAllTrips()
        {
            Request req = new Request.Builder().Type(RequestType.GET_ALL_TRIPS).Build();
            SendRequest(req);
            Response response = ReadResponse();
            if (response.Type == ResponseType.ERROR)
            {
                string err = response.Data.ToString();
                throw new TransportException(err);
            }

            Console.WriteLine("Response data: " + response.Data.GetType());
            Trip[] trips = (Trip[])response.Data;
            return trips;
        }

        public Trip SearchTripById(long id)
        {
            Request req = new Request.Builder().Type(RequestType.SEARCH_TRIP_BY_ID).Data(id).Build();
            SendRequest(req);
            Response response = ReadResponse();
            if (response.Type == ResponseType.ERROR)
            {
                string err = response.Data.ToString();
                throw new TransportException(err);
            }
            return (Trip)response.Data;
        }

        public Reservation AddReservation(Reservation reservation)
        {
            Request req = new Request.Builder().Type(RequestType.ADD_RESERVATION).Data(reservation).Build();
            SendRequest(req);
            Response response = ReadResponse();
            if (response.Type == ResponseType.ERROR)
            {
                string err = response.Data.ToString();
                throw new TransportException(err);
            }

            return reservation;
        }

        public List<Reservation> GetReservationsByTrip(Trip trip)
        {
            Request req = new Request.Builder().Type(RequestType.GET_RESERVATIONS_BY_TRIP).Data(trip).Build();
            SendRequest(req);
            Response response = ReadResponse();
            if (response.Type == ResponseType.ERROR)
            {
                string err = response.Data.ToString();
                throw new TransportException(err);
            }

            if (response.Data is List<Reservation> reservations)
            {
                return reservations;
            }
            else
            {
                throw new TransportException("Unexpected data type in GetReservationsByTrip() method: " +
                                             response.Data.GetType());
            }
        }

        public List<Seat> GetSeatsByReservation(long reservationId)
        {
            Request req = new Request.Builder().Type(RequestType.GET_SEATS_BY_RESERVATION).Data(reservationId).Build();
            SendRequest(req);
            Response response = ReadResponse();
            if (response.Type == ResponseType.ERROR)
            {
                string err = response.Data.ToString();
                throw new TransportException(err);
            }

            if (response.Data is List<Seat> seats)
            {
                return seats;
            }
            else
            {
                throw new TransportException("Unexpected data type in GetSeatsByReservation() method: " +
                                             response.Data.GetType());
            }
        }

        public List<Seat> GetSeatsByTripId(long tripId)
        {
            Request req = new Request.Builder().Type(RequestType.GET_SEATS_BY_TRIP).Data(tripId).Build();
            SendRequest(req);
            Response response = ReadResponse();
            if (response.Type == ResponseType.ERROR)
            {
                string err = response.Data.ToString();
                throw new TransportException(err);
            }

            if (response.Data is List<Seat> seats)
            {
                return seats;
            }
            else
            {
                throw new TransportException("Unexpected data type in GetSeatsByTripId() method: " +
                                             response.Data.GetType());
            }
        }

        public ReservationManager Logout(ReservationManager reservationManager, ITransportObserver client)
        {
            Request req = new Request.Builder().Type(RequestType.LOGOUT).Data(reservationManager).Build();
            SendRequest(req);
            Response response = ReadResponse();
            CloseConnection();
            if (response.Type == ResponseType.ERROR)
            {
                string err = response.Data.ToString();
                throw new TransportException(err);
            }

            return reservationManager;
        }

        private void CloseConnection()
        {
            finished = true;
            try
            {
                input.Close();
                output.Close();
                connection.Close();
                client = null;
            }
            catch (IOException e)
            {
                Console.WriteLine(e.StackTrace);
            }
        }

        private void SendRequest(Request request)
        {
            try
            {
                if (output == null)
                {
                    throw new InvalidOperationException("Output stream is not initialized.");
                }
                
                string json = JsonConvert.SerializeObject(request);
                output.WriteLine(json);
                output.Flush();
            }
            catch (IOException e)
            {
                throw new TransportException("Error sending object " + e);
            }
        }

        private Response ReadResponse()
        {
            try
            {
                //qresponses.TryDequeue(out response);
                return qresponses.Take();
            }
            catch (Exception e)
            {
                Console.WriteLine(e.StackTrace);
                throw new TransportException("Error reading response " + e);
            }
        }
        
        private void InitializeConnection()
        {
            try
            {
                Console.WriteLine("Connecting to server on port " + port + " and host " + host);
                connection = new Socket(AddressFamily.InterNetwork, SocketType.Stream, ProtocolType.Tcp);
                connection.Connect(host, port);
                output = new StreamWriter(new NetworkStream(connection));
                Console.WriteLine("Connected to server: " + connection + " output: " + output);
                input = new StreamReader(new NetworkStream(connection));
                finished = false;
                StartReader();
            }
            catch (SocketException e)
            {
                Console.WriteLine("Failed to connect to server: " + e.Message);
            }
            catch (IOException e)
            {
                Console.WriteLine(e.StackTrace);
            }
        }

        private void StartReader()
        {
            Thread tw = new Thread(new ReaderThread(this).Run);
            tw.Start();
        }

        private void HandleUpdate(Response response)
        {
            if (response.Type == ResponseType.NEW_RESERVATION)
            {
                Reservation message = (Reservation)response.Data;
                try
                {
                    client.ReservationAdded(message);
                }
                catch (TransportException e)
                {
                    Console.WriteLine(e.StackTrace);
                }
            }
        }

        private bool IsUpdate(Response response)
        {
            return response.Type == ResponseType.NEW_RESERVATION;
        }

        private class ReaderThread
        {
            private TransportServicesRpcProxy outerClass;

            public ReaderThread(TransportServicesRpcProxy outerClass)
            {
                this.outerClass = outerClass;
            }

            public void Run()
            {
                //BinaryFormatter formatter = new BinaryFormatter();
                
                while (!outerClass.finished)
                {
                    try
                    {
                        string line = outerClass.input.ReadLine();
                        Console.WriteLine("Line in Proxy: " + line);
                        if (line == null)
                            throw new IOException("Connection lost!");
                        Response response = JsonConvert.DeserializeObject<Response>(line!);
                        Console.WriteLine("Response Received in Proxy: " + response);
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
                            catch (Exception e)
                            {
                                Console.WriteLine(e.StackTrace);
                            }
                        }
                    }
                    catch (SocketException e)
                    {
                        Console.WriteLine("Connection error: " + e);
                        break;
                    }
                    catch (IOException e)
                    {
                        Console.WriteLine("Reading error " + e);
                        break;
                    }
                }
            }
        }
    }
}