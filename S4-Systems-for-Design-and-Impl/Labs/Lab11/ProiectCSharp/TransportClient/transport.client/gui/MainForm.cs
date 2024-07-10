using System;
using System.Collections.Generic;
using System.Data;
using System.Drawing;
using System.Linq;
using System.Windows.Forms;
using Transport.Model.transport.model;
using TransportModel.transport.model;
using TransportServices.transport.services;

namespace TransportClient.transport.client.gui
{
    public partial class MainForm : Form, ITransportObserver
    {
        private ITransportServices server;
        private ReservationManager LoggedUser;
        private Trip tripForReservation;
        
        public MainForm()
        {
            InitializeComponent();
            
            DestinationTextBox.GotFocus += new EventHandler(TextBox_GotFocus);
            DestinationTextBox.LostFocus += new EventHandler(TextBox_LostFocus);
            HourTextBox.GotFocus += new EventHandler(TextBox_GotFocus);
            HourTextBox.LostFocus += new EventHandler(TextBox_LostFocus);
            SeatsTextBox.GotFocus += new EventHandler(TextBox_GotFocus);
            SeatsTextBox.LostFocus += new EventHandler(TextBox_LostFocus);
            ClientNameTextBox.GotFocus += new EventHandler(TextBox_GotFocus);
            ClientNameTextBox.LostFocus += new EventHandler(TextBox_LostFocus);
            
            TripsDataGridView.SelectionChanged += TripsDataGridView_SelectionChanged;
            
            this.FormClosing += (s, e) => 
            {
                Logout();
                //Application.Exit();
                this.Close();
            };
        }
        
        public void SetServer(ITransportServices server)
        {
            this.server = server;
            //LoadData();
        }
        
        public void SetLoggedUser(ReservationManager loggedUser)
        {
            this.LoggedUser = loggedUser;
            LoadData();
        }

        public void LoadData()
        {
            try
            {
                TripsDataGridView.AutoGenerateColumns = true;
                TripsDataGridView.ColumnHeadersHeightSizeMode = DataGridViewColumnHeadersHeightSizeMode.AutoSize;

                IEnumerable<Trip> trips = server.GetAllTrips();
                foreach (Trip trip in trips)
                {
                    Console.WriteLine(trip);
                }
                List<Trip> tripsList = trips.ToList();

                DataTable table = new DataTable();

                table.Columns.Add("Id", typeof(long));
                table.Columns.Add("Destination", typeof(string));
                table.Columns.Add("DateDeparture", typeof(DateTime));
                table.Columns.Add("NoOfAvailableSeats", typeof(int));

                foreach (Trip trip in tripsList)
                {
                    table.Rows.Add(trip.id, trip.destination, trip.dateDeparture, trip.noOfAvailableSeats);
                }

                TripsDataGridView.DataSource = table;

                ReservedSeatsDataGridView.AutoGenerateColumns = true;
                ReservedSeatsDataGridView.ColumnHeadersHeightSizeMode =
                    DataGridViewColumnHeadersHeightSizeMode.AutoSize;

            }
            catch (TransportException ex)
            {
                MessageBox.Show("An error occurred while loading the data: " + ex.Message);
            }
        }

        public void ReservationAdded(Reservation reservation)
        {
            //BeginInvoke((Action)LoadData);
            this.BeginInvoke((MethodInvoker) delegate
            {
                Console.WriteLine("Reservation added: " + reservation);
                LoadData();
            });
        }
        
        private void ReserveButton_Click(object sender, EventArgs e)
        {
            Trip selectedTrip = tripForReservation;
            if (selectedTrip != null)
            {
                string clientName = ClientNameTextBox.Text;
                int noOfSeats = int.Parse(SeatsTextBox.Text);
                List<long> reservedSeats = new List<long>();
                for (int seat = 1; seat <= noOfSeats; seat++)
                {
                    reservedSeats.Add(seat);
                }
                Trip trip = server.SearchTripById(selectedTrip.id);
                Reservation reservation = new Reservation(trip, reservedSeats, clientName);
                server.AddReservation(reservation);
                MessageBox.Show("Reservation added successfully!");
                //LoadData();
                SearchButton_Click(null, null);
            }
            else
            {
                MessageBox.Show("No trip selected for reservation!");
            }
        }
        
