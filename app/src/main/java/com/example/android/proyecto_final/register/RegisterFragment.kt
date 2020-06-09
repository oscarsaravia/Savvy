package com.example.android.proyecto_final.register


import android.app.Activity
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore
import android.text.TextUtils
import android.util.Log
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
import com.example.android.proyecto_final.network.CurrentUserInfo
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.storage.FirebaseStorage
import java.util.*
import java.util.regex.Pattern


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
    private lateinit var imageUrl: String

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
        imageUrl = CurrentUserInfo.photoUrl




        binding.button.setOnClickListener { v: View ->
            register(v)

        }

        binding.submitPhoto.setOnClickListener {
            val intent = Intent(Intent.ACTION_PICK)
            intent.type = "image/*"
            startActivityForResult(intent, 0)
        }

        return binding.root
    }

    var selectedPhotoUri: Uri? = null

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == 0 && resultCode == Activity.RESULT_OK && data != null){
            selectedPhotoUri = data.data
            val bitmap = MediaStore.Images.Media.getBitmap(activity?.contentResolver, selectedPhotoUri)
            binding.uploadedImage.setImageBitmap(bitmap)
            binding.submitPhoto.alpha = 0f
        }

    }

    private fun uploadImage(){
        if (selectedPhotoUri == null) return
        val fileName = UUID.randomUUID().toString()
        CurrentUserInfo.fileName = fileName
        val ref = FirebaseStorage.getInstance().getReference("/images/$fileName")
        ref.putFile(selectedPhotoUri!!)
            .addOnSuccessListener {
                ref.downloadUrl.addOnSuccessListener {
                    CurrentUserInfo.photoUrl = it.toString()

                }
            }.addOnFailureListener{
                Toast.makeText(activity, "Error", Toast.LENGTH_LONG).show()
            }
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
                if (isEmailValid(email)){
                    activity?.let {
                        auth.createUserWithEmailAndPassword(email, password)
                            .addOnCompleteListener(it) { task ->
                                if (task.isComplete) {
                                    val user:FirebaseUser?=auth.currentUser
                                    uploadImage()
                                    val ref = FirebaseStorage.getInstance().getReference("/images/${CurrentUserInfo.fileName}")
                                    ref.downloadUrl.addOnSuccessListener {
                                        CurrentUserInfo.photoUrl = it.toString()
                                    }


                                    if(CurrentUserInfo.fileName != ""){
                                        FviewModel.crearUsuario(nametxt.text.toString(), companytxt.text.toString(), usernametxt.text.toString(), emailtxt.text.toString(), user?.uid.toString(), CurrentUserInfo.fileName)
                                        action()
                                    }
                                    else{
                                        Toast.makeText(activity, "Seleccione una imágen", Toast.LENGTH_LONG).show()
                                    }

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

    private fun isEmailValid(email: String): Boolean {
        return Pattern.compile(
            "^(([\\w-]+\\.)+[\\w-]+|([a-zA-Z]|[\\w-]{2,}))@"
                    + "((([0-1]?[0-9]{1,2}|25[0-5]|2[0-4][0-9])\\.([0-1]?"
                    + "[0-9]{1,2}|25[0-5]|2[0-4][0-9])\\."
                    + "([0-1]?[0-9]{1,2}|25[0-5]|2[0-4][0-9])\\.([0-1]?"
                    + "[0-9]{1,2}|25[0-5]|2[0-4][0-9]))|"
                    + "([a-zA-Z]+[\\w-]+\\.)+[a-zA-Z]{2,4})$"
        ).matcher(email).matches()
    }

    private fun setLink(source:String){
        imageUrl = source
    }





}





    

