package com.example.fishmarket.ui.home.transaction

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.os.bundleOf
import androidx.core.view.isVisible
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.fishmarket.R
import com.example.fishmarket.data.repository.transaction.source.local.entity.TransactionHomeEntity
import com.example.fishmarket.data.source.remote.Resource
import com.example.fishmarket.databinding.DialogChangeStatusTransactionBinding
import com.example.fishmarket.domain.model.ChangeStatusTransaction
import com.example.fishmarket.ui.home.add_transaction.SelectRestaurantAdapter
import com.example.fishmarket.utilis.Utils
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.google.gson.Gson
import org.alkaaf.btprint.BluetoothPrint
import org.koin.androidx.navigation.koinNavGraphViewModel

class ChangeStatusTransactionDialog : BottomSheetDialogFragment() {

    private var _binding: DialogChangeStatusTransactionBinding? = null
    private val binding get() = _binding!!
    private val homeViewModel: HomeViewModel by koinNavGraphViewModel(R.id.transaction)
    private lateinit var detailTransactionPaymentAdapter: DetailTransactionPaymentAdapter

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = DialogChangeStatusTransactionBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val json = arguments?.getString("transaction")
        val transaction = Gson().fromJson(json, TransactionHomeEntity::class.java)

        setupDetailTransactionPaymentAdapter()
        getDetailTransaction(transaction.id)

        val changeStatusAdapter = ChangeStatusTransactionAdapter(requireActivity())

        binding.rvStatus.adapter = changeStatusAdapter
        changeStatusAdapter.setOnItemClickCallBack(object :
            ChangeStatusTransactionAdapter.OnItemClickCallback {
            override fun onItemClicked(id: Int) {
                when (id) {
                    2 -> {
                        binding.llSelectRestaurant.visibility = View.VISIBLE
                        binding.rlDetail.visibility = View.GONE
                    }
                    4 -> {
//                        binding.llSelectRestaurant.visibility = View.GONE
//                        binding.rlDetail.visibility = View.VISIBLE
                    }
                    else -> {
                        binding.llSelectRestaurant.visibility = View.GONE
                        binding.rlDetail.visibility = View.GONE
                    }
                }
            }
        })

        val restaurantAdapter = SelectRestaurantAdapter()
        binding.rvRestaurant.adapter = restaurantAdapter
        binding.rvRestaurant.layoutManager = GridLayoutManager(requireActivity(), 3)

        homeViewModel.getRestaurant().observe(viewLifecycleOwner) { res ->
            when (res) {
                is Resource.Loading -> {

                }
                is Resource.Success -> {
                    if (res.data != null) {
                        restaurantAdapter.updateData(res.data)
                        restaurantAdapter.selectRestaurant(transaction.id_restaurant)
                    }
                }
                is Resource.Error -> {

                }
            }
        }

        binding.rvStatus.layoutManager = GridLayoutManager(requireActivity(), 4)

        homeViewModel.getStatusTransaction().observe(viewLifecycleOwner) { res ->
            when (res) {
                is Resource.Loading -> {
                    binding.btnSave.isVisible = false
                    binding.rvStatus.isVisible = false
                    binding.refresh.isVisible = true
                }
                is Resource.Success -> {
                    if (res.data != null) {
                        changeStatusAdapter.updateData(res.data)
                        changeStatusAdapter.selectStatus(transaction.status)
                    }
                    binding.refresh.isVisible = false
                    binding.btnSave.isVisible = true
                    binding.rvStatus.isVisible = true
                }
                is Resource.Error -> {
                    Toast.makeText(requireActivity(), res.message.toString(), Toast.LENGTH_LONG)
                        .show()
                    binding.refresh.isVisible = false
                    binding.btnSave.isVisible = true
                    binding.rvStatus.isVisible = true
                }
            }
        }