        private void SearchButton_Click(object sender, EventArgs e)
        {
            string destination = DestinationTextBox.Text;
            DateTime date = DatePicker.Value;
            string time = HourTextBox.Text;
            IEnumerable<Trip> trips = server.GetAllTrips();
            Trip selectedTrip = trips
                .FirstOrDefault(trip => trip.destination == destination && trip.dateDeparture.Date.ToString("yyyy/MM/dd") == date.ToString("yyyy/MM/dd") && (trip.dateDeparture.TimeOfDay).ToString() == time);
            tripForReservation = selectedTrip;
            if (selectedTrip != null)
            {
                List<Seat> seats = null;
                try
                {
                    seats = server.GetSeatsByTrip(selectedTrip);
                }
                catch (TransportException ex)
                {
                    MessageBox.Show("An error occurred while getting the seats (MainForm): " + ex.Message);
                }
                List<Reservation> reservations = server.GetReservationsByTrip(selectedTrip);
                DataTable reservationTable = new DataTable();
                reservationTable.Columns.Add("Client Name", typeof(string));
                reservationTable.Columns.Add("Reserved Seat", typeof(int));

                int noOfSeats = 0;
                foreach (Reservation reservation in reservations)
                {
                    foreach (int seat in reservation.reservedSeats)
                    {
                        noOfSeats += 1;
                        reservationTable.Rows.Add(reservation.clientName, noOfSeats);
                        //Console.WriteLine("ClientName: " + reservation.ClientName + " Seat: " + noOfSeats);
                    }
                }
                for (int seatNo = noOfSeats; seatNo < 18; seatNo++)
                {
                    reservationTable.Rows.Add("-", seatNo + 1);
                }
                
                ReservedSeatsDataGridView.DataSource = reservationTable;
            }
            else
            {
                MessageBox.Show("No trip found with the given parameters!");
            }
        }
        
        private void TripsDataGridView_SelectionChanged(object sender, EventArgs e)
        {
            if (TripsDataGridView.SelectedRows.Count > 0)
            {
                DataGridViewRow selectedRow = TripsDataGridView.SelectedRows[0];
                DestinationTextBox.Text = selectedRow.Cells["Destination"].Value.ToString();
                DatePicker.Value = DateTime.Parse(selectedRow.Cells["DateDeparture"].Value.ToString());
                HourTextBox.Text = DateTime.Parse(selectedRow.Cells["DateDeparture"].Value.ToString()).TimeOfDay.ToString();
            }
        }
        
        private void TextBox_GotFocus(object sender, EventArgs e)
        {
            System.Windows.Forms.TextBox textBox = (System.Windows.Forms.TextBox) sender;
            if (textBox.Text == textBox.Tag as string)
            {
                textBox.Text = "";
                textBox.ForeColor = SystemColors.WindowText;
            }
        }
        
        private void TextBox_LostFocus(object sender, EventArgs e)
        {
            System.Windows.Forms.TextBox textBox = (System.Windows.Forms.TextBox) sender;
            if (string.IsNullOrWhiteSpace(textBox.Text))
            {
                textBox.Text = textBox.Tag as string;
                textBox.ForeColor = SystemColors.GrayText;
            }
        }

        private void ExitButton_Click_1(object sender, EventArgs e)
        {
            this.Close();
        }

        ReservationManager Logout()
        {
            try
            {
                server.Logout(LoggedUser, this);
                MessageBox.Show("Logout successful (MainController).");
                return LoggedUser;
            }
            catch (TransportException ex)
            {
                MessageBox.Show("Logout failed (MainController): " + ex.Message);
            }
            return LoggedUser;
        }
    }
}