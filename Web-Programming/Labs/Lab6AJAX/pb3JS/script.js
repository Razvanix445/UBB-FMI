window.onload = function() {
    var inputs = document.querySelectorAll('#formular input');
    var saveButton = document.getElementById('saveButton');
    var userList = document.getElementById('userList');
    var users;

    fetch('fetchUsers.php')
        .then(response => response.text())
        .then(text => {
            console.log(text);
            return JSON.parse(text);
        })
        .then(fetchedUsers => {
            users = fetchedUsers;
            users.forEach(user => {
                var listItem = document.createElement('li');
                listItem.innerHTML = user.id + ' - ' + user.nume;
                userList.appendChild(listItem);

                listItem.addEventListener('click', function() {
                    if (saveButton.disabled === false) {
                        var saveChanges = confirm('You have unsaved changes. Do you want to save them?');
                        if (saveChanges) {
                            saveButton.click();
                        }
                    }

                    document.getElementById('id').value = user.id;
                    document.getElementById('nume').value = user.nume;
                    document.getElementById('prenume').value = user.prenume;
                    document.getElementById('telefon').value = user.telefon;
                    document.getElementById('email').value = user.email;

                    saveButton.disabled = true;
                });
            });
        })
        .catch(error => console.error('Error:', error));

    inputs.forEach(input => {
        input.addEventListener('input', function() {
            saveButton.disabled = false;
        });
    });

    saveButton.addEventListener('click', function() {
        var id = document.getElementById('id').value;
        var nume = document.getElementById('nume').value;
        var prenume = document.getElementById('prenume').value;
        var telefon = document.getElementById('telefon').value;
        var email = document.getElementById('email').value;

        fetch('updateUser.php', {
            method: 'POST',
            headers: {
                'Content-Type': 'application/x-www-form-urlencoded',
            },
            body: 'id=' + encodeURIComponent(id) + '&nume=' + encodeURIComponent(nume) + '&prenume=' + encodeURIComponent(prenume) + '&telefon=' + encodeURIComponent(telefon) + '&email=' + encodeURIComponent(email),
        })
            .then(response => response.text())
            .then(text => {
                console.log(text);
                location.reload();
            })
            .catch(error => console.error('Error:', error));
    });
};