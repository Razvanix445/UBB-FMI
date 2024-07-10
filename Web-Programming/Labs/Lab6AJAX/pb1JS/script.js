function getPlecari() {
    var xmlhttp = new XMLHttpRequest();
    var url = "plecari.php";
    xmlhttp.onreadystatechange = function() {
        if (xmlhttp.readyState == 4) {
            document.getElementById("orasPlecare").innerHTML = xmlhttp.responseText;
        }
    };
    xmlhttp.open("GET", url, true);
    xmlhttp.send();
}

function getSosiri(value) {
    var xmlhttp = new XMLHttpRequest();
    var url = "sosiri.php?name=" + value;
    xmlhttp.onreadystatechange = function() {
        if (xmlhttp.readyState == 4) {
            document.getElementById("orasSosire").innerHTML = xmlhttp.responseText;
        }
    };
    xmlhttp.open("GET", url, true);
    xmlhttp.send();
}