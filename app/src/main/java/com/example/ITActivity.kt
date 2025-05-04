package com.example

import android.os.Bundle
import android.view.View
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.textfield.TextInputEditText

class ITActivity : AppCompatActivity() {

    private lateinit var editTextFullName: TextInputEditText
    private lateinit var editTextAge: TextInputEditText
    private lateinit var editTextSalary: TextInputEditText
    private lateinit var radioGroupQuestion1: RadioGroup
    private lateinit var radioGroupQuestion2: RadioGroup
    private lateinit var radioGroupQuestion3: RadioGroup
    private lateinit var radioGroupQuestion4: RadioGroup
    private lateinit var radioGroupQuestion5: RadioGroup
    private lateinit var checkBoxExperience: CheckBox
    private lateinit var checkBoxTeamwork: CheckBox
    private lateinit var checkBoxTravel: CheckBox
    private lateinit var buttonSubmit: Button
    private lateinit var textViewResult: TextView
    private lateinit var textViewContacts: TextView

    private val passingScore = 10

    private val correctAnswers = mapOf(
        R.id.radioButtonQ1B_it to "B",
        R.id.radioButtonQ2A_it to "A",
        R.id.radioButtonQ3B_it to "B",
        R.id.radioButtonQ4A_it to "A",
        R.id.radioButtonQ5C_it to "C"
    )

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_it)

        editTextFullName = findViewById(R.id.editTextFullName_it)
        editTextAge = findViewById(R.id.editTextAge_it)
        editTextSalary = findViewById(R.id.editTextSalary_it)
        radioGroupQuestion1 = findViewById(R.id.radioGroupQuestion1_it)
        radioGroupQuestion2 = findViewById(R.id.radioGroupQuestion2_it)
        radioGroupQuestion3 = findViewById(R.id.radioGroupQuestion3_it)
        radioGroupQuestion4 = findViewById(R.id.radioGroupQuestion4_it)
        radioGroupQuestion5 = findViewById(R.id.radioGroupQuestion5_it)
        checkBoxExperience = findViewById(R.id.checkBoxExperience_it)
        checkBoxTeamwork = findViewById(R.id.checkBoxTeamwork_it)
        checkBoxTravel = findViewById(R.id.checkBoxTravel_it)
        buttonSubmit = findViewById(R.id.buttonSubmit_it)
        textViewResult = findViewById(R.id.textViewResult_it)
        textViewContacts = findViewById(R.id.textViewContacts_it)

        buttonSubmit.setOnClickListener {
            val score = calculateScore()
            textViewResult.visibility = View.VISIBLE
            textViewContacts.visibility = View.GONE

            val fullName = editTextFullName.text.toString()
            val age = editTextAge.text.toString()
            val salary = editTextSalary.text.toString()

            if (fullName.isNotBlank() && age.isNotBlank() && salary.isNotBlank()) {
                if (score >= passingScore) {
                    textViewResult.text = "$fullName, вітаємо! Ви набрали $score балів і успішно пройшли попередню оцінку."
                    textViewContacts.visibility = View.VISIBLE
                } else {
                    textViewResult.text = "$fullName, на жаль, ви не пройшли попередню оцінку. Набрано $score з $passingScore балів."
                }
            } else {
                textViewResult.text = "Будь ласка, заповніть всі особисті дані перед здачею тесту."
            }
        }
    }

    private fun calculateScore(): Int {
        var score = 0

        score += checkAnswer(radioGroupQuestion1, R.id.radioButtonQ1A_it, R.id.radioButtonQ1B_it, R.id.radioButtonQ1C_it, correctAnswers[R.id.radioButtonQ1B_it])
        score += checkAnswer(radioGroupQuestion2, R.id.radioButtonQ2A_it, R.id.radioButtonQ2B_it, R.id.radioButtonQ2C_it, correctAnswers[R.id.radioButtonQ2A_it])
        score += checkAnswer(radioGroupQuestion3, R.id.radioButtonQ3A_it, R.id.radioButtonQ3B_it, R.id.radioButtonQ3C_it, correctAnswers[R.id.radioButtonQ3B_it])
        score += checkAnswer(radioGroupQuestion4, R.id.radioButtonQ4A_it, R.id.radioButtonQ4B_it, R.id.radioButtonQ4C_it, correctAnswers[R.id.radioButtonQ4A_it])
        score += checkAnswer(radioGroupQuestion5, R.id.radioButtonQ5A_it, R.id.radioButtonQ5B_it, R.id.radioButtonQ5C_it, correctAnswers[R.id.radioButtonQ5C_it])

        if (checkBoxExperience.isChecked) {
            score += 2
        }
        if (checkBoxTeamwork.isChecked) {
            score += 1
        }
        if (checkBoxTravel.isChecked) {
            score += 1
        }

        return score
    }

    private fun checkAnswer(radioGroup: RadioGroup, idA: Int, idB: Int, idC: Int, correctAnswer: String?): Int {
        val checkedRadioButtonId = radioGroup.checkedRadioButtonId
        if (checkedRadioButtonId != -1) {
            val checkedRadioButton = findViewById<RadioButton>(checkedRadioButtonId)
            val selectedAnswer = when (checkedRadioButtonId) {
                idA -> "A"
                idB -> "B"
                idC -> "C"
                else -> ""
            }
            if (selectedAnswer == correctAnswer) {
                return 2
            }
        }
        return 0
    }
}