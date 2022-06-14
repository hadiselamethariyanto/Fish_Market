package com.example.fishmarket.ui.history.detail_history

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.fishmarket.data.repository.transaction.source.local.entity.DetailTransactionEntity
import com.example.fishmarket.data.repository.transaction.source.local.entity.DetailTransactionHistoryEntity
import com.example.fishmarket.data.repository.transaction.source.local.entity.TransactionWithDetailEntity
import com.example.fishmarket.databinding.FragmentDetailHistoryBinding
import com.example.fishmarket.utilis.Utils
import com.google.gson.Gson

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
        val transaction = Gson().fromJson(bundle, TransactionWithDetailEntity::class.java)

        binding.id.text = transaction.transactionEntity.id
        binding.time.text = Utils.formatTime(transaction.transactionEntity.created_date)
        binding.totalFee.text =
            Utils.formatNumberToRupiah(transaction.transactionEntity.total_fee, requireActivity())

        setupAdapter(transaction.detailTransactionEntity)

    }

    private fun setupAdapter(list: List<DetailTransactionHistoryEntity>) {
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

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

}