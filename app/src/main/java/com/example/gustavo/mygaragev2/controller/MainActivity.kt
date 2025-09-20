package com.example.gustavo.mygaragev2.controller

import android.content.Intent
import android.os.Bundle
import android.view.GestureDetector
import android.view.MotionEvent
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
import com.example.gustavo.mygaragev2.view.CityAdapter
import com.example.gustavo.mygaragev2.databinding.ActivityMainBinding
import com.example.gustavo.mygaragev2.model.DataStore

class MainActivity : AppCompatActivity() {

    private lateinit var adapter: CityAdapter
    private lateinit var binding: ActivityMainBinding
    private lateinit var gesture: GestureDetector

    private val addCityForResult = registerForActivityResult(ActivityResultContracts.StartActivityForResult()) {
            result -> if (result.resultCode == RESULT_OK) {
        adapter.notifyDataSetChanged()
        result.data?.let { data ->
            data.getStringExtra("cityName")?.let {
                    name -> this.showMessage("Cidade $name criada com sucesso!!!")
            }
        }
    }
    }

    private val editCityForResult = registerForActivityResult(ActivityResultContracts.StartActivityForResult()) {
            result -> if (result.resultCode == RESULT_OK) {
        adapter.notifyDataSetChanged()
        result.data?.let { data ->
            data.getStringExtra("cityName")?.let {
                    name -> this.showMessage("Cidade $name alterada com sucesso!!!")
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
    }

    fun configureFab() {
        binding.fab.setOnClickListener {
            Intent(this, ManagerActivity::class.java).also { i ->
                startActivity(i)
            }
        }
    }

    fun configureGestures(){
        gesture = GestureDetector(this, object:GestureDetector.SimpleOnGestureListener() {
            override fun onSingleTapConfirmed(e: MotionEvent): Boolean {
                binding.rcvCities.findChildViewUnder(e.x, e.y).also { view ->
                    view?.let {
                            child -> binding.rcvCities.getChildAdapterPosition(child).also { position ->
                        val city = DataStore.getCity(position)
                        Intent(this@MainActivity, ManagerActivity::class.java).also { i ->
                            i.putExtra("idCity", position)
                            editCityForResult.launch(i)
                        }
                        //Toast.makeText(this@MainActivity, city.name, Toast.LENGTH_LONG).show()
                    }
                    }
                }
                return super.onSingleTapConfirmed(e)
            }

            override fun onLongPress(e: MotionEvent) {
                binding.rcvCities.findChildViewUnder(e.x, e.y).also { view ->
                    view?.let {
                            child -> binding.rcvCities.getChildAdapterPosition(child).also { position ->
                        val city = DataStore.getCity(position)
                        AlertDialog.Builder(this@MainActivity).also {  dialog ->
                            dialog.setTitle("Cities App")
                            dialog.setMessage("Tem certeza que deseja excluir a cidade: ${city.name}")
                            dialog.setPositiveButton(android.R.string.ok) { _, _ ->
                                DataStore.delCity(position)
                                this@MainActivity.showMessage("Cidade ${city.name} removida com sucesso!!!")
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

    fun configureRecycleViewWithEvents(){
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
            adapter = CityAdapter(DataStore.cities)
            binding.rcvCities.adapter = adapter // populando a recicle view com dados
        }
    }

    fun showMessage(message: String) {
        Snackbar.make(binding.main, message, Snackbar.LENGTH_LONG).show()
    }

}