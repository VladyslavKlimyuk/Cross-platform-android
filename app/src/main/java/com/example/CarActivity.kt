package com.example

import com.example.models.Car;
import com.example.adapters.CarAdapter;
import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

class CarActivity : AppCompatActivity() {

    companion object {
        lateinit var cars: MutableList<Car>
            private set
        var filteredCars: List<Car>? = null
            internal set
    }

    private lateinit var recyclerView: RecyclerView
    private lateinit var carAdapter: CarAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_car)

        val toolbar: Toolbar = findViewById(R.id.toolbar_car)
        setSupportActionBar(toolbar)

        cars = mutableListOf(
            Car("Toyota", "Camry", 2020, "Надійний седан бізнес-класу", 25000.0, "camry_2020"),
            Car("BMW", "X5", 2019, "Потужний кросовер преміум-класу", 60000.0, "bmw_x5_2019"),
            Car("Honda", "Civic", 2021, "Компактний та економічний хетчбек", 20000.0, "civic_2021"),
            Car("Mercedes-Benz", "C-Class", 2022, "Елегантний седан", 45000.0, "c_class_2022"),
            Car("Audi", "A4", 2020, "Спортивний седан", 35000.0, "a4_2020"),
            Car("Toyota", "RAV4", 2023, "Популярний кросовер", 30000.0, "rav4_2023"),
            Car("BMW", "3 Series", 2021, "Динамічний седан", 40000.0, "bmw_3_series_2021"),
            Car("Honda", "CR-V", 2022, "Місткий сімейний кросовер", 28000.0, "crv_2022"),
            Car("Mercedes-Benz", "GLC", 2023, "Комфортабельний кросовер", 55000.0, "glc_2023"),
            Car("Audi", "Q5", 2021, "Універсальний кросовер", 42000.0, "q5_2021")
        )

        recyclerView = findViewById(R.id.recyclerViewCars)
        recyclerView.layoutManager = LinearLayoutManager(this)
        carAdapter = CarAdapter(cars)
        recyclerView.adapter = carAdapter
    }

    override fun onResume() {
        super.onResume()
        carAdapter.updateData(filteredCars ?: cars)
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.menu_car, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.action_search -> {
                val intent = Intent(this, SearchActivity::class.java)
                startActivity(intent)
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }
}