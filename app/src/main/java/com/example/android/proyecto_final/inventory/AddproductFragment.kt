package com.example.android.proyecto_final.inventory

import android.os.Bundle
import android.text.TextUtils
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
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import kotlinx.android.synthetic.main.content_main.*


class AddproductFragment : Fragment() {

    private val db = Firebase.firestore

    lateinit var toolbar: Toolbar

    override fun onCreate(savedInstanceState: Bundle?) {
        setHasOptionsMenu(true)
        toolbar = (activity as AppCompatActivity).toolbar
        toolbar.setNavigationIcon(null);          // to hide Navigation icon
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {

        (activity as AppCompatActivity).supportActionBar?.title = "Agregar un producto"
//        (activity as AppCompatActivity).supportActionBar?.hide()

        val binding = DataBindingUtil.inflate<AddproductFragmentBinding>(inflater, R.layout.addproduct_fragment, container, false)

        binding.add.setOnClickListener {
            if(!TextUtils.isEmpty(binding.nameInput.text) && !TextUtils.isEmpty(binding.priceInput.text) && !TextUtils.isEmpty(binding.providerInput.text) && !TextUtils.isEmpty(binding.quantityInput.text)){

                val newproduct = hashMapOf(
                    "name" to binding.nameInput.text.toString(),
                    "price" to binding.priceInput.text.toString(),
                    "provider" to binding.providerInput.text.toString(),
                    "quantity" to binding.quantityInput.text.toString()
                )
                FirebaseAuth.getInstance().currentUser?.uid?.let { it1 ->
                    db.collection("users").document(
                        it1
                    ).collection("products").add(newproduct)
                }
                view?.findNavController()?.navigate(R.id.action_addproductFragment_to_inventoryFragment)
            }

        }

        return binding.root
    }


}