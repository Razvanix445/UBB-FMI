using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;
using System.Data;
using System.Windows.Forms;

using Lab1_SGBD.service;
using Lab1_SGBD.repository;
using Lab1_SGBD.validator;
using System.Data.SqlClient;
using System.Drawing;

namespace Lab1_SGBD.ui
{
    public class UI: Form
    {
        string connectionString = @"Server=LAPTOP-C75FCJ0Q; Database=Filme; Integrated Security=true;";
        private readonly Service service;
        private readonly Repository repository;
        private readonly Validator validator;

        private DataGridView regizoriDataGridView;
        private DataGridView filmeDataGridView;

        private TextBox titluTextBox;
        private DateTimePicker dataLansareDateTimePicker;
        private TextBox descriereTextBox;
        private TextBox contentRatingTextBox;
        private TextBox userRatingTextBox;
        private TextBox budgetTextBox;
        private TextBox boxOfficeTextBox;

        public UI()
        {
            InitializeComponent();
            this.repository = new Repository(connectionString);
            this.service = new Service(repository);
            this.validator = new Validator();
            this.CenterToScreen();
            this.AutoScaleMode = System.Windows.Forms.AutoScaleMode.Font;
        }

        /* This function is an event that is triggered when the form is loaded. */
        private void UI_Load(object sender, EventArgs e)
        {
            DataTable regizoriData = service.GetAllRegizori();
            if (regizoriData != null)
            {
                regizoriDataGridView.DataSource = regizoriData;
                if (regizoriDataGridView.Rows.Count > 0)
                    regizoriDataGridView.Rows[0].Selected = true;
            }
            else
            {
                MessageBox.Show("Failed to retrieve data from the database.", "Error", MessageBoxButtons.OK, MessageBoxIcon.Error);
            }
        }

        /* This function is an event that is triggered when a row is selected in the parent table. */
        private void RegizoriDataGridView_SelectionChanged(object sender, EventArgs e)
        {
            if (regizoriDataGridView.SelectedRows.Count > 0)
            {
                int selectedRegizorId = (int)regizoriDataGridView.SelectedRows[0].Cells["RegizorId"].Value;
                DataTable relatedMovies = service.GetFilmeByRegizorId(selectedRegizorId);
                filmeDataGridView.DataSource = relatedMovies;
            }
        }

        /* This function is an event that is triggered when the form is loaded to set up all the controls. */
        private void InitializeComponent()
        {
            this.ClientSize = new System.Drawing.Size(1520, 800);
            this.BackColor = System.Drawing.Color.LightBlue;
            this.WindowState = FormWindowState.Maximized;
            this.Name = "UI";

            this.regizoriDataGridView = new DataGridView();
            this.filmeDataGridView = new DataGridView();
            ((System.ComponentModel.ISupportInitialize)(this.regizoriDataGridView)).BeginInit();
            ((System.ComponentModel.ISupportInitialize)(this.filmeDataGridView)).BeginInit();
            this.SuspendLayout();

            this.regizoriDataGridView.AutoSizeColumnsMode = DataGridViewAutoSizeColumnsMode.Fill;
            this.regizoriDataGridView.ColumnHeadersHeightSizeMode = DataGridViewColumnHeadersHeightSizeMode.AutoSize;
            this.regizoriDataGridView.Location = new System.Drawing.Point(30, 250);
            this.regizoriDataGridView.Width = 700;
            this.regizoriDataGridView.Height = 200;
            this.regizoriDataGridView.Name = "regizoriDataGridView";
            this.regizoriDataGridView.Font = new Font("Comic Sans MS", 12, FontStyle.Regular);
            this.regizoriDataGridView.RowTemplate.Height = 24;
            this.regizoriDataGridView.TabIndex = 0;
            // Setam modul de selectie a unei linii din regizoriDataGridView
            this.regizoriDataGridView.SelectionMode = DataGridViewSelectionMode.FullRowSelect;
            // Atribuim Eventul de selectie a unei linii din regizoriDataGridView pentru popularea filmeDataGridView
            this.regizoriDataGridView.SelectionChanged += RegizoriDataGridView_SelectionChanged;

            this.filmeDataGridView.AutoSizeColumnsMode = DataGridViewAutoSizeColumnsMode.Fill;
            this.filmeDataGridView.ColumnHeadersHeightSizeMode = DataGridViewColumnHeadersHeightSizeMode.AutoSize;
            this.filmeDataGridView.Location = new System.Drawing.Point(30, 500);
            this.filmeDataGridView.Width = 1460;
            this.filmeDataGridView.Height = 250;
            this.filmeDataGridView.Name = "filmeDataGridView";
            this.filmeDataGridView.Font = new Font("Comic Sans MS", 12, FontStyle.Regular);
            this.filmeDataGridView.RowTemplate.Height = 24;
            this.filmeDataGridView.TabIndex = 1;
            // Setam modul de selectie a unei linii din filmeDataGridView
            this.filmeDataGridView.SelectionMode = DataGridViewSelectionMode.FullRowSelect;

            this.Controls.Add(this.regizoriDataGridView);
            this.Controls.Add(this.filmeDataGridView);
            this.Load += new System.EventHandler(this.UI_Load);
            ((System.ComponentModel.ISupportInitialize)(this.regizoriDataGridView)).EndInit();
            ((System.ComponentModel.ISupportInitialize)(this.filmeDataGridView)).EndInit();

            addButtons();
            addLabels();
            addTextBoxes();

            this.ResumeLayout(false);
        }

