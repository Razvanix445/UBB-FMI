using System.Windows.Forms;

namespace ProiectRezervariCurse
{
    partial class Form1
    {
        /// <summary>
        /// Required designer variable.
        /// </summary>
        private System.ComponentModel.IContainer components = null;

        /// <summary>
        /// Clean up any resources being used.
        /// </summary>
        /// <param name="disposing">true if managed resources should be disposed; otherwise, false.</param>
        protected override void Dispose(bool disposing)
        {
            if (disposing && (components != null))
            {
                components.Dispose();
            }

            base.Dispose(disposing);
        }

        #region Windows Form Designer generated code

        /// <summary>
        /// Required method for Designer support - do not modify
        /// the contents of this method with the code editor.
        /// </summary>
        private void InitializeComponent()
        {
            this.TitleLabel = new System.Windows.Forms.Label();
            this.TitlePanel = new System.Windows.Forms.Panel();
            this.LeftPanel = new System.Windows.Forms.Panel();
            this.SeatsTextBox = new System.Windows.Forms.TextBox();
            this.FillSpace11 = new System.Windows.Forms.Label();
            this.FillSpace7 = new System.Windows.Forms.Label();
            this.ClientNameTextBox = new System.Windows.Forms.TextBox();
            this.FillSpace6 = new System.Windows.Forms.Label();
            this.ReserveButton = new System.Windows.Forms.Button();
            this.FillSpace5 = new System.Windows.Forms.Label();
            this.SearchButton = new System.Windows.Forms.Button();
            this.FillSpace4 = new System.Windows.Forms.Label();
            this.HourTextBox = new System.Windows.Forms.TextBox();
            this.FillSpace3 = new System.Windows.Forms.Label();
            this.DatePicker = new System.Windows.Forms.DateTimePicker();
            this.FillSpace2 = new System.Windows.Forms.Label();
            this.DestinationTextBox = new System.Windows.Forms.TextBox();
            this.FillSpace1 = new System.Windows.Forms.Label();
            this.SearchTrip = new System.Windows.Forms.Label();
            this.FillSpaceLabel5 = new System.Windows.Forms.Label();
            this.FillSpaceLabel4 = new System.Windows.Forms.Label();
            this.RightPanel = new System.Windows.Forms.Panel();
            this.BottomPanel = new System.Windows.Forms.Panel();
            this.ExitButton = new System.Windows.Forms.Button();
            this.FillSpaceLabel3 = new System.Windows.Forms.Label();
            this.FillSpaceLabel2 = new System.Windows.Forms.Label();
            this.FillSpaceLabel1 = new System.Windows.Forms.Label();
            this.CenterPanel = new System.Windows.Forms.Panel();
            this.FillSpace10 = new System.Windows.Forms.Label();
            this.ReservedSeatsDataGridView = new System.Windows.Forms.DataGridView();
            this.FillSpace9 = new System.Windows.Forms.Label();
            this.TripsDataGridView = new System.Windows.Forms.DataGridView();
            this.FillSpace8 = new System.Windows.Forms.Label();
            this.TitlePanel.SuspendLayout();
            this.LeftPanel.SuspendLayout();
            this.BottomPanel.SuspendLayout();
            this.CenterPanel.SuspendLayout();
            ((System.ComponentModel.ISupportInitialize)(this.ReservedSeatsDataGridView)).BeginInit();
            ((System.ComponentModel.ISupportInitialize)(this.TripsDataGridView)).BeginInit();
            this.SuspendLayout();
            // 
            // TitleLabel
            // 
            this.TitleLabel.Dock = System.Windows.Forms.DockStyle.Fill;
            this.TitleLabel.Font = new System.Drawing.Font("Comic Sans MS", 14.1F, System.Drawing.FontStyle.Regular, System.Drawing.GraphicsUnit.Point, ((byte)(238)));
            this.TitleLabel.Location = new System.Drawing.Point(0, 0);
            this.TitleLabel.Name = "TitleLabel";
            this.TitleLabel.Size = new System.Drawing.Size(2217, 100);
            this.TitleLabel.TabIndex = 2;
            this.TitleLabel.Text = "Aplicatie Rezervari Curse";
            this.TitleLabel.TextAlign = System.Drawing.ContentAlignment.MiddleCenter;
            // 
            // TitlePanel
            // 
            this.TitlePanel.BackColor = System.Drawing.Color.FromArgb(((int)(((byte)(100)))), ((int)(((byte)(100)))), ((int)(((byte)(100)))));
            this.TitlePanel.Controls.Add(this.TitleLabel);
            this.TitlePanel.Dock = System.Windows.Forms.DockStyle.Top;
            this.TitlePanel.Font = new System.Drawing.Font("Comic Sans MS", 12F, System.Drawing.FontStyle.Regular, System.Drawing.GraphicsUnit.Point, ((byte)(238)));
            this.TitlePanel.Location = new System.Drawing.Point(0, 0);
            this.TitlePanel.MaximumSize = new System.Drawing.Size(5000, 100);
            this.TitlePanel.Name = "TitlePanel";
            this.TitlePanel.Size = new System.Drawing.Size(2217, 100);
            this.TitlePanel.TabIndex = 3;
            // 
            // LeftPanel
            // 
            this.LeftPanel.AutoSize = true;
            this.LeftPanel.BackColor = System.Drawing.Color.FromArgb(((int)(((byte)(150)))), ((int)(((byte)(150)))), ((int)(((byte)(150)))));
            this.LeftPanel.Controls.Add(this.SeatsTextBox);
            this.LeftPanel.Controls.Add(this.FillSpace11);
            this.LeftPanel.Controls.Add(this.FillSpace7);
            this.LeftPanel.Controls.Add(this.ClientNameTextBox);
            this.LeftPanel.Controls.Add(this.FillSpace6);
            this.LeftPanel.Controls.Add(this.ReserveButton);
            this.LeftPanel.Controls.Add(this.FillSpace5);
            this.LeftPanel.Controls.Add(this.SearchButton);
            this.LeftPanel.Controls.Add(this.FillSpace4);
            this.LeftPanel.Controls.Add(this.HourTextBox);
            this.LeftPanel.Controls.Add(this.FillSpace3);
            this.LeftPanel.Controls.Add(this.DatePicker);
            this.LeftPanel.Controls.Add(this.FillSpace2);
            this.LeftPanel.Controls.Add(this.DestinationTextBox);
            this.LeftPanel.Controls.Add(this.FillSpace1);
            this.LeftPanel.Controls.Add(this.SearchTrip);
            this.LeftPanel.Controls.Add(this.FillSpaceLabel5);
            this.LeftPanel.Controls.Add(this.FillSpaceLabel4);
            this.LeftPanel.Dock = System.Windows.Forms.DockStyle.Fill;
            this.LeftPanel.Location = new System.Drawing.Point(0, 100);
            this.LeftPanel.MaximumSize = new System.Drawing.Size(400, 5000);
            this.LeftPanel.Name = "LeftPanel";
            this.LeftPanel.RightToLeft = System.Windows.Forms.RightToLeft.No;
            this.LeftPanel.Size = new System.Drawing.Size(400, 1289);
            this.LeftPanel.TabIndex = 5;
            // 
            // SeatsTextBox
            // 
            this.SeatsTextBox.Dock = System.Windows.Forms.DockStyle.Bottom;
            this.SeatsTextBox.Font = new System.Drawing.Font("Comic Sans MS", 11.1F, System.Drawing.FontStyle.Regular, System.Drawing.GraphicsUnit.Point, ((byte)(238)));
            this.SeatsTextBox.Location = new System.Drawing.Point(30, 1016);
            this.SeatsTextBox.Name = "SeatsTextBox";
            this.SeatsTextBox.Size = new System.Drawing.Size(340, 59);
            this.SeatsTextBox.TabIndex = 16;
            this.SeatsTextBox.Tag = "No. of seats...";
            // 
            // FillSpace11
            // 
            this.FillSpace11.Dock = System.Windows.Forms.DockStyle.Bottom;
            this.FillSpace11.Location = new System.Drawing.Point(30, 1075);
            this.FillSpace11.Name = "FillSpace11";
            this.FillSpace11.Size = new System.Drawing.Size(340, 23);
            this.FillSpace11.TabIndex = 15;
            // 
            // FillSpace7
            // 
            this.FillSpace7.Dock = System.Windows.Forms.DockStyle.Bottom;
            this.FillSpace7.Location = new System.Drawing.Point(30, 1098);
            this.FillSpace7.Name = "FillSpace7";
            this.FillSpace7.Size = new System.Drawing.Size(340, 8);
            this.FillSpace7.TabIndex = 14;
            // 
            // ClientNameTextBox
            // 
            this.ClientNameTextBox.Dock = System.Windows.Forms.DockStyle.Bottom;
            this.ClientNameTextBox.Font = new System.Drawing.Font("Comic Sans MS", 11.1F, System.Drawing.FontStyle.Regular, System.Drawing.GraphicsUnit.Point, ((byte)(238)));
            this.ClientNameTextBox.Location = new System.Drawing.Point(30, 1106);
            this.ClientNameTextBox.Name = "ClientNameTextBox";
            this.ClientNameTextBox.Size = new System.Drawing.Size(340, 59);
            this.ClientNameTextBox.TabIndex = 13;
            this.ClientNameTextBox.Tag = "Client Name...";
            // 
            // FillSpace6
            // 
            this.FillSpace6.Dock = System.Windows.Forms.DockStyle.Bottom;
            this.FillSpace6.Location = new System.Drawing.Point(30, 1165);
            this.FillSpace6.Name = "FillSpace6";
            this.FillSpace6.Size = new System.Drawing.Size(340, 23);
            this.FillSpace6.TabIndex = 12;
            // 
            // ReserveButton
            // 
            this.ReserveButton.Dock = System.Windows.Forms.DockStyle.Bottom;
            this.ReserveButton.Font = new System.Drawing.Font("Comic Sans MS", 11.1F, System.Drawing.FontStyle.Regular, System.Drawing.GraphicsUnit.Point, ((byte)(238)));
            this.ReserveButton.Location = new System.Drawing.Point(30, 1188);
            this.ReserveButton.Name = "ReserveButton";
            this.ReserveButton.Size = new System.Drawing.Size(340, 78);
            this.ReserveButton.TabIndex = 10;
            this.ReserveButton.Text = "Reserve Seat";
            this.ReserveButton.UseVisualStyleBackColor = true;
            this.ReserveButton.Click += new System.EventHandler(this.ReserveButton_Click);
            // 
            // FillSpace5
            // 
            this.FillSpace5.Dock = System.Windows.Forms.DockStyle.Bottom;
            this.FillSpace5.Location = new System.Drawing.Point(30, 1266);
            this.FillSpace5.Name = "FillSpace5";
            this.FillSpace5.Size = new System.Drawing.Size(340, 23);
            this.FillSpace5.TabIndex = 11;
            // 
            // SearchButton
            // 
            this.SearchButton.Dock = System.Windows.Forms.DockStyle.Top;
            this.SearchButton.Font = new System.Drawing.Font("Comic Sans MS", 11.1F, System.Drawing.FontStyle.Regular, System.Drawing.GraphicsUnit.Point, ((byte)(238)));
            this.SearchButton.Location = new System.Drawing.Point(30, 309);
            this.SearchButton.Name = "SearchButton";
            this.SearchButton.RightToLeft = System.Windows.Forms.RightToLeft.No;
            this.SearchButton.Size = new System.Drawing.Size(340, 76);
            this.SearchButton.TabIndex = 9;
            this.SearchButton.Text = "Search";
            this.SearchButton.UseVisualStyleBackColor = true;
            this.SearchButton.Click += new System.EventHandler(this.SearchButton_Click);
            // 
            // FillSpace4
            // 
            this.FillSpace4.Dock = System.Windows.Forms.DockStyle.Top;
            this.FillSpace4.Location = new System.Drawing.Point(30, 286);
            this.FillSpace4.Name = "FillSpace4";
            this.FillSpace4.Size = new System.Drawing.Size(340, 23);
            this.FillSpace4.TabIndex = 8;
            // 
            // HourTextBox
            // 
            this.HourTextBox.Dock = System.Windows.Forms.DockStyle.Top;
            this.HourTextBox.Font = new System.Drawing.Font("Comic Sans MS", 11.1F, System.Drawing.FontStyle.Regular, System.Drawing.GraphicsUnit.Point, ((byte)(238)));
            this.HourTextBox.Location = new System.Drawing.Point(30, 227);
            this.HourTextBox.Name = "HourTextBox";
            this.HourTextBox.Size = new System.Drawing.Size(340, 59);
            this.HourTextBox.TabIndex = 4;
            this.HourTextBox.Tag = "Hour...";
            // 
            // FillSpace3
            // 
            this.FillSpace3.Dock = System.Windows.Forms.DockStyle.Top;
            this.FillSpace3.Location = new System.Drawing.Point(30, 204);
            this.FillSpace3.Name = "FillSpace3";
            this.FillSpace3.Size = new System.Drawing.Size(340, 23);
            this.FillSpace3.TabIndex = 5;
            // 
            // DatePicker
            // 
            this.DatePicker.CalendarFont = new System.Drawing.Font("Microsoft Sans Serif", 11.1F, System.Drawing.FontStyle.Regular, System.Drawing.GraphicsUnit.Point, ((byte)(238)));
            this.DatePicker.Dock = System.Windows.Forms.DockStyle.Top;
            this.DatePicker.Location = new System.Drawing.Point(30, 166);
            this.DatePicker.Name = "DatePicker";
            this.DatePicker.Size = new System.Drawing.Size(340, 38);
            this.DatePicker.TabIndex = 2;
            // 
            // FillSpace2
            // 
            this.FillSpace2.Dock = System.Windows.Forms.DockStyle.Top;
            this.FillSpace2.Location = new System.Drawing.Point(30, 143);
            this.FillSpace2.Name = "FillSpace2";
            this.FillSpace2.Size = new System.Drawing.Size(340, 23);
            this.FillSpace2.TabIndex = 3;
            // 
            // DestinationTextBox
            // 
            this.DestinationTextBox.Dock = System.Windows.Forms.DockStyle.Top;
            this.DestinationTextBox.Font = new System.Drawing.Font("Comic Sans MS", 11.1F, System.Drawing.FontStyle.Regular, System.Drawing.GraphicsUnit.Point, ((byte)(238)));
            this.DestinationTextBox.Location = new System.Drawing.Point(30, 84);
            this.DestinationTextBox.MaximumSize = new System.Drawing.Size(500, 500);
            this.DestinationTextBox.Name = "DestinationTextBox";
            this.DestinationTextBox.Size = new System.Drawing.Size(340, 59);
            this.DestinationTextBox.TabIndex = 0;
            this.DestinationTextBox.Tag = "Destination...";
            // 
            // FillSpace1
            // 
            this.FillSpace1.Dock = System.Windows.Forms.DockStyle.Top;
            this.FillSpace1.Location = new System.Drawing.Point(30, 61);
            this.FillSpace1.Name = "FillSpace1";
            this.FillSpace1.Size = new System.Drawing.Size(340, 23);
            this.FillSpace1.TabIndex = 1;
            // 
            // SearchTrip
            // 
            this.SearchTrip.Dock = System.Windows.Forms.DockStyle.Top;
            this.SearchTrip.Font = new System.Drawing.Font("Comic Sans MS", 12F, System.Drawing.FontStyle.Regular, System.Drawing.GraphicsUnit.Point, ((byte)(238)));
            this.SearchTrip.Location = new System.Drawing.Point(30, 0);
            this.SearchTrip.MaximumSize = new System.Drawing.Size(1000, 1000);
            this.SearchTrip.Name = "SearchTrip";
            this.SearchTrip.Size = new System.Drawing.Size(340, 61);
            this.SearchTrip.TabIndex = 0;
            this.SearchTrip.Text = " Search Trip";
            this.SearchTrip.TextAlign = System.Drawing.ContentAlignment.MiddleCenter;
            // 
            // FillSpaceLabel5
            // 
            this.FillSpaceLabel5.Dock = System.Windows.Forms.DockStyle.Right;
            this.FillSpaceLabel5.Location = new System.Drawing.Point(370, 0);
            this.FillSpaceLabel5.Name = "FillSpaceLabel5";
            this.FillSpaceLabel5.Size = new System.Drawing.Size(30, 1289);
            this.FillSpaceLabel5.TabIndex = 7;
            // 
            // FillSpaceLabel4
            // 
            this.FillSpaceLabel4.Dock = System.Windows.Forms.DockStyle.Left;
            this.FillSpaceLabel4.Location = new System.Drawing.Point(0, 0);
            this.FillSpaceLabel4.Name = "FillSpaceLabel4";
            this.FillSpaceLabel4.Size = new System.Drawing.Size(30, 1289);
            this.FillSpaceLabel4.TabIndex = 6;
            // 
            // RightPanel
            // 
            this.RightPanel.BackColor = System.Drawing.Color.FromArgb(((int)(((byte)(150)))), ((int)(((byte)(150)))), ((int)(((byte)(150)))));
            this.RightPanel.Dock = System.Windows.Forms.DockStyle.Right;
            this.RightPanel.Location = new System.Drawing.Point(2067, 100);
            this.RightPanel.MaximumSize = new System.Drawing.Size(150, 3000);
            this.RightPanel.Name = "RightPanel";
            this.RightPanel.RightToLeft = System.Windows.Forms.RightToLeft.Yes;
            this.RightPanel.Size = new System.Drawing.Size(150, 1289);
            this.RightPanel.TabIndex = 6;
            // 
            // BottomPanel
            // 
            this.BottomPanel.BackColor = System.Drawing.Color.FromArgb(((int)(((byte)(100)))), ((int)(((byte)(100)))), ((int)(((byte)(100)))));
            this.BottomPanel.Controls.Add(this.ExitButton);
            this.BottomPanel.Controls.Add(this.FillSpaceLabel3);
            this.BottomPanel.Controls.Add(this.FillSpaceLabel2);
            this.BottomPanel.Controls.Add(this.FillSpaceLabel1);
            this.BottomPanel.Dock = System.Windows.Forms.DockStyle.Bottom;
            this.BottomPanel.Font = new System.Drawing.Font("Comic Sans MS", 12F, System.Drawing.FontStyle.Regular, System.Drawing.GraphicsUnit.Point, ((byte)(238)));
            this.BottomPanel.Location = new System.Drawing.Point(0, 1389);
            this.BottomPanel.MaximumSize = new System.Drawing.Size(5000, 150);
            this.BottomPanel.Name = "BottomPanel";
            this.BottomPanel.Size = new System.Drawing.Size(2217, 150);
            this.BottomPanel.TabIndex = 7;
            // 
            // ExitButton
            // 
            this.ExitButton.Dock = System.Windows.Forms.DockStyle.Right;
            this.ExitButton.Font = new System.Drawing.Font("Comic Sans MS", 8.1F, System.Drawing.FontStyle.Regular, System.Drawing.GraphicsUnit.Point, ((byte)(238)));
            this.ExitButton.Location = new System.Drawing.Point(2082, 30);
            this.ExitButton.Margin = new System.Windows.Forms.Padding(20);
            this.ExitButton.Name = "ExitButton";
            this.ExitButton.Size = new System.Drawing.Size(120, 90);
            this.ExitButton.TabIndex = 0;
            this.ExitButton.Text = "Exit";
            this.ExitButton.UseVisualStyleBackColor = true;
            this.ExitButton.Click += new System.EventHandler(this.ExitButton_Click_1);
            // 
            // FillSpaceLabel3
            // 
            this.FillSpaceLabel3.Dock = System.Windows.Forms.DockStyle.Bottom;
            this.FillSpaceLabel3.Location = new System.Drawing.Point(0, 120);
            this.FillSpaceLabel3.Name = "FillSpaceLabel3";
            this.FillSpaceLabel3.Size = new System.Drawing.Size(2202, 30);
            this.FillSpaceLabel3.TabIndex = 3;
            this.FillSpaceLabel3.TextAlign = System.Drawing.ContentAlignment.MiddleRight;
            // 
            // FillSpaceLabel2
            // 
            this.FillSpaceLabel2.Dock = System.Windows.Forms.DockStyle.Top;
            this.FillSpaceLabel2.Location = new System.Drawing.Point(0, 0);
            this.FillSpaceLabel2.Name = "FillSpaceLabel2";
            this.FillSpaceLabel2.Size = new System.Drawing.Size(2202, 30);
            this.FillSpaceLabel2.TabIndex = 2;
            // 
            // FillSpaceLabel1
            // 
            this.FillSpaceLabel1.Dock = System.Windows.Forms.DockStyle.Right;
            this.FillSpaceLabel1.Location = new System.Drawing.Point(2202, 0);
            this.FillSpaceLabel1.Name = "FillSpaceLabel1";
            this.FillSpaceLabel1.Size = new System.Drawing.Size(15, 150);
            this.FillSpaceLabel1.TabIndex = 1;
            // 
            // CenterPanel
            // 
            this.CenterPanel.Controls.Add(this.FillSpace10);
            this.CenterPanel.Controls.Add(this.ReservedSeatsDataGridView);
            this.CenterPanel.Controls.Add(this.FillSpace9);
            this.CenterPanel.Controls.Add(this.TripsDataGridView);
            this.CenterPanel.Controls.Add(this.FillSpace8);
            this.CenterPanel.Dock = System.Windows.Forms.DockStyle.Fill;
            this.CenterPanel.Location = new System.Drawing.Point(0, 100);
            this.CenterPanel.Name = "CenterPanel";
            this.CenterPanel.Size = new System.Drawing.Size(2217, 1289);
            this.CenterPanel.TabIndex = 8;
            // 
            // FillSpace10
            // 
            this.FillSpace10.Dock = System.Windows.Forms.DockStyle.Left;
            this.FillSpace10.Location = new System.Drawing.Point(1937, 0);
            this.FillSpace10.MaximumSize = new System.Drawing.Size(16, 2000);
            this.FillSpace10.Name = "FillSpace10";
            this.FillSpace10.Size = new System.Drawing.Size(8, 1289);
            this.FillSpace10.TabIndex = 4;
            // 
            // ReservedSeatsDataGridView
            // 
            this.ReservedSeatsDataGridView.AutoSizeColumnsMode = System.Windows.Forms.DataGridViewAutoSizeColumnsMode.Fill;
            this.ReservedSeatsDataGridView.ColumnHeadersHeightSizeMode = System.Windows.Forms.DataGridViewColumnHeadersHeightSizeMode.AutoSize;
            this.ReservedSeatsDataGridView.Dock = System.Windows.Forms.DockStyle.Left;
            this.ReservedSeatsDataGridView.Location = new System.Drawing.Point(437, 0);
            this.ReservedSeatsDataGridView.Name = "ReservedSeatsDataGridView";
            this.ReservedSeatsDataGridView.RowTemplate.Height = 40;
            this.ReservedSeatsDataGridView.Size = new System.Drawing.Size(1500, 1289);
            this.ReservedSeatsDataGridView.TabIndex = 3;
            // 
            // FillSpace9
            // 
            this.FillSpace9.Dock = System.Windows.Forms.DockStyle.Left;
            this.FillSpace9.Location = new System.Drawing.Point(0, 0);
            this.FillSpace9.Name = "FillSpace9";
            this.FillSpace9.Size = new System.Drawing.Size(437, 1289);
            this.FillSpace9.TabIndex = 2;
            // 
            // TripsDataGridView
            // 
            this.TripsDataGridView.AutoSizeColumnsMode = System.Windows.Forms.DataGridViewAutoSizeColumnsMode.Fill;
            this.TripsDataGridView.ColumnHeadersHeightSizeMode = System.Windows.Forms.DataGridViewColumnHeadersHeightSizeMode.AutoSize;
            this.TripsDataGridView.Dock = System.Windows.Forms.DockStyle.Right;
            this.TripsDataGridView.Location = new System.Drawing.Point(43, 0);
            this.TripsDataGridView.MaximumSize = new System.Drawing.Size(2000, 2000);
            this.TripsDataGridView.Name = "TripsDataGridView";
            this.TripsDataGridView.RowTemplate.Height = 40;
            this.TripsDataGridView.Size = new System.Drawing.Size(2000, 1289);
            this.TripsDataGridView.TabIndex = 0;
            // 
            // FillSpace8
            // 
            this.FillSpace8.Dock = System.Windows.Forms.DockStyle.Right;
            this.FillSpace8.Location = new System.Drawing.Point(2043, 0);
            this.FillSpace8.Name = "FillSpace8";
            this.FillSpace8.Size = new System.Drawing.Size(174, 1289);
            this.FillSpace8.TabIndex = 1;
            // 
            // Form1
            // 
            this.AutoScaleDimensions = new System.Drawing.SizeF(16F, 31F);
            this.AutoScaleMode = System.Windows.Forms.AutoScaleMode.Font;
            this.BackColor = System.Drawing.SystemColors.Control;
            this.ClientSize = new System.Drawing.Size(2217, 1539);
            this.Controls.Add(this.LeftPanel);
            this.Controls.Add(this.RightPanel);
            this.Controls.Add(this.CenterPanel);
            this.Controls.Add(this.BottomPanel);
            this.Controls.Add(this.TitlePanel);
            this.Location = new System.Drawing.Point(15, 15);
            this.MaximumSize = new System.Drawing.Size(5000, 3000);
            this.Name = "Form1";
            this.TitlePanel.ResumeLayout(false);
            this.LeftPanel.ResumeLayout(false);
            this.LeftPanel.PerformLayout();
            this.BottomPanel.ResumeLayout(false);
            this.CenterPanel.ResumeLayout(false);
            ((System.ComponentModel.ISupportInitialize)(this.ReservedSeatsDataGridView)).EndInit();
            ((System.ComponentModel.ISupportInitialize)(this.TripsDataGridView)).EndInit();
            this.ResumeLayout(false);
            this.PerformLayout();
        }

