package com.example.android.proyecto_final.register


import android.os.Bundle
import android.text.Editable
import android.text.TextUtils
import android.text.TextWatcher
import android.util.Patterns
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.findNavController
import com.example.android.proyecto_final.R
import com.example.android.proyecto_final.databinding.RegisterFragmentBinding
import com.example.android.proyecto_final.firebase.FirestoreViewModel
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
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
    private lateinit var db: FirebaseFirestore
    private lateinit var database: FirebaseDatabase
    private lateinit var auth: FirebaseAuth
    private lateinit var FviewModel: FirestoreViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        setHasOptionsMenu(false)
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {

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
        auth = FirebaseAuth.getInstance()


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


        if (!TextUtils.isEmpty(name) && !TextUtils.isEmpty(email) && !TextUtils.isEmpty(password) && !TextUtils.isEmpty(username) && !TextUtils.isEmpty(company)) {
            if(password.length >= 5){
                if (email.isValidEmail()){
                    activity?.let {
                        auth.createUserWithEmailAndPassword(email, password)
                            .addOnCompleteListener(it) { task ->
                                if (task.isComplete) {
                                    val user:FirebaseUser?=auth.currentUser
                                    FviewModel.crearUsuario(nametxt.text.toString(), companytxt.text.toString(), usernametxt.text.toString(), emailtxt.text.toString(), user?.uid.toString())
                                    action()

                                } else {
                                    Toast.makeText(activity, "ERROR", Toast.LENGTH_SHORT).show()
                                }
                            }
                    }
                } else {
                    Toast.makeText(activity, "Se require un correo electronico valido.", Toast.LENGTH_LONG).show()
                }
            } else{
                Toast.makeText(activity, "Se requieren como minimo 5 caracteres de contrasena.", Toast.LENGTH_LONG).show()
            }

        } else {
            Toast.makeText(activity, "Se requieren todos los campos llenos.", Toast.LENGTH_LONG).show()
        }
    }

    private fun action() {
        view?.findNavController()?.navigate(R.id.action_registerFragment_to_loginFragment)
    }

    private fun onSubmitClicked()
    {
        val password = passwordtxt.text.toString()
        if(4 > password.length)
        {
            passwordtxt.error = "Se requieren 5 caracteres en la contrasena.";
            return;
        }

        //continue processing

    }
}





    

