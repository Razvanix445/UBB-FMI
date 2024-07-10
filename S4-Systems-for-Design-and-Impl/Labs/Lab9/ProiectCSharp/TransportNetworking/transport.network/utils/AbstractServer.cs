using System;
using System.Net.Sockets;

namespace TransportNetworking.transport.network.utils
{
    public abstract class AbstractServer
    {
        private int port;
        private TcpListener server = null;

        public AbstractServer(int port)
        {
            this.port = port;
        }

        public void Start()
        {
            try
            {
                server = new TcpListener(System.Net.IPAddress.Any, port);
                server.Start();

                while (true)
                {
                    Console.WriteLine("Waiting for clients ...");
                    TcpClient client = server.AcceptTcpClient();
                    Console.WriteLine("Client connected ...");
                    ProcessRequest(client.Client);
                }
            }
            catch (Exception e)
            {
                throw new ServerException("Starting server error ", e);
            }
            finally
            {
                Stop();
            }
        }

        protected abstract void ProcessRequest(Socket client);

        public void Stop()
        {
            try
            {
                server.Stop();
            }
            catch (Exception e)
            {
                throw new Exception("Closing server error ", e);
            }
        }
    }
}