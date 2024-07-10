using System;
using System.Collections.Generic;
using System.Text;
using System.Threading.Tasks;
using System.Data;
using System.Windows.Forms;

using Lab2_SGBD.service;
using Lab2_SGBD.repository;
using Lab2_SGBD.validator;
using System.Data.SqlClient;
using System.Drawing;
using System.IO;
using System.Configuration;
using System.Linq;


namespace Lab2_SGBD.ui
{
    public class UI : Form
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

        private DataGridView parentDataGridView;
        private DataGridView childDataGridView;

        private Button addButton;
        private Button removeButton;
        private Button updateButton;
        private Button exitButton;
        private Label titluFereastra;
        private Label parentLabel;
        private Label childLabel;
        private Label functionalitatiLabel;
        private TableLayoutPanel tableLayoutPanel;

        public UI()
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
                parentDataGridView.DataSource = table;
            }
        }

        /* This function is an event that is triggered when a row is selected in the parent table. */
        private void RegizoriDataGridView_SelectionChanged(object sender, EventArgs e)
        {
            if (parentDataGridView.SelectedRows.Count > 0)
            {
                int parentId = (int)parentDataGridView.SelectedRows[0].Cells[parentTablePrimaryKey].Value;
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
                childDataGridView.DataSource = table;
            }
        }

        /* This function is an event that is triggered when the form is loaded to set up all the controls. */
        private void InitializeComponent()
        {
            this.parentDataGridView = new System.Windows.Forms.DataGridView();
            this.childDataGridView = new System.Windows.Forms.DataGridView();
            this.addButton = new System.Windows.Forms.Button();
            this.removeButton = new System.Windows.Forms.Button();
            this.updateButton = new System.Windows.Forms.Button();
            this.exitButton = new System.Windows.Forms.Button();
            this.titluFereastra = new System.Windows.Forms.Label();
            this.parentLabel = new System.Windows.Forms.Label();
            this.childLabel = new System.Windows.Forms.Label();
            this.functionalitatiLabel = new System.Windows.Forms.Label();
            this.tableLayoutPanel = new System.Windows.Forms.TableLayoutPanel();
            ((System.ComponentModel.ISupportInitialize)(this.parentDataGridView)).BeginInit();
            ((System.ComponentModel.ISupportInitialize)(this.childDataGridView)).BeginInit();
            this.SuspendLayout();
            // 
            // parentDataGridView
            // 
            this.parentDataGridView.AutoSizeColumnsMode = System.Windows.Forms.DataGridViewAutoSizeColumnsMode.Fill;
            this.parentDataGridView.ColumnHeadersHeightSizeMode = System.Windows.Forms.DataGridViewColumnHeadersHeightSizeMode.AutoSize;
            this.parentDataGridView.Font = new System.Drawing.Font("Comic Sans MS", 12F);
            this.parentDataGridView.Location = new System.Drawing.Point(80, 249);
            this.parentDataGridView.Name = "parentDataGridView";
            this.parentDataGridView.RowHeadersWidth = 102;
            this.parentDataGridView.RowTemplate.Height = 24;
            this.parentDataGridView.SelectionMode = System.Windows.Forms.DataGridViewSelectionMode.FullRowSelect;
            this.parentDataGridView.Size = new System.Drawing.Size(3000, 750);
            this.parentDataGridView.TabIndex = 0;
            this.parentDataGridView.SelectionChanged += new System.EventHandler(this.RegizoriDataGridView_SelectionChanged);
            // 
            // childDataGridView
            // 
            this.childDataGridView.AutoSizeColumnsMode = System.Windows.Forms.DataGridViewAutoSizeColumnsMode.Fill;
            this.childDataGridView.ColumnHeadersHeightSizeMode = System.Windows.Forms.DataGridViewColumnHeadersHeightSizeMode.AutoSize;
            this.childDataGridView.Font = new System.Drawing.Font("Comic Sans MS", 12F);
            this.childDataGridView.Location = new System.Drawing.Point(80, 1137);
            this.childDataGridView.Name = "childDataGridView";
            this.childDataGridView.RowHeadersWidth = 102;
            this.childDataGridView.RowTemplate.Height = 24;
            this.childDataGridView.SelectionMode = System.Windows.Forms.DataGridViewSelectionMode.FullRowSelect;
            this.childDataGridView.Size = new System.Drawing.Size(3000, 500);
            this.childDataGridView.TabIndex = 1;
            // 
            // addButton
            // 
            this.addButton.Anchor = System.Windows.Forms.AnchorStyles.None;
            this.addButton.Font = new System.Drawing.Font("Comic Sans MS", 12F, System.Drawing.FontStyle.Regular, System.Drawing.GraphicsUnit.Point, ((byte)(238)));
            this.addButton.Location = new System.Drawing.Point(3533, 540);
            this.addButton.Name = "addButton";
            this.addButton.Size = new System.Drawing.Size(200, 75);
            this.addButton.TabIndex = 2;
            this.addButton.Tag = "";
            this.addButton.Text = "Add";
            this.addButton.UseVisualStyleBackColor = true;
            this.addButton.Click += new System.EventHandler(this.AddButton_Click);
            // 
            // removeButton
            // 
            this.removeButton.Anchor = System.Windows.Forms.AnchorStyles.None;
            this.removeButton.Font = new System.Drawing.Font("Comic Sans MS", 12F, System.Drawing.FontStyle.Regular, System.Drawing.GraphicsUnit.Point, ((byte)(238)));
            this.removeButton.Location = new System.Drawing.Point(3533, 703);
            this.removeButton.Name = "removeButton";
            this.removeButton.Size = new System.Drawing.Size(200, 75);
            this.removeButton.TabIndex = 3;
            this.removeButton.Text = "Remove";
            this.removeButton.UseVisualStyleBackColor = true;
            this.removeButton.Click += new System.EventHandler(this.RemoveButton_Click);
            // 
            // updateButton
            // 
            this.updateButton.Anchor = System.Windows.Forms.AnchorStyles.None;
            this.updateButton.Font = new System.Drawing.Font("Comic Sans MS", 12F, System.Drawing.FontStyle.Regular, System.Drawing.GraphicsUnit.Point, ((byte)(238)));
            this.updateButton.Location = new System.Drawing.Point(3533, 864);
            this.updateButton.Name = "updateButton";
            this.updateButton.Size = new System.Drawing.Size(200, 75);
            this.updateButton.TabIndex = 4;
            this.updateButton.Text = "Update";
            this.updateButton.UseVisualStyleBackColor = true;
            this.updateButton.Click += new System.EventHandler(this.UpdateButton_Click);
            // 
            // exitButton
            // 
            this.exitButton.Anchor = System.Windows.Forms.AnchorStyles.None;
            this.exitButton.Font = new System.Drawing.Font("Comic Sans MS", 12F, System.Drawing.FontStyle.Regular, System.Drawing.GraphicsUnit.Point, ((byte)(238)));
            this.exitButton.Location = new System.Drawing.Point(3839, 1858);
            this.exitButton.Name = "exitButton";
            this.exitButton.Size = new System.Drawing.Size(150, 75);
            this.exitButton.TabIndex = 5;
            this.exitButton.Text = "Exit";
            this.exitButton.UseVisualStyleBackColor = true;
            this.exitButton.Click += new System.EventHandler(this.ExitButton_Click);
            // 
            // titluFereastra
            // 
            this.titluFereastra.Anchor = ((System.Windows.Forms.AnchorStyles)(((System.Windows.Forms.AnchorStyles.Top | System.Windows.Forms.AnchorStyles.Left) 
            | System.Windows.Forms.AnchorStyles.Right)));
            this.titluFereastra.AutoSize = true;
            this.titluFereastra.Font = new System.Drawing.Font("Comic Sans MS", 18F, System.Drawing.FontStyle.Regular, System.Drawing.GraphicsUnit.Point, ((byte)(238)));
            this.titluFereastra.Location = new System.Drawing.Point(1759, 31);
            this.titluFereastra.Name = "titluFereastra";
            this.titluFereastra.Size = new System.Drawing.Size(451, 84);
            this.titluFereastra.TabIndex = 1;
            this.titluFereastra.Text = "Aplicație Filme";
            // 
            // parentLabel
            // 
            this.parentLabel.AutoSize = true;
            this.parentLabel.Font = new System.Drawing.Font("Comic Sans MS", 18F, System.Drawing.FontStyle.Regular, System.Drawing.GraphicsUnit.Point, ((byte)(238)));
            this.parentLabel.Location = new System.Drawing.Point(66, 162);
            this.parentLabel.Name = "parentLabel";
            this.parentLabel.Size = new System.Drawing.Size(410, 84);
            this.parentLabel.TabIndex = 6;
            this.parentLabel.Text = "Tabel Părinte";
            // 
            // childLabel
            // 
            this.childLabel.AutoSize = true;
            this.childLabel.Font = new System.Drawing.Font("Comic Sans MS", 18F, System.Drawing.FontStyle.Regular, System.Drawing.GraphicsUnit.Point, ((byte)(238)));
            this.childLabel.Location = new System.Drawing.Point(66, 1050);
            this.childLabel.Name = "childLabel";
            this.childLabel.Size = new System.Drawing.Size(294, 84);
            this.childLabel.TabIndex = 7;
            this.childLabel.Text = "Tabel Fiu";
            // 
            // functionalitatiLabel
            // 
            this.functionalitatiLabel.AutoSize = true;
            this.functionalitatiLabel.Font = new System.Drawing.Font("Comic Sans MS", 24F, System.Drawing.FontStyle.Regular, System.Drawing.GraphicsUnit.Point, ((byte)(238)));
            this.functionalitatiLabel.Location = new System.Drawing.Point(3344, 268);
            this.functionalitatiLabel.Name = "functionalitatiLabel";
            this.functionalitatiLabel.Size = new System.Drawing.Size(589, 112);
            this.functionalitatiLabel.TabIndex = 8;
            this.functionalitatiLabel.Text = "Funcționalități";
            // 
            // tableLayoutPanel
            // 
            this.tableLayoutPanel.Location = new System.Drawing.Point(0, 0);
            this.tableLayoutPanel.Name = "tableLayoutPanel";
            this.tableLayoutPanel.Size = new System.Drawing.Size(200, 100);
            this.tableLayoutPanel.TabIndex = 0;
            // 
            // UI
            // 
            this.AutoScaleDimensions = new System.Drawing.SizeF(16F, 31F);
            this.AutoScaleMode = System.Windows.Forms.AutoScaleMode.Font;
            this.BackColor = System.Drawing.Color.LightBlue;
            this.ClientSize = new System.Drawing.Size(4101, 1979);
            this.Controls.Add(this.functionalitatiLabel);
            this.Controls.Add(this.childLabel);
            this.Controls.Add(this.parentLabel);
            this.Controls.Add(this.titluFereastra);
            this.Controls.Add(this.exitButton);
            this.Controls.Add(this.updateButton);
            this.Controls.Add(this.removeButton);
            this.Controls.Add(this.addButton);
            this.Controls.Add(this.parentDataGridView);
            this.Controls.Add(this.childDataGridView);
            this.Name = "UI";
            this.WindowState = System.Windows.Forms.FormWindowState.Maximized;
            this.Load += new System.EventHandler(this.UI_Load);
            ((System.ComponentModel.ISupportInitialize)(this.parentDataGridView)).EndInit();
            ((System.ComponentModel.ISupportInitialize)(this.childDataGridView)).EndInit();
            this.ResumeLayout(false);
            this.PerformLayout();

        }

        

        /* Event for adding a movie in the child table when button Add is pressed */
        private void AddButton_Click(object sender, EventArgs e)
        {
            if (childDataGridView.SelectedRows.Count == 0)
            {
                MessageBox.Show("Please select a row from the parent table.", "Error", MessageBoxButtons.OK, MessageBoxIcon.Error);
                return;
            }

            string[] childColumns = ConfigurationManager.AppSettings["ChildColumns"].Split(',');
            //string[] parentColumns = ConfigurationManager.AppSettings["ParentColumns"].Split(',');

            Dictionary<string, object> values = new Dictionary<string, object>();
            foreach (var column in childColumns)
            {
                values[column] = childDataGridView.SelectedRows[0].Cells[column].Value;
            }
            int selectedParentId = (int)parentDataGridView.SelectedRows[0].Cells[ConfigurationManager.AppSettings["ParentPrimaryKey"]].Value;

            try
            {
                service.AddChild(ConfigurationManager.AppSettings["ChildTable"], values, selectedParentId);
                childDataGridView.DataSource = service.GetChildrenByParentId(ConfigurationManager.AppSettings["ChildTable"], ConfigurationManager.AppSettings["ChildForeignKey"], selectedParentId);
            }
            catch (Exception ex)
            {
                MessageBox.Show("Failed to add data. Invalid input.", "Error", MessageBoxButtons.OK, MessageBoxIcon.Error);
            }
        }

        /* Event for removing a movie from the child table by its id when button Remove is pressed */
        private void RemoveButton_Click(object sender, EventArgs e)
        {
            if (childDataGridView.SelectedRows.Count == 0)
            {
                MessageBox.Show("Please select a row from the parent table.", "Error", MessageBoxButtons.OK, MessageBoxIcon.Error);
                return;
            }

            string tableName = ConfigurationManager.AppSettings["ChildTable"];
            string primaryKeyColumnName = ConfigurationManager.AppSettings["ChildPrimaryKey"];
            string foreignKeyColumnName = ConfigurationManager.AppSettings["ChildForeignKey"];
            int parentID = Convert.ToInt32(parentDataGridView.SelectedRows[0].Cells[ConfigurationManager.AppSettings["ParentPrimaryKey"]].Value);
            object primaryKeyValue = childDataGridView.SelectedRows[0].Cells[primaryKeyColumnName].Value;

            try
            {
                service.DeleteChild(tableName, primaryKeyColumnName, primaryKeyValue);
                childDataGridView.DataSource = service.GetChildrenByParentId(tableName, foreignKeyColumnName, parentID);
            }
            catch (Exception ex)
            {
                MessageBox.Show("Failed to delete data. Invalid input.", "Error", MessageBoxButtons.OK, MessageBoxIcon.Error);
            }
        }

        /* Event for updating a movie from the child table when button Update is pressed */
        private void UpdateButton_Click(object sender, EventArgs e)
        {
            if (childDataGridView.SelectedRows.Count == 0)
            {
                MessageBox.Show("Please select a row from the parent table.", "Error", MessageBoxButtons.OK, MessageBoxIcon.Error);
                return;
            }

            string childPrimaryKey = ConfigurationManager.AppSettings["ChildPrimaryKey"];
            if (!childDataGridView.Columns.Contains(childPrimaryKey))
            {
                MessageBox.Show($"Column named {childPrimaryKey} cannot be found in the DataGridView.", "Error", MessageBoxButtons.OK, MessageBoxIcon.Error);
                return;
            }

            int selectedChildId = (int)childDataGridView.SelectedRows[0].Cells[childPrimaryKey].Value;

            string[] childColumns = ConfigurationManager.AppSettings["ChildColumns"].Split(',');
            Dictionary<string, object> updatedValues = new Dictionary<string, object>();

            foreach (var column in childColumns)
            {
                updatedValues[column] = childDataGridView.SelectedRows[0].Cells[column].Value;
            }

            int selectedParentId = (int)parentDataGridView.SelectedRows[0].Cells[ConfigurationManager.AppSettings["ParentPrimaryKey"]].Value;

            service.UpdateChild(ConfigurationManager.AppSettings["ChildTable"], updatedValues, childPrimaryKey, selectedChildId);
            LoadChildData(selectedParentId);
        }

        private void ExitButton_Click(object sender, EventArgs e)
        {
            Application.Exit();
        }
    }
}
