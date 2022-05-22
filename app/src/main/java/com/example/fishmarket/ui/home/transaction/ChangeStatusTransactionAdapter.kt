package com.example.fishmarket.ui.home.transaction

import android.content.Context
import android.content.res.ColorStateList
import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.example.fishmarket.R
import com.example.fishmarket.data.repository.status_transaction.source.local.entity.StatusTransactionEntity
import com.example.fishmarket.databinding.ItemChangeStatusTransactionBinding

class ChangeStatusTransactionAdapter(private val context: Context) :
    RecyclerView.Adapter<ChangeStatusTransactionAdapter.ViewHolder>() {
    private val list = ArrayList<StatusTransactionEntity>()
    var selectedItemPos = -1
    var lastItemSelectedPos = -1


    fun updateData(new: List<StatusTransactionEntity>) {
        list.clear()
        list.addAll(new)
        notifyDataSetChanged()
    }

    fun getStatus(): Int = list[selectedItemPos].id

    fun selectStatus(position: Int) {
        list.mapIndexed { index, statusTransactionEntity ->
            if (statusTransactionEntity.id == position) {
                lastItemSelectedPos = index
                selectedItemPos = index
            }
        }
        notifyItemChanged(selectedItemPos)
    }


    inner class ViewHolder(private val binding: ItemChangeStatusTransactionBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun selectedBg() {
            binding.layout.background =
                ContextCompat.getDrawable(
                    itemView.context,
                    R.drawable.background_change_status_transaction_selected
                )
            binding.ivIcon.imageTintList =
                ColorStateList.valueOf(ContextCompat.getColor(context, R.color.white))
            binding.tvStatus.setTextColor(ContextCompat.getColor(context, R.color.white))

        }

        fun unSelectedBg() {
            binding.layout.background =
                ContextCompat.getDrawable(
                    itemView.context,
                    R.drawable.background_change_status_transaction
                )
            binding.ivIcon.imageTintList =
                ColorStateList.valueOf(ContextCompat.getColor(context, R.color.black))
            binding.tvStatus.setTextColor(ContextCompat.getColor(context, R.color.black))
        }

        fun bindItem(status: StatusTransactionEntity) {
            binding.tvStatus.text = status.name
            binding.ivIcon.setImageResource(status.icon)

            itemView.setOnClickListener {
                selectedItemPos = adapterPosition
                lastItemSelectedPos = if (lastItemSelectedPos == -1) {
                    selectedItemPos
                } else {
                    notifyItemChanged(lastItemSelectedPos)
                    selectedItemPos
                }
                notifyItemChanged(selectedItemPos)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = ItemChangeStatusTransactionBinding.inflate(
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

    override fun getItemCount() = list.size
}