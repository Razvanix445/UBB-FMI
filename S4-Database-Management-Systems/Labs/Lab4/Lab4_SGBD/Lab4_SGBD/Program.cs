using System;
using System.Configuration;
using System.Data.SqlClient;
using System.Threading;

namespace Lab4_SGBD
{
    class Program
    {
        static string connectionString = ConfigurationManager.AppSettings["ConnectionString"];
        static int noRetries = 3;

        public static void Main(string[] args)
        {
            Thread t1 = new Thread(new ThreadStart(Transaction1));
            Thread t2 = new Thread(new ThreadStart(Transaction2));

            t1.Start();
            t2.Start();
            
            t1.Join();
            t2.Join();
        }

        static void Transaction1()
        {
            int noTries = 0;
            while (!Transaction1_Run())
            {
                noTries++;
                if (noTries >= noRetries)
                    break;
            }
            if (noTries == noRetries)
                Console.WriteLine("Transaction 1 aborted.");
        }

        static void Transaction2()
        {
            int noTries = 0;
            while (!Transaction2_Run())
            {
                noTries++;
                if (noTries >= noRetries)
                    break;
            }
            if (noTries == noRetries)
                Console.WriteLine("Transaction 2 aborted.");
        }

        static bool Transaction1_Run()
        {
            bool success = false;

            Console.WriteLine("Transaction 1 started...");

            using (SqlConnection connection = new SqlConnection(connectionString))
            {
                SqlCommand command = connection.CreateCommand();
                try
                {
                    connection.Open();
                    command.Connection = connection;
                    command.CommandText = "EXEC DeadlockT1";
                    command.ExecuteNonQuery();
                    success = true;
                    Console.WriteLine("Transaction 1 complete!");
                }

                catch (SqlException ex)
                {
                    if (ex.Number == 1205)
                    {
                        Console.WriteLine("Transaction 1: Commit exception type: {0}", ex.GetType());
                        Console.WriteLine("Message: {0}", ex.Message);
                    }

                }
                return success;
            }
        }

        static bool Transaction2_Run()
        {
            bool success = false;

            Console.WriteLine("Transaction 2 started...");

            using (SqlConnection connection = new SqlConnection(connectionString))
            {
                SqlCommand command = connection.CreateCommand();
                try
                {
                    connection.Open();
                    command.Connection = connection;
                    command.CommandText = "EXEC DeadlockT2";
                    command.ExecuteNonQuery();
                    success = true;
                    Console.WriteLine("Transaction 2 complete!");
                }

                catch (SqlException ex)
                {
                    if (ex.Number == 1205)
                    {
                        Console.WriteLine("Transaction 2: Commit exception type: {0}", ex.GetType());
                        Console.WriteLine("Message: {0}", ex.Message);
                    }

                }
                return success;
            }
        }
    }
}