        /* Function for Button management */
        private void addButtons()
        {
            Button addButton = new Button();
            addButton.Location = new System.Drawing.Point(900, 400);
            addButton.Width = 100;
            addButton.Height = 50;
            addButton.Name = "addButton";
            addButton.TabIndex = 2;
            addButton.Text = "Add";
            addButton.Font = new Font("Comic Sans MS", 12, FontStyle.Regular);
            addButton.UseVisualStyleBackColor = true;
            addButton.Click += AddButton_Click;
            this.Controls.Add(addButton);

            Button removeButton = new Button();
            removeButton.Location = new System.Drawing.Point(1100, 400);
            removeButton.Width = 100;
            removeButton.Height = 50;
            removeButton.Name = "removeButton";
            removeButton.TabIndex = 3;
            removeButton.Text = "Remove";
            removeButton.Font = new Font("Comic Sans MS", 12, FontStyle.Regular);
            removeButton.UseVisualStyleBackColor = true;
            removeButton.Click += RemoveButton_Click;
            this.Controls.Add(removeButton);

            Button updateButton = new Button();
            updateButton.Location = new System.Drawing.Point(1300, 400);
            updateButton.Width = 100;
            updateButton.Height = 50;
            updateButton.Name = "updateButton";
            updateButton.TabIndex = 4;
            updateButton.Text = "Update";
            updateButton.Font = new Font("Comic Sans MS", 12, FontStyle.Regular);
            updateButton.UseVisualStyleBackColor = true;
            updateButton.Click += UpdateButton_Click;
            this.Controls.Add(updateButton);

            Button exitButton = new Button();
            exitButton.Location = new System.Drawing.Point(1350, 750);
            exitButton.Width = 100;
            exitButton.Height = 50;
            exitButton.Name = "exitButton";
            exitButton.TabIndex = 5;
            exitButton.Text = "Exit";
            exitButton.Font = new Font("Comic Sans MS", 12, FontStyle.Regular);
            exitButton.UseVisualStyleBackColor = true;
            exitButton.Click += ExitButton_Click;
            this.Controls.Add(exitButton);
        }

