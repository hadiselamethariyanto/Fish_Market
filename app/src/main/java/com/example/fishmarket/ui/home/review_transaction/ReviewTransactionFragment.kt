package com.example.fishmarket.ui.home.review_transaction

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import com.example.fishmarket.App
import com.example.fishmarket.R
import com.example.fishmarket.data.repository.transaction.source.remote.model.DetailTransactionResponse
import com.example.fishmarket.data.source.remote.Resource
import com.example.fishmarket.databinding.FragmentReviewTransactionBinding
import com.example.fishmarket.domain.model.Transaction
import com.example.fishmarket.ui.home.add_transaction.AddTransactionViewModel
import com.example.fishmarket.utilis.Product
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
        setTable()
        selectTable()
        setupSaveTransaction()
        setDismissObserver()
    }

    private fun setupAdapter() {
        val detailTransaction = ct.getCart().cart
        detailHistoryAdapter = ReviewTransactionAdapter(detailTransaction)
        detailHistoryAdapter.setOnItemClickCallback(object :
            ReviewTransactionAdapter.OnItemClickCallback {
            override fun onItemClicked(position: Int, product: Product) {
                val bundle = bundleOf(
                    "position" to position,
                    "name" to product.name,
                    "price" to product.price,
                    "quantity" to product.quantity,
                    "unit" to product.unit
                )

                findNavController().navigate(
                    R.id.action_reviewTransactionFragment_to_editTransactionFragment,
                    bundle
                )
            }
        })
        binding.rvDetailTransaction.adapter = detailHistoryAdapter
    }

    private fun setDismissObserver() {
        findNavController().currentBackStackEntry?.savedStateHandle?.getLiveData<Boolean>(
            "DISMISS"
        )?.observe(viewLifecycleOwner) {
            it?.let {
                setupInfo()
                detailHistoryAdapter.notifyDataSetChanged()
            }
        }
    }

    private fun setupInfo() {
        val totalFee = ct.getCart().totalFee
        binding.tvTotalFee.text = Utils.formatNumberToRupiah(totalFee, requireActivity())
    }

    private fun setTable() {
        viewModel.table.observe(viewLifecycleOwner) {
            binding.tvTableName.text = it.name
            binding.tvSelectTable.text = it.name
        }
    }

    private fun selectTable() {
        binding.tvSelectTable.setOnClickListener {
            findNavController().navigate(R.id.action_reviewTransactionFragment_to_navigation_dialog_select_table)
        }
    }

    private fun setupSaveTransaction() {
        binding.btnSave.setOnClickListener {

            val table = viewModel.table.value?.name ?: ""

            if (table.isEmpty()) {
                Utils.showMessage(
                    requireActivity(),
                    getString(R.string.warning_message_select_table)
                )
            } else {
                val totalFee = ct.getCart().totalFee
                val detailList = ArrayList<DetailTransactionResponse>()
                for (x in 0 until ct.getCart().cartSize) {
                    val menu = ct.getCart().getProduct(x)
                    val detail = DetailTransactionResponse(
                        id = Utils.getRandomString(),
                        id_menu = menu.id,
                        quantity = menu.quantity,
                        price = menu.price,
                        status = true
                    )
                    detailList.add(detail)
                }
                viewModel.addTransaction(totalFee, detailList)
                    .observe(viewLifecycleOwner, addTransactionObserver)
            }
        }
    }


    private val addTransactionObserver = Observer<Resource<Transaction>> { res ->
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