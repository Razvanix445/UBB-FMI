using System;
using Newtonsoft.Json;
using TransportModel.transport.model;

namespace TransportNetworking.transport.network.rpcprotocol
{
    [Serializable]
    public class Request
    {
        private Request() { }
        
        public RequestType Type { get; private set; }
        [JsonProperty(TypeNameHandling = TypeNameHandling.Auto)]
        public object Data { get; private set; }
        
        /*public ReservationManager ReservationManager { get; set; }
        public Trip Trip { get; set; }
        public Reservation Reservation { get; set; }*/

        public override string ToString()
        {
            return $"Request{{type='{Type}', data='{Data}'}}";
        }

        public class Builder
        {
            private Request request = new Request();

            public Builder Type(RequestType type)
            {
                request.Type = type;
                return this;
            }

            public Builder Data(object data)
            {
                request.Data = data;
                return this;
            }

            public Request Build()
            {
                return request;
            }
        }
    }
}