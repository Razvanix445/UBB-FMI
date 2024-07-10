using System;
using System.Collections.Generic;
using System.Configuration;
using System.Data;
using System.Data.SqlClient;
using System.Windows.Forms;
using Lab3_SGBD.repository;
using Lab3_SGBD.service;
using Lab3_SGBD.validator;

namespace Lab3_SGBD.ui
{
    public partial class Form1 : System.Windows.Forms.Form
    {
        private string connectionString;
        private string windowTitle;
        private readonly string childTable;
        private readonly string parentTable;
        private readonly string childTableForeignKey;
        private readonly string parentTablePrimaryKey;
        private readonly string[] parentColumns;
        private readonly string[] childColumns;
        private readonly string parentQuery;
        private readonly string childQuery;

        private readonly Service service;
        private readonly Repository repository;
        private readonly Validator validator;
        
        public Form1()
        {
            InitializeComponent();
            try
            {
                connectionString = ConfigurationManager.AppSettings["ConnectionString"];
                windowTitle = ConfigurationManager.AppSettings["WindowTitle"];
                //parentTable = ConfigurationManager.AppSettings["ParentTable"];
                childTable = ConfigurationManager.AppSettings["ChildTable"];
                parentTablePrimaryKey = ConfigurationManager.AppSettings["ParentPrimaryKey"];
                childTableForeignKey = ConfigurationManager.AppSettings["ChildForeignKey"];
                //parentColumns = ConfigurationManager.AppSettings["ParentColumns"].Split(',');
                childColumns = ConfigurationManager.AppSettings["ChildColumns"].Split(',');
                parentQuery = ConfigurationManager.AppSettings["ParentQuery"];
                childQuery = ConfigurationManager.AppSettings["ChildQuery"];
            }
            catch (Exception ex)
            {
                MessageBox.Show("Failed to load configuration from file.", "Error", MessageBoxButtons.OK, MessageBoxIcon.Error);
            }
            this.repository = new Repository(connectionString);
            this.service = new Service(repository);
            this.validator = new Validator();
            this.CenterToScreen();
            this.AutoScaleMode = System.Windows.Forms.AutoScaleMode.Font;
            
            this.ParentDataGridView.SelectionChanged += new System.EventHandler(this.ParentDataGridView_SelectionChanged);
            this.AddButton.Click += new System.EventHandler(this.AddButton_Click);
            this.DeleteButton.Click += new System.EventHandler(this.RemoveButton_Click);
            this.UpdateButton.Click += new System.EventHandler(this.UpdateButton_Click);
            //this.ExitButton.Click += new System.EventHandler(this.ExitButton_Click);
        }
        
        /* This function is an event that is triggered when the form is loaded. */
        private void UI_Load(object sender, EventArgs e)
        {
            using (SqlConnection connection = new SqlConnection(connectionString))
            using (SqlCommand command = new SqlCommand(parentQuery, connection))
            {
                connection.Open();
                SqlDataAdapter adapter = new SqlDataAdapter(command);
                DataTable table = new DataTable();
                adapter.Fill(table);
                ParentDataGridView.DataSource = table;
            }
        }

        /* This function is an event that is triggered when a row is selected in the parent table. */
        private void ParentDataGridView_SelectionChanged(object sender, EventArgs e)
        {
            if (ParentDataGridView.SelectedRows.Count > 0)
            {
                int parentId = (int)ParentDataGridView.SelectedRows[0].Cells[parentTablePrimaryKey].Value;
                LoadChildData(parentId);
            }
        }

        private void LoadChildData(int parentId)
        {
            using (SqlConnection connection = new SqlConnection(connectionString))
            using (SqlCommand command = new SqlCommand(childQuery, connection))
            {
                command.Parameters.AddWithValue("@ParentId", parentId);
                connection.Open();
                SqlDataAdapter adapter = new SqlDataAdapter(command);
                DataTable table = new DataTable();
                adapter.Fill(table);
                ChildDataGridView.DataSource = table;
            }
        }
        
