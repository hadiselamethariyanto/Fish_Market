package com.example.fishmarket.ui.home.review_transaction

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import com.example.fishmarket.App
import com.example.fishmarket.R
import com.example.fishmarket.data.repository.transaction.source.remote.model.DetailTransactionResponse
import com.example.fishmarket.data.source.remote.Resource
import com.example.fishmarket.databinding.FragmentReviewTransactionBinding
import com.example.fishmarket.ui.home.add_transaction.AddTransactionViewModel
import com.example.fishmarket.utilis.Utils
import org.koin.androidx.navigation.koinNavGraphViewModel


class ReviewTransactionFragment : Fragment() {

    private var _binding: FragmentReviewTransactionBinding? = null
    private val binding get() = _binding!!
    private lateinit var detailHistoryAdapter: ReviewTransactionAdapter
    private val viewModel: AddTransactionViewModel by koinNavGraphViewModel(R.id.home)
    private val ct = App.getApp()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentReviewTransactionBinding.inflate(layoutInflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupAdapter()
        setupInfo()
        setupSaveTransaction()
    }

    private fun setupAdapter() {
        val detailTransaction = ct.getCart().cart
        detailHistoryAdapter = ReviewTransactionAdapter(detailTransaction)
        binding.rvDetailTransaction.adapter = detailHistoryAdapter
    }

    private fun setupInfo() {
        val totalFee = ct.getCart().totalFee
        binding.tvTableName.text = viewModel.table.value?.name ?: ""
        binding.tvTotalFee.text = Utils.formatNumberToRupiah(totalFee, requireActivity())
    }

    private fun setupSaveTransaction() {
        binding.btnSave.setOnClickListener {
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
                        binding.btnSave.isEnabled = false
                    }
                    is Resource.Success -> {
                        binding.btnSave.isEnabled = true
                        viewModel.resetTransaction()
                        ct.getCart().clearArray()
                        findNavController().popBackStack(R.id.navigation_add_transaction, false)
                    }
                    is Resource.Error -> {
                        binding.btnSave.isEnabled = true
                        Utils.showMessage(requireActivity(), res.message.toString())
                    }
                }
            }
        }
    }

}