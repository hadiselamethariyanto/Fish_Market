package com.example.fishmarket.ui.home.review_transaction

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.fishmarket.databinding.ItemHistoryDetailBinding
import com.example.fishmarket.utilis.Product
import com.example.fishmarket.utilis.Utils

class ReviewTransactionAdapter(private val list: List<Product>) :
    RecyclerView.Adapter<ReviewTransactionAdapter.ViewHolder>() {

    class ViewHolder(private val binding: ItemHistoryDetailBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bindItem(product: Product) {
            binding.tvMenuName.text = product.name
            binding.tvPrice.text = Utils.formatNumberToRupiah(product.price, itemView.context)
            binding.tvFee.text =
                Utils.formatDoubleToRupiah(product.price * product.quantity, itemView.context)
            val check = product.quantity - product.quantity.toInt() == 0.0
            if (!check) {
                binding.tvQuantity.text = product.quantity.toString()
            } else {
                binding.tvQuantity.text = product.quantity.toInt().toString()
            }
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