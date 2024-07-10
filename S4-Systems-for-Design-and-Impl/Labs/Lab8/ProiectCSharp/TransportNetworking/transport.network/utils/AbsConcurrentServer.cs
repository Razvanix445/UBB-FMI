using System;
using System.Net.Sockets;
using System.Threading;

namespace TransportNetworking.transport.network.utils
{
    public abstract class AbsConcurrentServer : AbstractServer
    {
        public AbsConcurrentServer(int port) : base(port)
        {
            Console.WriteLine("Concurrent AbstractServer");
        }

        protected override void ProcessRequest(Socket client)
        {
            Thread tw = CreateWorker(client);
            tw.Start();
        }

        protected abstract Thread CreateWorker(Socket client);

        public virtual void Stop()
        {
        }
    }
}