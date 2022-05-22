package com.example.fishmarket.ui.home.transaction

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.example.fishmarket.R
import com.example.fishmarket.data.repository.transaction.source.local.entity.TransactionHomeEntity
import com.example.fishmarket.databinding.FragmentHomeBinding
import org.koin.androidx.viewmodel.ext.android.viewModel

class HomeFragment : Fragment() {

    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding!!
    private val viewModel: HomeViewModel by viewModel()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val transactionAdapter = TransactionAdapter(requireActivity())
        transactionAdapter.setOnItemClickCallback(object : TransactionAdapter.OnItemClickCallback {
            override fun onItemClicked(transaction: TransactionHomeEntity) {
                if (transaction.status != 3) {
                    val bundle = bundleOf(
                        "status" to transaction.status,
                        "id" to transaction.id,
                        "id_table" to transaction.id_table
                    )
                    findNavController().navigate(
                        R.id.action_navigation_home_to_navigation_dialog_change_status_transaction,
                        bundle
                    )
                }
            }
        })
        binding.rvTransactions.adapter = transactionAdapter

        viewModel.getTransactions().observe(viewLifecycleOwner) {
            if (it.isNotEmpty()) {
                binding.rvTransactions.visibility = View.VISIBLE
                binding.llNoData.visibility = View.GONE
                transactionAdapter.updateData(it)
            } else {
                binding.rvTransactions.visibility = View.GONE
                binding.llNoData.visibility = View.VISIBLE
            }
        }

        binding.fabAddTransaction.setOnClickListener {
            findNavController().navigate(R.id.action_navigation_home_to_navigation_add_transaction)
        }


    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}