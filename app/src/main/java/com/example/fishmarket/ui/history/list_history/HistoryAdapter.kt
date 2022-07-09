package com.example.fishmarket.ui.history.list_history

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.core.text.HtmlCompat
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.fishmarket.R
import com.example.fishmarket.databinding.ItemHistoryBinding
import com.example.fishmarket.databinding.ItemHistoryDateBinding
import com.example.fishmarket.domain.model.History
import com.example.fishmarket.domain.model.TransactionWithDetail
import com.example.fishmarket.utilis.Utils

class HistoryAdapter : RecyclerView.Adapter<HistoryAdapter.ViewHolder>() {
    private val list = ArrayList<History>()

    fun updateData(new: List<History>) {
        list.clear()
        list.addAll(new)
        notifyDataSetChanged()
    }

    private lateinit var onItemClickCallback: OnItemClickCallback

    fun setOnItemClickCallback(onItemClickCallback: OnItemClickCallback) {
        this.onItemClickCallback = onItemClickCallback
    }

    open inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView)

    inner class ViewHolderItem(private val binding: ItemHistoryBinding) :
        ViewHolder(binding.root) {
        fun bindItem(transaction: TransactionWithDetail) {
            when (transaction.transaction.status) {
                1 -> {
                    Glide.with(itemView.context).load(R.drawable.ic_dibersihkan_history)
                        .into(binding.tvStatus)
                }
                2 -> {
                    Glide.with(itemView.context).load(R.drawable.ic_fire_history)
                        .into(binding.tvStatus)
                }
                3 -> {
                    Glide.with(itemView.context).load(R.drawable.ic_served_history)
                        .into(binding.tvStatus)
                }
                else -> {
                    Glide.with(itemView.context).load(R.drawable.ic_history_dibayar)
                        .into(binding.tvStatus)
                }
            }

            binding.tvTotalBiaya.text = Utils.formatNumberToRupiah(
                transaction.transaction.total_fee,
                itemView.context
            )
            binding.tvRestaurant.text = transaction.transaction.id_restaurant
            binding.tvJam.text = Utils.formatTime(transaction.transaction.created_date)
            binding.tvTable.text = transaction.transaction.id_table
            itemView.setOnClickListener {
                onItemClickCallback.onItemClicked(transaction)
            }
        }
    }

    inner class ViewHolderDate(private val binding: ItemHistoryDateBinding) :
        ViewHolder(binding.root) {
        fun bindItem(transaction: TransactionWithDetail) {
            binding.date.text = Utils.formatDate(transaction.transaction.created_date)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return when (viewType) {
            0 -> ViewHolderItem(
                ItemHistoryBinding.inflate(LayoutInflater.from(parent.context), parent, false)
            )
            else -> ViewHolderDate(
                ItemHistoryDateBinding.inflate(
                    LayoutInflater.from(parent.context),
                    parent,
                    false
                )
            )
        }
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        if (list[position].viewType == 0) {
            (holder as ViewHolderItem).bindItem(list[position].data)
        } else {
            (holder as ViewHolderDate).bindItem(list[position].data)
        }
    }

    override fun getItemCount(): Int = list.size

    override fun getItemViewType(position: Int): Int = list[position].viewType

    interface OnItemClickCallback {
        fun onItemClicked(transaction: TransactionWithDetail)
    }
}