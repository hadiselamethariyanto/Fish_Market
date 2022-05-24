package com.example.fishmarket.ui.home.add_transaction

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.example.fishmarket.R
import com.example.fishmarket.data.repository.restaurant.source.local.entity.RestaurantEntity
import com.example.fishmarket.databinding.ItemSelectRestaurantBinding

class SelectRestaurantAdapter : RecyclerView.Adapter<SelectRestaurantAdapter.ViewHolder>() {

    private val list = ArrayList<RestaurantEntity>()
    var selectedItemPos = -1
    var lastItemSelectedPos = -1


    fun updateData(new: List<RestaurantEntity>) {
        list.clear()
        list.addAll(new)
        notifyDataSetChanged()
    }

    fun selectRestaurant(id: Int) {
        list.mapIndexed { index, restaurantEntity ->
            if (restaurantEntity.id == id) {
                selectedItemPos = index
                lastItemSelectedPos = index
            }
        }
        notifyItemChanged(selectedItemPos)
    }

    fun getSelectedPosition(): Int = selectedItemPos

    fun getSelectedRestaurant(): RestaurantEntity = list[selectedItemPos]

    inner class ViewHolder(private val binding: ItemSelectRestaurantBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun selectedBg() {
            binding.layout.background =
                ContextCompat.getDrawable(itemView.context, R.drawable.circle_highlighted_table)
            binding.ivIcon.setImageResource(R.drawable.ic_store_white)
            binding.tvRestaurant.setTextColor(
                ContextCompat.getColor(
                    itemView.context,
                    R.color.white
                )
            )
        }

        fun unSelectedBg() {
            binding.ivIcon.setImageResource(R.drawable.ic_store)
            binding.layout.background =
                ContextCompat.getDrawable(itemView.context, R.drawable.circle_not_highlicted_table)
            binding.tvRestaurant.setTextColor(
                ContextCompat.getColor(
                    itemView.context,
                    R.color.black
                )
            )
        }


        fun bindItem(restaurant: RestaurantEntity) {
            binding.tvRestaurant.text = restaurant.name
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
        val binding =
            ItemSelectRestaurantBinding.inflate(LayoutInflater.from(parent.context), parent, false)
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