        /* Event for adding a movie in the child table when button Add is pressed */
        private void AddButton_Click(object sender, EventArgs e)
        {
            if (ChildDataGridView.SelectedRows.Count == 0)
            {
                MessageBox.Show("Please select a row from the child table.", "Error", MessageBoxButtons.OK, MessageBoxIcon.Error);
                return;
            }

            string[] childColumns = ConfigurationManager.AppSettings["ChildColumns"].Split(',');
            //string[] parentColumns = ConfigurationManager.AppSettings["ParentColumns"].Split(',');

            Dictionary<string, object> values = new Dictionary<string, object>();
            foreach (var column in childColumns)
            {
                values[column] = ChildDataGridView.SelectedRows[0].Cells[column].Value;
            }
            int selectedParentId = (int)ParentDataGridView.SelectedRows[0].Cells[ConfigurationManager.AppSettings["ParentPrimaryKey"]].Value;

            try
            {
                service.AddChild(ConfigurationManager.AppSettings["ChildTable"], values, selectedParentId);
                ChildDataGridView.DataSource = service.GetChildrenByParentId(ConfigurationManager.AppSettings["ChildTable"], ConfigurationManager.AppSettings["ChildForeignKey"], selectedParentId);
            }
            catch (Exception ex)
            {
                MessageBox.Show("Failed to add data. Invalid input.", "Error", MessageBoxButtons.OK, MessageBoxIcon.Error);
            }
        }

        /* Event for removing a movie from the child table by its id when button Remove is pressed */
        private void RemoveButton_Click(object sender, EventArgs e)
        {
            if (ChildDataGridView.SelectedRows.Count == 0)
            {
                MessageBox.Show("Please select a row from the child table.", "Error", MessageBoxButtons.OK, MessageBoxIcon.Error);
                return;
            }

            string tableName = ConfigurationManager.AppSettings["ChildTable"];
            string primaryKeyColumnName = ConfigurationManager.AppSettings["ChildPrimaryKey"];
            string foreignKeyColumnName = ConfigurationManager.AppSettings["ChildForeignKey"];
            int parentID = Convert.ToInt32(ParentDataGridView.SelectedRows[0].Cells[ConfigurationManager.AppSettings["ParentPrimaryKey"]].Value);
            object primaryKeyValue = ChildDataGridView.SelectedRows[0].Cells[primaryKeyColumnName].Value;

            try
            {
                service.DeleteChild(tableName, primaryKeyColumnName, primaryKeyValue);
                ChildDataGridView.DataSource = service.GetChildrenByParentId(tableName, foreignKeyColumnName, parentID);
            }
            catch (Exception ex)
            {
                MessageBox.Show("Failed to delete data. Invalid input.", "Error", MessageBoxButtons.OK, MessageBoxIcon.Error);
            }
        }

        /* Event for updating a movie from the child table when button Update is pressed */
        private void UpdateButton_Click(object sender, EventArgs e)
        {
            if (ChildDataGridView.SelectedRows.Count == 0)
            {
                MessageBox.Show("Please select a row from the child table.", "Error", MessageBoxButtons.OK, MessageBoxIcon.Error);
                return;
            }

            string childPrimaryKey = ConfigurationManager.AppSettings["ChildPrimaryKey"];
            if (!ChildDataGridView.Columns.Contains(childPrimaryKey))
            {
                MessageBox.Show($"Column named {childPrimaryKey} cannot be found in the DataGridView.", "Error", MessageBoxButtons.OK, MessageBoxIcon.Error);
                return;
            }

            int selectedChildId = (int)ChildDataGridView.SelectedRows[0].Cells[childPrimaryKey].Value;

            string[] childColumns = ConfigurationManager.AppSettings["ChildColumns"].Split(',');
            Dictionary<string, object> updatedValues = new Dictionary<string, object>();

            foreach (var column in childColumns)
            {
                updatedValues[column] = ChildDataGridView.SelectedRows[0].Cells[column].Value;
            }

            int selectedParentId = (int)ParentDataGridView.SelectedRows[0].Cells[ConfigurationManager.AppSettings["ParentPrimaryKey"]].Value;

            service.UpdateChild(ConfigurationManager.AppSettings["ChildTable"], updatedValues, childPrimaryKey, selectedChildId);
            LoadChildData(selectedParentId);
        }

        private void ExitButton_Click(object sender, EventArgs e)
        {
            Application.Exit();
        }
    }
}