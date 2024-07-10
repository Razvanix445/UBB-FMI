using System;
using System.IO;
using System.Net.Sockets;
using System.Reflection;
using System.Runtime.Serialization.Formatters.Binary;
using System.Threading;
using Newtonsoft.Json;
using TransportModel.transport.model;
using TransportServices.transport.services;
using JsonSerializer = System.Text.Json.JsonSerializer;

namespace TransportNetworking.transport.network.rpcprotocol
{
    public class TransportClientRpcReflectionWorker : ITransportObserver
    {
        private ITransportServices server;
        private Socket connection;

        private StreamReader input;
        private StreamWriter output;
        private volatile bool connected;

        public TransportClientRpcReflectionWorker(ITransportServices server, Socket connection)
        {
            this.server = server;
            this.connection = connection;
            try
            {
                NetworkStream networkStream = new NetworkStream(connection);
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
                    string line = input.ReadLine();
                    Request request = JsonConvert.DeserializeObject<Request>(line!);
                    //BinaryFormatter formatter = new BinaryFormatter();
                    //object request = formatter.Deserialize(input.BaseStream);
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
                Console.WriteLine("Error " + e);
            }
        }

        public void ReservationAdded(Reservation reservation)
        {
            Response resp = new Response.Builder().Type(ResponseType.NEW_RESERVATION).Data(reservation).Build();
            Console.WriteLine("Reservation added: " + reservation);
            try
            {
                SendResponse(resp);
            }
            catch (IOException e)
            {
                Console.WriteLine(e.StackTrace);
            }
        }

        private static Response okResponse = new Response.Builder().Type(ResponseType.OK).Build();

        private Response HandleRequest(Request request)
        {
            Response response = null;
            string handlerName = "Handle" + request.Type;
            Console.WriteLine("HandlerName " + handlerName);
            try
            {
                MethodInfo method = this.GetType().GetMethod(handlerName, new Type[] { typeof(Request) });
                if (method != null)
                {
                    response = (Response)method.Invoke(this, new object[] { request });
                    Console.WriteLine("Method " + handlerName + " invoked");
                }
                else
                {
                    Console.WriteLine("Method " + handlerName + " not found");
                }
            }
            catch (Exception e)
            {
                Console.WriteLine(e.StackTrace);
            }

            return response;
        }

        public Response HandleLOGIN(Request request)
        {
            Console.WriteLine("Login Request ... " + request.Type + " " + request.Data);
            ReservationManager reservationManager = request.Data as ReservationManager;
            if (reservationManager == null)
            {
                Console.WriteLine("Error: ReservationManager is null");
                return new Response.Builder().Type(ResponseType.ERROR).Data("ReservationManager is null").Build();
            }
            try
            {
                Console.WriteLine("Reservation Manager in HandleLOGIN: " + reservationManager);
                ReservationManager reservationManager2 = server.Login(reservationManager, this);
                Console.WriteLine("Login successful! (Worker)");
                return new Response(){Data = reservationManager2};
            }
            catch (Exception e)
            {
                Console.WriteLine("Exception during login: " + e.Message);
                Console.WriteLine(e.StackTrace);
                connected = false;
                return new Response.Builder().Type(ResponseType.ERROR).Data(e.Message).Build();
            }
        }

        public Response HandleSEARCH_RESERVATION_MANAGER_BY_NAME(Request request)
        {
            Console.WriteLine("Search Reservation Manager By Name Request ...");
            string name = (string)request.Data;
            try
            {
                return new Response.Builder().Type(ResponseType.SEARCH_RESERVATION_MANAGER_BY_NAME)
                    .Data(server.SearchReservationManagerByName(name)).Build();
            }
            catch (TransportException e)
            {
                return new Response.Builder().Type(ResponseType.ERROR).Data(e.Message).Build();
            }
        }

        public Response HandleGET_ALL_TRIPS(Request request)
        {
            Console.WriteLine("Get All Trips Request ...");
            try
            {
                return new Response.Builder().Type(ResponseType.GET_ALL_TRIPS).Data(server.GetAllTrips()).Build();
            }
            catch (TransportException e)
            {
                return new Response.Builder().Type(ResponseType.ERROR).Data(e.Message).Build();
            }
        }

        public Response HandleSEARCH_TRIP_BY_ID(Request request)
        {
            Console.WriteLine("Search Trip By Id Request ...");
            long id = (long)request.Data;
            try
            {
                return new Response.Builder().Type(ResponseType.SEARCH_TRIP_BY_ID).Data(server.SearchTripById(id)).Build();
            }
            catch (TransportException e)
            {
                return new Response.Builder().Type(ResponseType.ERROR).Data(e.Message).Build();
            }
        }

        public Response HandleADD_RESERVATION(Request request)
        {
            Console.WriteLine("Add Reservation Request ...");
            Reservation reservation = (Reservation)request.Data;
            new Thread(() =>
            {
                try
                {
                    server.AddReservation(reservation);
                }
                catch (TransportException e)
                {
                    Console.WriteLine(e.StackTrace);
                }
            }).Start();
            return okResponse;
        }

        public Response HandleGET_RESERVATIONS_BY_TRIP(Request request)
        {
            Console.WriteLine("Get Reservations By Trip Request ...");
            Trip trip = (Trip)request.Data;
            try
            {
                return new Response.Builder().Type(ResponseType.GET_RESERVATIONS_BY_TRIP).Data(server.GetReservationsByTrip(trip)).Build();
            }
            catch (TransportException e)
            {
                return new Response.Builder().Type(ResponseType.ERROR).Data(e.Message).Build();
            }
        }

        /*public Response HandleGET_SEATS_BY_RESERVATION(Request request)
        {
            Console.WriteLine("Get Seats By Reservation Request ...");
            long reservationId = (long)request.Data();
            try
            {
                return new Response.Builder().Type(ResponseType.GET_SEATS_BY_RESERVATION).Data(server.GetSeatsByReservation(reservationId)).Build();
            }
            catch (TransportException e)
            {
                return new Response.Builder().Type(ResponseType.ERROR).Data(e.Message).Build();
            }
        }

        public Response HandleGET_SEATS_BY_TRIP(Request request)
        {
            Console.WriteLine("Get Seats By Trip Request ...");
            long tripId = (long)request.Data();
            try
            {
                return new Response.Builder().Type(ResponseType.GET_SEATS_BY_TRIP).Data(server.GetSeatsByTripId(tripId)).Build();
            }
            catch (TransportException e)
            {
                return new Response.Builder().Type(ResponseType.ERROR).Data(e.Message).Build();
            }
        }*/

        public Response HandleLOGOUT(Request request)
        {
            Console.WriteLine("Logout Request...");
            ReservationManager reservationManager = (ReservationManager)request.Data;
            try
            {
                server.Logout(reservationManager, this);
                connected = false;
                return okResponse;
            }
            catch (TransportException e)
            {
                return new Response.Builder().Type(ResponseType.ERROR).Data(e.Message).Build();
            }
        }

        private void SendResponse(Response response)
        {
            Console.WriteLine("sending response " + response);
            lock (output)
            {
                string responseString = JsonConvert.SerializeObject(response);
                output.WriteLine(responseString);
                output.Flush();
                //BinaryFormatter formatter = new BinaryFormatter();
                /*using (MemoryStream ms = new MemoryStream())
                {
                    formatter.Serialize(ms, response);
                    byte[] buffer = ms.ToArray();
                    output.Write(buffer, 0, buffer.Length);
                    output.Flush();
                }*/
            }
        }
    }
}