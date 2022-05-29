package com.example.fishmarket.ui.home.transaction

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.example.fishmarket.R
import com.example.fishmarket.data.repository.transaction.source.local.entity.TransactionHomeEntity
import com.example.fishmarket.data.source.remote.Resource
import com.example.fishmarket.databinding.FragmentHomeBinding
import com.google.gson.Gson
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
                if (transaction.status != 4) {
                    val json = Gson().toJson(transaction)

                    val bundle = bundleOf(
                        "transaction" to json
                    )

                    findNavController().navigate(
                        R.id.action_navigation_home_to_navigation_dialog_change_status_transaction,
                        bundle
                    )
                }
            }
        })
        binding.rvTransactions.adapter = transactionAdapter

        viewModel.transactions.observe(viewLifecycleOwner) { res ->
            when (res) {
                is Resource.Loading -> {
                    binding.refresh.isRefreshing = true
                }
                is Resource.Success -> {
                    if (res.data != null) {
                        if (res.data.isNotEmpty()) {
                            binding.rvTransactions.visibility = View.VISIBLE
                            binding.llNoData.visibility = View.GONE
                            transactionAdapter.updateData(res.data)
                        } else {
                            binding.rvTransactions.visibility = View.GONE
                            binding.llNoData.visibility = View.VISIBLE
                        }
                    }
                    binding.refresh.isRefreshing = false
                }
                is Resource.Error -> {
                    binding.refresh.isRefreshing = false
                    Toast.makeText(requireActivity(), res.message.toString(), Toast.LENGTH_LONG)
                        .show()
                }
            }
        }

        viewModel.filter.observe(viewLifecycleOwner) {
            if (it == 0) {
                binding.btnAll.background = ContextCompat.getDrawable(
                    requireActivity(),
                    R.drawable.rectangle_highlighted_transaction
                )
                binding.btnAll.setTextColor(
                    ContextCompat.getColor(
                        requireActivity(),
                        R.color.white
                    )
                )

                binding.btnFinished.background = ContextCompat.getDrawable(
                    requireActivity(),
                    R.drawable.rectangle_not_highlighted_transaction
                )
                binding.btnFinished.setTextColor(
                    ContextCompat.getColor(
                        requireActivity(),
                        R.color.orange_500
                    )
                )
            } else {
                binding.btnAll.background = ContextCompat.getDrawable(
                    requireActivity(),
                    R.drawable.rectangle_not_highlighted_transaction
                )
                binding.btnAll.setTextColor(
                    ContextCompat.getColor(
                        requireActivity(),
                        R.color.orange_500
                    )
                )

                binding.btnFinished.background = ContextCompat.getDrawable(
                    requireActivity(),
                    R.drawable.rectangle_highlighted_transaction
                )
                binding.btnFinished.setTextColor(
                    ContextCompat.getColor(
                        requireActivity(),
                        R.color.white
                    )
                )
            }
        }

        binding.fabAddTransaction.setOnClickListener {
            findNavController().navigate(R.id.action_navigation_home_to_navigation_add_transaction)
        }

        binding.btnAll.setOnClickListener {
            viewModel.changeFilter()
        }

        binding.btnFinished.setOnClickListener {
            viewModel.changeFilter()
        }


    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}