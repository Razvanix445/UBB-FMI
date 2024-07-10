using System;
using System.Collections.Generic;
using System.Data;
using System.Linq;
using System.Net.Sockets;
using System.Text;
using System.Threading.Tasks;
using System.Xml.Serialization;

using Lab1_SGBD.repository;

namespace Lab1_SGBD.service
{
    public class Service
    {
        private readonly Repository repository;
        public Service(Repository repository)
        {
            this.repository = repository;
        }

        public DataTable GetAllRegizori()
        {
            return repository.GetAllRegizori();
        }

        public DataTable GetFilmeByRegizorId(int parentId) {
            return repository.GetFilmeByRegizorId(parentId);
        }

        public void AddFilm(String titlu, DateTime dataLansare, String descriere, String contentRating, float userRating, float buget, float incasari, int regizorId)
        {
            repository.AddFilm(titlu, dataLansare, descriere, contentRating, userRating, buget, incasari, regizorId);
        }

        public void UpdateFilm(String titlu, DateTime dataLansare, String descriere, String contentRating, float userRating, float buget, float incasari, int regizorId)
        {
            repository.UpdateFilm(titlu, dataLansare, descriere, contentRating, userRating, buget, incasari, regizorId);
        }

        public void DeleteFilm(String title)
        {
            repository.DeleteFilm(title);
        }
    }
}
