package com.example.fishmarket.ui.restaurant.list_restaurant

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.fishmarket.data.repository.restaurant.source.local.entity.RestaurantEntity
import com.example.fishmarket.databinding.ItemRestaurantBinding

class RestaurantAdapter : RecyclerView.Adapter<RestaurantAdapter.ViewHolder>() {

    private val list = ArrayList<RestaurantEntity>()
    private lateinit var onItemClickCallback: OnItemClickCallback

    fun setOnItemClickCallback(onItemClickCallback: OnItemClickCallback) {
        this.onItemClickCallback = onItemClickCallback
    }

    fun updateData(new: List<RestaurantEntity>) {
        list.clear()
        list.addAll(new)
        notifyDataSetChanged()
    }

    class ViewHolder(private val binding: ItemRestaurantBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bindItem(restaurant: RestaurantEntity, onItemClickCallback: OnItemClickCallback) {
            binding.tvRestaurantName.text = restaurant.name

            itemView.setOnLongClickListener {
                onItemClickCallback.onItemLongClicked(restaurant)
                true
            }

            itemView.setOnClickListener {
                onItemClickCallback.onItemClicked(restaurant)
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
        fun onItemLongClicked(restaurant: RestaurantEntity)
        fun onItemClicked(restaurant: RestaurantEntity)
    }
}