package com.example

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity

class MainActivity : AppCompatActivity() {

    private lateinit var startGameButton: Button
    private lateinit var calculatorButton: Button
    private lateinit var itInterviewButton: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        startGameButton = findViewById(R.id.startGameButton)
        calculatorButton = findViewById(R.id.calculatorButton)
        itInterviewButton = findViewById(R.id.itInterviewButton)

        startGameButton.setOnClickListener {
            val intent = Intent(this, GameActivity::class.java)
            startActivity(intent)
        }

        calculatorButton.setOnClickListener {
            val intent = Intent(this, CalcActivity::class.java)
            startActivity(intent)
        }

        itInterviewButton.setOnClickListener {
            val intent = Intent(this, ITActivity::class.java)
            startActivity(intent)
        }
    }
}