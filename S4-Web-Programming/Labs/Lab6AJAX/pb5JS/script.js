let pathMap = {};
pathMap["1"] = "main_directory";
let counter = 1;

function loadFolders(id) {
    let element = document.getElementById(id);
    if (element.classList.contains('opened')) {
        return;
    }
    element.classList.add("opened");
    let path = pathMap[id];
    let list = element.children[0];
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
                            newItem.addEventListener('click', function() {
                                loadFolders(counter);
                            });
                        })(counter);
                        newItem.id = counter;
                        newItem.appendChild(newList);
                        newItem.style.color = "green";
                    } else {
                        newItem.innerText = folder;
                        pathMap[counter] = path + '/' + folder;
                        (function(counter) {
                            newItem.addEventListener('click', function() {
                                loadFile(counter);
                            });
                        })(counter);
                        if (folder.endsWith('.txt')) {
                            newItem.style.color = "black";
                        } else if (folder.endsWith('.html')) {
                            newItem.style.color = "orange";
                        }
                    }
                    list.appendChild(newItem);
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
        let xhr = new XMLHttpRequest();
        xhr.onreadystatechange = function() {
            if (xhr.readyState === 4 && xhr.status === 200) {
                document.getElementById('explorer').value = xhr.responseText;
            } else if (xhr.readyState === 4) {
                alert('Error: ' + xhr.status);
            }
        };
        xhr.open('GET', 'getFile.php?path=' + path, true);
        xhr.send();
    // }
}