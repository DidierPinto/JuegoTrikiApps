package com.example.juegotrikiapps;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.GridLayout;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    private Button[][] buttons = new Button[3][3];
    private boolean playerXTurn = true;
    private TextView textViewTurn;
    private GridLayout gridLayoutBoard;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        textViewTurn = findViewById(R.id.textViewTurn);
        gridLayoutBoard = findViewById(R.id.gridLayoutBoard);

        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                String buttonID = "button" + i + j;
                int resID = getResources().getIdentifier(buttonID, "id", getPackageName());
                buttons[i][j] = findViewById(resID);
                buttons[i][j].setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        onCellClick(v);
                    }
                });
            }
        }
    }

    public void onCellClick(View v) {
        Button clickedButton = (Button) v;
        if (clickedButton.getText().toString().isEmpty()) { // Only allow click if the cell is empty
            if (playerXTurn) {
                clickedButton.setText("X");
                textViewTurn.setText("Turno de Jugador O");
            } else {
                clickedButton.setText("O");
                textViewTurn.setText("Turno de Jugador X");
            }
            playerXTurn = !playerXTurn; // Switch turns
            checkForWinner();
        }
    }

    private void checkForWinner() {
        String[][] board = new String[3][3];
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                board[i][j] = buttons[i][j].getText().toString();
            }
        }

        for (int i = 0; i < 3; i++) {
            if (board[i][0].equals(board[i][1]) && board[i][1].equals(board[i][2]) && !board[i][0].isEmpty()) {
                declareWinner(board[i][0]);
                return;
            }
        }

        for (int j = 0; j < 3; j++) {
            if (board[0][j].equals(board[1][j]) && board[1][j].equals(board[2][j]) && !board[0][j].isEmpty()) {
                declareWinner(board[0][j]);
                return;
            }
        }

        if (board[0][0].equals(board[1][1]) && board[1][1].equals(board[2][2]) && !board[0][0].isEmpty()) {
            declareWinner(board[0][0]);
            return;
        }
        if (board[0][2].equals(board[1][1]) && board[1][1].equals(board[2][0]) && !board[0][2].isEmpty()) {
            declareWinner(board[0][2]);
        }

        boolean isDraw = true;
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                if (board[i][j].isEmpty()) {
                    isDraw = false;
                    break;
                }
            }
            if (!isDraw) break;
        }
        if (isDraw) {
            textViewTurn.setText("Empate");
        }
    }

    private void declareWinner(String winner) {
        textViewTurn.setText("¡Jugador " + winner + " ha ganado!");
        disableAllButtons();
    }

    private void disableAllButtons() {
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                buttons[i][j].setEnabled(false);
            }
        }
    }
    public void resetGame(View view) {
        // Reset the game board
        playerXTurn = true;
        textViewTurn.setText("Turno de Jugador X");
        // Clear button texts
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                buttons[i][j].setText("");
                buttons[i][j].setEnabled(true);
            }
        }
    }
}

