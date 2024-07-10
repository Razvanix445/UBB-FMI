let pathMap = {};
pathMap["1"] = "main_directory";
let counter = 1;

function loadFolders(id) {
    let element = $('#' + id);
    if (element.hasClass('opened')) {
        return;
    }
    element.addClass("opened");
    let path = pathMap[id];
    let list = element.children(":first");
    let xhr = new XMLHttpRequest();
    xhr.onreadystatechange = function() {
        if (xhr.readyState === 4 && xhr.status === 200) {
            let folders = JSON.parse(xhr.responseText);
            if (folders.length > 2) {
                for (let i = 2; i < folders.length; i++) {
                    counter++;
                    let folder = folders[i];
                    let newItem = document.createElement('li');
                    if (folder.indexOf('.') === -1) { // it's a directory
                        newItem.innerText = folder;
                        let newList = document.createElement('ul');
                        pathMap[counter] = path + '/' + folder;
                        (function(counter) {
                            $(newItem).on('click', function() {
                                loadFolders(counter);
                            });
                        })(counter);
                        $(newItem).attr('id', counter);
                        $(newItem).append(newList);
                        $(newItem).css("color", "green");
                    } else { // it's a file
                        newItem.innerText = folder;
                        pathMap[counter] = path + '/' + folder;
                        (function(counter) {
                            $(newItem).on('click', function() {
                                loadFile(counter);
                            });
                        })(counter);
                        if (folder.endsWith('.txt')) {
                            $(newItem).css("color", "black");
                        } else if (folder.endsWith('.html')) {
                            $(newItem).css("color", "orange");
                        }
                    }
                    list.append(newItem);
                }
            }
        } else if (xhr.readyState === 4) {
            alert('Error: ' + xhr.status);
        }
    };
    xhr.open('GET', 'getFolders.php?path=' + path, true);
    xhr.send();
}

function loadFile(id) {
    let path = pathMap[id];
    // if (path.endsWith('.html')) {
    //     window.open(path);
    // } else {
        let xhr= new XMLHttpRequest();
        xhr.onreadystatechange = function() {
            if (xhr.readyState === 4 && xhr.status === 200) {
                $('#explorer').val(xhr.responseText);
            } else if (xhr.readyState === 4) {
                alert('Error: ' + xhr.status);
            }
        };
        xhr.open('GET', 'getFile.php?path=' + path, true);
        xhr.send();
    // }
}