        /* Function for Label management */
        private void addLabels()
        {
            Label titluFereastra = new Label();
            titluFereastra.Location = new System.Drawing.Point(660, 20);
            titluFereastra.Width = 200;
            titluFereastra.Height = 30;
            titluFereastra.Name = "titluFereastra";
            titluFereastra.TabIndex = 1;
            titluFereastra.Text = "Aplicație Filme";
            titluFereastra.Font = new Font("Comic Sans MS", 18, FontStyle.Regular);
            this.Controls.Add(titluFereastra);

            Label regizoriLabel = new Label();
            regizoriLabel.Location = new System.Drawing.Point(30, 220);
            regizoriLabel.Width = 150;
            regizoriLabel.Height = 30;
            regizoriLabel.Name = "regizoriLabel";
            regizoriLabel.TabIndex = 5;
            regizoriLabel.Text = "Regizori";
            regizoriLabel.Font = new Font("Comic Sans MS", 18, FontStyle.Regular);
            this.Controls.Add(regizoriLabel);

            Label filmeLabel = new Label();
            filmeLabel.Location = new System.Drawing.Point(30, 470);
            filmeLabel.Width = 150;
            filmeLabel.Height = 30;
            filmeLabel.Name = "filmeLabel";
            filmeLabel.TabIndex = 6;
            filmeLabel.Text = "Filme";
            filmeLabel.Font = new Font("Comic Sans MS", 18, FontStyle.Regular);
            this.Controls.Add(filmeLabel);

            Label dataLansareLabel = new Label();
            dataLansareLabel.Location = new System.Drawing.Point(242, 120);
            dataLansareLabel.Width = 200;
            dataLansareLabel.Height = 30;
            dataLansareLabel.Name = "Release Date";
            dataLansareLabel.TabIndex = 15;
            dataLansareLabel.Text = "Release Date             ↓";
            dataLansareLabel.Font = new Font("Comic Sans MS", 12, FontStyle.Regular);
            this.Controls.Add(dataLansareLabel);

            Label functionalitatiLabel = new Label();
            functionalitatiLabel.Location = new System.Drawing.Point(950, 250);
            functionalitatiLabel.Width = 400;
            functionalitatiLabel.Height = 50;
            functionalitatiLabel.TextAlign = ContentAlignment.BottomCenter;
            functionalitatiLabel.Name = "functionalitatiLabel";
            functionalitatiLabel.TabIndex = 16;
            functionalitatiLabel.Text = "Funcționalități";
            functionalitatiLabel.Font = new Font("Comic Sans MS", 24, FontStyle.Regular);
            this.Controls.Add(functionalitatiLabel);
        }

