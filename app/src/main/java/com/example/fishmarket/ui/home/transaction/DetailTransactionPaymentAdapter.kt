package com.example.fishmarket.ui.home.transaction

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.example.fishmarket.R
import com.example.fishmarket.databinding.ItemDetailTransactionPaymentBinding
import com.example.fishmarket.domain.model.DetailTransactionHistory
import com.example.fishmarket.utilis.Utils

class DetailTransactionPaymentAdapter :
    RecyclerView.Adapter<DetailTransactionPaymentAdapter.ViewHolder>() {

    private lateinit var onItemClickCallback: OnItemClickCallback
    private val list = ArrayList<DetailTransactionHistory>()

    fun setOnItemClickCallback(onItemClickCallback: OnItemClickCallback) {
        this.onItemClickCallback = onItemClickCallback
    }

    fun updateData(new: List<DetailTransactionHistory>) {
        list.clear()
        list.addAll(new)
        notifyDataSetChanged()
    }

    inner class ViewHolder(private val binding: ItemDetailTransactionPaymentBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bindItem(data: DetailTransactionHistory, position: Int) {
            binding.tvMenuName.text = data.name
            if (data.unit == "Decimal") {
                binding.tvQuantity.text = data.quantity.toString()
            } else {
                binding.tvQuantity.text = data.quantity.toInt().toString()
            }
            binding.tvPrice.text = Utils.formatNumberToRupiah(data.price, itemView.context)
            binding.tvFee.text =
                Utils.formatDoubleToRupiah(data.price * data.quantity, itemView.context)

            if (data.status) {
                binding.layout.setBackgroundColor(
                    ContextCompat.getColor(
                        itemView.context,
                        R.color.white
                    )
                )
                binding.btnDelete.visibility = View.VISIBLE
            } else {
                binding.layout.setBackgroundColor(
                    ContextCompat.getColor(
                        itemView.context,
                        R.color.gray_200
                    )
                )
                binding.btnDelete.visibility = View.GONE
            }

            binding.btnDelete.setOnClickListener {
                onItemClickCallback.onRemoveClicked(position)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = ItemDetailTransactionPaymentBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bindItem(list[position], position)
    }

    override fun getItemCount(): Int = list.size

    interface OnItemClickCallback {
        fun onRemoveClicked(position: Int)
    }
}