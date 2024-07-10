using System.ComponentModel;
using System.Windows.Forms;

namespace Lab3_SGBD.ui
{
    partial class Form1
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
            this.TitleLabel = new System.Windows.Forms.Label();
            this.ParentDataGridView = new System.Windows.Forms.DataGridView();
            this.BottomPanel = new System.Windows.Forms.Panel();
            this.LeftPanel = new System.Windows.Forms.Panel();
            this.RightPanel = new System.Windows.Forms.Panel();
            this.CRUDButtonsPanel = new System.Windows.Forms.Panel();
            this.UpdateButton = new System.Windows.Forms.Button();
            this.EmptySpaceButtons3 = new System.Windows.Forms.Label();
            this.DeleteButton = new System.Windows.Forms.Button();
            this.EmptySpaceButtons2 = new System.Windows.Forms.Label();
            this.AddButton = new System.Windows.Forms.Button();
            this.EmptySpaceButtonsBottom = new System.Windows.Forms.Label();
            this.EmptySpaceButtonsRight = new System.Windows.Forms.Label();
            this.EmptySpaceButtonsLeft = new System.Windows.Forms.Label();
            this.EmptySpaceButtons1 = new System.Windows.Forms.Label();
            this.FunctionalitiesTitlePanel = new System.Windows.Forms.Panel();
            this.FunctionalitiesTitleLabel = new System.Windows.Forms.Label();
            this.ParentPanel = new System.Windows.Forms.Panel();
            this.ParentTableTitleLabel = new System.Windows.Forms.Label();
            this.EmptySpaceParentTop = new System.Windows.Forms.Label();
            this.ChildPanel = new System.Windows.Forms.Panel();
            this.ChildDataGridView = new System.Windows.Forms.DataGridView();
            this.ChildTableTitleLabel = new System.Windows.Forms.Label();
            this.EmptySpaceChildTop = new System.Windows.Forms.Label();
            ((System.ComponentModel.ISupportInitialize)(this.ParentDataGridView)).BeginInit();
            this.RightPanel.SuspendLayout();
            this.CRUDButtonsPanel.SuspendLayout();
            this.FunctionalitiesTitlePanel.SuspendLayout();
            this.ParentPanel.SuspendLayout();
            this.ChildPanel.SuspendLayout();
            ((System.ComponentModel.ISupportInitialize)(this.ChildDataGridView)).BeginInit();
            this.SuspendLayout();
            // 
            // TitleLabel
            // 
            this.TitleLabel.BackColor = System.Drawing.SystemColors.ButtonShadow;
            this.TitleLabel.Dock = System.Windows.Forms.DockStyle.Top;
            this.TitleLabel.Font = new System.Drawing.Font("Comic Sans MS", 18F, System.Drawing.FontStyle.Regular, System.Drawing.GraphicsUnit.Point, ((byte)(238)));
            this.TitleLabel.Location = new System.Drawing.Point(0, 0);
            this.TitleLabel.MaximumSize = new System.Drawing.Size(4500, 2500);
            this.TitleLabel.Name = "TitleLabel";
            this.TitleLabel.Size = new System.Drawing.Size(2160, 100);
            this.TitleLabel.TabIndex = 0;
            this.TitleLabel.Text = "Aplicatie Filme";
            this.TitleLabel.TextAlign = System.Drawing.ContentAlignment.MiddleCenter;
            // 
            // ParentDataGridView
            // 
            this.ParentDataGridView.AutoSizeColumnsMode = System.Windows.Forms.DataGridViewAutoSizeColumnsMode.Fill;
            this.ParentDataGridView.ColumnHeadersHeightSizeMode = System.Windows.Forms.DataGridViewColumnHeadersHeightSizeMode.AutoSize;
            this.ParentDataGridView.Dock = System.Windows.Forms.DockStyle.Fill;
            this.ParentDataGridView.Location = new System.Drawing.Point(0, 125);
            this.ParentDataGridView.Name = "ParentDataGridView";
            this.ParentDataGridView.RowTemplate.Height = 40;
            this.ParentDataGridView.Size = new System.Drawing.Size(1610, 375);
            this.ParentDataGridView.TabIndex = 1;
            // 
            // BottomPanel
            // 
            this.BottomPanel.AutoScroll = true;
            this.BottomPanel.BackColor = System.Drawing.SystemColors.ButtonShadow;
            this.BottomPanel.Dock = System.Windows.Forms.DockStyle.Bottom;
            this.BottomPanel.Location = new System.Drawing.Point(0, 1069);
            this.BottomPanel.MaximumSize = new System.Drawing.Size(4500, 2500);
            this.BottomPanel.Name = "BottomPanel";
            this.BottomPanel.Size = new System.Drawing.Size(2160, 100);
            this.BottomPanel.TabIndex = 2;
            // 
            // LeftPanel
            // 
            this.LeftPanel.AutoScroll = true;
            this.LeftPanel.BackColor = System.Drawing.SystemColors.ScrollBar;
            this.LeftPanel.Dock = System.Windows.Forms.DockStyle.Left;
            this.LeftPanel.Location = new System.Drawing.Point(0, 100);
            this.LeftPanel.MaximumSize = new System.Drawing.Size(50, 2500);
            this.LeftPanel.Name = "LeftPanel";
            this.LeftPanel.Size = new System.Drawing.Size(50, 969);
            this.LeftPanel.TabIndex = 3;
            // 
            // RightPanel
            // 
            this.RightPanel.BackColor = System.Drawing.SystemColors.ScrollBar;
            this.RightPanel.Controls.Add(this.CRUDButtonsPanel);
            this.RightPanel.Controls.Add(this.FunctionalitiesTitlePanel);
            this.RightPanel.Dock = System.Windows.Forms.DockStyle.Right;
            this.RightPanel.Font = new System.Drawing.Font("Comic Sans MS", 12F, System.Drawing.FontStyle.Regular, System.Drawing.GraphicsUnit.Point, ((byte)(238)));
            this.RightPanel.Location = new System.Drawing.Point(1660, 100);
            this.RightPanel.MaximumSize = new System.Drawing.Size(500, 2500);
            this.RightPanel.Name = "RightPanel";
            this.RightPanel.Size = new System.Drawing.Size(500, 969);
            this.RightPanel.TabIndex = 4;
            // 
            // CRUDButtonsPanel
            // 
            this.CRUDButtonsPanel.Controls.Add(this.UpdateButton);
            this.CRUDButtonsPanel.Controls.Add(this.EmptySpaceButtons3);
            this.CRUDButtonsPanel.Controls.Add(this.DeleteButton);
            this.CRUDButtonsPanel.Controls.Add(this.EmptySpaceButtons2);
            this.CRUDButtonsPanel.Controls.Add(this.AddButton);
            this.CRUDButtonsPanel.Controls.Add(this.EmptySpaceButtonsBottom);
            this.CRUDButtonsPanel.Controls.Add(this.EmptySpaceButtonsRight);
            this.CRUDButtonsPanel.Controls.Add(this.EmptySpaceButtonsLeft);
            this.CRUDButtonsPanel.Controls.Add(this.EmptySpaceButtons1);
            this.CRUDButtonsPanel.Dock = System.Windows.Forms.DockStyle.Fill;
            this.CRUDButtonsPanel.Location = new System.Drawing.Point(0, 500);
            this.CRUDButtonsPanel.Name = "CRUDButtonsPanel";
            this.CRUDButtonsPanel.Size = new System.Drawing.Size(500, 469);
            this.CRUDButtonsPanel.TabIndex = 1;
            // 
            // UpdateButton
            // 
            this.UpdateButton.Dock = System.Windows.Forms.DockStyle.Top;
            this.UpdateButton.Location = new System.Drawing.Point(100, 300);
            this.UpdateButton.Name = "UpdateButton";
            this.UpdateButton.Size = new System.Drawing.Size(300, 75);
            this.UpdateButton.TabIndex = 8;
            this.UpdateButton.Text = "Update";
            this.UpdateButton.UseVisualStyleBackColor = true;
            // 
            // EmptySpaceButtons3
            // 
            this.EmptySpaceButtons3.Dock = System.Windows.Forms.DockStyle.Top;
            this.EmptySpaceButtons3.Location = new System.Drawing.Point(100, 250);
            this.EmptySpaceButtons3.Name = "EmptySpaceButtons3";
            this.EmptySpaceButtons3.Size = new System.Drawing.Size(300, 50);
            this.EmptySpaceButtons3.TabIndex = 7;
            // 
            // DeleteButton
            // 
            this.DeleteButton.Dock = System.Windows.Forms.DockStyle.Top;
            this.DeleteButton.Font = new System.Drawing.Font("Comic Sans MS", 12F, System.Drawing.FontStyle.Regular, System.Drawing.GraphicsUnit.Point, ((byte)(238)));
            this.DeleteButton.Location = new System.Drawing.Point(100, 175);
            this.DeleteButton.Name = "DeleteButton";
            this.DeleteButton.Size = new System.Drawing.Size(300, 75);
            this.DeleteButton.TabIndex = 6;
            this.DeleteButton.Text = "Delete";
            this.DeleteButton.UseVisualStyleBackColor = true;
            // 
            // EmptySpaceButtons2
            // 
            this.EmptySpaceButtons2.Dock = System.Windows.Forms.DockStyle.Top;
            this.EmptySpaceButtons2.Location = new System.Drawing.Point(100, 125);
            this.EmptySpaceButtons2.Name = "EmptySpaceButtons2";
            this.EmptySpaceButtons2.Size = new System.Drawing.Size(300, 50);
            this.EmptySpaceButtons2.TabIndex = 5;
            // 
            // AddButton
            // 
            this.AddButton.Dock = System.Windows.Forms.DockStyle.Top;
            this.AddButton.Font = new System.Drawing.Font("Comic Sans MS", 12F, System.Drawing.FontStyle.Regular, System.Drawing.GraphicsUnit.Point, ((byte)(238)));
            this.AddButton.Location = new System.Drawing.Point(100, 50);
            this.AddButton.Name = "AddButton";
            this.AddButton.Size = new System.Drawing.Size(300, 75);
            this.AddButton.TabIndex = 4;
            this.AddButton.Text = "Add";
            this.AddButton.UseVisualStyleBackColor = true;
            // 
            // EmptySpaceButtonsBottom
            // 
            this.EmptySpaceButtonsBottom.Dock = System.Windows.Forms.DockStyle.Bottom;
            this.EmptySpaceButtonsBottom.Location = new System.Drawing.Point(100, 419);
            this.EmptySpaceButtonsBottom.Name = "EmptySpaceButtonsBottom";
            this.EmptySpaceButtonsBottom.Size = new System.Drawing.Size(300, 50);
            this.EmptySpaceButtonsBottom.TabIndex = 3;
            // 
            // EmptySpaceButtonsRight
            // 
            this.EmptySpaceButtonsRight.Dock = System.Windows.Forms.DockStyle.Right;
            this.EmptySpaceButtonsRight.Location = new System.Drawing.Point(400, 50);
            this.EmptySpaceButtonsRight.Name = "EmptySpaceButtonsRight";
            this.EmptySpaceButtonsRight.Size = new System.Drawing.Size(100, 419);
            this.EmptySpaceButtonsRight.TabIndex = 2;
            // 
            // EmptySpaceButtonsLeft
            // 
            this.EmptySpaceButtonsLeft.Dock = System.Windows.Forms.DockStyle.Left;
            this.EmptySpaceButtonsLeft.Location = new System.Drawing.Point(0, 50);
            this.EmptySpaceButtonsLeft.Name = "EmptySpaceButtonsLeft";
            this.EmptySpaceButtonsLeft.Size = new System.Drawing.Size(100, 419);
            this.EmptySpaceButtonsLeft.TabIndex = 1;
            // 
            // EmptySpaceButtons1
            // 
            this.EmptySpaceButtons1.Dock = System.Windows.Forms.DockStyle.Top;
            this.EmptySpaceButtons1.Location = new System.Drawing.Point(0, 0);
            this.EmptySpaceButtons1.Name = "EmptySpaceButtons1";
            this.EmptySpaceButtons1.Size = new System.Drawing.Size(500, 50);
            this.EmptySpaceButtons1.TabIndex = 0;
            // 
            // FunctionalitiesTitlePanel
            // 
            this.FunctionalitiesTitlePanel.Controls.Add(this.FunctionalitiesTitleLabel);
            this.FunctionalitiesTitlePanel.Dock = System.Windows.Forms.DockStyle.Top;
            this.FunctionalitiesTitlePanel.Location = new System.Drawing.Point(0, 0);
            this.FunctionalitiesTitlePanel.Name = "FunctionalitiesTitlePanel";
            this.FunctionalitiesTitlePanel.Size = new System.Drawing.Size(500, 500);
            this.FunctionalitiesTitlePanel.TabIndex = 0;
            // 
            // FunctionalitiesTitleLabel
            // 
            this.FunctionalitiesTitleLabel.Dock = System.Windows.Forms.DockStyle.Fill;
            this.FunctionalitiesTitleLabel.Font = new System.Drawing.Font("Comic Sans MS", 18F, System.Drawing.FontStyle.Regular, System.Drawing.GraphicsUnit.Point, ((byte)(238)));
            this.FunctionalitiesTitleLabel.Location = new System.Drawing.Point(0, 0);
            this.FunctionalitiesTitleLabel.Name = "FunctionalitiesTitleLabel";
            this.FunctionalitiesTitleLabel.Size = new System.Drawing.Size(500, 500);
            this.FunctionalitiesTitleLabel.TabIndex = 0;
            this.FunctionalitiesTitleLabel.Text = "Functionalities";
            this.FunctionalitiesTitleLabel.TextAlign = System.Drawing.ContentAlignment.MiddleCenter;
            // 
            // ParentPanel
            // 
            this.ParentPanel.Controls.Add(this.ParentDataGridView);
            this.ParentPanel.Controls.Add(this.ParentTableTitleLabel);
            this.ParentPanel.Controls.Add(this.EmptySpaceParentTop);
            this.ParentPanel.Dock = System.Windows.Forms.DockStyle.Top;
            this.ParentPanel.Font = new System.Drawing.Font("Comic Sans MS", 12F, System.Drawing.FontStyle.Regular, System.Drawing.GraphicsUnit.Point, ((byte)(238)));
            this.ParentPanel.Location = new System.Drawing.Point(50, 100);
            this.ParentPanel.MaximumSize = new System.Drawing.Size(4500, 800);
            this.ParentPanel.Name = "ParentPanel";
            this.ParentPanel.Size = new System.Drawing.Size(1610, 800);
            this.ParentPanel.TabIndex = 5;
            // 
            // ParentTableTitleLabel
            // 
            this.ParentTableTitleLabel.BackColor = System.Drawing.Color.Gainsboro;
            this.ParentTableTitleLabel.Dock = System.Windows.Forms.DockStyle.Top;
            this.ParentTableTitleLabel.Location = new System.Drawing.Point(0, 50);
            this.ParentTableTitleLabel.MaximumSize = new System.Drawing.Size(4500, 75);
            this.ParentTableTitleLabel.Name = "ParentTableTitleLabel";
            this.ParentTableTitleLabel.Size = new System.Drawing.Size(1610, 75);
            this.ParentTableTitleLabel.TabIndex = 0;
            this.ParentTableTitleLabel.Text = "Parent Table";
            // 
            // EmptySpaceParentTop
            // 
            this.EmptySpaceParentTop.BackColor = System.Drawing.Color.Gray;
            this.EmptySpaceParentTop.Dock = System.Windows.Forms.DockStyle.Top;
            this.EmptySpaceParentTop.Location = new System.Drawing.Point(0, 0);
            this.EmptySpaceParentTop.MaximumSize = new System.Drawing.Size(4500, 50);
            this.EmptySpaceParentTop.Name = "EmptySpaceParentTop";
            this.EmptySpaceParentTop.Size = new System.Drawing.Size(1610, 50);
            this.EmptySpaceParentTop.TabIndex = 0;
            // 
            // ChildPanel
            // 
            this.ChildPanel.Controls.Add(this.ChildDataGridView);
            this.ChildPanel.Controls.Add(this.ChildTableTitleLabel);
            this.ChildPanel.Controls.Add(this.EmptySpaceChildTop);
            this.ChildPanel.Dock = System.Windows.Forms.DockStyle.Fill;
            this.ChildPanel.Font = new System.Drawing.Font("Comic Sans MS", 12F, System.Drawing.FontStyle.Regular, System.Drawing.GraphicsUnit.Point, ((byte)(238)));
            this.ChildPanel.Location = new System.Drawing.Point(50, 600);
            this.ChildPanel.Name = "ChildPanel";
            this.ChildPanel.Size = new System.Drawing.Size(1610, 469);
            this.ChildPanel.TabIndex = 6;
            // 
            // ChildDataGridView
            // 
            this.ChildDataGridView.AutoSizeColumnsMode = System.Windows.Forms.DataGridViewAutoSizeColumnsMode.Fill;
            this.ChildDataGridView.ColumnHeadersHeightSizeMode = System.Windows.Forms.DataGridViewColumnHeadersHeightSizeMode.AutoSize;
            this.ChildDataGridView.Dock = System.Windows.Forms.DockStyle.Fill;
            this.ChildDataGridView.Location = new System.Drawing.Point(0, 125);
            this.ChildDataGridView.Name = "ChildDataGridView";
            this.ChildDataGridView.RowTemplate.Height = 40;
            this.ChildDataGridView.Size = new System.Drawing.Size(1610, 344);
            this.ChildDataGridView.TabIndex = 2;
            // 
            // ChildTableTitleLabel
            // 
            this.ChildTableTitleLabel.BackColor = System.Drawing.Color.Gainsboro;
            this.ChildTableTitleLabel.Dock = System.Windows.Forms.DockStyle.Top;
            this.ChildTableTitleLabel.Location = new System.Drawing.Point(0, 50);
            this.ChildTableTitleLabel.Name = "ChildTableTitleLabel";
            this.ChildTableTitleLabel.Size = new System.Drawing.Size(1610, 75);
            this.ChildTableTitleLabel.TabIndex = 1;
            this.ChildTableTitleLabel.Text = "Child Table";
            // 
            // EmptySpaceChildTop
            // 
            this.EmptySpaceChildTop.BackColor = System.Drawing.Color.Gray;
            this.EmptySpaceChildTop.Dock = System.Windows.Forms.DockStyle.Top;
            this.EmptySpaceChildTop.Location = new System.Drawing.Point(0, 0);
            this.EmptySpaceChildTop.MaximumSize = new System.Drawing.Size(4500, 50);
            this.EmptySpaceChildTop.Name = "EmptySpaceChildTop";
            this.EmptySpaceChildTop.Size = new System.Drawing.Size(1610, 50);
            this.EmptySpaceChildTop.TabIndex = 0;
            // 
            // Form1
            // 
            this.AutoScaleDimensions = new System.Drawing.SizeF(16F, 31F);
            this.AutoScaleMode = System.Windows.Forms.AutoScaleMode.Font;
            this.ClientSize = new System.Drawing.Size(2160, 1169);
            this.Controls.Add(this.ChildPanel);
            this.Controls.Add(this.ParentPanel);
            this.Controls.Add(this.RightPanel);
            this.Controls.Add(this.LeftPanel);
            this.Controls.Add(this.BottomPanel);
            this.Controls.Add(this.TitleLabel);
            this.Name = "Form1";
            this.Text = "Form";
            ((System.ComponentModel.ISupportInitialize)(this.ParentDataGridView)).EndInit();
            this.RightPanel.ResumeLayout(false);
            this.CRUDButtonsPanel.ResumeLayout(false);
            this.FunctionalitiesTitlePanel.ResumeLayout(false);
            this.ParentPanel.ResumeLayout(false);
            this.ChildPanel.ResumeLayout(false);
            ((System.ComponentModel.ISupportInitialize)(this.ChildDataGridView)).EndInit();
            this.WindowState = FormWindowState.Maximized;
            this.Load += new System.EventHandler(this.UI_Load);
            this.ResumeLayout(false);
            this.PerformLayout();
        }

        private System.Windows.Forms.Button UpdateButton;

        private System.Windows.Forms.Label EmptySpaceButtons3;

        private System.Windows.Forms.Button DeleteButton;

        private System.Windows.Forms.Label EmptySpaceButtons2;

        private System.Windows.Forms.Button AddButton;

        private System.Windows.Forms.Label EmptySpaceButtonsBottom;
        
        private System.Windows.Forms.Label EmptySpaceButtonsRight;

        private System.Windows.Forms.Label EmptySpaceButtonsLeft;

        private System.Windows.Forms.Label EmptySpaceButtons1;

        private System.Windows.Forms.Panel CRUDButtonsPanel;

        private System.Windows.Forms.Label FunctionalitiesTitleLabel;

        private System.Windows.Forms.Panel FunctionalitiesTitlePanel;

        private System.Windows.Forms.DataGridView ChildDataGridView;

        private System.Windows.Forms.Label ChildTableTitleLabel;

        private System.Windows.Forms.Label EmptySpaceChildTop;

        private System.Windows.Forms.Panel ChildPanel;

        private System.Windows.Forms.Label EmptySpaceParentTop;

        private System.Windows.Forms.Label ParentTableTitleLabel;
        
        private System.Windows.Forms.Panel ParentPanel;

        private System.Windows.Forms.Panel RightPanel;

        private System.Windows.Forms.Panel LeftPanel;

        private System.Windows.Forms.Panel BottomPanel;

        private System.Windows.Forms.DataGridView ParentDataGridView;

        private System.Windows.Forms.Label TitleLabel;

        #endregion
    }
}