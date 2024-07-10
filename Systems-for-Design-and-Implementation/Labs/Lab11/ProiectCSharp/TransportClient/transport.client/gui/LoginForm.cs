using System;
using System.Windows.Forms;
using TransportModel.transport.model;
using TransportServices.transport.services;

namespace TransportClient.transport.client.gui
{
    public partial class LoginForm : Form
    {
        private MainForm MainForm;
        private ReservationManager LoggedUser;
        private ITransportServices Server;
        
        public void SetServer(ITransportServices server)
        {
            this.Server = server;
        }
        
        public void SetMainForm(MainForm mainForm)
        {
            this.MainForm = mainForm;
        }
        
        public LoginForm()
        {
            InitializeComponent();
        }

        private void LoginButton_Click(object sender, EventArgs e)
        {
            string username = UsernameTextBox.Text;
            string password = PasswordTextBox.Text;
            LoggedUser = new ReservationManager(username, password);
            
            /*MainForm mainForm = new MainForm();
            mainForm.SetServer(Server);
            mainForm.SetLoggedUser(LoggedUser);*/
            
            // Console.WriteLine($"Server is {(Server == null ? "null" : "not null")}");
            // Console.WriteLine($"LoggedUser is {(LoggedUser == null ? "null" : "not null")}");
            // Console.WriteLine($"mainForm is {(MainForm == null ? "null" : "not null")}");
            
            if (LoggedUser == null)
            {
                MessageBox.Show("Error: LoggedUser is null");
                return;
            }
            
            try
            {
                //Console.WriteLine("Reservation Manager in LoginForm: " + LoggedUser);
                ReservationManager reservationManager = Server.Login(LoggedUser, MainForm);
                Console.WriteLine("User successfully logged in " + username);
                MainForm.SetLoggedUser(LoggedUser);
                //MainForm.LoadData();
                MainForm.Show();
                this.Hide();
            }
            catch (Exception ex)
            {
                MessageBox.Show("Failed to log in: " + ex.Message);
            }
        }
        
        private void ExitButton_Click(object sender, EventArgs e)
        {
            Application.Exit();
        }
    }
}