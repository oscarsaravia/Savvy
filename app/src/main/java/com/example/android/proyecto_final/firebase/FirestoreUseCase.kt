package com.example.android.proyecto_final.firebase

class FirestoreUseCase {

    val repo = FirebaseRepo()

    fun setUserFirestore(name:String, company:String, username:String, email:String, uid:String, image:String){
        repo.setUserData(name,company,username,email, uid, image)
    }

}