        /* Function for TextBox management*/
        private void addTextBoxes()
        {
            titluTextBox = new TextBox();
            titluTextBox.Location = new System.Drawing.Point(30, 150);
            titluTextBox.Width = 187;
            titluTextBox.Height = 30;
            titluTextBox.Name = "titluTextBox";
            titluTextBox.TabIndex = 7;
            titluTextBox.Text = "Enter title...";
            titluTextBox.Font = new Font("Comic Sans MS", 12, FontStyle.Regular);
            titluTextBox.Tag = "Enter title...";
            titluTextBox.ForeColor = SystemColors.GrayText;
            titluTextBox.GotFocus += TextBox_GotFocus;
            titluTextBox.LostFocus += TextBox_LostFocus;
            this.Controls.Add(titluTextBox);

            dataLansareDateTimePicker = new DateTimePicker();
            dataLansareDateTimePicker.Location = new System.Drawing.Point(242, 150);
            dataLansareDateTimePicker.Width = 187;
            dataLansareDateTimePicker.Height = 30;
            dataLansareDateTimePicker.Name = "dataLansareDateTimePicker";
            dataLansareDateTimePicker.Format = DateTimePickerFormat.Short;
            dataLansareDateTimePicker.Font = new Font("Comic Sans MS", 12, FontStyle.Regular);
            dataLansareDateTimePicker.TabIndex = 8;
            this.Controls.Add(dataLansareDateTimePicker);

            descriereTextBox = new TextBox();
            descriereTextBox.Location = new System.Drawing.Point(454, 150);
            descriereTextBox.Width = 187;
            descriereTextBox.Height = 30;
            descriereTextBox.Name = "descriereTextBox";
            descriereTextBox.TabIndex = 9;
            descriereTextBox.Text = "Enter description...";
            descriereTextBox.Font = new Font("Comic Sans MS", 12, FontStyle.Regular);
            descriereTextBox.Tag = "Enter description...";
            descriereTextBox.ForeColor = SystemColors.GrayText;
            descriereTextBox.GotFocus += TextBox_GotFocus;
            descriereTextBox.LostFocus += TextBox_LostFocus;
            this.Controls.Add(descriereTextBox);

            contentRatingTextBox = new TextBox();
            contentRatingTextBox.Location = new System.Drawing.Point(666, 150);
            contentRatingTextBox.Width = 187;
            contentRatingTextBox.Height = 30;
            contentRatingTextBox.Name = "ContentRating";
            contentRatingTextBox.TabIndex = 10;
            contentRatingTextBox.Text = "Enter content rating...";
            contentRatingTextBox.Font = new Font("Comic Sans MS", 12, FontStyle.Regular);
            contentRatingTextBox.Tag = "Enter content rating...";
            contentRatingTextBox.ForeColor = SystemColors.GrayText;
            contentRatingTextBox.GotFocus += TextBox_GotFocus;
            contentRatingTextBox.LostFocus += TextBox_LostFocus;
            this.Controls.Add(contentRatingTextBox);

            userRatingTextBox = new TextBox();
            userRatingTextBox.Location = new System.Drawing.Point(878, 150);
            userRatingTextBox.Width = 187;
            userRatingTextBox.Height = 30;
            userRatingTextBox.Name = "UserRating";
            userRatingTextBox.TabIndex = 11;
            userRatingTextBox.Text = "Enter user rating...";
            userRatingTextBox.Font = new Font("Comic Sans MS", 12, FontStyle.Regular);
            userRatingTextBox.Tag = "Enter user rating...";
            userRatingTextBox.ForeColor = SystemColors.GrayText;
            userRatingTextBox.GotFocus += TextBox_GotFocus;
            userRatingTextBox.LostFocus += TextBox_LostFocus;
            this.Controls.Add(userRatingTextBox);

            budgetTextBox = new TextBox();
            budgetTextBox.Location = new System.Drawing.Point(1090, 150);
            budgetTextBox.Width = 187;
            budgetTextBox.Height = 30;
            budgetTextBox.Name = "Budget";
            budgetTextBox.TabIndex = 13;
            budgetTextBox.Text = "Enter buget...";
            budgetTextBox.Font = new Font("Comic Sans MS", 12, FontStyle.Regular);
            budgetTextBox.Tag = "Enter buget...";
            budgetTextBox.ForeColor = SystemColors.GrayText;
            budgetTextBox.GotFocus += TextBox_GotFocus;
            budgetTextBox.LostFocus += TextBox_LostFocus;
            this.Controls.Add(budgetTextBox);

            boxOfficeTextBox = new TextBox();
            boxOfficeTextBox.Location = new System.Drawing.Point(1302, 150);
            boxOfficeTextBox.Width = 187;
            boxOfficeTextBox.Height = 30;
            boxOfficeTextBox.Name = "Box Office";
            boxOfficeTextBox.TabIndex = 14;
            boxOfficeTextBox.Text = "Enter Box Office...";
            boxOfficeTextBox.Font = new Font("Comic Sans MS", 12, FontStyle.Regular);
            boxOfficeTextBox.Tag = "Enter Box Office...";
            boxOfficeTextBox.ForeColor = SystemColors.GrayText;
            boxOfficeTextBox.GotFocus += TextBox_GotFocus;
            boxOfficeTextBox.LostFocus += TextBox_LostFocus;
            this.Controls.Add(boxOfficeTextBox);
        }

