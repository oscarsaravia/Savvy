package com.example.android.proyecto_final.network

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Filter
import android.widget.Filterable
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.android.proyecto_final.R
import kotlinx.android.synthetic.main.product_item.view.*

class MainAdapter(private val context: Context, var clickListener: OnProductItemClickListener): RecyclerView.Adapter<MainAdapter.MainViewHolder>(), Filterable{

    private var dataList = mutableListOf<Product>()
    private var dataListFull = mutableListOf<Product>()

    fun setListData(data:MutableList<Product>){
        dataList = data
        dataListFull = ArrayList(data)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MainViewHolder {
        val view = LayoutInflater.from(context).inflate(R.layout.product_item, parent, false)
        return MainViewHolder(view)
    }

    override fun getItemCount(): Int {
        return if(dataList.size > 0){
            dataList.size
        } else{
            0
        }
    }

    override fun onBindViewHolder(holder: MainViewHolder, position: Int) {
        val product: Product = dataList[position]
        holder.initialize(product, clickListener)
    }

    inner class MainViewHolder(itemView:View):RecyclerView.ViewHolder(itemView){

        fun bindView(product: Product){
            Glide.with(context)
            itemView.title_product.text = product.name
            itemView.quantity.text = "Cantidad: "+ product.quantity
            itemView.price.text = "Precio: Q"+ product.price
            itemView.provider.text = "Proveedor: " + product.provider

        }
        fun initialize(item:Product, action: OnProductItemClickListener){
            itemView.title_product.text = item.name
            itemView.quantity.text = "Cantidad: "+ item.quantity
            itemView.price.text = "Precio: Q"+ item.price
            itemView.provider.text = "Proveedor: " + item.provider
            itemView.setOnClickListener{
                action.onItemClick(item, adapterPosition)
            }
        }

    }

    override fun getFilter(): Filter? {
        return exampleFilter
    }

    private val exampleFilter: Filter = object : Filter() {

        override fun performFiltering(constraint: CharSequence): FilterResults {
            val filteredList: MutableList<Product> = ArrayList()
            if (constraint == null || constraint.isEmpty()) {
                filteredList.addAll(dataListFull)
            } else {
                val filterPattern =
                    constraint.toString().toLowerCase().trim { it <= ' ' }
                for (item in dataListFull) {
                    if (item.name.toLowerCase().contains(filterPattern)) {
                        filteredList.add(item)
                    }
                }
            }
            val results = FilterResults()
            results.values = filteredList
            return results
        }

        override fun publishResults(
            constraint: CharSequence,
            results: FilterResults
        ) {
            dataList.clear()
            dataList.addAll(results.values as List<Product>)
            notifyDataSetChanged()
        }
    }
}

interface OnProductItemClickListener{
    fun onItemClick(item:Product, position: Int)
}