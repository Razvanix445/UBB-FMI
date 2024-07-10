using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;
using System.Windows.Forms;

namespace Lab3_SGBD.validator
{
    public class Validator
    {
        /* Validate the inputs for a new film */
        public bool ValidateInputs(string title, DateTime releaseDate, string description, string contentRating, float userRating, float budget, float boxOffice)
        {
            // Validate title
            if (string.IsNullOrWhiteSpace(title))
            {
                MessageBox.Show("Please enter a title.", "Error", MessageBoxButtons.OK, MessageBoxIcon.Error);
                return false;
            }

            // Validate release date
            if (releaseDate == DateTime.MinValue)
            {
                MessageBox.Show("Please select a valid release date.", "Error", MessageBoxButtons.OK, MessageBoxIcon.Error);
                return false;
            }

            // Validate description
            if (string.IsNullOrWhiteSpace(description))
            {
                MessageBox.Show("Please enter a description.", "Error", MessageBoxButtons.OK, MessageBoxIcon.Error);
                return false;
            }

            // Validate content rating
            if (string.IsNullOrWhiteSpace(contentRating))
            {
                MessageBox.Show("Please enter a content rating.", "Error", MessageBoxButtons.OK, MessageBoxIcon.Error);
                return false;
            }

            // Validate user rating
            if (userRating < 0 || userRating > 10)
            {
                MessageBox.Show("Please enter a valid user rating between 0 and 10.", "Error", MessageBoxButtons.OK, MessageBoxIcon.Error);
                return false;
            }

            // Validate budget
            if (budget < 0)
            {
                MessageBox.Show("Please enter a valid budget greater than or equal to 0.", "Error", MessageBoxButtons.OK, MessageBoxIcon.Error);
                return false;
            }

            // Validate box office
            if (boxOffice < 0)
            {
                MessageBox.Show("Please enter a valid box office value greater than or equal to 0.", "Error", MessageBoxButtons.OK, MessageBoxIcon.Error);
                return false;
            }

            // All inputs are valid
            return true;
        }
    }
}
