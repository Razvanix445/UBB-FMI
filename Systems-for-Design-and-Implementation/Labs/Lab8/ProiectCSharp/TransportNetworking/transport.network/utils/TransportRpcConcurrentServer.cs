using System;
using System.Net.Sockets;
using System.Threading;
using TransportNetworking.transport.network.jsonprotocol;
using TransportServices.transport.services;

namespace TransportNetworking.transport.network.utils
{
    public class TransportRpcConcurrentServer : AbsConcurrentServer
    {
        private ITransportServices transportServer;

        public TransportRpcConcurrentServer(int port, ITransportServices transportServer) : base(port)
        {
            this.transportServer = transportServer;
            Console.WriteLine("Transport - TransportRpcConcurrentServer");
        }

        protected override Thread CreateWorker(Socket client)
        {
            //TransportClientRpcReflectionWorker worker = new TransportClientRpcReflectionWorker(transportServer, client);
            TransportClientJsonWorker worker = new TransportClientJsonWorker(transportServer, client);

            Thread tw = new Thread(worker.Run);
            return tw;
        }

        public override void Stop()
        {
            Console.WriteLine("Stopping services ...");
            base.Stop();
        }
    }
}