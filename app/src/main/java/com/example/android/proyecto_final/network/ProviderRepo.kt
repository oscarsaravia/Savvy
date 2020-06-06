package com.example.android.proyecto_final.network

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore

class ProviderRepo {

    fun getProviderData(): LiveData<MutableList<Provider>> {
        val mutableData = MutableLiveData<MutableList<Provider>>()
        Log.d("AQUI", FirebaseAuth.getInstance().currentUser?.uid)
        FirebaseAuth.getInstance().currentUser?.uid?.let {
            FirebaseFirestore.getInstance().collection("users").document(
                it
            ).collection("providers").get().addOnSuccessListener {result->
                val listData = mutableListOf<Provider>()
                for(document in result){
                    val name = document.getString("name")
                    val phone = document.getString("phone")
                    val email = document.getString("email")
                    val uid = document.id
                    val provider =
                        Provider(
                            name!!,
                            phone!!,
                            email!!,
                            uid!!
                        )
                    listData.add(provider)
                }
                mutableData.value = listData
            }
        }
        return mutableData
    }

}