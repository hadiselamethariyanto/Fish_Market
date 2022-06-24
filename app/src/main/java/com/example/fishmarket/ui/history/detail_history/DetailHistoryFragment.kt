package com.example.fishmarket.ui.history.detail_history

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.fishmarket.R
import com.example.fishmarket.databinding.FragmentDetailHistoryBinding
import com.example.fishmarket.domain.model.DetailTransactionHistory
import com.example.fishmarket.domain.model.TransactionWithDetail
import com.example.fishmarket.utilis.Utils
import com.google.gson.Gson
import org.alkaaf.btprint.BluetoothPrint

class DetailHistoryFragment : Fragment() {

    private var _binding: FragmentDetailHistoryBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentDetailHistoryBinding.inflate(layoutInflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val bundle = arguments?.getString("data")
        val transaction = Gson().fromJson(bundle, TransactionWithDetail::class.java)

        binding.id.text = transaction.transaction.id
        binding.time.text = Utils.formatTime(transaction.transaction.created_date)
        binding.totalFee.text =
            Utils.formatNumberToRupiah(transaction.transaction.total_fee, requireActivity())

        setupAdapter(transaction.detailTransactionHistory)

        binding.btnPrint.setOnClickListener {
            print(transaction)
        }
    }

    private fun setupAdapter(list: List<DetailTransactionHistory>) {
        val detailHistoryAdapter = DetailHistoryAdapter(list)
        binding.rvDetail.adapter = detailHistoryAdapter
        binding.rvDetail.addItemDecoration(
            DividerItemDecoration(
                requireActivity(),
                LinearLayoutManager.VERTICAL
            )
        )

        if (list.isNotEmpty()) {
            binding.rvDetail.visibility = View.VISIBLE
            binding.emptyData.llNoData.visibility = View.GONE
        } else {
            binding.rvDetail.visibility = View.GONE
            binding.emptyData.llNoData.visibility = View.VISIBLE
        }

    }

    private fun print(transaction: TransactionWithDetail) {
        val builder = BluetoothPrint.Builder(BluetoothPrint.Size.WIDTH58)
        builder.addLine()
        builder.setAlignMid()
        builder.addTextln(getString(R.string.app_name))
        builder.addTextln(getString(R.string.address))
        builder.addLine()
        builder.setAlignLeft()
        builder.addFrontEnd("No Transaksi: ", transaction.transaction.id)
        builder.addFrontEnd("Hari: ", Utils.formatDate(transaction.transaction.created_date))
        builder.addLine()

        val detailTransaction = transaction.detailTransactionHistory
        for (product in detailTransaction) {
            val name = product.name
            val quantity = product.quantity
            val price = product.price

            builder.setAlignLeft()
            builder.addFrontEnd(
                "$name $quantity x $price",
                ":${quantity * price}"
            )
        }

        builder.addLine()
        builder.addFrontEnd(
            "Total Biaya",
            ":${Utils.formatNumberToRupiah(transaction.transaction.total_fee, requireActivity())}"
        )
        builder.addLine()
        builder.setAlignMid()
        builder.addTextln("Terimakasih atas kunjungan anda")
        builder.addTextln("---------Copy----------")
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