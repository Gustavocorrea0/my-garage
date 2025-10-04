package com.example.gustavo.mygaragev2.model

import android.media.Image

/*
* nome - name
* marca - brand
* placa - plate
* ano - year
* cor - color
* combustivel - fuel
* valor fipe - value fipe
* valor venda - sale value
* */

// modelo do objeto(neste caso cidade) com parametros
// getters e setter ja estao definidos
class Car (
    var carName: String,
    var carBrand: String,
    var carPlate: String,
    var carYear: Int,
    var carColor: String,
    var carFuel: String,
    var carFipeValue: Double,
    var carSaleValue: Double,
    var carImage: String
) {
    //constructor(): this("", 0)
    //constructor(name: String): this(name, 0)
}