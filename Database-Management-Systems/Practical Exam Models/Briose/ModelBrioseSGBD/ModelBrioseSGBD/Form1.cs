using System.Data;
using System.Data.SqlClient;

namespace ModelBrioseSGBD;

public partial class Form1 : Form
{
    string connectionString = @"Server=LAPTOP-C75FCJ0Q; Database=Briose; Integrated Security=true;";
    
    private DataGridView cofetariiDataGridView;
    private DataGridView brioseDataGridView;
    private Button saveButton;
    private Button deleteButton;
    
    public Form1()
    {
        InitializeComponent();
        
        // Initialize DataGridView for cofetarii
        cofetariiDataGridView = new DataGridView();
        this.Controls.Add(cofetariiDataGridView);
        cofetariiDataGridView.Dock = DockStyle.Top;
        cofetariiDataGridView.SelectionChanged += CofetariiDataGridView_SelectionChanged;

        // Initialize DataGridView for briose
        brioseDataGridView = new DataGridView();
        this.Controls.Add(brioseDataGridView);
        brioseDataGridView.Dock = DockStyle.Top;

        // Initialize Save button
        saveButton = new Button();
        this.Controls.Add(saveButton);
        saveButton.Text = "Save";
        saveButton.Dock = DockStyle.Bottom;
        saveButton.Click += SaveButton_Click;
        
        deleteButton = new Button();
        this.Controls.Add(deleteButton);
        deleteButton.Text = "Delete";
        deleteButton.Dock = DockStyle.Bottom;
        deleteButton.Click += DeleteButton_Click;
        
        LoadCofetarii();
    }
    
    private void LoadCofetarii()
    {
        using (SqlConnection connection = new SqlConnection(connectionString))
        {
            connection.Open();
            SqlDataAdapter cofetariiAdapter = new SqlDataAdapter("SELECT * FROM Cofetarii", connection);
            DataTable cofetariiTable = new DataTable();
            cofetariiAdapter.Fill(cofetariiTable);
            cofetariiDataGridView.DataSource = cofetariiTable;
        }
    }
    
    private void LoadBriose(int cofetarieId)
    {
        using (SqlConnection connection = new SqlConnection(connectionString))
        {
            connection.Open();
            SqlDataAdapter brioseAdapter = new SqlDataAdapter($"SELECT * FROM Briose WHERE CofetarieId = {cofetarieId}", connection);
            DataTable brioseTable = new DataTable();
            brioseAdapter.Fill(brioseTable);
            brioseDataGridView.DataSource = brioseTable;
            
            // Hide the CofetarieId column
            if (brioseDataGridView.Columns.Contains("CofetarieId"))
            {
                brioseDataGridView.Columns["CofetarieId"].Visible = false;
            }
        }
    }
    
    private void CofetariiDataGridView_SelectionChanged(object sender, EventArgs e)
    {
        if (cofetariiDataGridView.SelectedRows.Count > 0)
        {
            int selectedCofetarieId = (int)cofetariiDataGridView.SelectedRows[0].Cells["Id"].Value;
            LoadBriose(selectedCofetarieId);
        }
    }

    private void SaveButton_Click(object sender, EventArgs e)
    {
        DataTable brioseTable = brioseDataGridView.DataSource as DataTable;
        if (brioseTable != null)
        {
            int selectedCofetarieId = (int)cofetariiDataGridView.SelectedRows[0].Cells["Id"].Value;
            foreach (DataRow row in brioseTable.Rows)
            {
                if (row.RowState == DataRowState.Added)
                {
                    row["CofetarieId"] = selectedCofetarieId;
                }
            }
            using (SqlConnection connection = new SqlConnection(connectionString))
            {
                connection.Open();
                SqlDataAdapter brioseAdapter = new SqlDataAdapter($"SELECT * FROM Briose WHERE CofetarieId = {brioseTable.Rows[0]["CofetarieId"]}", connection);
                new SqlCommandBuilder(brioseAdapter);
                brioseAdapter.Update(brioseTable);
            }
        }
    }
    
    private void DeleteButton_Click(object sender, EventArgs e)
    {
        if (brioseDataGridView.SelectedRows.Count > 0)
        {
            DataGridViewRow selectedRow = brioseDataGridView.SelectedRows[0];
            
            DataTable brioseTable = brioseDataGridView.DataSource as DataTable;
            if (brioseTable != null)
            {
                brioseTable.Rows[selectedRow.Index].Delete();
                
                using (SqlConnection connection = new SqlConnection(connectionString))
                {
                    connection.Open();
                    SqlDataAdapter brioseAdapter = new SqlDataAdapter($"SELECT * FROM Briose WHERE CofetarieId = {brioseTable.Rows[0]["CofetarieId"]}", connection);
                    new SqlCommandBuilder(brioseAdapter);
                    brioseAdapter.Update(brioseTable);
                }
            }
        }
    }
}