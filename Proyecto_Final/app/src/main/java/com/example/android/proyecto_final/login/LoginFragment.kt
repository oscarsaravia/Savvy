package com.example.android.proyecto_final.login

import androidx.lifecycle.ViewModelProviders
import android.os.Bundle
import android.text.TextUtils
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.findNavController

import com.example.android.proyecto_final.R
import com.example.android.proyecto_final.databinding.LoginFragmentBinding
import com.example.android.proyecto_final.firebase.FirestoreViewModel
import com.google.firebase.auth.FirebaseAuth

class LoginFragment : Fragment() {

    companion object {
        fun newInstance() = LoginFragment()
    }

    private lateinit var viewModel: LoginViewModel
    private lateinit var txtUser: EditText
    private lateinit var txtPassword: EditText
    private lateinit var auth:FirebaseAuth


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        //Data Binding
        val binding = DataBindingUtil.inflate<LoginFragmentBinding>(inflater,
            R.layout.login_fragment, container, false)

        txtUser = binding.userText
        txtPassword = binding.passwordText
        auth = FirebaseAuth.getInstance()


        //Login button on click listener
        binding.loginButton.setOnClickListener{v:View->
            loginUser()
        }
        binding.createAccountButton.setOnClickListener {v:View->
            v.findNavController().navigate(R.id.action_loginFragment_to_registerFragment)
        }

        return binding.root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProviders.of(this).get(LoginViewModel::class.java)
        // TODO: Use the ViewModel
    }

    private fun loginUser(){
        val user:String = txtUser.text.toString()
        val password:String = txtPassword.text.toString()

        if(!TextUtils.isEmpty(user) && !TextUtils.isEmpty(password)){
            activity?.let {
                auth.signInWithEmailAndPassword(user, password)
                    .addOnCompleteListener(it){
                        task ->

                        if(task.isSuccessful){
                            txtPassword.setText("")
                            view?.findNavController()?.navigate(R.id.action_loginFragment_to_homeFragment)
                        }
                        else{
                            Toast.makeText(activity, "Error en la autenticación", Toast.LENGTH_LONG).show()
                            txtPassword.setText("")
                        }

                    }
            }

        }
    }

}
