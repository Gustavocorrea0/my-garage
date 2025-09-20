package com.example.gustavo.mygaragev2.model

// controle o armazenamento e manipulacao de dados (CRUD)

object DataStore {
    val cities: MutableList<City> = arrayListOf()

    // informacoes iniciais
    init {
        cities.add(City("Sao Paulo", 12000000))
        cities.add(City("Curitiba", 1800000))
        cities.add(City("Terra Boa", 16000))
        cities.add(City("Cianorte", 80000))
    }

    // retornar cidade
    fun getCity(position: Int): City {
        return cities[position]
    }

    // criar cidade
    fun addCity(city: City) {
        cities.add(city)
    }

    // editar cidade
    fun editCity(position: Int, city: City) {
        cities.set(position, city)
    }

    // deletar cidade
    fun delCity(position: Int){
        cities.removeAt(position) // remover a partir da posicao e nao do objeto
    }

}