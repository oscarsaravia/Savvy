package com.example.android.proyecto_final.register

import android.content.ContentValues.TAG
import android.os.Bundle
import android.text.TextUtils
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.findNavController


import com.example.android.proyecto_final.R
import com.example.android.proyecto_final.databinding.RegisterFragmentBinding
import com.example.android.proyecto_final.firebase.FirestoreViewModel
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.firestore.FirebaseFirestore

/**
 * A simple [Fragment] subclass.
 */
class RegisterFragment : Fragment() {

    private lateinit var nametxt: EditText
    private lateinit var passwordtxt: EditText
    private lateinit var emailtxt: EditText
    private lateinit var usernametxt: EditText
    private lateinit var companytxt: EditText
    private lateinit var binding: RegisterFragmentBinding
    private lateinit var dbReference: DatabaseReference
    private lateinit var db:FirebaseFirestore
    private lateinit var database: FirebaseDatabase
    private lateinit var auth: FirebaseAuth
    private lateinit var FviewModel: FirestoreViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        //Set fragment title
        (activity as AppCompatActivity).supportActionBar?.title = "Registro"

        //Data Binding
        binding = DataBindingUtil.inflate<RegisterFragmentBinding>(
            inflater,
            R.layout.register_fragment, container, false
        )

        db = FirebaseFirestore.getInstance()
        FviewModel = ViewModelProvider(this).get(FirestoreViewModel::class.java)
        nametxt = binding.nameEditText
        passwordtxt = binding.passwordEditText
        emailtxt = binding.emailEditText
        usernametxt = binding.usernameEditText
        companytxt = binding.companyEditText
        database = FirebaseDatabase.getInstance()
        auth = FirebaseAuth.getInstance()
        dbReference = database.reference.child("User")

        binding.button.setOnClickListener { v: View ->
            register(v)
        }

        return binding.root
    }

    private fun register(view: View) {
        createNewAccount()
    }

    private fun createNewAccount() {
        val name = nametxt.text.toString()
        val email = emailtxt.text.toString()
        val password = passwordtxt.text.toString()
        val username = usernametxt.text.toString()
        val company = companytxt.text.toString()

        if (!TextUtils.isEmpty(name) && !TextUtils.isEmpty(email) && !TextUtils.isEmpty(password) && !TextUtils.isEmpty(username) && !TextUtils.isEmpty(company)
        ) {
            FviewModel.crearUsuario(name, company, username, email)
            activity?.let {
                auth.createUserWithEmailAndPassword(email, password)
                    .addOnCompleteListener(it) { task ->
                        if (task.isComplete) {
                            //Toast.makeText(activity, "Registrado correctamente", Toast.LENGTH_LONG) .show()
                            //val userHashMap = hashMapOf(
                             //   "name" to name,
                              //  "company" to company,
                                //"username" to username,
                                //"email" to email)
                            //db.collection("users")
                              //  .add(userHashMap)
                                //.addOnSuccessListener { documentReference ->
                                  //  Log.d("OSCAR", "DocumentSnapshot added with ID: ${documentReference.id}")
                                //}
                                //.addOnFailureListener { e ->
                                 //   Log.w("OSCAR", "Error adding document", e)
                                //}

                            //val user: FirebaseUser? = auth.currentUser
                            //verifyEmail(user)
                            //val userBD = user?.uid?.let { it1 -> dbReference.child(it1) }
                            //userBD?.child("Name")?.setValue(name)
                            //userBD?.child("Company")?.setValue(company)
                            action()

                        } else {
                            Toast.makeText(activity, "ERROR", Toast.LENGTH_SHORT).show()
                        }
                    }
            }
        } else {
            Toast.makeText(activity, "Llene todos los campos", Toast.LENGTH_LONG).show()
        }
    }

    private fun action() {
        view?.findNavController()?.navigate(R.id.action_registerFragment_to_loginFragment)
    }

    private fun verifyEmail(user: FirebaseUser?) {
        activity?.let {
            user?.sendEmailVerification()
                ?.addOnCompleteListener(it) { task ->

                    if (task.isComplete) {
                        Toast.makeText(
                            activity,
                            "E-mail de verificación enviado",
                            Toast.LENGTH_LONG
                        ).show()
                    } else {
                        Toast.makeText(
                            activity,
                            "Error al enviar correo de verificación",
                            Toast.LENGTH_LONG
                        ).show()
                    }

                }
        }
    }
}


    

