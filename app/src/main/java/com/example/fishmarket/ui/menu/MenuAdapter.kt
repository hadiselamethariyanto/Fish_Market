package com.example.fishmarket.ui.menu

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.fishmarket.data.repository.menu.source.local.entity.MenuEntity
import com.example.fishmarket.databinding.ItemMenuBinding
import com.example.fishmarket.utilis.Utils

class MenuAdapter : RecyclerView.Adapter<MenuAdapter.ViewHolder>() {

    private val list = ArrayList<MenuEntity>()

    private lateinit var onItemClickCallback: OnItemClickCallback

    fun setOnItemClickCallback(onItemClickCallback: OnItemClickCallback) {
        this.onItemClickCallback = onItemClickCallback
    }

    fun updateData(new: List<MenuEntity>) {
        list.clear()
        list.addAll(new)
        notifyDataSetChanged()
    }

    class ViewHolder(private val binding: ItemMenuBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bindItem(
            menuEntity: MenuEntity,
            onItemClickCallback: OnItemClickCallback
        ) {
            binding.tvName.text = menuEntity.name
            binding.tvPrice.text = Utils.formatNumberToRupiah(menuEntity.price, itemView.context)

            itemView.setOnLongClickListener {
                onItemClickCallback.onItemLongClicked(menuEntity)
                true
            }

            itemView.setOnClickListener {
                onItemClickCallback.onItemClicked(menuEntity)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = ItemMenuBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bindItem(list[position], onItemClickCallback)
    }

    override fun getItemCount(): Int = list.size

    interface OnItemClickCallback {
        fun onItemLongClicked(menu: MenuEntity)
        fun onItemClicked(menu: MenuEntity)
    }
}