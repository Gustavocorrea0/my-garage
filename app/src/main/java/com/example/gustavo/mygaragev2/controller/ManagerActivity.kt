package com.example.gustavo.mygaragev2.controller

import android.content.Intent
import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.gustavo.mygaragev2.databinding.ActivityManagerBinding
import com.example.gustavo.mygaragev2.model.DataStore
import com.example.gustavo.mygaragev2.model.City
import com.example.gustavo.mygaragev2.R

class ManagerActivity : AppCompatActivity() {

    private lateinit var binding: ActivityManagerBinding
    private var position = -1

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityManagerBinding.inflate(layoutInflater)

        position = intent.getIntExtra("idCity", -1)
        if (position > -1) {
            val city = DataStore.getCity(position)
            binding.txtName.setText(city.name)
            binding.txtPeople.setText(city.people.toString())
        }

        this.configureUI()

        // funcoes dos botoes
        binding.btnSave.setOnClickListener {
            val name = binding.txtName.text.toString()
            val people = binding.txtPeople.text.toString().toIntOrNull() ?: 0 // se for nulo retorna 0
            City(name, people).also { city ->
                if (position == -1) {
                    DataStore.addCity(city)
                } else {
                    DataStore.editCity(position, city)
                } //validacao de edicao

                //DataStore.addCity(city)
            }
            Intent().apply {
                this.putExtra("cityName", name)
                setResult(RESULT_OK, this)
            }
            finish()
        }

        binding.btnCancel.setOnClickListener {
            setResult(RESULT_CANCELED)
            finish()
        }
    }

    fun configureUI() {
        enableEdgeToEdge()
        setContentView(binding.root)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.layManager)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
    }
}