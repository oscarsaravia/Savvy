package com.example.android.proyecto_final.providers

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.android.proyecto_final.network.Provider
import com.example.android.proyecto_final.network.ProviderRepo


class ProvidersViewModel : ViewModel() {
    val repo = ProviderRepo()

    fun fetchProductData(): LiveData<MutableList<Provider>> {
        val mutableData = MutableLiveData<MutableList<Provider>>()
        repo.getProviderData().observeForever{
            mutableData.value = it
        }
        return mutableData
    }
}