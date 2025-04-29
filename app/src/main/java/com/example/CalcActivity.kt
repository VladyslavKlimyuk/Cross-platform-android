package com.example

import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import kotlin.math.log10
import kotlin.math.pow

class CalcActivity : AppCompatActivity() {

    private lateinit var resultTextView: TextView
    private var currentInput: StringBuilder = StringBuilder()
    private var operand1: Double? = null
    private var operator: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_calc)

        resultTextView = findViewById(R.id.resultTextView)

        val buttons = listOf(
            R.id.buttonC, R.id.buttonDot, R.id.buttonPlus, R.id.buttonMinus,
            R.id.buttonMultiply, R.id.buttonDivide, R.id.buttonPower, R.id.buttonLog,
            R.id.button0, R.id.button1, R.id.button2, R.id.button3,
            R.id.button4, R.id.button5, R.id.button6, R.id.button7,
            R.id.button8, R.id.button9, R.id.buttonEquals
        )

        buttons.forEach { buttonId ->
            findViewById<Button>(buttonId).setOnClickListener { onButtonClick(it) }
        }
    }

    private fun onButtonClick(view: View) {
        val button = view as Button
        val text = button.text.toString()

        when (text) {
            in "0".."9" -> appendNumber(text)
            "." -> appendDot()
            "+" -> performOperation("+")
            "-" -> performOperation("-")
            "×" -> performOperation("×")
            "÷" -> performOperation("÷")
            "^" -> performOperation("^")
            "log" -> performLogarithm()
            "C" -> clearInput()
            "=" -> calculateResult()
        }
    }

    private fun appendNumber(number: String) {
        currentInput.append(number)
        updateResultTextView()
    }

    private fun appendDot() {
        if (!currentInput.contains(".")) {
            currentInput.append(".")
            updateResultTextView()
        }
    }

    private fun performOperation(op: String) {
        if (currentInput.isNotEmpty()) {
            operand1 = currentInput.toString().toDouble()
            operator = op
            currentInput.clear()
            updateResultTextView()
        }
    }

    private fun performLogarithm() {
        if (currentInput.isNotEmpty()) {
            try {
                val number = currentInput.toString().toDouble()
                val result = log10(number)
                currentInput.clear()
                currentInput.append(result.toString())
                updateResultTextView()
                operand1 = null
                operator = null
            } catch (e: NumberFormatException) {
                Toast.makeText(this, "Невірний формат числа", Toast.LENGTH_SHORT).show()
                clearInput()
            }
        }
    }

    private fun clearInput() {
        currentInput.clear()
        operand1 = null
        operator = null
        updateResultTextView("0")
    }

    private fun calculateResult() {
        if (operand1 != null && operator != null && currentInput.isNotEmpty()) {
            try {
                val operand2 = currentInput.toString().toDouble()
                val result = when (operator) {
                    "+" -> operand1!! + operand2
                    "-" -> operand1!! - operand2
                    "×" -> operand1!! * operand2
                    "÷" -> {
                        if (operand2 == 0.0) {
                            Toast.makeText(this, "Ділення на нуль!", Toast.LENGTH_SHORT).show()
                            return
                        }
                        operand1!! / operand2
                    }
                    "^" -> operand1!!.pow(operand2)
                    else -> return
                }
                currentInput.clear()
                currentInput.append(result.toString())
                updateResultTextView()
                operand1 = null
                operator = null
            } catch (e: NumberFormatException) {
                Toast.makeText(this, "Невірний формат числа", Toast.LENGTH_SHORT).show()
                clearInput()
            }
        }
    }

    private fun updateResultTextView(text: String = currentInput.toString()) {
        resultTextView.text = text.ifEmpty { "0" }
    }
}