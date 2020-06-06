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
import com.example.android.proyecto_final.databinding.EditProductFragmentBinding
import com.example.android.proyecto_final.databinding.EditProviderFragmentBinding
import com.example.android.proyecto_final.network.CurrentProductInfo
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase


class EditProviderFragment : Fragment() {

    private val db = Firebase.firestore

    override fun onCreate(savedInstanceState: Bundle?) {
        setHasOptionsMenu(false)
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val binding = DataBindingUtil.inflate<EditProviderFragmentBinding>(inflater,
            R.layout.edit_provider_fragment, container, false)

        (activity as AppCompatActivity).supportActionBar?.hide()

        binding.nameInput.setText(CurrentProviderInfo.currentName)
        binding.phoneInput.setText(CurrentProviderInfo.currentPhone)
        binding.priceInput.setText(CurrentProviderInfo.currentEmail)

        binding.add.setOnClickListener {
            val newprovider = hashMapOf(
                "name" to binding.nameInput.text.toString(),
                "email" to binding.priceInput.text.toString(),
                "phone" to binding.phoneInput.text.toString()
            )
            FirebaseAuth.getInstance().currentUser?.uid?.let { it1 ->
                db.collection("users").document(
                    it1
                ).collection("providers").document(CurrentProviderInfo.currentId).update(newprovider as Map<String, Any>)
            }

            CurrentProviderInfo.currentName = newprovider["name"].toString()
            CurrentProviderInfo.currentPhone = newprovider["phone"].toString()
            CurrentProviderInfo.currentEmail = newprovider["email"].toString()

            view?.findNavController()?.navigate(R.id.action_editProviderFragment_to_providersFragment)

        }

        return binding.root
    }


}