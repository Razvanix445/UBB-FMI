using System;
using System.Collections.Generic;
using System.ComponentModel;
using System.Data;
using System.Data.SqlClient;
using System.Drawing;
using System.Linq;
using System.Text;
using System.Threading.Tasks;
using System.Windows.Forms;

namespace ModelBiscuitiSGBD
{
    public partial class Form1 : Form
    {
        string connectionString = @"Server=LAPTOP-C75FCJ0Q; Database=Biscuiti; Integrated Security=true;";
        
        DataGridView biscuitiDataGridView;
        DataGridView producatoriDataGridView;
        Button saveButton;
        Button deleteButton;
        
        public Form1()
        {
            InitializeComponent();
            
            producatoriDataGridView = new DataGridView();
            this.Controls.Add(producatoriDataGridView);
            producatoriDataGridView.Dock = DockStyle.Top;
            producatoriDataGridView.Height = 200;
            producatoriDataGridView.SelectionChanged += ProducatoriDataGridView_SelectionChanged;
        
            biscuitiDataGridView = new DataGridView();
            this.Controls.Add(biscuitiDataGridView);
            biscuitiDataGridView.Dock = DockStyle.Top;
            biscuitiDataGridView.Height = 200;
        
            saveButton = new Button();
            this.Controls.Add(saveButton);
            saveButton.Text = "Save";
            saveButton.Dock = DockStyle.Bottom;
            saveButton.Height = 100;
            saveButton.Click += SaveButton_Click;
        
            deleteButton = new Button();
            this.Controls.Add(deleteButton);
            deleteButton.Text = "Delete";
            deleteButton.Dock = DockStyle.Bottom;
            deleteButton.Height = 100;
            deleteButton.Click += DeleteButton_Click;
        
            LoadProducatori();
        }
        
        private void LoadProducatori()
        {
            using (SqlConnection connection = new SqlConnection(connectionString))
            {
                connection.Open();
                SqlDataAdapter producatoriAdapter = new SqlDataAdapter("SELECT * FROM Producatori", connection);
                DataTable producatoriTable = new DataTable();
                producatoriAdapter.Fill(producatoriTable);
                producatoriDataGridView.DataSource = producatoriTable;
            }
        }
        
        private void LoadBiscuiti(int producatorId)
        {
            using (SqlConnection connection = new SqlConnection(connectionString))
            {
                connection.Open();
                SqlDataAdapter adapter = new SqlDataAdapter($"SELECT * FROM Biscuiti WHERE ProducatorId = {producatorId}", connection);
                DataTable melodiiTable = new DataTable();
                adapter.Fill(melodiiTable);
                biscuitiDataGridView.DataSource = melodiiTable;
                
                if (biscuitiDataGridView.Columns.Contains("ProducatorId"))
                {
                    biscuitiDataGridView.Columns["ProducatorId"].Visible = false;
                }
            }
        }
        
        private void ProducatoriDataGridView_SelectionChanged(object sender, EventArgs e)
        {
            if (producatoriDataGridView.SelectedRows.Count > 0)
            {
                int producatorId = (int)producatoriDataGridView.SelectedRows[0].Cells["Id"].Value;
                LoadBiscuiti(producatorId);
            }
        }
        
        private void SaveButton_Click(object sender, EventArgs e)
        {
            DataTable biscuitiTable = (DataTable)biscuitiDataGridView.DataSource;
            if (biscuitiTable != null)
            {
                int selectedProducatorId = (int)producatoriDataGridView.SelectedRows[0].Cells["Id"].Value;
                foreach (DataRow row in biscuitiTable.Rows)
                {
                    if (row.RowState == DataRowState.Added)
                    {
                        row["ProducatorId"] = selectedProducatorId;
                    }
                }
                using (SqlConnection connection = new SqlConnection(connectionString))
                {
                    connection.Open();
                    SqlDataAdapter adapter = new SqlDataAdapter($"SELECT * FROM Biscuiti WHERE ProducatorId = {biscuitiTable.Rows[0]["ProducatorId"]}", connection);
                    new SqlCommandBuilder(adapter);
                    adapter.Update(biscuitiTable);
                }
            }
        }
        
        private void DeleteButton_Click(object sender, EventArgs e)
        {
            if (biscuitiDataGridView.SelectedRows.Count > 0)
            {
                DataGridViewRow selectedRow = biscuitiDataGridView.SelectedRows[0];
                
                DataTable biscuitiTable = (DataTable)biscuitiDataGridView.DataSource;
                if (biscuitiTable != null)
                {
                    biscuitiTable.Rows[selectedRow.Index].Delete();
                    
                    using (SqlConnection connection = new SqlConnection(connectionString))
                    {
                        connection.Open();
                        SqlDataAdapter adapter = new SqlDataAdapter($"SELECT * FROM Biscuiti WHERE ProducatorId = {biscuitiTable.Rows[0]["ProducatorId"]}", connection);
                        new SqlCommandBuilder(adapter);
                        adapter.Update(biscuitiTable);
                    }
                }
            }
        }
    }
}