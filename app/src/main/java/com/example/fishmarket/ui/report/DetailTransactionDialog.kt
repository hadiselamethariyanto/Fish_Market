package com.example.fishmarket.ui.report

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.fishmarket.R
import com.example.fishmarket.databinding.FragmentDetailTransactionDialogBinding
import com.example.fishmarket.domain.model.DetailTransactionHistory
import com.example.fishmarket.ui.history.detail_history.DetailHistoryAdapter
import com.example.fishmarket.utilis.Utils
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import org.alkaaf.btprint.BluetoothPrint
import org.koin.androidx.navigation.koinNavGraphViewModel


class DetailTransactionDialog : BottomSheetDialogFragment() {

    private var _binding: FragmentDetailTransactionDialogBinding? = null
    private val binding get() = _binding!!
    private lateinit var detailHistoryAdapter: DetailHistoryAdapter
    private val viewModel: ReportViewModel by koinNavGraphViewModel(R.id.reportFragment)

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentDetailTransactionDialogBinding.inflate(layoutInflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val idRestaurant = arguments?.getString("idRestaurant") ?: ""
        val first = arguments?.getLong("first") ?: 0
        val second = arguments?.getLong("second") ?: 0
        val restaurantName = arguments?.getString("restaurantName") ?: ""
        val transactionCount = arguments?.getInt("transactionCount") ?: 0
        val originalFee = arguments?.getInt("originalFee") ?: 0
        val discount = arguments?.getInt("discount") ?: 0
        val totalFee = arguments?.getInt("totalFee") ?: 0

        binding.btnPrint.setOnClickListener {
            printDetailTransaction(
                restaurantName,
                first,
                second,
                transactionCount,
                originalFee,
                discount,
                totalFee
            )
        }

        binding.tvOriginalFee.text = Utils.formatNumberToRupiah(originalFee, requireActivity())
        binding.tvDiscount.text = "-" + Utils.formatNumberToRupiah(discount, requireActivity())
        binding.tvTotalIncome.text = Utils.formatNumberToRupiah(totalFee, requireActivity())

        viewModel.getDetailTransactionRestaurant(idRestaurant, first, second)
        viewModel.detailTransactionHistory.observe(
            viewLifecycleOwner,
            detailTransactionHistoryObserver
        )
    }

    private fun printDetailTransaction(
        restaurantName: String,
        first: Long,
        second: Long,
        transactionCount: Int,
        originalFee: Int,
        discount: Int,
        totalFee: Int,
    ) {
        val detailTransactionHistory = viewModel.detailTransactionHistory.value ?: listOf()
        val firstDate = Utils.formatDate(first)
        val secondDate = Utils.formatDate(second)

        val builder = BluetoothPrint.Builder(BluetoothPrint.Size.WIDTH58)
        builder.addLine()
        builder.setAlignMid()
        builder.addTextln(restaurantName)
        builder.addLine()
        builder.setAlignLeft()
        if (firstDate != secondDate) {
            builder.addFrontEnd("Dari tanggal: ", firstDate)
            builder.addFrontEnd("Sampai tanggal", secondDate)
        } else {
            builder.addFrontEnd("tanggal", firstDate)
        }
        builder.addFrontEnd("Jumlah Transaksi", transactionCount.toString())
        builder.addLine()

        for (x in detailTransactionHistory) {
            val name = x.name
            val price = x.price
            val quantity = x.quantity

            builder.setAlignLeft()
            if (x.unit == "Decimal") {
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

        builder.addLine()
        builder.addFrontEnd(
            "Pemasukan",
            ":${Utils.formatNumberToRupiah(originalFee, requireActivity())}"
        )
        builder.addFrontEnd(
            "Discount",
            ": -${Utils.formatNumberToRupiah(discount, requireActivity())}"
        )
        builder.addLine()
        builder.addFrontEnd(
            "Total Pemasukan",
            ":${
                Utils.formatNumberToRupiah(
                    totalFee,
                    requireActivity()
                )
            }"
        )
        builder.addLine()
        builder.setAlignMid()
        builder.addTextln("")
        builder.addTextln("")
        builder.addTextln("")
        builder.addTextln("")

        BluetoothPrint.with(requireActivity())
            .autoCloseAfter(1)
            .setData(builder.byte)
            .print()
    }

    private val detailTransactionHistoryObserver =
        Observer<List<DetailTransactionHistory>> { list ->
            detailHistoryAdapter = DetailHistoryAdapter(list)
            binding.rvDetail.adapter = detailHistoryAdapter
            binding.rvDetail.addItemDecoration(
                DividerItemDecoration(
                    requireActivity(),
                    LinearLayoutManager.VERTICAL
                )
            )
        }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

}