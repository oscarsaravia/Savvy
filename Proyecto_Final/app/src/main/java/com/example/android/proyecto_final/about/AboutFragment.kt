package com.example.android.proyecto_final.about

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import com.example.android.proyecto_final.R
import com.example.android.proyecto_final.databinding.AboutFragmentBinding


/**
 * A simple [Fragment] subclass.
 */
class AboutFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        //Set title
        (activity as AppCompatActivity).supportActionBar?.title = "@string/about_title"

        //Data Binding
        val binding = DataBindingUtil.inflate<AboutFragmentBinding>(inflater,
            R.layout.about_fragment, container, false)

        return binding.root

    }

}
