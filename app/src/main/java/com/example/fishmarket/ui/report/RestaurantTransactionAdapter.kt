package com.example.fishmarket.ui.report

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.fishmarket.databinding.ItemReportRestaurantBinding
import com.example.fishmarket.domain.model.RestaurantTransaction
import com.example.fishmarket.utilis.Utils

class RestaurantTransactionAdapter :
    RecyclerView.Adapter<RestaurantTransactionAdapter.ViewHolder>() {

    private lateinit var onItemClickCallback: OnItemClickCallback
    private val list = ArrayList<RestaurantTransaction>()

    fun setOnItemClickCallback(onItemClickCallback: OnItemClickCallback) {
        this.onItemClickCallback = onItemClickCallback
    }

    fun updateData(new: List<RestaurantTransaction>) {
        list.clear()
        list.addAll(new)
        notifyDataSetChanged()
    }

    inner class ViewHolder(private val binding: ItemReportRestaurantBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bindItem(data: RestaurantTransaction) {
            binding.tvName.text = data.name
            binding.tvIncome.text = Utils.formatDoubleToRupiah(data.income, itemView.context)
            binding.tvTransactionCount.text = data.transactionCount.toString()
            binding.tvOriginalFeeAndDiscount.text = Utils.formatNumberToRupiah(
                data.originalFee,
                itemView.context
            ) + " - " + Utils.formatNumberToRupiah(data.discount, itemView.context)

            itemView.setOnClickListener {
                onItemClickCallback.onItemClicked(
                    data.id,
                    data.name,
                    data.transactionCount,
                    data.originalFee,
                    data.discount,
                    data.income.toInt()
                )
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding =
            ItemReportRestaurantBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bindItem(list[position])
    }

    override fun getItemCount(): Int = list.size

    interface OnItemClickCallback {
        fun onItemClicked(
            idRestaurant: String,
            restaurantName: String,
            transactionCount: Int,
            originalFee: Int,
            discount: Int,
            totalFee: Int
        )
    }
}