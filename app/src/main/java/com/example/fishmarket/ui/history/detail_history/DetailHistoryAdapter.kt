package com.example.fishmarket.ui.history.detail_history

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.fishmarket.data.repository.transaction.source.local.entity.DetailTransactionHistoryEntity
import com.example.fishmarket.databinding.ItemHistoryDetailBinding
import com.example.fishmarket.utilis.Utils

class DetailHistoryAdapter(private val list: List<DetailTransactionHistoryEntity>) :
    RecyclerView.Adapter<DetailHistoryAdapter.ViewHolder>() {

    class ViewHolder(private val binding: ItemHistoryDetailBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bindItem(data: DetailTransactionHistoryEntity) {
            binding.tvMenuName.text = data.name
            if (data.unit == "Decimal") {
                binding.tvQuantity.text = data.quantity.toString()
            } else {
                binding.tvQuantity.text = data.quantity.toInt().toString()
            }
            binding.tvPrice.text = Utils.formatNumberToRupiah(data.price, itemView.context)
            binding.tvFee.text =
                Utils.formatDoubleToRupiah(data.price * data.quantity, itemView.context)
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