        binding.btnSave.setOnClickListener {
            val newStatus = changeStatusAdapter.getStatus()
            if (newStatus > transaction.status + 1) {
                Toast.makeText(
                    requireActivity(),
                    resources.getString(R.string.warning_select_progress),
                    Toast.LENGTH_LONG
                ).show()
            } else {
                if (newStatus == 2) {
                    if (restaurantAdapter.getSelectedPosition() == -1) {
                        Toast.makeText(
                            requireActivity(),
                            resources.getString(R.string.warning_message_select_restaurant),
                            Toast.LENGTH_LONG
                        ).show()
                    } else {
                        val restaurant = restaurantAdapter.getSelectedRestaurant()
                        homeViewModel.changeStatusTransaction(
                            transaction,
                            newStatus,
                            restaurant.id
                        ).observe(viewLifecycleOwner, changeStatusObserver)
                    }
                } else if (newStatus == 4) {
                    val jsonTransaction = Gson().toJson(transaction)

                    val bundle = bundleOf("transaction" to jsonTransaction)
                    findNavController().navigate(
                        R.id.action_navigation_dialog_change_status_transaction_to_paymentFragment,
                        bundle
                    )
                    dismiss()
                } else {
                    homeViewModel.changeStatusTransaction(transaction, newStatus, "")
                        .observe(viewLifecycleOwner, changeStatusObserver)
                }
            }
        }
    }

    private val changeStatusObserver = Observer<Resource<ChangeStatusTransaction>> { res ->
        when (res) {
            is Resource.Loading -> {

            }
            is Resource.Success -> {
                if ((res.data?.status ?: 0) == 4 || (res.data?.status ?: 0) == 2) {
                    res.data?.let { printTransaction(it) }
                }
                dismiss()
            }
            is Resource.Error -> {

            }
        }
    }

    private fun setupDetailTransactionPaymentAdapter() {
        detailTransactionPaymentAdapter = DetailTransactionPaymentAdapter()
        detailTransactionPaymentAdapter.setOnItemClickCallback(object :
            DetailTransactionPaymentAdapter.OnItemClickCallback {
            override fun onRemoveClicked(position: Int) {
                homeViewModel.removeItem(position)
            }
        })
        binding.rvDetailTransaction.adapter = detailTransactionPaymentAdapter
        binding.rvDetailTransaction.addItemDecoration(
            DividerItemDecoration(
                requireActivity(),
                LinearLayoutManager.VERTICAL
            )
        )
    }

    private fun getDetailTransaction(id: String) {
        homeViewModel.getDetailTransaction(id)
        homeViewModel.detailTransactionHistory.observe(viewLifecycleOwner) { list ->
            if (list.isNotEmpty()) {
                detailTransactionPaymentAdapter.updateData(list)
//                val totalFee = homeViewModel.getTotalFee()
//                binding.tvTotalFee.text = Utils.formatNumberToRupiah(totalFee, requireActivity())
            }
        }
    }

    private fun printTransaction(transaction: ChangeStatusTransaction) {
        val detailTransaction = homeViewModel.detailTransactionHistory.value

        val builder = BluetoothPrint.Builder(BluetoothPrint.Size.WIDTH58)
        builder.addLine()
        builder.setAlignMid()
        builder.addTextln(getString(R.string.app_name))
        builder.addTextln(getString(R.string.address))
        builder.addLine()
        builder.setAlignLeft()
        builder.addFrontEnd("No Urut: ", transaction.no_urut.toString())
        builder.addFrontEnd("Hari: ", Utils.formatDate(transaction.created_date))
        builder.addFrontEnd("Pembakar: ", transaction.restaurant_name)
        builder.addFrontEnd("Meja: ", transaction.table_name)
        builder.addLine()

        for (x in 0 until detailTransaction?.size!!) {
            val product = detailTransaction[x]
            val name = product.name
            val quantity = product.quantity
            val price = product.price

            if (product.status) {
                builder.setAlignLeft()
                if (product.unit == "Decimal") {
                    builder.addFrontEnd(
                        "$name x $quantity",
                        ":${Utils.formatNumberThousand((quantity * price).toInt())}"
                    )
                } else {
                    builder.addFrontEnd(
                        "$name x ${quantity.toInt()}",
                        ":${Utils.formatNumberThousand((quantity * price).toInt())}"
                    )
                }
            }
        }

        builder.addLine()
        builder.addFrontEnd(
            "Total Biaya",
            ":${Utils.formatNumberToRupiah(transaction.total_fee, requireActivity())}"
        )
        builder.addLine()
        builder.setAlignMid()
        builder.addTextln("Terimakasih atas kunjungan anda")
        builder.addTextln("")
        builder.addTextln("")
        builder.addTextln("")
        builder.addTextln("")

        BluetoothPrint.with(requireActivity())
            .autoCloseAfter(1)
            .setData(builder.byte)
            .print()
    }



    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}