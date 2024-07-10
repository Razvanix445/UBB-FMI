using System;
using System.Collections.Generic;
using System.Data;
using System.Data.SqlClient;
using System.Linq;
using System.Net.Sockets;
using System.Text;
using System.Threading.Tasks;
using System.Xml.Serialization;

using Lab3_SGBD.repository;

namespace Lab3_SGBD.service
{
    public class Service
    {
        private readonly Repository repository;
        public Service(Repository repository)
        {
            this.repository = repository;
        }

        public void AddChild(string tableName, Dictionary<string, object> values, int parentId)
        {
            repository.AddChild(tableName, values, parentId);
        }

        public void DeleteChild(string tableName, string primaryKeyColumnName, object primaryKeyValue)
        {
            repository.DeleteChild(tableName, primaryKeyColumnName, primaryKeyValue);
        }

        public void UpdateChild(string tableName, Dictionary<string, object> updatedValues, string primaryKeyColumnName, int primaryKeyValue)
        {
            repository.UpdateChild(tableName, updatedValues, primaryKeyColumnName, primaryKeyValue);
        }

        public DataTable GetChildrenByParentId(string tableName, string foreignKeyColumnName, int parentId)
        {
            return repository.GetChildrenByParentId(tableName, foreignKeyColumnName, parentId);
        }

        public DataTable GetAllRegizori()
        {
            return repository.GetAllRegizori();
        }

        public DataTable GetFilmeByRegizorId(int parentId)
        {
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
