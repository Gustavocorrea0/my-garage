package com.example.gustavo.mygaragev2.controller

import android.content.Intent
import android.os.Bundle
import android.widget.ImageView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.bumptech.glide.Glide
import com.example.gustavo.mygaragev2.databinding.ActivityManagerBinding
import com.example.gustavo.mygaragev2.model.DataStore
import com.example.gustavo.mygaragev2.model.Car
import com.example.gustavo.mygaragev2.R

class CarActivity : AppCompatActivity() {

    private lateinit var binding: ActivityManagerBinding
    private var position = -1

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityManagerBinding.inflate(layoutInflater)

        // REVER PROBLEMA DE DIGITACAO DE NUMEROS
        position = intent.getIntExtra("idCar", -1)

        // MONTA O BLOCO DE LISTAGEM
        if (position > -1) {
            val car = DataStore.getCar(position)
            binding.txtCarName.setText(car.carName)
            binding.txtCarBrand.setText(car.carBrand)
            binding.txtCarPlate.setText(car.carPlate)
            binding.txtCarYear.setText(car.carYear.toString())
            binding.txtCarColor.setText(car.carColor)
            binding.txtCarFuel.setText(car.carFuel)
            binding.txtCarFipeValue.setText(car.carFipeValue.toString())
            binding.txtCarSaleValue.setText(car.carSaleValue.toString())

            Glide.with(this).load(car.carImage).into(binding.imgView)
        }

        this.configureUI()

        // funcoes dos botoes - criar carro
        binding.btnSave.setOnClickListener {
            val carName = binding.txtCarName.text.toString()
            val carBrand = binding.txtCarBrand.text.toString()
            val carPlate = binding.txtCarPlate.text.toString()
            val carYear = binding.txtCarYear.text.toString().toIntOrNull() ?: 0
            val carColor = binding.txtCarColor.text.toString()
            val carFuel = binding.txtCarFuel.text.toString()
            val carFipeValue = binding.txtCarFipeValue.text.toString().toDoubleOrNull() ?: 0.0
            val carSaleValue = binding.txtCarSaleValue.text.toString().toDoubleOrNull() ?: 0.0
            val carImage = binding.imgView.toString()

            // rever logica de atualizacao da tela inicial
            
            Car(carName, carBrand, carPlate, carYear, carColor, carFuel, carFipeValue, carSaleValue, carImage).also { car ->

                if (position == -1) {
                    DataStore.addCar(car)
                } else {
                    DataStore.editCar(position, car)
                } //validacao de edicao

                //DataStore.addCity(city)
            }
            Intent().apply {
                this.putExtra("carName", carName)
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

private fun CarActivity.findViewById(id: ImageView) {}
