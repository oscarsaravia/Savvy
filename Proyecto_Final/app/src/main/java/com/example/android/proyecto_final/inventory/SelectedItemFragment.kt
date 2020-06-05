package com.example.android.proyecto_final.inventory

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.navigation.findNavController
import com.example.android.proyecto_final.R
import com.example.android.proyecto_final.databinding.AboutFragmentBinding
import com.example.android.proyecto_final.databinding.SelectedItemFragmentBinding
import com.example.android.proyecto_final.network.CurrentProductInfo
import com.example.android.proyecto_final.providers.CurrentProviderInfo
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase


class SelectedItemFragment : Fragment() {

    private val db = Firebase.firestore

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        (activity as AppCompatActivity).supportActionBar?.title = CurrentProductInfo.currentName

        val binding = DataBindingUtil.inflate<SelectedItemFragmentBinding>(inflater,
            R.layout.selected_item_fragment, container, false)

        binding.currentName.text = CurrentProductInfo.currentName
        binding.currentPrice.text = CurrentProductInfo.currentPrice
        binding.currentProvider.text = CurrentProductInfo.currentProvider
        binding.currentQuantity.text = CurrentProductInfo.currentQuantity

        binding.editProduct.setOnClickListener {
            view?.findNavController()?.navigate(R.id.action_selectedItemFragment_to_editProductFragment)
        }

        binding.deletePruduct.setOnClickListener {

            FirebaseAuth.getInstance().currentUser?.uid?.let { it1 ->
                db.collection("users").document(
                    it1
                ).collection("products").document(CurrentProductInfo.currentId).delete()
            }

            view?.findNavController()?.navigate(R.id.action_selectedItemFragment_to_inventoryFragment)
        }

        return binding.root

    }


}