package com.example.android.proyecto_final.providers

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.databinding.DataBindingUtil
import androidx.navigation.findNavController
import com.example.android.proyecto_final.R
import com.example.android.proyecto_final.databinding.AddproductFragmentBinding
import com.example.android.proyecto_final.databinding.AddproviderFragmentBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import kotlinx.android.synthetic.main.content_main.*


class AddproviderFragment : Fragment() {

    private val db = Firebase.firestore
    lateinit var toolbar: Toolbar


    override fun onCreate(savedInstanceState: Bundle?) {
        setHasOptionsMenu(true)
        toolbar = (activity as AppCompatActivity).toolbar
        toolbar.setNavigationIcon(null);          // to hide Navigation icon
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        (activity as AppCompatActivity).supportActionBar?.title = "Agregar un proveedor"
        val binding = DataBindingUtil.inflate<AddproviderFragmentBinding>(inflater,
            R.layout.addprovider_fragment, container, false)

        binding.add.setOnClickListener {
            val newproduct = hashMapOf(
                "name" to binding.nameInput.text.toString(),
                "email" to binding.priceInput.text.toString(),
                "phone" to binding.phoneInput.text.toString()

            )
            FirebaseAuth.getInstance().currentUser?.uid?.let { it1 ->
                db.collection("users").document(
                    it1
                ).collection("providers").add(newproduct)
            }

            view?.findNavController()?.navigate(R.id.action_addproviderFragment_to_providersFragment)
        }

        return binding.root

    }


}