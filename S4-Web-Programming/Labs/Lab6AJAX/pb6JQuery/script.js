$(document).ready(function() {
    var properties = ['producator', 'procesor', 'memorie', 'hdd', 'video'];

    properties.forEach(function(property) {
        $.ajax({
            url: 'get' + property.charAt(0).toUpperCase() + property.slice(1) + '.php',
            type: 'GET',
            dataType: 'json',
            success: function(items) {
                var select = $('#' + property);
                items.forEach(function(item) {
                    var option = $('<option></option>').attr('value', item).text(item);
                    select.append(option);
                });
            },
            error: function() {
                alert('A aparut o eroare la incarcarea ' + property + '.');
            }
        });
    });

    $('#filterButton').click(function() {
        var producator = $('#producator').val();
        var procesor = $('#procesor').val();
        var memorie = $('#memorie').val();
        var hdd = $('#hdd').val();
        var video = $('#video').val();

        $.ajax({
            url: 'filter.php',
            type: 'GET',
            data: {
                producator: producator,
                procesor: procesor,
                memorie: memorie,
                hdd: hdd,
                video: video
            },
            dataType: 'json',
            success: function(data) {
                var results = $('#results');
                var table = $('<table></table>');
                var thead = $('<thead></thead>');
                var tbody = $('<tbody></tbody>');

                // Create table header
                var tr = $('<tr></tr>');
                for (var key in data[0]) {
                    var th = $('<th></th>').text(key);
                    tr.append(th);
                }
                thead.append(tr);

                data.forEach(function(item) {
                    var tr = $('<tr></tr>');
                    for (var key in item) {
                        var td = $('<td></td>').text(item[key]);
                        tr.append(td);
                    }
                    tbody.append(tr);
                });

                table.append(thead);
                table.append(tbody);

                results.html('');
                results.append(table);
            },
            error: function() {
                alert('A aparut o eroare la filtrare.');
            }
        });
    });
});