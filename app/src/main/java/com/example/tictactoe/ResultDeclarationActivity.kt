package com.example.tictactoe

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ImageView
import android.widget.TextView

class ResultDeclarationActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_result_declaration)

        declareResult()
    }

    private fun declareResult() {
        val result: TextView = findViewById(R.id.result)
        val drawable: ImageView = findViewById(R.id.imageView)

        intent.getStringExtra("WINNER")?.let {
            result.setText(getString(R.string.winText, it))
            drawable.setImageResource(R.drawable.won);
        }

        intent.getStringExtra("TIE")?.let {
            result.setText(getString(R.string.tieText))
            drawable.setImageResource(R.drawable.tie)
        }
    }
}