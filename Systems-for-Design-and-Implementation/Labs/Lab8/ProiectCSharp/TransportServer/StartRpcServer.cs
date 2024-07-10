using System;
using System.Collections.Generic;
using System.Configuration;

using TransportNetworking.transport.network.utils;
using TransportPersistence.transport.persistence;
using TransportServer.transport.server;
using TransportServices.transport.services;

namespace TransportServer
{
    public class StartRpcServer
    {
        private static int defaultPort = 55555;
        private static String defaultId = "127.0.0.1";

        /*[STAThread]*/
        public static void Main(string[] args)
        {
            IReservationManagerRepository reservationManagerRepository = new ReservationManagerDBRepository();
            IReservationRepository reservationRepository = new ReservationDBRepository();
            ITripRepository tripRepository = new TripDBRepository();
            ISeatRepository seatRepository = new SeatDBRepository();
            ITransportServices transportServerImpl = new TransportServiceImpl(reservationManagerRepository, reservationRepository, tripRepository, seatRepository);

            int transportServerPort = defaultPort;
            try
            {
                transportServerPort = int.Parse(ConfigurationManager.AppSettings["TransportServer.Port"]);
            }
            catch (FormatException nef)
            {
                Console.Error.WriteLine("Wrong Port Number" + nef.Message);
                Console.Error.WriteLine("Using default port " + defaultPort);
            }
            Console.WriteLine("Starting server on port: " + transportServerPort);
            AbstractServer server = new TransportRpcConcurrentServer(transportServerPort, transportServerImpl);
            try
            {
                server.Start();
            }
            catch (ServerException e)
            {
                Console.Error.WriteLine("Error starting the server" + e.Message);
            }
            finally
            {
                try
                {
                    server.Stop();
                }
                catch (ServerException e)
                {
                    Console.Error.WriteLine("Error stopping server " + e.Message);
                }
            }
        }
    }
}