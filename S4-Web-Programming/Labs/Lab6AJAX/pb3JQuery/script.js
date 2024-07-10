$(document).ready(function() {
    var users;

    $.get('fetchUsers.php', function(data) {
        users = JSON.parse(data);
        $.each(users, function(i, user) {
            var listItem = $('<li>').text(user.id + ' - ' + user.nume);
            $('#userList').append(listItem);

            listItem.click(function() {
                if (!$('#saveButton').prop('disabled')) {
                    var saveChanges = confirm('You have unsaved changes. Do you want to save them?');
                    if (saveChanges) {
                        $('#saveButton').click();
                    }
                }

                $('#id').val(user.id);
                $('#nume').val(user.nume);
                $('#prenume').val(user.prenume);
                $('#telefon').val(user.telefon);
                $('#email').val(user.email);

                $('#saveButton').prop('disabled', true);
            });
        });
    });

    $('#formular input').on('input', function() {
        $('#saveButton').prop('disabled', false);
    });

    $('#saveButton').click(function() {
        var id = $('#id').val();
        var nume = $('#nume').val();
        var prenume = $('#prenume').val();
        var telefon = $('#telefon').val();
        var email = $('#email').val();

        $.post('updateUser.php', {
            id: id,
            nume: nume,
            prenume: prenume,
            telefon: telefon,
            email: email
        }, function(data) {
            console.log(data);
            location.reload();
        });
    });
});