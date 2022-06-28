package com.example.fishmarket.ui.home.add_transaction

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.example.fishmarket.R
import com.example.fishmarket.data.repository.table.source.local.entity.TableEntity
import com.example.fishmarket.databinding.ItemSelectTableBinding
import com.example.fishmarket.domain.model.Table

class SelectTableAdapter : RecyclerView.Adapter<SelectTableAdapter.ViewHolder>() {

    private val list = ArrayList<Table>()
    var selectedItemPos = -1
    var lastItemSelectedPos = -1

    fun updateData(new: List<Table>) {
        list.clear()
        list.addAll(new)
        notifyDataSetChanged()
    }

    fun getSelectedPosition(): Int = selectedItemPos

    fun getSelectedTable(): Table = list[selectedItemPos]

    inner class ViewHolder(private val binding: ItemSelectTableBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun selectedBg() {
            binding.layout.background =
                ContextCompat.getDrawable(itemView.context, R.drawable.circle_highlighted_table)
            binding.ivIcon.setImageResource(R.drawable.ic_table_white)
            binding.tvTable.setTextColor(
                ContextCompat.getColor(
                    itemView.context,
                    R.color.white
                )
            )
        }

        fun unSelectedBg() {
            binding.ivIcon.setImageResource(R.drawable.ic_table)
            binding.layout.background =
                ContextCompat.getDrawable(itemView.context, R.drawable.circle_not_highlicted_table)
            binding.tvTable.setTextColor(
                ContextCompat.getColor(
                    itemView.context,
                    R.color.black
                )
            )
        }

        fun bindItem(table: Table) {
            binding.tvTable.text = table.name
            itemView.setOnClickListener {
                selectedItemPos = adapterPosition
                if (lastItemSelectedPos == -1) {
                    lastItemSelectedPos = selectedItemPos
                } else {
                    notifyItemChanged(lastItemSelectedPos)
                    lastItemSelectedPos = selectedItemPos
                }
                notifyItemChanged(selectedItemPos)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding =
            ItemSelectTableBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bindItem(list[position])
        if (position == selectedItemPos) {
            holder.selectedBg()
        } else {
            if (list[position].status){
                holder.selectedBg()
            }else{
                holder.unSelectedBg()
            }
        }
    }

    override fun getItemCount() = list.size
}