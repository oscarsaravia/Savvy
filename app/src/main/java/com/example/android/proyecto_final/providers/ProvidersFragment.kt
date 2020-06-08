package com.example.android.proyecto_final.providers

import androidx.lifecycle.ViewModelProviders
import android.os.Bundle
import android.view.*
import android.view.inputmethod.EditorInfo
import android.widget.SearchView
import androidx.fragment.app.Fragment
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.findNavController
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.android.proyecto_final.R
import com.example.android.proyecto_final.databinding.InventoryFragmentBinding
import com.example.android.proyecto_final.databinding.ProvidersFragmentBinding
import com.example.android.proyecto_final.inventory.InventoryViewModel
import com.example.android.proyecto_final.network.*
import kotlinx.android.synthetic.main.content_main.*
import kotlinx.android.synthetic.main.inventory_fragment.*

class ProvidersFragment : Fragment(), OnProviderItemClickListener{

    companion object {
        fun newInstance() = ProvidersFragment()
    }

    private val viewModel by lazy { ViewModelProvider(this).get(ProvidersViewModel::class.java) }
    private lateinit var adapter: ProviderAdapter
    private lateinit var binding: ProvidersFragmentBinding
    lateinit var toolbar: Toolbar


    override fun onCreate(savedInstanceState: Bundle?) {
        setHasOptionsMenu(true)
        toolbar = (activity as AppCompatActivity).toolbar
        toolbar.setNavigationIcon(null);          // to hide Navigation icon
        super.onCreate(savedInstanceState)
    }


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {

        (activity as AppCompatActivity).supportActionBar?.title = "Proveedores"

        val binding = DataBindingUtil.inflate<ProvidersFragmentBinding>(inflater, R.layout.providers_fragment, container, false)

        adapter = activity?.let {
            ProviderAdapter(
                it
                , this)
        }!!
        binding.recyclerViewProvider.layoutManager = LinearLayoutManager(this.context)
        binding.recyclerViewProvider.adapter = adapter
        binding.recyclerViewProvider.addItemDecoration(DividerItemDecoration(this.context, DividerItemDecoration.VERTICAL))


        observeData()


        return binding.root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

    }

    fun observeData(){
        //shimmer_view_container.startShimmer()
        viewModel.fetchProductData().observe(viewLifecycleOwner, Observer {
            shimmer_view_container.visibility = View.GONE
            adapter.setListData(it)
            adapter.notifyDataSetChanged()
        })
    }

    override fun onItemClick(item: Provider, position: Int) {
        CurrentProviderInfo.currentName = item.name
        CurrentProviderInfo.currentEmail = item.email
        CurrentProviderInfo.currentPhone = item.phone
        CurrentProviderInfo.currentId = item.uid

        view?.findNavController()?.navigate(R.id.action_providersFragment_to_selectedProviderFragment)
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {

        inflater!!.inflate(R.menu.menu_search, menu)
        super.onCreateOptionsMenu(menu, inflater)

        val searchItem = menu.findItem(R.id.action_search)
        val searchView: SearchView = searchItem.actionView as SearchView
        searchView.imeOptions = EditorInfo.IME_ACTION_DONE

        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                return false
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                adapter.getFilter()?.filter(newText)
                return false
            }
        })
    }

}