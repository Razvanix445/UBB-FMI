using System;

namespace TransportModel.transport.model
{
    [Serializable]
    public class Entity<T>
    {
        public T Id { get; set; }
    }
}