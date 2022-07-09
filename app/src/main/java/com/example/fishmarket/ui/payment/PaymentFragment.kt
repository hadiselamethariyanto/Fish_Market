package com.example.fishmarket.ui.payment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.fishmarket.R
import com.example.fishmarket.data.repository.transaction.source.local.entity.TransactionHomeEntity
import com.example.fishmarket.data.source.remote.Resource
import com.example.fishmarket.databinding.FragmentPaymentBinding
import com.example.fishmarket.domain.model.ChangeStatusTransaction
import com.example.fishmarket.ui.home.transaction.DetailTransactionPaymentAdapter
import com.example.fishmarket.ui.home.transaction.HomeViewModel
import com.example.fishmarket.utilis.Utils
import com.google.gson.Gson
import org.alkaaf.btprint.BluetoothPrint
import org.koin.androidx.navigation.koinNavGraphViewModel


class PaymentFragment : Fragment() {

    private var _binding: FragmentPaymentBinding? = null
    private val binding get() = _binding!!
    private val homeViewModel: HomeViewModel by koinNavGraphViewModel(R.id.transaction)
    private lateinit var detailTransactionPaymentAdapter: DetailTransactionPaymentAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentPaymentBinding.inflate(layoutInflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val json = arguments?.getString("transaction")
        val transaction = Gson().fromJson(json, TransactionHomeEntity::class.java)

        setupDetailTransactionPaymentAdapter()
        getDetailTransaction(transaction.id)

        homeViewModel.discount.observe(viewLifecycleOwner) { discount ->
            binding.tvDiscount.text = Utils.formatNumberToRupiah(discount, requireActivity())
            binding.tvDiscountValue.text =
                "-" + Utils.formatNumberToRupiah(discount, requireActivity())
            val totalFee = homeViewModel.getTotalFee()
            binding.tvTotalFee.text = Utils.formatNumberToRupiah(totalFee, requireActivity())
        }


        binding.tvDiscount.setOnClickListener {
            val bundle = bundleOf("original_fee" to transaction.total_fee)
            findNavController().navigate(R.id.action_paymentFragment_to_addDiscountFragment, bundle)
        }

        binding.btnSave.setOnClickListener {
            homeViewModel.changeStatusTransaction(transaction, 4, "")
                .observe(viewLifecycleOwner, changeStatusObserver)
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
                homeViewModel.resetDiscount()
                findNavController().popBackStack(R.id.navigation_home, false)
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
                val originalFee = homeViewModel.getOriginalFee()
                val totalFee = homeViewModel.getTotalFee()
                binding.tvSubtotal.text = Utils.formatNumberToRupiah(originalFee, requireActivity())
                binding.tvTotalFee.text = Utils.formatNumberToRupiah(totalFee, requireActivity())
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
            "Subtotal",
            ":${Utils.formatNumberToRupiah(transaction.original_fee, requireActivity())}"
        )
        builder.addFrontEnd(
            "Discount",
            ":${Utils.formatNumberToRupiah(transaction.discount, requireActivity())}"
        )
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