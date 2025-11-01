package com.example.gustavo.mygaragev2.controller

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.view.GestureDetector
import android.view.MotionEvent
import android.widget.Button
import androidx.activity.enableEdgeToEdge
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.gustavo.mygaragev2.R
import com.google.android.material.snackbar.Snackbar
import com.example.gustavo.mygaragev2.view.CarAdapter
import com.example.gustavo.mygaragev2.databinding.ActivityMainBinding
import com.example.gustavo.mygaragev2.model.DataStore
import android.widget.ImageButton


class MainActivity : AppCompatActivity() {

    private lateinit var adapter: CarAdapter
    private lateinit var binding: ActivityMainBinding
    private lateinit var gesture: GestureDetector

    @SuppressLint("NotifyDataSetChanged")
    private val addCarForResult = registerForActivityResult(ActivityResultContracts.StartActivityForResult()) {
        result -> if (result.resultCode == RESULT_OK) {
            adapter.notifyDataSetChanged()
            result.data?.let { data ->
                data.getStringExtra("carName")?.let {
                        name -> this.showMessage("$name Created Successfully!")
                }
            }
        }
    }

    @SuppressLint("NotifyDataSetChanged")
    private val editCarForResult = registerForActivityResult(ActivityResultContracts.StartActivityForResult()) {
        result -> if (result.resultCode == RESULT_OK) {
            adapter.notifyDataSetChanged()
            result.data?.let { data ->
                data.getStringExtra("carName")?.let {
                        name -> this.showMessage("$name Changed Successfully!")
                }
            }
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        this.configureUI()
        this.loadRecyclerViewData()
        this.configureFab()
        this.configureGestures()
        this.configureRecycleViewWithEvents()

        val btnBack = findViewById<ImageButton>(R.id.btnBack)
        btnBack.setOnClickListener {
            val intent = Intent(this, LoginActivity::class.java)
            startActivity(intent)
            finish()
        }
    }

    fun configureFab() {
        binding.fab.setOnClickListener {
            Intent(this, CarActivity::class.java).also { i ->
                addCarForResult.launch(i)
            }
        }
    }

    fun configureGestures() {
        gesture = GestureDetector(this, object:GestureDetector.SimpleOnGestureListener() {
            override fun onSingleTapConfirmed(e: MotionEvent): Boolean {
                binding.rcvCities.findChildViewUnder(e.x, e.y).also { view ->
                    view?.let {
                            child -> binding.rcvCities.getChildAdapterPosition(child).also { position ->
                            val car = DataStore.getCar(position)
                            Intent(this@MainActivity, CarActivity::class.java).also { i ->
                                    i.putExtra("idCar", position)
                                    editCarForResult.launch(i)
                                }
                            }
                    }
                }
                return super.onSingleTapConfirmed(e)
            }

            @SuppressLint("NotifyDataSetChanged")
            override fun onLongPress(e: MotionEvent) {
                binding.rcvCities.findChildViewUnder(e.x, e.y).also { view ->
                    view?.let {
                            child -> binding.rcvCities.getChildAdapterPosition(child).also { position ->
                        val car = DataStore.getCar(position)
                        AlertDialog.Builder(this@MainActivity).also {  dialog ->
                            dialog.setTitle("MyGarage")
                            dialog.setMessage("Are you sure you want to delete the car: ${car.carName} " +
                                    "| Plate ${car.carPlate}")
                            dialog.setPositiveButton(android.R.string.ok) { _, _ ->
                                DataStore.delCar(position)
                                this@MainActivity.showMessage("${car.carName} Successfully Removed!")
                                adapter.notifyDataSetChanged()
                            }
                            dialog.setNegativeButton(android.R.string.cancel, null)
                            dialog.show()
                        }
                    }
                    }
                }
                super.onLongPress(e)
            }
        })
    }

    // REVER ESTE BLOCO
    fun configureRecycleViewWithEvents() {
        binding.rcvCities.addOnItemTouchListener(object : RecyclerView.OnItemTouchListener{
            override fun onInterceptTouchEvent( rv: RecyclerView, e: MotionEvent): Boolean {
                rv.findChildViewUnder(e.x, e.y).also { view ->
                    return (view != null && gesture.onTouchEvent(e))
                }
            }

            override fun onTouchEvent(rv: RecyclerView, e: MotionEvent) {
                TODO("Not yet implemented")
            }

            override fun onRequestDisallowInterceptTouchEvent(disallowIntercept: Boolean) {
                TODO("Not yet implemented")
            }
        })
    }

    fun configureUI(){
        enableEdgeToEdge()
        setContentView(binding.root)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
    }

    fun loadRecyclerViewData() {
        LinearLayoutManager(this).also { llm ->
            llm.orientation = LinearLayoutManager.VERTICAL
            binding.rcvCities.layoutManager = llm
            adapter = CarAdapter(DataStore.cars)
            binding.rcvCities.adapter = adapter // populando a recicle view com dados
        }
    }

    fun showMessage(message: String) {
        Snackbar.make(binding.main, message, Snackbar.LENGTH_LONG).show()
    }

}