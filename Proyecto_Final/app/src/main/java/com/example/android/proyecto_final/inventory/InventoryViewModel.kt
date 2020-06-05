package com.example.android.proyecto_final.inventory

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.android.proyecto_final.network.Product
import com.example.android.proyecto_final.network.Repo

class InventoryViewModel : ViewModel() {

    val repo = Repo()

    fun fetchProductData():LiveData<MutableList<Product>>{
        val mutableData = MutableLiveData<MutableList<Product>>()
        repo.getProductData().observeForever{
            mutableData.value = it
        }
        return mutableData
    }

}
