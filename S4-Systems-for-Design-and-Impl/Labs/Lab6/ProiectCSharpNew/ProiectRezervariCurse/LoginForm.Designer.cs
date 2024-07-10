using System.ComponentModel;

namespace ProiectRezervariCurse
{
    partial class LoginForm
    {
        /// <summary>
        /// Required designer variable.
        /// </summary>
        private IContainer components = null;

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
            this.TitlePanel = new System.Windows.Forms.Panel();
            this.TitleLabel = new System.Windows.Forms.Label();
            this.BottomPanel = new System.Windows.Forms.Panel();
            this.LeftPanel = new System.Windows.Forms.Panel();
            this.RightPanel = new System.Windows.Forms.Panel();
            this.CenterPanel = new System.Windows.Forms.Panel();
            this.ExitButtonPanel = new System.Windows.Forms.Panel();
            this.ExitButton = new System.Windows.Forms.Button();
            this.LoginButtonPanel = new System.Windows.Forms.Panel();
            this.LoginButton = new System.Windows.Forms.Button();
            this.PasswordPanel = new System.Windows.Forms.Panel();
            this.PasswordTextBox = new System.Windows.Forms.TextBox();
            this.PasswordLabel = new System.Windows.Forms.Label();
            this.panel1 = new System.Windows.Forms.Panel();
            this.UsernameTextBox = new System.Windows.Forms.TextBox();
            this.UsernameLabel = new System.Windows.Forms.Label();
            this.CenterTopPanel = new System.Windows.Forms.Panel();
            this.TitlePanel.SuspendLayout();
            this.CenterPanel.SuspendLayout();
            this.ExitButtonPanel.SuspendLayout();
            this.LoginButtonPanel.SuspendLayout();
            this.PasswordPanel.SuspendLayout();
            this.panel1.SuspendLayout();
            this.SuspendLayout();
            // 
            // TitlePanel
            // 
            this.TitlePanel.BackColor = System.Drawing.Color.FromArgb(((int)(((byte)(100)))), ((int)(((byte)(100)))), ((int)(((byte)(100)))));
            this.TitlePanel.Controls.Add(this.TitleLabel);
            this.TitlePanel.Dock = System.Windows.Forms.DockStyle.Top;
            this.TitlePanel.Font = new System.Drawing.Font("Comic Sans MS", 8.1F, System.Drawing.FontStyle.Regular, System.Drawing.GraphicsUnit.Point, ((byte)(238)));
            this.TitlePanel.Location = new System.Drawing.Point(0, 0);
            this.TitlePanel.Name = "TitlePanel";
            this.TitlePanel.Size = new System.Drawing.Size(2231, 100);
            this.TitlePanel.TabIndex = 1;
            // 
            // TitleLabel
            // 
            this.TitleLabel.BackColor = System.Drawing.Color.FromArgb(((int)(((byte)(100)))), ((int)(((byte)(100)))), ((int)(((byte)(100)))));
            this.TitleLabel.Dock = System.Windows.Forms.DockStyle.Fill;
            this.TitleLabel.Font = new System.Drawing.Font("Comic Sans MS", 15.9F, System.Drawing.FontStyle.Regular, System.Drawing.GraphicsUnit.Point, ((byte)(238)));
            this.TitleLabel.Location = new System.Drawing.Point(0, 0);
            this.TitleLabel.Name = "TitleLabel";
            this.TitleLabel.Size = new System.Drawing.Size(2231, 100);
            this.TitleLabel.TabIndex = 2;
            this.TitleLabel.Text = "Login";
            this.TitleLabel.TextAlign = System.Drawing.ContentAlignment.MiddleCenter;
            // 
            // BottomPanel
            // 
            this.BottomPanel.BackColor = System.Drawing.Color.FromArgb(((int)(((byte)(100)))), ((int)(((byte)(100)))), ((int)(((byte)(100)))));
            this.BottomPanel.Dock = System.Windows.Forms.DockStyle.Bottom;
            this.BottomPanel.Location = new System.Drawing.Point(0, 1222);
            this.BottomPanel.Name = "BottomPanel";
            this.BottomPanel.Size = new System.Drawing.Size(2231, 100);
            this.BottomPanel.TabIndex = 2;
            // 
            // LeftPanel
            // 
            this.LeftPanel.BackColor = System.Drawing.Color.FromArgb(((int)(((byte)(150)))), ((int)(((byte)(150)))), ((int)(((byte)(150)))));
            this.LeftPanel.Dock = System.Windows.Forms.DockStyle.Left;
            this.LeftPanel.Location = new System.Drawing.Point(0, 100);
            this.LeftPanel.MaximumSize = new System.Drawing.Size(200, 2000);
            this.LeftPanel.Name = "LeftPanel";
            this.LeftPanel.Size = new System.Drawing.Size(200, 1122);
            this.LeftPanel.TabIndex = 3;
            // 
            // RightPanel
            // 
            this.RightPanel.BackColor = System.Drawing.Color.FromArgb(((int)(((byte)(150)))), ((int)(((byte)(150)))), ((int)(((byte)(150)))));
            this.RightPanel.Dock = System.Windows.Forms.DockStyle.Right;
            this.RightPanel.Location = new System.Drawing.Point(2031, 100);
            this.RightPanel.MaximumSize = new System.Drawing.Size(200, 2000);
            this.RightPanel.Name = "RightPanel";
            this.RightPanel.Size = new System.Drawing.Size(200, 1122);
            this.RightPanel.TabIndex = 4;
            // 
            // CenterPanel
            // 
            this.CenterPanel.Controls.Add(this.ExitButtonPanel);
            this.CenterPanel.Controls.Add(this.LoginButtonPanel);
            this.CenterPanel.Controls.Add(this.PasswordPanel);
            this.CenterPanel.Controls.Add(this.panel1);
            this.CenterPanel.Controls.Add(this.CenterTopPanel);
            this.CenterPanel.Dock = System.Windows.Forms.DockStyle.Fill;
            this.CenterPanel.Location = new System.Drawing.Point(200, 100);
            this.CenterPanel.Name = "CenterPanel";
            this.CenterPanel.Size = new System.Drawing.Size(1831, 1122);
            this.CenterPanel.TabIndex = 5;
            // 
            // ExitButtonPanel
            // 
            this.ExitButtonPanel.Controls.Add(this.ExitButton);
            this.ExitButtonPanel.Dock = System.Windows.Forms.DockStyle.Fill;
            this.ExitButtonPanel.Location = new System.Drawing.Point(0, 487);
            this.ExitButtonPanel.Name = "ExitButtonPanel";
            this.ExitButtonPanel.Size = new System.Drawing.Size(1831, 635);
            this.ExitButtonPanel.TabIndex = 4;
            // 
            // ExitButton
            // 
            this.ExitButton.Font = new System.Drawing.Font("Comic Sans MS", 12F, System.Drawing.FontStyle.Regular, System.Drawing.GraphicsUnit.Point, ((byte)(238)));
            this.ExitButton.Location = new System.Drawing.Point(483, 15);
            this.ExitButton.Name = "ExitButton";
            this.ExitButton.Size = new System.Drawing.Size(189, 83);
            this.ExitButton.TabIndex = 0;
            this.ExitButton.Text = "Exit";
            this.ExitButton.UseVisualStyleBackColor = true;
            this.ExitButton.Click += new System.EventHandler(this.ExitButton_Click);
            // 
            // LoginButtonPanel
            // 
            this.LoginButtonPanel.Controls.Add(this.LoginButton);
            this.LoginButtonPanel.Dock = System.Windows.Forms.DockStyle.Top;
            this.LoginButtonPanel.Location = new System.Drawing.Point(0, 400);
            this.LoginButtonPanel.Name = "LoginButtonPanel";
            this.LoginButtonPanel.Size = new System.Drawing.Size(1831, 87);
            this.LoginButtonPanel.TabIndex = 3;
            // 
            // LoginButton
            // 
            this.LoginButton.Font = new System.Drawing.Font("Comic Sans MS", 12F, System.Drawing.FontStyle.Regular, System.Drawing.GraphicsUnit.Point, ((byte)(238)));
            this.LoginButton.Location = new System.Drawing.Point(483, 3);
            this.LoginButton.Name = "LoginButton";
            this.LoginButton.Size = new System.Drawing.Size(189, 75);
            this.LoginButton.TabIndex = 0;
            this.LoginButton.Text = "Login";
            this.LoginButton.UseVisualStyleBackColor = true;
            this.LoginButton.Click += new System.EventHandler(this.LoginButton_Click);
            // 
            // PasswordPanel
            // 
            this.PasswordPanel.Controls.Add(this.PasswordTextBox);
            this.PasswordPanel.Controls.Add(this.PasswordLabel);
            this.PasswordPanel.Dock = System.Windows.Forms.DockStyle.Top;
            this.PasswordPanel.Location = new System.Drawing.Point(0, 250);
            this.PasswordPanel.Name = "PasswordPanel";
            this.PasswordPanel.Size = new System.Drawing.Size(1831, 150);
            this.PasswordPanel.TabIndex = 2;
            // 
            // PasswordTextBox
            // 
            this.PasswordTextBox.Location = new System.Drawing.Point(769, 64);
            this.PasswordTextBox.Name = "PasswordTextBox";
            this.PasswordTextBox.PasswordChar = '*';
            this.PasswordTextBox.Size = new System.Drawing.Size(261, 38);
            this.PasswordTextBox.TabIndex = 1;
            // 
            // PasswordLabel
            // 
            this.PasswordLabel.Font = new System.Drawing.Font("Comic Sans MS", 12F, System.Drawing.FontStyle.Regular, System.Drawing.GraphicsUnit.Point, ((byte)(238)));
            this.PasswordLabel.Location = new System.Drawing.Point(212, 49);
            this.PasswordLabel.Name = "PasswordLabel";
            this.PasswordLabel.Size = new System.Drawing.Size(218, 61);
            this.PasswordLabel.TabIndex = 0;
            this.PasswordLabel.Text = "Password: ";
            // 
            // panel1
            // 
            this.panel1.Controls.Add(this.UsernameTextBox);
            this.panel1.Controls.Add(this.UsernameLabel);
            this.panel1.Dock = System.Windows.Forms.DockStyle.Top;
            this.panel1.Location = new System.Drawing.Point(0, 100);
            this.panel1.Name = "panel1";
            this.panel1.Size = new System.Drawing.Size(1831, 150);
            this.panel1.TabIndex = 1;
            // 
            // UsernameTextBox
            // 
            this.UsernameTextBox.Location = new System.Drawing.Point(769, 38);
            this.UsernameTextBox.Name = "UsernameTextBox";
            this.UsernameTextBox.Size = new System.Drawing.Size(261, 38);
            this.UsernameTextBox.TabIndex = 1;
            // 
            // UsernameLabel
            // 
            this.UsernameLabel.Font = new System.Drawing.Font("Comic Sans MS", 12F, System.Drawing.FontStyle.Regular, System.Drawing.GraphicsUnit.Point, ((byte)(238)));
            this.UsernameLabel.Location = new System.Drawing.Point(174, 18);
            this.UsernameLabel.Name = "UsernameLabel";
            this.UsernameLabel.Size = new System.Drawing.Size(300, 67);
            this.UsernameLabel.TabIndex = 0;
            this.UsernameLabel.Text = "Username: ";
            this.UsernameLabel.TextAlign = System.Drawing.ContentAlignment.MiddleCenter;
            // 
            // CenterTopPanel
            // 
            this.CenterTopPanel.Dock = System.Windows.Forms.DockStyle.Top;
            this.CenterTopPanel.Location = new System.Drawing.Point(0, 0);
            this.CenterTopPanel.Name = "CenterTopPanel";
            this.CenterTopPanel.Size = new System.Drawing.Size(1831, 100);
            this.CenterTopPanel.TabIndex = 0;
            // 
            // LoginForm
            // 
            this.AutoScaleDimensions = new System.Drawing.SizeF(16F, 31F);
            this.AutoScaleMode = System.Windows.Forms.AutoScaleMode.Font;
            this.ClientSize = new System.Drawing.Size(2231, 1322);
            this.Controls.Add(this.CenterPanel);
            this.Controls.Add(this.RightPanel);
            this.Controls.Add(this.LeftPanel);
            this.Controls.Add(this.BottomPanel);
            this.Controls.Add(this.TitlePanel);
            this.Name = "LoginForm";
            this.Text = "LoginForm";
            this.TitlePanel.ResumeLayout(false);
            this.CenterPanel.ResumeLayout(false);
            this.ExitButtonPanel.ResumeLayout(false);
            this.LoginButtonPanel.ResumeLayout(false);
            this.PasswordPanel.ResumeLayout(false);
            this.PasswordPanel.PerformLayout();
            this.panel1.ResumeLayout(false);
            this.panel1.PerformLayout();
            this.ResumeLayout(false);
        }

        private System.Windows.Forms.TextBox PasswordTextBox;

        private System.Windows.Forms.Button ExitButton;

        private System.Windows.Forms.Panel ExitButtonPanel;

        private System.Windows.Forms.Button LoginButton;

        private System.Windows.Forms.Panel LoginButtonPanel;

        private System.Windows.Forms.Label PasswordLabel;

        private System.Windows.Forms.Panel PasswordPanel;

        private System.Windows.Forms.TextBox UsernameTextBox;

        private System.Windows.Forms.Label UsernameLabel;

        private System.Windows.Forms.Panel panel1;

        private System.Windows.Forms.Panel CenterTopPanel;

        private System.Windows.Forms.Panel CenterPanel;

        private System.Windows.Forms.Panel LeftPanel;

        private System.Windows.Forms.Panel RightPanel;

        private System.Windows.Forms.Panel BottomPanel;

        private System.Windows.Forms.Label TitleLabel;

        private System.Windows.Forms.Panel TitlePanel;

        #endregion
    }
}