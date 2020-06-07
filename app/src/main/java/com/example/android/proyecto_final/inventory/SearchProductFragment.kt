package com.example.android.proyecto_final.inventory

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.android.proyecto_final.Product
import com.example.android.proyecto_final.R
import com.example.android.proyecto_final.databinding.AddproductFragmentBinding
import com.firebase.ui.database.FirebaseRecyclerAdapter
import com.firebase.ui.database.FirebaseRecyclerOptions
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import kotlinx.android.synthetic.main.fragment_search_product.*
import kotlinx.android.synthetic.main.inventory_fragment.*
import kotlinx.android.synthetic.main.layout_list.view.*


class SearchProductFragment : Fragment() {

    lateinit var mSearchText : EditText
    lateinit var mRecyclerView : RecyclerView

    lateinit var mDatabase : DatabaseReference

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {

        (activity as AppCompatActivity).supportActionBar?.title = "Buscar productos por nombre"
        (activity as AppCompatActivity).supportActionBar?.hide()

        val binding = DataBindingUtil.inflate<AddproductFragmentBinding>(inflater, R.layout.fragment_search_product, container, false)
//
//        mSearchText = searchText
//        mRecyclerView = list_view
//
//        mDatabase = FirebaseDatabase.getInstance().getReference("products")
//
//        mRecyclerView.setHasFixedSize(true)
//        mRecyclerView.layoutManager = LinearLayoutManager(this.context)
//
//        loadFirebaseData()


        return binding.root
    }

//    private fun loadFirebaseData() {
//
////        val options: FirebaseRecyclerOptions<Product> = FirebaseRecyclerOptions.Builder<Product>()
////            .setQuery(mDatabase, Product::class.java)
////            .build()
////
////        val FirebaseRecyclerAdapter = object : FirebaseRecyclerAdapter<Product , ProductsViewHolder>(
////            Product.class,
////            R.layout.layout_list,
////            ProductsViewHolder.class,
////            mDatabase
////        ){
////            override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ProductsViewHolder {
////                TODO("Not yet implemented")
////            }
////
////            override fun onBindViewHolder(holder: ProductsViewHolder, position: Int, model: Product
////            ) {
////                holder?.mView?.name?.text = model?.name
////                holder?.mView?.price?.text = model?.price
////                holder?.mView?.provider?.text = model?.provider
////                holder?.mView?.quantity?.text = model?.quantity
////            }
////
////        }
//
//        val options: FirebaseRecyclerOptions<Product> = FirebaseRecyclerOptions.Builder<Product>()
//            .setQuery(mDatabase, Product::class.java)
//            .build()
//
//        val firebaseRecyclerAdapter: FirebaseRecyclerAdapter<*, *> =
//            object : FirebaseRecyclerAdapter<Product, ProductsViewHolder>(options) {
//
//                override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ProductsViewHolder {
//                    val view: View = LayoutInflater.from(parent.context).inflate(binding.layout_list, parent, false)
//                    return ProductsViewHolder(view)
//                }
//
//                protected override fun onBindViewHolder(
//                    holder: ProductsViewHolder,
//                    position: Int,
//                    model: Product
//                ) {
//
//
//                }
//            }
//
//
//
//        firebaseRecyclerAdapter.startListening()
//        recyclerView.setAdapter(firebaseRecyclerAdapter)
//
//
//    }
//
//    class ProductsViewHolder(var mView : View): RecyclerView.ViewHolder(mView){
//
//    }



}