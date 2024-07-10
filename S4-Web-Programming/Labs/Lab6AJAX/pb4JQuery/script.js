$(document).ready(function() {
    var player;

    $('#chooseX').click(function() {
        player = 'X';
        $('#chooseX, #choose0').hide();
    });

    $('#choose0').click(function() {
        player = '0';
        $('#chooseX, #choose0').hide();
        makeMove();
    });

    $('#gameBoard td').click(function() {
        if ($(this).text() == '' && player !== undefined) {
            $(this).text(player);
            makeMove();
        }
    });

    function makeMove() {
        var board = [];
        $('#gameBoard tr').each(function() {
            var row = [];
            $(this).find('td').each(function() {
                row.push($(this).text());
            });
            board.push(row);
        });

        $.ajax({
            url: 'game.php',
            type: 'POST',
            data: { board: JSON.stringify(board), player: player },
            success: function(response) {
                console.log(response);
                var newBoard = response.board;
                $('#gameBoard tr').each(function(i) {
                    $(this).find('td').each(function(j) {
                        $(this).text(newBoard[i][j] || '');
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
                    $('#gameBoard td').off('click');
                }
            }
        });
    }
});