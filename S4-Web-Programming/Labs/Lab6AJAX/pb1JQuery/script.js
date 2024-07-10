function getPlecari() {
    var url = "plecari.php";
    $.get(url, function(data) {
        $('#orasPlecare').html(data);
    });
}

function getSosiri(value) {
    var url = "sosiri.php";
    $.get(url, { name: value }, function(data) {
        $('#orasSosire').html(data);
    });
}