        private System.Windows.Forms.TextBox SeatsTextBox;

        private System.Windows.Forms.Label FillSpace11;

        private System.Windows.Forms.DataGridView ReservedSeatsDataGridView;

        private System.Windows.Forms.Label FillSpace10;
        
        private System.Windows.Forms.Label FillSpace9;

        private System.Windows.Forms.Label FillSpace8;

        private System.Windows.Forms.DataGridView TripsDataGridView;

        private System.Windows.Forms.Label FillSpace7;

        private System.Windows.Forms.TextBox ClientNameTextBox;

        private System.Windows.Forms.Label FillSpace6;

        private System.Windows.Forms.Button ReserveButton;
        private System.Windows.Forms.Label FillSpace5;

        private System.Windows.Forms.Button SearchButton;

        private System.Windows.Forms.Label FillSpace4;

        private System.Windows.Forms.Label FillSpaceLabel5;

        private System.Windows.Forms.Label FillSpaceLabel4;

        private System.Windows.Forms.Label FillSpaceLabel2;

        private System.Windows.Forms.Label FillSpaceLabel3;

        private System.Windows.Forms.Label FillSpaceLabel1;

        private System.Windows.Forms.Button ExitButton;

        private System.Windows.Forms.TextBox DestinationTextBox;

        private System.Windows.Forms.Label FillSpace3;

        private System.Windows.Forms.TextBox HourTextBox;

        private System.Windows.Forms.Label FillSpace2;

        private System.Windows.Forms.DateTimePicker DatePicker;

        private System.Windows.Forms.Label FillSpace1;

        private System.Windows.Forms.TextBox DateTextBox;

        private System.Windows.Forms.Label SearchTrip;

        private System.Windows.Forms.Panel CenterPanel;

        private System.Windows.Forms.Panel BottomPanel;

        private System.Windows.Forms.Panel RightPanel;

        private System.Windows.Forms.Panel LeftPanel;

        private System.Windows.Forms.Panel TitlePanel;

        private System.Windows.Forms.Label TitleLabel;

        #endregion
    }
}