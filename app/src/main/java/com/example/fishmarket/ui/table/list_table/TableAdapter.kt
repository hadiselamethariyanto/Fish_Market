package com.example.fishmarket.ui.table.list_table

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.fishmarket.databinding.ItemTableBinding
import com.example.fishmarket.domain.model.Table

class TableAdapter : RecyclerView.Adapter<TableAdapter.ViewHolder>() {

    private val list = ArrayList<Table>()

    private lateinit var onItemClickCallBack: OnItemClickCallBack

    fun setOnItemClickCallback(onItemClickCallBack: OnItemClickCallBack) {
        this.onItemClickCallBack = onItemClickCallBack
    }

    fun updateData(new: List<Table>) {
        list.clear()
        list.addAll(new)
        notifyDataSetChanged()
    }

    class ViewHolder(private val binding: ItemTableBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bindItem(table: Table, onItemClickCallBack: OnItemClickCallBack) {
            binding.tvTableName.text = table.name

            itemView.setOnLongClickListener {
                onItemClickCallBack.onItemLongClicked(table)
                true
            }

            itemView.setOnClickListener {
                onItemClickCallBack.onItemClicked(table)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = ItemTableBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bindItem(list[position], onItemClickCallBack)
    }

    override fun getItemCount() = list.size

    interface OnItemClickCallBack {
        fun onItemLongClicked(table: Table)
        fun onItemClicked(table: Table)
    }
}