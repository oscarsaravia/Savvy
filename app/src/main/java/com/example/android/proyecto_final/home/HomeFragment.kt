package com.example.android.proyecto_final.home
import android.os.Bundle
import android.view.*
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.findNavController
import com.example.android.proyecto_final.MainActivity
import com.example.android.proyecto_final.R
import com.example.android.proyecto_final.databinding.HomeFragmentBinding
import kotlinx.android.synthetic.main.content_main.*


class HomeFragment : Fragment() {

    companion object {
        fun newInstance() =
            HomeFragment()
    }

    private lateinit var viewModel: HomeViewModel
    lateinit var toolbar: Toolbar

    override fun onCreate(savedInstanceState: Bundle?) {
        setHasOptionsMenu(true)
//        toolbar = (activity as AppCompatActivity).toolbar
//        toolbar.navigationIcon = getResources().getDrawable(R.drawable.ic_menu)
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        toolbar = (activity as AppCompatActivity).toolbar
        toolbar.navigationIcon = getResources().getDrawable(R.drawable.ic_menu)

        //Set fragment title
        (activity as AppCompatActivity).supportActionBar?.title = "Inicio"
        (activity as AppCompatActivity).supportActionBar?.show()

        //Data Binding
        val binding = DataBindingUtil.inflate<HomeFragmentBinding>(inflater,
            R.layout.home_fragment, container, false)

        binding.inventoryButton.setOnClickListener{
            view?.findNavController()?.navigate(R.id.action_homeFragment_to_inventoryFragment)
        }
        binding.addProductoButton.setOnClickListener{
            view?.findNavController()?.navigate(R.id.action_homeFragment_to_addproductFragment)
        }
        binding.searchButton2.setOnClickListener {
            view?.findNavController()?.navigate(R.id.action_homeFragment_to_providersFragment)
        }
        binding.addProviderButton.setOnClickListener {
            view?.findNavController()?.navigate(R.id.action_homeFragment_to_addproviderFragment)
        }

        return binding.root
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.nav_menu, menu)
        super.onCreateOptionsMenu(menu, inflater)
    }


    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProviders.of(this).get(HomeViewModel::class.java)
        // TODO: Use the ViewModel
    }

}
