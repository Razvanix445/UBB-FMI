using System.Data;
using System.Data.SqlClient;

namespace ModelMelodiiSGBD;

public partial class Form1 : Form
{
    string connectionString = @"Server=LAPTOP-C75FCJ0Q; Database=Melodii; Integrated Security=true;";
    
    DataGridView melodiiDataGridView;
    DataGridView artistiDataGridView;
    public Button saveButton;
    public Button deleteButton;
    
    public Form1()
    {
        InitializeComponent();
        
        artistiDataGridView = new DataGridView();
        this.Controls.Add(artistiDataGridView);
        artistiDataGridView.Dock = DockStyle.Top;
        artistiDataGridView.Height = 600;
        artistiDataGridView.SelectionChanged += ArtistiDataGridView_SelectionChanged;
        
        melodiiDataGridView = new DataGridView();
        this.Controls.Add(melodiiDataGridView);
        melodiiDataGridView.Dock = DockStyle.Top;
        melodiiDataGridView.Height = 600;
        
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
        
        LoadArtisti();
    }
    
    private void LoadArtisti()
    {
        using (SqlConnection connection = new SqlConnection(connectionString))
        {
            connection.Open();
            SqlDataAdapter artistiAdapter = new SqlDataAdapter("SELECT * FROM Artisti", connection);
            DataTable artistiTable = new DataTable();
            artistiAdapter.Fill(artistiTable);
            artistiDataGridView.DataSource = artistiTable;
        }
    }
    
    private void LoadMelodii(int artistId)
    {
        using (SqlConnection connection = new SqlConnection(connectionString))
        {
            connection.Open();
            SqlDataAdapter melodiiAdapter = new SqlDataAdapter($"SELECT * FROM Melodii WHERE ArtistId = {artistId}", connection);
            DataTable melodiiTable = new DataTable();
            melodiiAdapter.Fill(melodiiTable);
            melodiiDataGridView.DataSource = melodiiTable;
            
            if (melodiiDataGridView.Columns.Contains("ArtistId"))
            {
                melodiiDataGridView.Columns["ArtistId"].Visible = false;
            }
        }
    }
    
    private void ArtistiDataGridView_SelectionChanged(object sender, EventArgs e)
    {
        if (artistiDataGridView.SelectedRows.Count > 0)
        {
            int artistId = (int)artistiDataGridView.SelectedRows[0].Cells["Id"].Value;
            LoadMelodii(artistId);
        }
    }
    
    private void SaveButton_Click(object sender, EventArgs e)
    {
        DataTable melodiiTable = (DataTable)melodiiDataGridView.DataSource;
        if (melodiiTable != null)
        {
            int selectedArtistId = (int)artistiDataGridView.SelectedRows[0].Cells["Id"].Value;
            foreach (DataRow row in melodiiTable.Rows)
            {
                if (row.RowState == DataRowState.Added)
                {
                    row["ArtistId"] = selectedArtistId;
                }
            }
            using (SqlConnection connection = new SqlConnection(connectionString))
            {
                connection.Open();
                SqlDataAdapter melodiiAdapter = new SqlDataAdapter($"SELECT * FROM Melodii WHERE ArtistId = {melodiiTable.Rows[0]["ArtistId"]}", connection);
                new SqlCommandBuilder(melodiiAdapter);
                melodiiAdapter.Update(melodiiTable);
            }
        }
    }
    
    private void DeleteButton_Click(object sender, EventArgs e)
    {
        if (melodiiDataGridView.SelectedRows.Count > 0)
        {
            DataGridViewRow selectedRow = melodiiDataGridView.SelectedRows[0];
            
            DataTable melodiiTable = (DataTable)melodiiDataGridView.DataSource;
            if (melodiiTable != null)
            {
                melodiiTable.Rows[selectedRow.Index].Delete();
                
                using (SqlConnection connection = new SqlConnection(connectionString))
                {
                    connection.Open();
                    SqlDataAdapter melodiiAdapter = new SqlDataAdapter($"SELECT * FROM Melodii WHERE ArtistId = {melodiiTable.Rows[0]["ArtistId"]}", connection);
                    new SqlCommandBuilder(melodiiAdapter);
                    melodiiAdapter.Update(melodiiTable);
                }
            }
        }
    }
}