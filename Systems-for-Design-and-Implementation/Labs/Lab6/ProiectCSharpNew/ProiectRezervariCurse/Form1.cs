using System;
using System.Collections;
using System.Collections.Generic;
using System.ComponentModel;
using System.ComponentModel.Design;
using System.Data;
using System.Drawing;
using System.Linq;
using System.Text;
using System.Threading.Tasks;
using System.Web.UI.WebControls;
using System.Windows.Forms;
using ProiectRezervariCurse.domain;
using ProiectRezervariCurse.repository.database;
using ProiectRezervariCurse.repository.interfaces;
using ProiectRezervariCurse.service;

namespace ProiectRezervariCurse
{
    public partial class Form1 : Form
    {
        private Service service;

        private Trip tripForReservation;
        
        public Form1()
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
        }
        
        public void SetService(Service service)
        {
            this.service = service;
            LoadData();
        }

        private void LoadData()
        {
            TripsDataGridView.AutoGenerateColumns = true;
            TripsDataGridView.ColumnHeadersHeightSizeMode = DataGridViewColumnHeadersHeightSizeMode.AutoSize;
            
            IEnumerable<Trip> trips = service.getAllTrips();
            List<Trip> tripsList = trips.ToList();

            DataTable table = new DataTable();

            table.Columns.Add("Id", typeof(long));
            table.Columns.Add("Destination", typeof(string));
            table.Columns.Add("DateDeparture", typeof(DateTime));
            table.Columns.Add("NoOfAvailableSeats", typeof(int));
            
            foreach (Trip trip in tripsList)
            {
                table.Rows.Add(trip.Id, trip.Destination, trip.DateDeparture, trip.NoOfAvailableSeats);
            }
            
            TripsDataGridView.DataSource = table;
            
            ReservedSeatsDataGridView.AutoGenerateColumns = true;
            ReservedSeatsDataGridView.ColumnHeadersHeightSizeMode = DataGridViewColumnHeadersHeightSizeMode.AutoSize;
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
                service.AddReservation(selectedTrip.Id, reservedSeats, clientName);
                MessageBox.Show("Reservation added successfully!");
                LoadData();
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
            IEnumerable<Trip> trips = service.getAllTrips();
            Trip selectedTrip = trips
                .FirstOrDefault(trip => trip.Destination == destination && trip.DateDeparture.Date.ToString("yyyy/MM/dd") == date.ToString("yyyy/MM/dd") && (trip.DateDeparture.TimeOfDay).ToString() == time);
            tripForReservation = selectedTrip;
            //Console.WriteLine("Destination: " + destination + " Date: " + date.ToString("yyyy/MM/dd") + " Time: " + time);
            //Console.WriteLine("Destination: " + selectedTrip.Destination + " Date: " + selectedTrip.DateDeparture.Date.ToString("yyyy/MM/dd") + " Time: " + selectedTrip.DateDeparture.TimeOfDay);
            if (selectedTrip != null)
            {
                List<Reservation> reservations = service.getReservationsByTripId(selectedTrip.Id);
                DataTable reservationTable = new DataTable();
                reservationTable.Columns.Add("Client Name", typeof(string));
                reservationTable.Columns.Add("Reserved Seat", typeof(int));

                int noOfSeats = 0;
                foreach (Reservation reservation in reservations)
                {
                    foreach (int seat in reservation.ReservedSeats)
                    {
                        noOfSeats += 1;
                        reservationTable.Rows.Add(reservation.ClientName, noOfSeats);
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
    }
}