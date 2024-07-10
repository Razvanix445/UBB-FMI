using System;
using System.Collections.Generic;
using System.Data;
using System.Data.SqlClient;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

namespace Lab1_SGBD.repository
{
    
    public class Repository
    {
        private readonly String connectionString;
        public Repository(String connectionString)
        {
            this.connectionString = connectionString;
        }

        public DataTable GetAllRegizori()
        {
            using (SqlConnection connection = new SqlConnection(connectionString))
            {
                string query = "SELECT * FROM Regizori";
                SqlDataAdapter adapter = new SqlDataAdapter(query, connection);
                DataTable table = new DataTable();
                adapter.Fill(table);
                return table;
            }
        }

        public DataTable GetFilmeByRegizorId(int parentId)
        {
            using (SqlConnection connection = new SqlConnection(connectionString))
            {
                string query = "SELECT Titlu, DataLansare, Descriere, ContentRating, UserRating, Buget, Incasari FROM Filme WHERE RegizorID = @parentId";
                SqlDataAdapter adapter = new SqlDataAdapter(query, connection);
                adapter.SelectCommand.Parameters.AddWithValue("@parentId", parentId);
                DataTable table = new DataTable();
                adapter.Fill(table);
                return table;
            }
        }

        public void AddFilm(String titlu, DateTime dataLansare, String descriere, String contentRating, float userRating, float buget, float incasari, int regizorId)
        {
            using (SqlConnection connection = new SqlConnection(connectionString))
            {
                string query = "INSERT INTO Filme (Titlu, DataLansare, Descriere, ContentRating, UserRating, Buget, Incasari, RegizorID) VALUES (@titlu, @dataLansare, @descriere, @contentRating, @userRating, @buget, @incasari, @regizorId)";
                SqlCommand command = new SqlCommand(query, connection);
                command.Parameters.AddWithValue("@titlu", titlu);
                command.Parameters.AddWithValue("@dataLansare", dataLansare);
                command.Parameters.AddWithValue("@descriere", descriere);
                command.Parameters.AddWithValue("@contentRating", contentRating);
                command.Parameters.AddWithValue("@userRating", userRating);
                command.Parameters.AddWithValue("@buget", buget);
                command.Parameters.AddWithValue("@incasari", incasari);
                command.Parameters.AddWithValue("@regizorId", regizorId);
                connection.Open();
                command.ExecuteNonQuery();
            }
        }

        public void UpdateFilm(String titlu, DateTime dataLansare, String descriere, String contentRating, float userRating, float buget, float incasari, int regizorId)
        {
            using (SqlConnection connection = new SqlConnection(connectionString))
            {
                string query = "UPDATE Filme SET DataLansare = @dataLansare, Descriere = @descriere, ContentRating = @contentRating, UserRating = @userRating, Buget = @buget, Incasari = @incasari WHERE Titlu = @titlu AND RegizorID = @regizorId";
                SqlCommand command = new SqlCommand(query, connection);
                command.Parameters.AddWithValue("@titlu", titlu);
                command.Parameters.AddWithValue("@dataLansare", dataLansare);
                command.Parameters.AddWithValue("@descriere", descriere);
                command.Parameters.AddWithValue("@contentRating", contentRating);
                command.Parameters.AddWithValue("@userRating", userRating);
                command.Parameters.AddWithValue("@buget", buget);
                command.Parameters.AddWithValue("@incasari", incasari);
                command.Parameters.AddWithValue("@regizorId", regizorId);
                connection.Open();
                command.ExecuteNonQuery();
            }
        }

        public void DeleteFilm(String title)
        {
            using (SqlConnection connection = new SqlConnection(connectionString))
            {
                string query = "DELETE FROM Filme WHERE Titlu = @title";
                SqlCommand command = new SqlCommand(query, connection);
                command.Parameters.AddWithValue("@title", title);
                connection.Open();
                command.ExecuteNonQuery();
            }
        }
    }
}
