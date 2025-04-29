package com.example

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity

class MainActivity : AppCompatActivity() {

    private lateinit var startGameButton: Button
    private lateinit var calculatorButton: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        startGameButton = findViewById(R.id.startGameButton)
        calculatorButton = findViewById(R.id.calculatorButton)

        startGameButton.setOnClickListener {
            val intent = Intent(this, GameActivity::class.java)
            startActivity(intent)
        }

        calculatorButton.setOnClickListener {
            val intent = Intent(this, CalcActivity::class.java)
            startActivity(intent)
        }
    }
}