package com.example.fishmarket.ui.home.add_transaction

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.CenterCrop
import com.bumptech.glide.load.resource.bitmap.RoundedCorners
import com.example.fishmarket.App
import com.example.fishmarket.R
import com.example.fishmarket.data.repository.menu.source.local.entity.MenuEntity
import com.example.fishmarket.databinding.ItemAddTransactionBinding
import com.example.fishmarket.utilis.Product
import com.example.fishmarket.utilis.Utils
import com.example.fishmarket.utilis.Utils.capitalizeWords

class AddTransactionAdapter(private val ct: App, private val fragment: AddTransactionFragment) :
    RecyclerView.Adapter<AddTransactionAdapter.ViewHolder>() {
    private val list = ArrayList<MenuEntity>()

    fun updateData(new: List<MenuEntity>) {
        list.clear()
        list.addAll(new)
        notifyDataSetChanged()
    }

    inner class ViewHolder(private val binding: ItemAddTransactionBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bindItem(menu: MenuEntity, ct: App, position: Int) {

            if (ct.getCart().checkData(menu.id)) {
                for (x in 0 until ct.getCart().cartSize) {
                    val product = ct.getCart().getProduct(x)
                    if (product.id == menu.id) {
                        binding.btnAdd.visibility = View.GONE
                        binding.llPlusMinusData.visibility = View.VISIBLE
                        binding.tvQuantity.text = product.quantity.toString()
                    }
                }
            } else {
                binding.btnAdd.visibility = View.VISIBLE
                binding.llPlusMinusData.visibility = View.GONE
            }

            binding.tvMenuName.text = menu.name.capitalizeWords()
            binding.tvMenuPrice.text = Utils.formatNumberToRupiah(menu.price, itemView.context)

            Glide.with(itemView.context).load(R.mipmap.image_placeholder)
                .error(R.mipmap.image_placeholder).placeholder(R.mipmap.image_placeholder)
                .transform(CenterCrop(), RoundedCorners(24))
                .into(binding.imgMenu)

            binding.tvPlus.setOnClickListener {
                if (ct.getCart().checkData(menu.id)) {
                    for (x in 0 until ct.getCart().cartSize) {
                        if (ct.getCart().getProduct(x).id == menu.id) {
                            var jumlahTemp = ct.getCart().getProduct(x).quantity
                            jumlahTemp++
                            ct.getCart().getProduct(x).quantity = jumlahTemp
                        }
                    }
                }

                notifyItemChanged(position)
                fragment.checkCart()

            }

            binding.tvMinus.setOnClickListener {
                var jumlah: Int = binding.tvQuantity.text.toString().toInt()
                jumlah--

                if (jumlah <= 0) {

                    for (i in 0 until ct.getCart().cartSize) {
                        if (ct.getCart().getProduct(i).id == menu.id) {
                            ct.getCart().removeProduct(i)
                            notifyItemChanged(position)
                            fragment.checkCart()
                            return@setOnClickListener
                        }
                    }
                } else {
                    for (i in 0 until ct.getCart().cartSize) {
                        if (ct.getCart().getProduct(i).id == menu.id) {
                            ct.getCart().getProduct(i).quantity = jumlah
                            binding.tvQuantity.text = jumlah.toString()
                        }
                    }
                }

                notifyItemChanged(position)
                fragment.checkCart()
            }

            itemView.setOnClickListener {
                var quantity = 0
                quantity++

                val product =
                    Product(id = menu.id, name = menu.name, price = menu.price, quantity = quantity)

                if (ct.getCart().checkData(menu.id)) {
                    for (x in 0 until ct.getCart().cartSize) {
                        if (ct.getCart().getProduct(x).id == menu.id) {
                            var jumlahTemp = ct.getCart().getProduct(x).quantity
                            jumlahTemp++
                            ct.getCart().getProduct(x).quantity = jumlahTemp
                        }
                    }
                } else {
                    ct.getCart().setProduct(product)
                }

                notifyItemChanged(position)
                fragment.checkCart()

            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding =
            ItemAddTransactionBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bindItem(list[position], ct, position)
    }

    override fun getItemCount(): Int = list.size
}