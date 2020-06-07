package com.example.android.proyecto_final

import java.sql.ClientInfoStatus

class Product {
    var name : String? = null
    var price : String? = null
    var provider : String? = null
    var quantity : String? = null

    constructor(){

    }

    constructor(name: String?, status: String?, price: String?, provider: String?, quantity: String?){
        this.name = name
        this.price = price
        this.provider = provider
        this.quantity = quantity
    }

}