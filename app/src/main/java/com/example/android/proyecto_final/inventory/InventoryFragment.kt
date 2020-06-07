package com.example.android.proyecto_final.inventory

import android.os.Bundle
import android.view.*
import android.view.inputmethod.EditorInfo
import android.widget.SearchView
import android.widget.SearchView.OnQueryTextListener
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.findNavController
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.android.proyecto_final.R
import com.example.android.proyecto_final.databinding.InventoryFragmentBinding
import com.example.android.proyecto_final.network.CurrentProductInfo
import com.example.android.proyecto_final.network.MainAdapter
import com.example.android.proyecto_final.network.OnProductItemClickListener
import com.example.android.proyecto_final.network.Product
import kotlinx.android.synthetic.main.inventory_fragment.*


class InventoryFragment : Fragment(), OnProductItemClickListener{

    companion object {
        fun newInstance() = InventoryFragment()
    }

    private val viewModel by lazy { ViewModelProvider(this).get(InventoryViewModel::class.java) }
    private lateinit var adapter: MainAdapter
    private lateinit var binding: InventoryFragmentBinding


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {

//        (activity as AppCompatActivity).supportActionBar?.title = "Inventario"
//        (activity as AppCompatActivity).supportActionBar?.hide()

        val binding = DataBindingUtil.inflate<InventoryFragmentBinding>(inflater, R.layout.inventory_fragment, container, false)




        adapter = activity?.let {
            MainAdapter(
                it
            , this)
        }!!
        binding.recyclerView.layoutManager = LinearLayoutManager(this.context)
        binding.recyclerView.adapter = adapter
        binding.recyclerView.addItemDecoration(DividerItemDecoration(this.context, DividerItemDecoration.VERTICAL))


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

    override fun onItemClick(item: Product, position: Int) {
        CurrentProductInfo.currentName = item.name
        CurrentProductInfo.currentPrice = item.price
        CurrentProductInfo.currentProvider = item.provider
        CurrentProductInfo.currentQuantity = item.quantity
        CurrentProductInfo.currentId = item.uid

        view?.findNavController()?.navigate(R.id.action_inventoryFragment_to_selectedItemFragment)
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {

        inflater!!.inflate(R.menu.menu_search, menu)
        super.onCreateOptionsMenu(menu, inflater)

        val searchItem = menu.findItem(R.id.action_search)
        val searchView: SearchView = searchItem.actionView as SearchView
        searchView.imeOptions = EditorInfo.IME_ACTION_DONE

        searchView.setOnQueryTextListener(object : OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                return false
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                adapter.filter!!.filter(newText)
                return false
            }
        })
    }

}
