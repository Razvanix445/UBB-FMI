<?php
function checkGameOver($board, $computerSymbol) {
    // Check rows
    for ($i = 0; $i < 3; $i++) {
        if ($board[$i][0] == $board[$i][1] && $board[$i][1] == $board[$i][2] && $board[$i][0] != '') {
            return $board[$i][0] == $computerSymbol ? 'computer' : 'player';
        }
    }

    // Check columns
    for ($i = 0; $i < 3; $i++) {
        if ($board[0][$i] == $board[1][$i] && $board[1][$i] == $board[2][$i] && $board[0][$i] != '') {
            return $board[0][$i] == $computerSymbol ? 'computer' : 'player';
        }
    }

    // Check diagonals
    if ($board[0][0] == $board[1][1] && $board[1][1] == $board[2][2] && $board[0][0] != '') {
        return $board[0][0] == $computerSymbol ? 'computer' : 'player';
    }

    if ($board[0][2] == $board[1][1] && $board[1][1] == $board[2][0] && $board[0][2] != '') {
        return $board[0][2] == $computerSymbol ? 'computer' : 'player';
    }

    foreach ($board as $row) {
        if (in_array('', $row)) {
            return false;
        }
    }
    return 'draw';
}

if ($_SERVER['REQUEST_METHOD'] == 'POST') {
    $board = json_decode($_POST['board'], true);
    $player = $_POST['player'];
    $computerSymbol = $player == 'X' ? '0' : 'X';

    // if the player has chosen '0', the computer makes the first move
    if (empty(array_filter($board)) && $player == '0') {
        $board[0][0] = $computerSymbol;
    } else {
        for ($i = 0; $i < 3; $i++) {
            for ($j = 0; $j < 3; $j++) {
                if ($board[$i][$j] == '') {
                    $board[$i][$j] = $computerSymbol;
                    break 2;
                }
            }
        }
    }

    $gameOver = checkGameOver($board, $computerSymbol);

    header('Content-Type: application/json');
    echo json_encode(['board' => $board, 'gameOver' => $gameOver]);
} else {
    header('Content-Type: application/json');
    echo json_encode(['error' => 'Invalid request method']);
}
?>