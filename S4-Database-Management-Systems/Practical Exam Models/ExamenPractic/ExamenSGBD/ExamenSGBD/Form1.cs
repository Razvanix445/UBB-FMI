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

namespace ExamenSGBD
{
    public partial class Form1 : Form
    {
        string connectionString = @"Server=LAPTOP-C75FCJ0Q; Database=Examen; Integrated Security=true;";
    
        DataGridView tablouriDataGridView;
        DataGridView tipuriDataGridView;
        public Button saveButton;
        public Button deleteButton;
        
        public Form1()
        {
            InitializeComponent();
            
            tipuriDataGridView = new DataGridView();
            this.Controls.Add(tipuriDataGridView);
            tipuriDataGridView.Dock = DockStyle.Top;
            tipuriDataGridView.Height = 200;
            tipuriDataGridView.SelectionChanged += TipuriDataGridView_SelectionChanged;
            
            tablouriDataGridView = new DataGridView();
            this.Controls.Add(tablouriDataGridView);
            tablouriDataGridView.Dock = DockStyle.Top;
            tablouriDataGridView.Height = 200;
            
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
            
            LoadTipuri();
        }
        
        private void LoadTipuri()
        {
            using (SqlConnection connection = new SqlConnection(connectionString))
            {
                connection.Open();
                SqlDataAdapter adapter = new SqlDataAdapter("SELECT * FROM Tipuri", connection);
                DataTable table = new DataTable();
                adapter.Fill(table);
                tipuriDataGridView.DataSource = table;
            }
        }
        
        private void LoadTablouri(int tablouId)
        {
            using (SqlConnection connection = new SqlConnection(connectionString))
            {
                connection.Open();
                SqlDataAdapter adapter = new SqlDataAdapter($"SELECT * FROM Tablouri WHERE TablouId = {tablouId}", connection);
                DataTable table = new DataTable();
                adapter.Fill(table);
                tablouriDataGridView.DataSource = table;
                
                if (tablouriDataGridView.Columns.Contains("TablouId"))
                {
                    tablouriDataGridView.Columns["TablouId"].Visible = false;
                }
            }
        }
        
        private void TipuriDataGridView_SelectionChanged(object sender, EventArgs e)
        {
            if (tipuriDataGridView.SelectedRows.Count > 0)
            {
                int titluId = (int)tipuriDataGridView.SelectedRows[0].Cells["Id"].Value;
                LoadTablouri(titluId);
            }
        }
        
        private void SaveButton_Click(object sender, EventArgs e)
        {
            DataTable table = (DataTable)tablouriDataGridView.DataSource;
            if (table != null)
            {
                int selectedId = (int)tipuriDataGridView.SelectedRows[0].Cells["Id"].Value;
                foreach (DataRow row in table.Rows)
                {
                    if (row.RowState == DataRowState.Added)
                    {
                        row["TablouId"] = selectedId;
                    }
                }
                using (SqlConnection connection = new SqlConnection(connectionString))
                {
                    connection.Open();
                    SqlDataAdapter adapter = new SqlDataAdapter($"SELECT * FROM Tablouri WHERE TablouId = {table.Rows[0]["TablouId"]}", connection);
                    new SqlCommandBuilder(adapter);
                    adapter.Update(table);
                }
            }
        }
        
        private void DeleteButton_Click(object sender, EventArgs e)
        {
            if (tablouriDataGridView.SelectedRows.Count > 0)
            {
                DataGridViewRow selectedRow = tablouriDataGridView.SelectedRows[0];
                
                DataTable table = (DataTable)tablouriDataGridView.DataSource;
                if (table != null)
                {
                    table.Rows[selectedRow.Index].Delete();
                    
                    using (SqlConnection connection = new SqlConnection(connectionString))
                    {
                        connection.Open();
                        SqlDataAdapter adapter = new SqlDataAdapter($"SELECT * FROM Tablouri WHERE Id = {table.Rows[0]["TablouId"]}", connection);
                        new SqlCommandBuilder(adapter);
                        adapter.Update(table);
                    }
                }
            }
        }
    }
}