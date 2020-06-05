package com.example.android.proyecto_final.providers

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.navigation.findNavController
import com.example.android.proyecto_final.R
import com.example.android.proyecto_final.databinding.SelectedItemFragmentBinding
import com.example.android.proyecto_final.databinding.SelectedProviderFragmentBinding
import com.example.android.proyecto_final.network.CurrentProductInfo
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase

class SelectedProviderFragment : Fragment() {

    private val db = Firebase.firestore

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        (activity as AppCompatActivity).supportActionBar?.title = CurrentProviderInfo.currentName

        val binding = DataBindingUtil.inflate<SelectedProviderFragmentBinding>(inflater,
            R.layout.selected_provider_fragment, container, false)

        binding.currentName.text = CurrentProviderInfo.currentName
        binding.currentQuantity.text = CurrentProviderInfo.currentPhone
        binding.currentPrice.text = CurrentProviderInfo.currentEmail

        binding.editProduct.setOnClickListener {
            view?.findNavController()?.navigate(R.id.action_selectedProviderFragment_to_editProviderFragment)
        }

        binding.deletePruduct.setOnClickListener {

            FirebaseAuth.getInstance().currentUser?.uid?.let { it1 ->
                db.collection("users").document(
                    it1
                ).collection("providers").document(CurrentProviderInfo.currentId).delete()
            }

            view?.findNavController()?.navigate(R.id.action_selectedProviderFragment_to_providersFragment)
        }

        return binding.root
    }

}