package com.example.fishmarket.ui.home.transaction

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.core.text.HtmlCompat
import androidx.recyclerview.widget.RecyclerView
import com.example.fishmarket.R
import com.example.fishmarket.data.repository.transaction.source.local.entity.TransactionHomeEntity
import com.example.fishmarket.databinding.ItemTransactionsBinding
import java.text.SimpleDateFormat
import java.util.*


class TransactionAdapter(private val context: Context) :
    RecyclerView.Adapter<TransactionAdapter.ViewHolder>() {

    private val list = ArrayList<TransactionHomeEntity>()
    private lateinit var onItemClickCallback: OnItemClickCallback

    fun setOnItemClickCallback(onItemClickCallback: OnItemClickCallback) {
        this.onItemClickCallback = onItemClickCallback
    }

    fun updateData(new: List<TransactionHomeEntity>) {
        list.clear()
        list.addAll(new)
        notifyDataSetChanged()
    }

    inner class ViewHolder(private val binding: ItemTransactionsBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bindItem(transaction: TransactionHomeEntity, onItemClickCallback: OnItemClickCallback) {

            val simpleDateFormat = SimpleDateFormat("HH:mm", Locale.getDefault())

            binding.tvId.text = context.resources.getString(R.string.transaction_id, transaction.id)
            binding.tvRestaurant.text = transaction.restaurant
            binding.tvTable.text = transaction.table
            binding.tvClock.text = simpleDateFormat.format(transaction.created_date)
            when (transaction.status) {
                1 -> {
                    val colorHex = "#" + Integer.toHexString(
                        ContextCompat.getColor(
                            context,
                            R.color.yellow_700
                        ) and 0x00ffffff
                    )

                    binding.tvStatus.text = HtmlCompat.fromHtml(
                        context.resources.getString(
                            R.string.status_transaction,
                            colorHex,
                            "Dibersihkan"
                        ), HtmlCompat.FROM_HTML_MODE_LEGACY
                    )
                }
                2 -> {
                    val colorHex = "#" + Integer.toHexString(
                        ContextCompat.getColor(
                            context,
                            R.color.red_600
                        ) and 0x00ffffff
                    )

                    binding.tvStatus.text = HtmlCompat.fromHtml(
                        context.resources.getString(
                            R.string.status_transaction,
                            colorHex,
                            "Dibakar"
                        ), HtmlCompat.FROM_HTML_MODE_LEGACY
                    )
                }
                else -> {
                    val colorHex = "#" + Integer.toHexString(
                        ContextCompat.getColor(
                            context,
                            R.color.green_500
                        ) and 0x00ffffff
                    )

                    binding.tvStatus.text = HtmlCompat.fromHtml(
                        context.resources.getString(
                            R.string.status_transaction,
                            colorHex,
                            "Dibayar"
                        ), HtmlCompat.FROM_HTML_MODE_LEGACY
                    )
                }
            }

            itemView.setOnClickListener {
                onItemClickCallback.onItemClicked(transaction)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding =
            ItemTransactionsBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bindItem(list[position], onItemClickCallback)
    }

    override fun getItemCount() = list.size

    interface OnItemClickCallback {
        fun onItemClicked(transaction: TransactionHomeEntity)
    }
}