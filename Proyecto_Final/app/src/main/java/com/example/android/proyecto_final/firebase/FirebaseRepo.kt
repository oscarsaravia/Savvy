package com.example.android.proyecto_final.firebase

import android.util.Log
import android.widget.Toast
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase

class FirebaseRepo {

    private val db = Firebase.firestore

    fun setUserData(name:String, company:String, username:String, email:String, uid:String){
        Log.d("ENTER", "Entrando al metodo")

        val userHashMap = hashMapOf(
            "name" to name,
            "company" to company,
            "username" to username,
            "email" to email)

        db.collection("users").document(uid)
            .set(userHashMap).addOnSuccessListener {
                Log.d("HERE", "DocumentSnapshot added with ID:")
            }
            .addOnFailureListener{e->
                Log.w("HERE", "Error adding document", e)
            }

    }

}
