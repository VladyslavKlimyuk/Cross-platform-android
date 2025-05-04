package com.example

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.View
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import java.util.*
import android.util.Log

class SearchActivity : AppCompatActivity() {

    private lateinit var brandAutoCompleteTextView: AutoCompleteTextView
    private lateinit var modelAutoCompleteTextView: AutoCompleteTextView
    private lateinit var yearFromSpinner: Spinner
    private lateinit var yearToSpinner: Spinner
    private lateinit var costFromEditText: EditText
    private lateinit var costToEditText: EditText
    private lateinit var matchesButton: Button

    private var currentFilteredCars: MutableList<Car> = CarActivity.cars.toMutableList()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_search)

        val toolbar: Toolbar = findViewById(R.id.toolbar_search)
        setSupportActionBar(toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        brandAutoCompleteTextView = findViewById(R.id.autoCompleteTextViewBrand)
        modelAutoCompleteTextView = findViewById(R.id.autoCompleteTextViewModel)
        yearFromSpinner = findViewById(R.id.spinnerYearFrom)
        yearToSpinner = findViewById(R.id.spinnerYearTo)
        costFromEditText = findViewById(R.id.editTextCostFrom)
        costToEditText = findViewById(R.id.editTextCostTo)
        matchesButton = findViewById(R.id.buttonMatches)
        matchesButton.isEnabled = false

        val brands = CarActivity.cars.map { it.brand }.toSortedSet().toList()
        Log.d("SearchActivity", "Brands list in onCreate: $brands")
        val brandAdapter = ArrayAdapter(this, android.R.layout.simple_dropdown_item_1line, brands)
        brandAutoCompleteTextView.setAdapter(brandAdapter)

        val models = CarActivity.cars.map { it.model }.toSortedSet().toList()
        val modelAdapter = ArrayAdapter(this, android.R.layout.simple_dropdown_item_1line, models)
        modelAutoCompleteTextView.setAdapter(modelAdapter)

        val years = CarActivity.cars.map { it.year }.toSortedSet().toList()
        val yearsWithAny = mutableListOf("Будь-який рік") + years
        val yearAdapter = ArrayAdapter(this, android.R.layout.simple_spinner_dropdown_item, yearsWithAny)
        yearFromSpinner.adapter = yearAdapter
        yearToSpinner.adapter = yearAdapter

        val costValues = listOf("Будь-яка", "1000", "10000", "100000")
        val costAdapter = ArrayAdapter(this, android.R.layout.simple_spinner_dropdown_item, costValues)
        costFromEditText.hint = "Від (${costValues[1]})"
        costToEditText.hint = "До (${costValues.last()})"

        val textWatcher = object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                filterCars()
            }
            override fun afterTextChanged(s: Editable?) {}
        }

        brandAutoCompleteTextView.addTextChangedListener(textWatcher)
        modelAutoCompleteTextView.addTextChangedListener(textWatcher)
        costFromEditText.addTextChangedListener(textWatcher)
        costToEditText.addTextChangedListener(textWatcher)

        yearFromSpinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
                filterCars()
            }
            override fun onNothingSelected(parent: AdapterView<*>?) {}
        }

        yearToSpinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
                filterCars()
            }
            override fun onNothingSelected(parent: AdapterView<*>?) {}
        }

        matchesButton.setOnClickListener {
            CarActivity.filteredCars = currentFilteredCars
            finish()
        }

        filterCars()
    }

    private fun filterCars() {
        Log.d("SearchActivity", "filterCars() викликано")
        Log.d("SearchActivity", "brandQuery: ${brandAutoCompleteTextView.text}, modelQuery: ${modelAutoCompleteTextView.text}, yearFrom: ${yearFromSpinner.selectedItem}, yearTo: ${yearToSpinner.selectedItem}, costFromText: ${costFromEditText.text}, costToText: ${costToEditText.text}")

        val brandQuery = brandAutoCompleteTextView.text.toString().toLowerCase(Locale.getDefault())
        val modelQuery = modelAutoCompleteTextView.text.toString().toLowerCase(Locale.getDefault())

        val yearFromSelectedItem = yearFromSpinner.selectedItem?.toString()
        val yearFrom = if (yearFromSelectedItem == "Будь-який рік") null else yearFromSelectedItem?.toIntOrNull()

        val yearToSelectedItem = yearToSpinner.selectedItem?.toString()
        val yearTo = if (yearToSelectedItem == "Будь-який рік") null else yearToSelectedItem?.toIntOrNull()

        val costFromText = costFromEditText.text.toString()
        val costToText = costToEditText.text.toString()

        val costFrom = when (costFromText) {
            "Будь-яка", "" -> 0.0
            else -> costFromText.toDoubleOrNull() ?: 0.0
        }

        val costTo = when (costToText) {
            "Будь-яка", "" -> Double.MAX_VALUE
            else -> costToText.toDoubleOrNull() ?: Double.MAX_VALUE
        }

        currentFilteredCars = CarActivity.cars.filter { car ->
            val brandMatch = brandQuery.isEmpty() || car.brand.toLowerCase(Locale.getDefault()).contains(brandQuery)
            val modelMatch = modelQuery.isEmpty() || car.model.toLowerCase(Locale.getDefault()).contains(modelQuery)
            val yearFromMatch = yearFrom == null || car.year >= yearFrom
            val yearToMatch = yearTo == null || car.year <= yearTo
            val costFromMatch = car.cost >= costFrom
            val costToMatch = car.cost <= costTo

            brandMatch && modelMatch && yearFromMatch && yearToMatch && costFromMatch && costToMatch
        }.toMutableList()

        matchesButton.isEnabled = brandQuery.isNotEmpty() ||
                modelQuery.isNotEmpty() ||
                yearFrom != null ||
                yearTo != null ||
                costFromText.isNotEmpty() ||
                costToText.isNotEmpty()
    }
}