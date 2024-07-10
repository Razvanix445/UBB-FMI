document.addEventListener('DOMContentLoaded', function() {
    var properties = ['producator', 'procesor', 'memorie', 'hdd', 'video'];

    properties.forEach(function(property) {
        var xhr = new XMLHttpRequest();
        xhr.open('GET', 'get' + property.charAt(0).toUpperCase() + property.slice(1) + '.php', true);
        xhr.onload = function() {
            if (this.status >= 200 && this.status < 400) {
                var items = JSON.parse(this.response);
                var select = document.getElementById(property);
                items.forEach(function(item) {
                    var option = document.createElement('option');
                    option.value = item;
                    option.textContent = item;
                    select.appendChild(option);
                });
            } else {
                alert('A aparut o eroare la incarcarea ' + property + '.');
            }
        };
        xhr.onerror = function() {
            alert('A aparut o eroare la incarcarea ' + property + '.');
        };
        xhr.send();
    });

    document.getElementById('filterButton').addEventListener('click', function() {
        var producator = document.getElementById('producator').value;
        var procesor = document.getElementById('procesor').value;
        var memorie = document.getElementById('memorie').value;
        var hdd = document.getElementById('hdd').value;
        var video = document.getElementById('video').value;

        var xhr = new XMLHttpRequest();
        xhr.open('GET', 'filter.php?producator=' + producator + '&procesor=' + procesor + '&memorie=' + memorie + '&hdd=' + hdd + '&video=' + video, true);
        xhr.onload = function() {
            if (this.status >= 200 && this.status < 400) {
                var data = JSON.parse(this.response);
                var results = document.getElementById('results');

                // Create table
                var table = document.createElement('table');
                var thead = document.createElement('thead');
                var tbody = document.createElement('tbody');

                var tr = document.createElement('tr');
                for (var key in data[0]) {
                    var th = document.createElement('th');
                    th.textContent = key;
                    tr.appendChild(th);
                }
                thead.appendChild(tr);

                data.forEach(function(item) {
                    var tr = document.createElement('tr');
                    for (var key in item) {
                        var td = document.createElement('td');
                        td.textContent = item[key];
                        tr.appendChild(td);
                    }
                    tbody.appendChild(tr);
                });

                table.appendChild(thead);
                table.appendChild(tbody);

                results.innerHTML = '';
                results.appendChild(table);
            } else {
                alert('A aparut o eroare la filtrare.');
            }
        };
        xhr.onerror = function() {
            alert('A aparut o eroare la filtrare.');
        };
        xhr.send();
    });
});