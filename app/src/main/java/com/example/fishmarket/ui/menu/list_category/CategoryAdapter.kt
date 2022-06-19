package com.example.fishmarket.ui.menu.list_category

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.fishmarket.databinding.ItemCategoryBinding
import com.example.fishmarket.domain.model.Category

class CategoryAdapter : RecyclerView.Adapter<CategoryAdapter.ViewHolder>() {

    private val list = ArrayList<Category>()

    private lateinit var onItemClickCallback: OnItemClickCallback

    fun setOnItemClickCallback(onItemClickCallback: OnItemClickCallback) {
        this.onItemClickCallback = onItemClickCallback
    }

    fun updateData(new: List<Category>) {
        list.clear()
        list.addAll(new)
        notifyDataSetChanged()
    }

    class ViewHolder(private val binding: ItemCategoryBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bindItem(
            category: Category,
            onItemClickCallback: OnItemClickCallback
        ) {
            binding.tvCategoryName.text = category.name

            itemView.setOnClickListener {
                onItemClickCallback.onItemClicked(category)
            }

            itemView.setOnLongClickListener {
                onItemClickCallback.onItemLongClicked(category)
                true
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding =
            ItemCategoryBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bindItem(list[position], onItemClickCallback)
    }

    override fun getItemCount(): Int = list.size

    interface OnItemClickCallback {
        fun onItemLongClicked(category: Category)
        fun onItemClicked(category: Category)
    }
}