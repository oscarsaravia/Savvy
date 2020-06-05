package com.example.android.proyecto_final.inventory

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.navigation.findNavController
import com.example.android.proyecto_final.R
import com.example.android.proyecto_final.databinding.EditProductFragmentBinding
import com.example.android.proyecto_final.databinding.InventoryFragmentBinding
import com.example.android.proyecto_final.network.CurrentProductInfo
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase


class EditProductFragment : Fragment() {

    private val db = Firebase.firestore

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val binding = DataBindingUtil.inflate<EditProductFragmentBinding>(inflater,
            R.layout.edit_product_fragment, container, false)

        binding.nameInput.setText(CurrentProductInfo.currentName)
        binding.quantityInput.setText(CurrentProductInfo.currentQuantity)
        binding.priceInput.setText(CurrentProductInfo.currentPrice)
        binding.providerInput.setText(CurrentProductInfo.currentProvider)

        binding.add.setOnClickListener {
            val newproduct = hashMapOf(
                "name" to binding.nameInput.text.toString(),
                "price" to binding.priceInput.text.toString(),
                "provider" to binding.providerInput.text.toString(),
                "quantity" to binding.quantityInput.text.toString()
            )
            FirebaseAuth.getInstance().currentUser?.uid?.let { it1 ->
                db.collection("users").document(
                    it1
                ).collection("products").document(CurrentProductInfo.currentId).update(newproduct as Map<String, Any>)
            }

            CurrentProductInfo.currentName = newproduct["name"].toString()
            CurrentProductInfo.currentQuantity = newproduct["quantity"].toString()
            CurrentProductInfo.currentProvider = newproduct["provider"].toString()
            CurrentProductInfo.currentPrice = newproduct["price"].toString()

            view?.findNavController()?.navigate(R.id.action_editProductFragment_to_inventoryFragment)

        }

        return binding.root
    }
}