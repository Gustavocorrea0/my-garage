package com.example.gustavo.mygaragev2.controller

import android.content.Intent
import android.net.Uri
import android.os.Build
import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.activity.result.contract.ActivityResultContracts
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import androidx.core.net.toUri
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.bumptech.glide.Glide
import com.example.gustavo.mygaragev2.databinding.ActivityManagerBinding
import com.example.gustavo.mygaragev2.model.Car
import com.example.gustavo.mygaragev2.model.DataStore
import com.google.android.material.snackbar.Snackbar
import java.util.Calendar

class CarActivity : AppCompatActivity() {

    private lateinit var binding: ActivityManagerBinding
    private var position = -1
    private var currentImageUri: String = ""
    private var thisYear = getCurrentYear()

    // Contrato para abrir a galeria e pegar a URI da imagem
    private val getImage = registerForActivityResult(ActivityResultContracts.GetContent()) { uri: Uri? ->
        if (uri != null) {
            currentImageUri = uri.toString()
            Glide.with(this).load(uri).into(binding.imgView)
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        binding = ActivityManagerBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Ajusta UI para bordas
        configureUI()

        // Recupera posição recebida por Intent
        position = intent.getIntExtra("idCar", -1)

        // Clique na imagem → abre galeria
        binding.imgView.setOnClickListener {
            getImage.launch("image/*")
        }

        // Se for edição de carro já existente
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

            if (car.carImage.isNotEmpty()) {
                currentImageUri = car.carImage
                Glide.with(this).load(car.carImage.toUri()).into(binding.imgView)
            }
        }

        // Botão salvar
        binding.btnSave.setOnClickListener {
            val carName = binding.txtCarName.text.toString()
            val carBrand = binding.txtCarBrand.text.toString()
            val carPlate = binding.txtCarPlate.text.toString()
            val carYear = binding.txtCarYear.text.toString().toIntOrNull() ?: 0
            val carColor = binding.txtCarColor.text.toString()
            val carFuel = binding.txtCarFuel.text.toString()
            val carFipeValue = binding.txtCarFipeValue.text.toString().toDoubleOrNull() ?: 0.0
            val carSaleValue = binding.txtCarSaleValue.text.toString().toDoubleOrNull() ?: 0.0
            var carIsValid: Boolean = true

            if (carName == "") {
                carIsValid = false
                this.showMessage("Insert a valid Name")
            }
            if (carBrand == "") {
                carIsValid = false
                this.showMessage("Insert a valid Brand")
            }
            if (carPlate == "" || carPlate.length > 8 || carPlate.length < 7) {
                carIsValid = false
                this.showMessage("Insert a valid License Plate")
            }
            if (carYear == 0 || carYear > (thisYear + 1) || carYear < 1886) {
                carIsValid = false
                this.showMessage("Insert a valid Year")
            }

            if (carColor == "") {
                carIsValid = false
                this.showMessage("Insert a valid Color")
            }

            if (carFuel == "") {
                carIsValid = false
                this.showMessage("Insert a valid Fuel")
            }

            if (carSaleValue == 0.0) {
                carIsValid = false
                this.showMessage("Insert Sales Value")
            }

            val car = Car(
                carName,
                carBrand,
                carPlate,
                carYear,
                carColor,
                carFuel,
                carFipeValue,
                carSaleValue,
                currentImageUri
            )

            // erro ao adicionar validar campos
            if (carIsValid && position == -1) {
                DataStore.addCar(car)
                Intent().apply {
                    this.putExtra("carName", carName)
                    setResult(RESULT_OK, this)
                }
                finish()
            } else if (carIsValid && position > -1) {
                DataStore.editCar(position, car)
                Intent().apply {
                    this.putExtra("carName", carName)
                    setResult(RESULT_OK, this)
                }
                finish()
            }

        }

        // Botão cancelar
        binding.btnCancel.setOnClickListener {
            setResult(RESULT_CANCELED)
            finish()
        }

    }

    private fun configureUI() {
        enableEdgeToEdge()
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(binding.layManager.id)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
    }

    fun showMessage(message: String) {
        Snackbar.make(binding.root, message, Snackbar.LENGTH_LONG).show()
    }

    fun getCurrentYear(): Int {
        val calendar = Calendar.getInstance()
        return calendar.get(Calendar.YEAR)
    }
}
