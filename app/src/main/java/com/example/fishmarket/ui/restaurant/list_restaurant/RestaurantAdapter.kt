package com.example.fishmarket.ui.restaurant.list_restaurant

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.fishmarket.databinding.ItemRestaurantBinding
import com.example.fishmarket.domain.model.Restaurant
import com.example.fishmarket.domain.model.RestaurantWithTransaction

class RestaurantAdapter(private val context: Context) :
    RecyclerView.Adapter<RestaurantAdapter.ViewHolder>() {

    private val list = ArrayList<RestaurantWithTransaction>()
    private lateinit var onItemClickCallback: OnItemClickCallback

    fun setOnItemClickCallback(onItemClickCallback: OnItemClickCallback) {
        this.onItemClickCallback = onItemClickCallback
    }

    fun updateData(new: List<RestaurantWithTransaction>) {
        list.clear()
        list.addAll(new)
        notifyDataSetChanged()
    }

    inner class ViewHolder(private val binding: ItemRestaurantBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bindItem(
            restaurant: RestaurantWithTransaction,
            onItemClickCallback: OnItemClickCallback
        ) {
            binding.tvRestaurantName.text = restaurant.restaurant.name

            val restaurantTransactionAdapter = RestaurantTransactionAdapter(restaurant.transaction)
            binding.rvTransactions.adapter = restaurantTransactionAdapter
            binding.rvTransactions.layoutManager =
                LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)

            itemView.setOnLongClickListener {
                onItemClickCallback.onItemLongClicked(restaurant.restaurant)
                true
            }

            itemView.setOnClickListener {
                onItemClickCallback.onItemClicked(restaurant.restaurant)
            }

        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding =
            ItemRestaurantBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bindItem(list[position], onItemClickCallback)
    }

    override fun getItemCount(): Int = list.size

    interface OnItemClickCallback {
        fun onItemLongClicked(restaurant: Restaurant)
        fun onItemClicked(restaurant: Restaurant)
    }
}