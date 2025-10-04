package com.example.gustavo.mygaragev2.model

// controle o armazenamento e manipulacao de dados (CRUD)

object DataStore {
    val cars: MutableList<Car> = arrayListOf()

    // informacoes iniciais
    init {
        cars.add(Car("RS3", "Audi", "AAA1A111", 2019,
            "Metallic Gray","Gasoline",422000.0,434999.9,
            "https://motorshow.com.br/wp-content/uploads/sites/2/2019/01/10_ms423_audi-rs-3-sportback1.jpg"))

        cars.add(Car("A45 AMG", "Mercedes-Benz", "BBB1B111", 2020,
            "Red","Gasoline", 359070.0,379070.0,
            "https://garagemde0a100.com.br/wp-content/uploads/2024/11/mercedes-amg-a-45-4matic-2019-1.jpg"))

        cars.add(Car("Astra GSI 16V", "Chevrolet", "CCC1C111",
            0, "Red Apple","Gasoline",0.0,0.0,
            "https://i.ytimg.com/vi/sEYpa8GfwGY/maxresdefault.jpg"))
    }

    // retornar carro
    fun getCar(position: Int): Car {
        return cars[position]
    }

    // criar carro
    fun addCar(car: Car) {
        cars.add(car)
    }

    // editar carro
    fun editCar(position: Int, car: Car) {
        cars.set(position, car)
    }

    // deletar carro
    fun delCar(position: Int){
        cars.removeAt(position) // remover a partir da posicao e nao do objeto
    }

}