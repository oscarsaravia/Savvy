package com.example.android.proyecto_final.firebase

import androidx.lifecycle.ViewModel

class FirestoreViewModel: ViewModel() {

    val firestoreUseCase = FirestoreUseCase()

    fun crearUsuario(name:String, company:String, username:String, email:String, uid:String){
        firestoreUseCase.setUserFirestore(name,company,username,email,uid)

    }

}