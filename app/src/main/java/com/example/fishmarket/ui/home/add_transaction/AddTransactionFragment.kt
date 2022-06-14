package com.example.fishmarket.ui.home.add_transaction

import android.os.Bundle
import android.util.Log
import android.view.*
import android.widget.TextView
import androidx.fragment.app.Fragment
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import com.example.fishmarket.App
import com.example.fishmarket.R
import com.example.fishmarket.data.repository.menu.source.local.entity.MenuEntity
import com.example.fishmarket.data.repository.transaction.source.remote.model.DetailTransactionResponse
import com.example.fishmarket.data.source.remote.Resource
import com.example.fishmarket.databinding.FragmentAddTransactionBinding
import com.example.fishmarket.utilis.Utils
import org.koin.androidx.navigation.koinNavGraphViewModel

class AddTransactionFragment : Fragment() {

    private var _binding: FragmentAddTransactionBinding? = null
    private val binding get() = _binding!!
    private val viewModel: AddTransactionViewModel by koinNavGraphViewModel(R.id.home)
    private val ct = App.getApp()
    private lateinit var addTransactionAdapter: AddTransactionAdapter
    private lateinit var tvBadge: TextView

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentAddTransactionBinding.inflate(inflater, container, false)
        setHasOptionsMenu(true)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setTable()

        val gridLayoutManager = GridLayoutManager(requireActivity(), 2)
        addTransactionAdapter = AddTransactionAdapter(ct, this)

        binding.rvMenu.adapter = addTransactionAdapter
        binding.rvMenu.layoutManager = gridLayoutManager

        binding.llSaveTransaction.setOnClickListener {
            val tableId = viewModel.table.value?.id ?: ""
            if (tableId == "") {
                Toast.makeText(requireActivity(), "mohon pilih meja", Toast.LENGTH_LONG).show()
            } else {
                val totalFee = ct.getCart().totalFee
                val detailList = ArrayList<DetailTransactionResponse>()
                for (x in 0 until ct.getCart().cartSize) {
                    val menu = ct.getCart().getProduct(x)
                    val detail = DetailTransactionResponse(
                        id = Utils.getRandomString(),
                        id_menu = menu.id,
                        quantity = menu.quantity,
                        price = menu.price
                    )
                    detailList.add(detail)
                }
                viewModel.addTransaction(totalFee, detailList).observe(viewLifecycleOwner) { res ->
                    when (res) {
                        is Resource.Loading -> {
                            binding.tvTotalFee.visibility = View.GONE
                            binding.tvTotalItem.visibility = View.GONE
                            binding.progress.visibility = View.VISIBLE
                            binding.llSaveTransaction.isEnabled = false
                        }
                        is Resource.Success -> {
                            binding.tvTotalFee.visibility = View.VISIBLE
                            binding.tvTotalItem.visibility = View.VISIBLE
                            binding.progress.visibility = View.GONE
                            binding.llSaveTransaction.isEnabled = true
                            viewModel.resetTransaction()
                            ct.getCart().clearArray()
                            checkCart()
                            addTransactionAdapter.notifyDataSetChanged()
                        }
                        is Resource.Error -> {
                            binding.tvTotalFee.visibility = View.VISIBLE
                            binding.tvTotalItem.visibility = View.VISIBLE
                            binding.progress.visibility = View.GONE
                            binding.llSaveTransaction.isEnabled = true

                            Toast.makeText(
                                requireActivity(),
                                res.message.toString(),
                                Toast.LENGTH_LONG
                            ).show()
                        }
                    }
                }
            }
        }
        viewModel.getMenus().observe(viewLifecycleOwner, menusObserver)
    }

    private fun setTable() {
        viewModel.table.observe(viewLifecycleOwner) {
            val tableId = it.id
            if (tableId == "") {
                tvBadge.visibility = View.GONE
            } else {
                tvBadge.visibility = View.VISIBLE
                tvBadge.text = it.name
            }
        }
    }

    fun checkCart() {
        val total = ct.getCart().totalItem
        if (total == 0) {
            binding.llSaveTransaction.visibility = View.GONE
        } else {
            val totalFee = ct.getCart().totalFee
            binding.tvTotalItem.text = "$total Items"
            binding.tvTotalFee.text = Utils.formatNumberToRupiah(totalFee, requireActivity())
            binding.llSaveTransaction.visibility = View.VISIBLE
        }
    }

    private val menusObserver = Observer<Resource<List<MenuEntity>>> { res ->
        when (res) {
            is Resource.Loading -> {
                binding.refresh.isRefreshing = true
            }
            is Resource.Success -> {
                binding.refresh.isRefreshing = false
                if (res.data != null) {
                    if (res.data.isEmpty()) {
                        binding.rvMenu.visibility = View.GONE
                        binding.llNoData.visibility = View.VISIBLE
                    } else {
                        binding.rvMenu.visibility = View.VISIBLE
                        binding.llNoData.visibility = View.GONE
                    }
                    addTransactionAdapter.updateData(res.data)
                }
            }
            is Resource.Error -> {
                binding.refresh.isRefreshing = false
                Toast.makeText(requireActivity(), res.message.toString(), Toast.LENGTH_LONG)
                    .show()
            }
        }

    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        super.onCreateOptionsMenu(menu, inflater)
        requireActivity().menuInflater.inflate(R.menu.menu_home, menu)

        val menuItem = menu.findItem(R.id.select_table)
        val actionView = menuItem.actionView
        tvBadge = actionView.findViewById(R.id.table_badge)

        actionView.setOnClickListener {
            onOptionsItemSelected(menuItem)
        }

    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.select_table -> {
                findNavController().navigate(R.id.action_navigation_add_transaction_to_navigation_dialog_select_table)
                true
            }
            else -> {
                super.onOptionsItemSelected(item)
            }
        }
    }

    override fun onResume() {
        super.onResume()
        checkCart()
    }


}