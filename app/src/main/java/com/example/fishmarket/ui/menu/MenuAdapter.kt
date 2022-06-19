package com.example.fishmarket.ui.menu

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.fishmarket.databinding.ItemMenuBinding
import com.example.fishmarket.domain.model.Menu
import com.example.fishmarket.utilis.Utils

class MenuAdapter : RecyclerView.Adapter<MenuAdapter.ViewHolder>() {

    private val list = ArrayList<Menu>()

    private lateinit var onItemClickCallback: OnItemClickCallback

    fun setOnItemClickCallback(onItemClickCallback: OnItemClickCallback) {
        this.onItemClickCallback = onItemClickCallback
    }

    fun updateData(new: List<Menu>) {
        list.clear()
        list.addAll(new)
        notifyDataSetChanged()
    }

    class ViewHolder(private val binding: ItemMenuBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bindItem(
            menu: Menu,
            onItemClickCallback: OnItemClickCallback
        ) {
            binding.tvName.text = menu.name
            binding.tvPrice.text = Utils.formatNumberToRupiah(menu.price, itemView.context)

            itemView.setOnLongClickListener {
                onItemClickCallback.onItemLongClicked(menu)
                true
            }

            itemView.setOnClickListener {
                onItemClickCallback.onItemClicked(menu)
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
        fun onItemLongClicked(menu: Menu)
        fun onItemClicked(menu: Menu)
    }
}