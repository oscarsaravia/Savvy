package com.example.android.proyecto_final.network

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.android.proyecto_final.R
import kotlinx.android.synthetic.main.product_item.view.*
import kotlinx.android.synthetic.main.product_item.view.title_product
import kotlinx.android.synthetic.main.provider_item.view.*

class ProviderAdapter (private val context: Context, var clickListener: OnProviderItemClickListener): RecyclerView.Adapter<ProviderAdapter.ProviderViewHolder>() {

    private var dataList = mutableListOf<Provider>()

    fun setListData(data:MutableList<Provider>){
        dataList = data
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ProviderAdapter.ProviderViewHolder {
        val view = LayoutInflater.from(context).inflate(R.layout.provider_item, parent, false)
        return ProviderViewHolder(view)
    }



    override fun getItemCount(): Int {
        return if(dataList.size > 0){
            dataList.size
        } else{
            0
        }
    }

    override fun onBindViewHolder(holder: ProviderViewHolder, position: Int) {
        val provider: Provider = dataList[position]
        holder.initialize(provider, clickListener)

    }

    inner class ProviderViewHolder(itemView: View):RecyclerView.ViewHolder(itemView){

        fun bindView(provider: Provider){
            Glide.with(context)
            itemView.title_product.text = provider.name
            itemView.email.text = "E-mail: " + provider.email
            itemView.phone.text = "Teléfono: " + provider.phone


        }
        fun initialize(item:Provider, action: OnProviderItemClickListener){
            itemView.title_product.text = item.name
            itemView.email.text = "E-mail: " + item.email
            itemView.phone.text = "Teléfono: " + item.phone
            itemView.setOnClickListener{
                action.onItemClick(item, adapterPosition)
            }
        }

    }
}

interface OnProviderItemClickListener{
    fun onItemClick(item:Provider, position: Int)
}