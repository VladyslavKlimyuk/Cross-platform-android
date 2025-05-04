package com.example

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
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

class CarAdapter(private var cars: List<Car>) : RecyclerView.Adapter<CarAdapter.CarViewHolder>() {

    class CarViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val brandTextView: TextView = itemView.findViewById(R.id.textViewBrand)
        val modelTextView: TextView = itemView.findViewById(R.id.textViewModel)
        val yearTextView: TextView = itemView.findViewById(R.id.textViewYear)
        val descriptionTextView: TextView = itemView.findViewById(R.id.textViewDescription)
        val costTextView: TextView = itemView.findViewById(R.id.textViewCost)
        val photoImageView: ImageView = itemView.findViewById(R.id.imageViewPhoto)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CarViewHolder {
        val itemView = LayoutInflater.from(parent.context)
            .inflate(R.layout.activity_itemcar, parent, false)
        return CarViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: CarViewHolder, position: Int) {
        val currentCar = cars[position]
        holder.brandTextView.text = currentCar.brand
        holder.modelTextView.text = currentCar.model
        holder.yearTextView.text = currentCar.year.toString()
        holder.descriptionTextView.text = currentCar.description
        holder.costTextView.text = String.format("%.2f USD", currentCar.cost)
        val imageResource = holder.itemView.context.resources.getIdentifier(
            currentCar.photoName,
            "drawable",
            holder.itemView.context.packageName
        )
        holder.photoImageView.setImageResource(imageResource)
    }

    override fun getItemCount() = cars.size

    fun updateData(newCars: List<Car>) {
        cars = newCars
        notifyDataSetChanged()
    }
}