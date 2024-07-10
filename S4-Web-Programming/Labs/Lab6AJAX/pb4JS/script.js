document.addEventListener('DOMContentLoaded', function() {
    var player;

    document.getElementById('chooseX').addEventListener('click', function() {
        player = 'X';
        document.getElementById('chooseX').style.display = 'none';
        document.getElementById('choose0').style.display = 'none';
    });

    document.getElementById('choose0').addEventListener('click', function() {
        player = '0';
        document.getElementById('chooseX').style.display = 'none';
        document.getElementById('choose0').style.display = 'none';
        makeMove();
    });

    var cells = document.querySelectorAll('#gameBoard td');
    cells.forEach(function(cell) {
        cell.addEventListener('click', function() {
            if (this.textContent == '' && player !== undefined) {
                this.textContent = player;
                makeMove();
            }
        });
    });

    function makeMove() {
        var board = [];
        var rows = document.querySelectorAll('#gameBoard tr');
        rows.forEach(function(row) {
            var rowArray = [];
            var cells = row.querySelectorAll('td');
            cells.forEach(function(cell) {
                rowArray.push(cell.textContent);
            });
            board.push(rowArray);
        });

        var xhr = new XMLHttpRequest();
        xhr.open('POST', 'game.php', true);
        xhr.setRequestHeader('Content-Type', 'application/x-www-form-urlencoded');
        xhr.onload = function() {
            if (this.status == 200) {
                var response = JSON.parse(this.responseText);
                console.log(response); // Log the server response
                var newBoard = response.board; // response.board is already an object
                rows.forEach(function(row, i) {
                    var cells = row.querySelectorAll('td');
                    cells.forEach(function(cell, j) {
                        cell.textContent = newBoard[i][j] || '';
                    });
                });
                if (response.gameOver) {
                    var message;
                    switch (response.gameOver) {
                        case 'player':
                            message = 'You won!';
                            break;
                        case 'computer':
                            message = 'Computer won!';
                            break;
                        case 'draw':
                            message = 'It\'s a draw!';
                            break;
                    }
                    alert(message);
                    cells.forEach(function(cell) {
                        cell.removeEventListener('click');
                    });
                }
            }
        };
        xhr.send('board=' + JSON.stringify(board) + '&player=' + player);
    }
});