package com.example.fishmarket.ui.history.detail_history

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.fishmarket.data.repository.transaction.source.local.entity.DetailTransactionEntity
import com.example.fishmarket.databinding.ItemHistoryDetailBinding
import com.example.fishmarket.utilis.Utils

class DetailHistoryAdapter(private val list: List<DetailTransactionEntity>) : RecyclerView.Adapter<DetailHistoryAdapter.ViewHolder>() {

    class ViewHolder(private val binding: ItemHistoryDetailBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bindItem(data: DetailTransactionEntity) {
            binding.tvMenuName.text = data.id_menu
            binding.tvQuantity.text = data.quantity.toString()
            binding.tvPrice.text = Utils.formatNumberToRupiah(data.price, itemView.context)
            binding.tvFee.text =
                Utils.formatNumberToRupiah(data.price * data.quantity, itemView.context)
        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding =
            ItemHistoryDetailBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bindItem(list[position])
    }

    override fun getItemCount(): Int = list.size
}