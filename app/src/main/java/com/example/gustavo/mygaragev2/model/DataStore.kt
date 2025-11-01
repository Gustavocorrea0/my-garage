package com.example.gustavo.mygaragev2.model

// controle o armazenamento e manipulacao de dados (CRUD)

object DataStore {
    val cars: MutableList<Car> = arrayListOf()

    // informacoes iniciais
    init {
        cars.add(Car("RB21", "Red Bull/Honda", "MVD2X42", 2021,
            "White","Gasoline",3500000.0,3500000.0,
            "https://www.minimundi.com.br/cdn/imagens/produtos/det/miniatura-formula-1-oracle-red-bull-racing-rb19-1-max-verstappen-2023-1-43-burago-c-acrilico-38083-b.jpeg"

            ))

        cars.add(Car("RS3", "Audi", "AAA2D23", 2019,
            "Metallic Gray","Gasoline", 422000.0,434999.0,
            "https://cdn.awsli.com.br/2500x2500/2571/2571273/produto/284644765/gt434-fl-ctkws0dsre.jpg"
            ))

        cars.add(Car("911 GT3 RS", "Porsche", "FDA2D24",
            2021, "White","Gasoline",1500000.0,1700000.0,
            "https://cdn.awsli.com.br/2500x2500/1302/1302593/produto/237201775/t64mc-005-wr_a-mweogkhq9l.jpg"))
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