        /* Event for adding a movie in the child table when button Add is pressed */
        private void AddButton_Click(object sender, EventArgs e)
        {
            String title = this.titluTextBox.Text;
            DateTime releaseDate = this.dataLansareDateTimePicker.Value;
            String description = this.descriereTextBox.Text;
            String contentRating = this.contentRatingTextBox.Text;
            float userRating = float.Parse(this.userRatingTextBox.Text);
            float budget = float.Parse(this.budgetTextBox.Text);
            float boxOffice = float.Parse(this.boxOfficeTextBox.Text);

            if (regizoriDataGridView.SelectedRows.Count > 0 && validator.ValidateInputs(title, releaseDate, description, contentRating, userRating, budget, boxOffice))
            {
                int selectedRegizorId = (int)regizoriDataGridView.SelectedRows[0].Cells["RegizorId"].Value;
                service.AddFilm(title, releaseDate, description, contentRating, userRating, budget, boxOffice, selectedRegizorId);
                filmeDataGridView.DataSource = service.GetFilmeByRegizorId(selectedRegizorId);
            }
            else
            {
                MessageBox.Show("Please select a director from the parent table.", "Error", MessageBoxButtons.OK, MessageBoxIcon.Error);
            }
        }

        /* Event for removing a movie from the child table by its id when button Remove is pressed */
        private void RemoveButton_Click(object sender, EventArgs e)
        {
            String selectedFilmTitle = (String)filmeDataGridView.SelectedRows[0].Cells["Titlu"].Value;
            service.DeleteFilm(selectedFilmTitle);
            filmeDataGridView.DataSource = service.GetFilmeByRegizorId((int)regizoriDataGridView.SelectedRows[0].Cells["RegizorId"].Value);
        }

        /* Event for updating a movie from the child table when button Update is pressed */
        private void UpdateButton_Click(object sender, EventArgs e)
        {
            String title = (String)filmeDataGridView.SelectedRows[0].Cells["Titlu"].Value;
            DateTime releaseDate = (DateTime)filmeDataGridView.SelectedRows[0].Cells["DataLansare"].Value;
            String description = (String)filmeDataGridView.SelectedRows[0].Cells["Descriere"].Value;
            String contentRating = (String)filmeDataGridView.SelectedRows[0].Cells["ContentRating"].Value;
            float userRating = (float)filmeDataGridView.SelectedRows[0].Cells["UserRating"].Value;
            float budget;
            float.TryParse(filmeDataGridView.SelectedRows[0].Cells["Buget"].Value?.ToString(), out budget);
            float boxOffice;
            float.TryParse(filmeDataGridView.SelectedRows[0].Cells["Incasari"].Value?.ToString(), out boxOffice);

            if (regizoriDataGridView.SelectedRows.Count > 0 && validator.ValidateInputs(title, releaseDate, description, contentRating, userRating, budget, boxOffice))
            {
                int selectedRegizorId = (int)regizoriDataGridView.SelectedRows[0].Cells["RegizorId"].Value;
                service.UpdateFilm(title, releaseDate, description, contentRating, userRating, budget, boxOffice, selectedRegizorId);
                filmeDataGridView.DataSource = service.GetFilmeByRegizorId(selectedRegizorId);
            }
            else
            {
                MessageBox.Show("Please select a director from the parent table.", "Error", MessageBoxButtons.OK, MessageBoxIcon.Error);
            }
        }

        /* Event for deleting the placeholder text when the TextBox is pressed */
        private void TextBox_GotFocus(object sender, EventArgs e)
        {
            TextBox textBox = (TextBox)sender;
            if (textBox.Text == textBox.Tag as string)
            {
                textBox.Text = "";
                textBox.ForeColor = SystemColors.WindowText;
            }
        }

        /* Event for making a placeholder text that shows while the TextBox is not pressed */
        private void TextBox_LostFocus(object sender, EventArgs e)
        {
            TextBox textBox = (TextBox)sender;
            if (string.IsNullOrWhiteSpace(textBox.Text))
            {
                textBox.Text = textBox.Tag as string;
                textBox.ForeColor = SystemColors.GrayText;
            }
        }

        private void ExitButton_Click(object sender, EventArgs e)
        {
            Application.Exit();
        }
    }
}
