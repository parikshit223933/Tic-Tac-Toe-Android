package com.example.tictactoe

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.Toast

private var matrix = arrayOf(
    arrayOf(".", ".", "."),
    arrayOf(".", ".", "."),
    arrayOf(".", ".", ".")
)
private val player1 = "0"
private val player2 = "X"
private var player1Name = ""
private var player2Name = ""
private var currentMove = player2;

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        this.setup()
    }

    private fun setup() {
        setButtonStatus(false) // All buttons disabled
        val setNames: Button = findViewById(R.id.button10);
        setNames.setOnClickListener {
            disableTextFields()
            playGame()
        }
    }

    private fun playGame() {
        switchCurrentMove()
        val buttonsMatrix = getButtonMatrix();

        buttonsMatrix.forEachIndexed { i, arrayOfButtons ->
            arrayOfButtons.forEachIndexed { j, button ->
                button.setOnClickListener {

                    if (areMoreMovesLeft()) {

                        if (currentMove == player1) {
                            makeMove(player1, button, i, j);
                        } else {
                            makeMove(player2, button, i, j);
                        }

                        if (hasWon(player1)) {
                            resetGame()
                            declareResult(player1)
                        } else if (hasWon(player2)) {
                            resetGame()
                            declareResult(player2)
                        }
                        if (areMoreMovesLeft()) {
                            switchCurrentMove()
                        } else {
                            resetGame()
                            declareResult()
                        }
                    } else {
                        resetGame()
                        declareResult()
                    }
                }

            }
        }
    }

    private fun getButtonMatrix(): Array<Array<Button>> {
        val button1: Button = findViewById(R.id.button);
        val button2: Button = findViewById(R.id.button9);
        val button3: Button = findViewById(R.id.button8);
        val button4: Button = findViewById(R.id.button7);
        val button5: Button = findViewById(R.id.button6);
        val button6: Button = findViewById(R.id.button5);
        val button7: Button = findViewById(R.id.button4);
        val button8: Button = findViewById(R.id.button3);
        val button9: Button = findViewById(R.id.button2);

        return arrayOf(
            arrayOf(button1, button2, button3),
            arrayOf(button4, button5, button6),
            arrayOf(button7, button8, button9)
        )
    }

    private fun setButtonStatus(toBeEnabled: Boolean) {
        val buttonMatrix = getButtonMatrix();
        buttonMatrix.forEach {
            it.forEach {
                it.isEnabled = toBeEnabled
            }
        }
    }

    private fun disableTextFields() {
        val name1: EditText = findViewById(R.id.editTextTextPersonName)
        val name2: EditText = findViewById(R.id.editTextTextPersonName2)

        name1.isEnabled = false;
        name2.isEnabled = false;
        player1Name = name1.text.toString();
        player2Name = name2.text.toString();
        setButtonStatus(true);
    }

    private fun hasWon(player: String): Boolean {
        for (row in matrix) {
            if (row.all { it == player }) {
                return true;
            }
        }
        for (i in 0..2) {
            var count = 0;
            for (j in 0..2) {
                if (matrix[j][i] == player) {
                    count++;
                }
            }
            if (count == 3) {
                return true;
            }
        }
        var count = 0;
        for (i in 0..2) {
            if (matrix[i][i] == player) {
                count++;
            }
        }
        if (count == 3) {
            return true;
        }
        for (i in 0..2) {
            count = 0
            for (j in 2 downTo 0) {
                if (matrix[i][j] == player) {
                    count++;
                }
            }
            if (count == 3) {
                return true;
            }
        }
        return false;
    }

    private fun makeMove(player: String, button: Button, i: Int, j: Int) {
        button.text = player;
        button.isEnabled = false
        matrix[i][j] = player;
    }

    private fun areMoreMovesLeft(): Boolean {
        matrix.forEach {
            it.forEach {
                if (it == ".") {
                    return true;
                }
            }
        }
        return false;
    }

    private fun resetGame() {
        val buttonMatrix = getButtonMatrix();
        buttonMatrix.forEach {
            it.forEach {
                it.setText("");
            }
        }
        matrix = arrayOf(
            arrayOf(".", ".", "."),
            arrayOf(".", ".", "."),
            arrayOf(".", ".", ".")
        )
        setButtonStatus(false)
        val name1: EditText = findViewById(R.id.editTextTextPersonName)
        val name2: EditText = findViewById(R.id.editTextTextPersonName2)
        name1.isEnabled = true;
        name1.setText("")
        name2.isEnabled = true;
        name2.setText("")
    }

    private fun switchCurrentMove() {
        if (currentMove == player1) {
            currentMove = player2;
            Toast.makeText(this, "$player2Name's turn", Toast.LENGTH_SHORT).show()
        } else {
            currentMove = player1
            Toast.makeText(this, "$player1Name's turn", Toast.LENGTH_SHORT).show()
        }
    }

    private fun declareResult(winner: Any? = null) {
        val intent = Intent(this, ResultDeclarationActivity::class.java)
        intent.apply {
            if (winner == player1) {
                putExtra("WINNER", player1Name)
            } else if (winner == player2) {
                putExtra("WINNER", player2Name)
            } else {
                putExtra("TIE", "NONE")
            }
        }
        startActivity(intent);
    }
}