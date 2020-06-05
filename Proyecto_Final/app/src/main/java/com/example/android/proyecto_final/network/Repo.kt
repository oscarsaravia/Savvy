package com.example.android.proyecto_final.network

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore

class Repo {

    fun getProductData():LiveData<MutableList<Product>>{
        val mutableData = MutableLiveData<MutableList<Product>>()
        Log.d("AQUI", FirebaseAuth.getInstance().currentUser?.uid)
        FirebaseAuth.getInstance().currentUser?.uid?.let {
            FirebaseFirestore.getInstance().collection("users").document(
                it
            ).collection("products").get().addOnSuccessListener {result->
                val listData = mutableListOf<Product>()
                for(document in result){
                    val name = document.getString("name")
                    val price = document.getString("price")
                    val provider = document.getString("provider")
                    val quantity = document.getString("quantity")
                    val uid = document.id
                    val product =
                        Product(
                            name!!,
                            quantity!!,
                            price!!,
                            provider!!,
                            uid!!
                        )
                    listData.add(product)
                }
                mutableData.value = listData
            }
        }
        return mutableData
    }

}