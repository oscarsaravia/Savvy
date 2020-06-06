package com.example.android.proyecto_final.about

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import com.example.android.proyecto_final.R
import com.example.android.proyecto_final.databinding.AboutFragmentBinding
import kotlinx.android.synthetic.main.content_main.*


class AboutFragment : Fragment() {

    override fun onCreate(savedInstanceState: Bundle?) {
        setHasOptionsMenu(false)
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {

        //Set title
        (activity as AppCompatActivity).supportActionBar?.title = "@Acerca de la aplicación"
        //Data Binding
        val binding = DataBindingUtil.inflate<AboutFragmentBinding>(inflater,
            R.layout.about_fragment, container, false)

        val btnSupport = binding.btnSupport

        btnSupport.setOnClickListener {
            val url : String = "https://danielavillamar.wixsite.com/savvy"
            val intent : Intent = Intent(Intent.ACTION_VIEW)
            intent.setData(Uri.parse(url))
            startActivity(intent)
            Toast.makeText(activity, "Redirigiendo a pagina de soporte...", Toast.LENGTH_SHORT).show()
        }

        return binding.root

    }

}
