package com.example.android.proyecto_final.firebase

import android.util.Log
import android.widget.Toast
import com.google.firebase.firestore.FirebaseFirestore

class FirebaseRepo {

    val db = FirebaseFirestore.getInstance()

    fun setUserData(name:String, company:String, username:String, email:String){

        val userHashMap = hashMapOf(
            "name" to name,
            "company" to company,
            "username" to username,
            "email" to email)

        db.collection("users")
            .add(userHashMap as Map<String, Any>).addOnCompleteListener{
                if(it.isSuccessful){

                }
            }

    }

}
