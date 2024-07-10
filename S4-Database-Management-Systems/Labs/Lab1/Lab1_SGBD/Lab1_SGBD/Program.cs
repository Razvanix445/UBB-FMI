using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;
using System.Data.SqlClient;
using Lab1_SGBD.service;

using Lab1_SGBD.ui;
using System.Windows.Forms;

namespace Lab1_SGBD
{
    internal class Program
    {
        [STAThread]
        static void Main(string[] args)
        {
            Application.EnableVisualStyles();
            Application.SetCompatibleTextRenderingDefault(false);
            Application.Run(new UI());
        }
    }
}