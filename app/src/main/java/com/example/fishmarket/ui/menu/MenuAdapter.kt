package com.example.fishmarket.ui.menu

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.fishmarket.data.repository.menu.source.local.entity.MenuEntity
import com.example.fishmarket.databinding.ItemMenuBinding

class MenuAdapter : RecyclerView.Adapter<MenuAdapter.ViewHolder>() {

    private val list = ArrayList<MenuEntity>()

    fun updateData(new: List<MenuEntity>) {
        list.clear()
        list.addAll(new)
        notifyDataSetChanged()
    }

    class ViewHolder(private val binding: ItemMenuBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bindItem(menuEntity: MenuEntity) {
            binding.tvName.text = menuEntity.name
            binding.tvPrice.text = menuEntity.price.toString()
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = ItemMenuBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bindItem(list[position])
    }

    override fun getItemCount(): Int = list.size
}