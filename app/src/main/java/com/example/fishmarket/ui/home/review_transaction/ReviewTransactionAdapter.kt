package com.example.fishmarket.ui.home.review_transaction

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.fishmarket.databinding.ItemHistoryDetailBinding
import com.example.fishmarket.utilis.Product
import com.example.fishmarket.utilis.Utils

class ReviewTransactionAdapter() :
    RecyclerView.Adapter<ReviewTransactionAdapter.ViewHolder>() {
    private val list = ArrayList<Product>()

    private lateinit var onItemClickCallback: OnItemClickCallback

    fun updateData(newList: List<Product>) {
        list.clear()
        list.addAll(newList)
        notifyItemRangeChanged(0, list.size)
    }

    fun setOnItemClickCallback(onItemClickCallback: OnItemClickCallback) {
        this.onItemClickCallback = onItemClickCallback
    }

    inner class ViewHolder(private val binding: ItemHistoryDetailBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bindItem(product: Product, position: Int) {
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

            itemView.setOnClickListener {
                onItemClickCallback.onItemClicked(position, product)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding =
            ItemHistoryDetailBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bindItem(list[position], position)
    }

    override fun getItemCount(): Int = list.size

    interface OnItemClickCallback {
        fun onItemClicked(position: Int, product: Product)
    }
}