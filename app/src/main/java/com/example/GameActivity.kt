package com.example

import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.LinearLayout
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity

class GameActivity : AppCompatActivity() {

    private lateinit var hintTextView: TextView
    private lateinit var guessEditText: EditText
    private lateinit var submitButton: Button
    private lateinit var gameMode: String
    private var minRange = 1
    private var maxRange = 100
    private var playerSecretNumber: Int? = null
    private var secretNumber = 0

    private lateinit var moreButton: Button
    private lateinit var lessButton: Button
    private lateinit var guessedButton: Button
    private lateinit var startGameButton: Button
    private lateinit var computerGuessButton: Button
    private lateinit var playerGuessButton: Button
    private lateinit var gameModeLayout: LinearLayout

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_game)

        startGameButton = findViewById(R.id.startGameButton)
        computerGuessButton = findViewById(R.id.computerGuessButton)
        playerGuessButton = findViewById(R.id.playerGuessButton)
        hintTextView = findViewById(R.id.hintTextView)
        guessEditText = findViewById(R.id.guessEditText)
        submitButton = findViewById(R.id.submitButton)
        moreButton = findViewById(R.id.moreButton)
        lessButton = findViewById(R.id.lessButton)
        guessedButton = findViewById(R.id.guessedButton)
        gameModeLayout = findViewById(R.id.gameModeLayout)

        startGameButton.setOnClickListener {
            showGameModeButtons()
        }
    }

    private fun showGameModeButtons() {
        startGameButton.visibility = View.GONE
        gameModeLayout.visibility = View.VISIBLE

        computerGuessButton.setOnClickListener {
            startGame("computer")
        }

        playerGuessButton.setOnClickListener {
            startGame("player")
        }
    }

    private fun startGame(mode: String) {
        gameMode = mode

        gameModeLayout.visibility = View.GONE;
        hintTextView.visibility = View.VISIBLE
        guessEditText.visibility = View.VISIBLE


        if (gameMode == "computer") {
            startGameForComputer()
        } else if (gameMode == "player") {
            startGameForPlayer()
        }
    }

    private fun startGameForComputer() {
        secretNumber = (1..100).random()
        hintTextView.text = "Введіть ваше число:"
        guessEditText.hint = "Ваше число"
        submitButton.text = "Підтвердити"
        submitButton.visibility = View.VISIBLE

        submitButton.setOnClickListener {
            handleComputerGuess()
        }
    }

    private fun handleComputerGuess() {
        val guessStr = guessEditText.text.toString()
        val guess = guessStr.toIntOrNull()
        if (guess != null) {
            if (guess > secretNumber) {
                hintTextView.text = "Загадане число менше"
            } else if (guess < secretNumber) {
                hintTextView.text = "Загадане число більше"
            } else {
                hintTextView.text = "Ви вгадали! Гра розпочнеться заново через 5 секунд."
                Handler(Looper.getMainLooper()).postDelayed({
                    startGameForComputer()
                }, 5000)
            }
        } else {
            hintTextView.text = "Будь ласка, введіть число"
        }
    }

    private fun startGameForPlayer() {
        hintTextView.text = "Загадайте число від 1 до 100 та введіть його:"
        guessEditText.hint = "Ваше число"
        submitButton.text = "Готово"
        submitButton.visibility = View.VISIBLE

        submitButton.setOnClickListener {
            if (playerSecretNumber == null) {
                val playerSecretStr = guessEditText.text.toString()
                val playerSecret = playerSecretStr.toIntOrNull()
                if (playerSecret != null && playerSecret in 1..100) {
                    playerSecretNumber = playerSecret
                    hintTextView.text = "Я починаю вгадувати..."
                    guessEditText.visibility = View.GONE
                    submitButton.visibility = View.GONE
                    moreButton.visibility = View.VISIBLE
                    lessButton.visibility = View.VISIBLE
                    guessedButton.visibility = View.VISIBLE
                    makeComputerGuess()
                } else {
                    hintTextView.text = "Будь ласка, введіть число від 1 до 100"
                }
            } else {
                handlePlayerHint()
            }
        }

        moreButton.setOnClickListener {
            minRange = secretNumber + 1
            makeComputerGuess()
        }

        lessButton.setOnClickListener {
            maxRange = secretNumber - 1
            makeComputerGuess()
        }

        guessedButton.setOnClickListener {
            hintTextView.text = "Ура, я молодець!"
            guessEditText.visibility = View.GONE
            moreButton.visibility = View.GONE
            lessButton.visibility = View.GONE
            guessedButton.visibility = View.GONE
            Handler(Looper.getMainLooper()).postDelayed({
                hintTextView.text = "Загадайте число від 1 до 100 та введіть його:"
                guessEditText.visibility = View.VISIBLE
                guessEditText.hint = "Ваше число"
                submitButton.visibility = View.VISIBLE
                submitButton.text = "Готово"
                minRange = 1
                maxRange = 100
                playerSecretNumber = null
            }, 5000)
        }
    }

    private fun makeComputerGuess() {
        if (minRange <= maxRange) {
            secretNumber = (minRange..maxRange).random()
            hintTextView.text = "Моя спроба: $secretNumber"
        } else {
            hintTextView.text = "Я не можу вгадати! Ви помилилися з підказками."
        }
    }

    private fun handlePlayerHint() {
        if (playerSecretNumber != null) {
            val hint = submitButton.text.toString().toLowerCase()
            if (hint == "більше") {
                minRange = secretNumber + 1
                makeComputerGuess()
            } else if (hint == "менше") {
                maxRange = secretNumber - 1
                makeComputerGuess()
            } else if (hint == "вгадав") {
                hintTextView.text = "Ура, я молодець!"
                guessEditText.visibility = View.GONE
                moreButton.visibility = View.GONE
                lessButton.visibility = View.GONE
                guessedButton.visibility = View.GONE
                Handler(Looper.getMainLooper()).postDelayed({
                    hintTextView.text = "Загадайте число від 1 до 100 та введіть його:"
                    guessEditText.visibility = View.VISIBLE
                    guessEditText.hint = "Ваше число"
                    submitButton.visibility = View.VISIBLE
                    submitButton.text = "Готово"
                    minRange = 1
                    maxRange = 100
                    playerSecretNumber = null
                }, 5000)
            } else {
                hintTextView.text = "Будь ласка, введіть 'більше', 'менше' або 'вгадав'"
            }
        }
    }
}
