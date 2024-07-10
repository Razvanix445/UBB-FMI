$(document).ready(function() {
    var page = 1;

    function fetchData() {
        $.ajax({
            url: "fetchUsers.php",
            type: "GET",
            data: { page: page },
            dataType: 'json',
            success: function(response) {
                console.log(response);
                var data = response.data;
                var total = parseInt(response.total);
                var table = $("#data-table");
                table.html("<tr><th>Nume</th><th>Prenume</th><th>Telefon</th><th>E-mail</th></tr>");
                for (var i = 0; i < data.length; i++) {
                    var row = "<tr><td>" + data[i].nume + "</td><td>" + data[i].prenume + "</td><td>" + data[i].telefon + "</td><td>" + data[i].email + "</td></tr>";
                    table.append(row);
                }
                $("#prev-btn").prop("disabled", page == 1);
                $("#next-btn").prop("disabled", page * 3 >= total);
            },
            error: function(jqXHR, textStatus, errorThrown) {
                console.log(textStatus, errorThrown);
            }
        });
    }

    $("#prev-btn").click(function() {
        page--;
        fetchData();
    });

    $("#next-btn").click(function() {
        page++;
        fetchData();
    });

    fetchData();
});