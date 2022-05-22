package com.example.fishmarket.ui.restaurant.list_restaurant

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.fishmarket.data.repository.transaction.source.local.entity.TransactionFireEntity
import com.example.fishmarket.databinding.ItemRestaurantTransactionBinding

class RestaurantTransactionAdapter(private val list: List<TransactionFireEntity>) :
    RecyclerView.Adapter<RestaurantTransactionAdapter.ViewHolder>() {

    class ViewHolder(private val binding: ItemRestaurantTransactionBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bindItem(transaction: TransactionFireEntity) {
            binding.tvTable.text = transaction.name
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = ItemRestaurantTransactionBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bindItem(list[position])
    }

    override fun getItemCount() = list.size


}