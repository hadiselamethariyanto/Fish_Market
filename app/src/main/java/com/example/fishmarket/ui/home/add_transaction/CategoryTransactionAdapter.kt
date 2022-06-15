package com.example.fishmarket.ui.home.add_transaction

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.example.fishmarket.R
import com.example.fishmarket.data.repository.category.source.local.entity.CategoryEntity
import com.example.fishmarket.databinding.ItemCategoryAddTransactionBinding

class CategoryTransactionAdapter(private val context: Context) :
    RecyclerView.Adapter<CategoryTransactionAdapter.ViewHolder>() {
    private val list = ArrayList<CategoryEntity>()
    private lateinit var onItemClickCallback: OnItemClickCallback
    var selectedItemPos = -1
    var lastItemSelectedPos = -1

    fun setOnItemClickCallback(onItemClickCallback: OnItemClickCallback) {
        this.onItemClickCallback = onItemClickCallback
    }

    fun updateData(new: List<CategoryEntity>) {
        list.clear()
        list.add(CategoryEntity("0", context.resources.getString(R.string.all), 0))
        list.addAll(new)
        notifyDataSetChanged()
    }

    inner class ViewHolder(private val binding: ItemCategoryAddTransactionBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun selectedBg() {
            binding.layout.background = ContextCompat.getDrawable(
                itemView.context,
                R.drawable.background_change_status_transaction_selected
            )
            binding.tvCategoryName.setTextColor(ContextCompat.getColor(context, R.color.white))
        }

        fun unSelectedBg() {
            binding.layout.background = ContextCompat.getDrawable(
                itemView.context,
                R.drawable.background_change_status_transaction
            )
            binding.tvCategoryName.setTextColor(ContextCompat.getColor(context, R.color.gray_500))
        }

        fun bindItem(categoryEntity: CategoryEntity) {
            binding.tvCategoryName.text = categoryEntity.name

            itemView.setOnClickListener {
                selectedItemPos = adapterPosition
                lastItemSelectedPos = if (lastItemSelectedPos == -1) {
                    selectedItemPos
                } else {
                    notifyItemChanged(lastItemSelectedPos)
                    selectedItemPos
                }
                notifyItemChanged(selectedItemPos)
                onItemClickCallback.onItemClicked(categoryEntity)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = ItemCategoryAddTransactionBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        if (position == selectedItemPos) {
            holder.selectedBg()
        } else {
            holder.unSelectedBg()
        }
        holder.bindItem(list[position])
    }

    override fun getItemCount(): Int = list.size

    interface OnItemClickCallback {
        fun onItemClicked(categoryEntity: CategoryEntity)
    }
}