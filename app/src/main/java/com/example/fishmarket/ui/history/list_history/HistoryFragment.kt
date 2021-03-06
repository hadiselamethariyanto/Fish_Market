package com.example.fishmarket.ui.history.list_history

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.os.bundleOf
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.fishmarket.R
import com.example.fishmarket.data.source.remote.Resource
import com.example.fishmarket.databinding.FragmentHistoryBinding
import com.example.fishmarket.domain.model.History
import com.example.fishmarket.domain.model.TransactionWithDetail
import com.example.fishmarket.utilis.Utils
import com.google.gson.Gson
import org.koin.androidx.viewmodel.ext.android.viewModel

class HistoryFragment : Fragment() {

    private val viewModel: HistoryViewModel by viewModel()
    private var _binding: FragmentHistoryBinding? = null
    private val binding get() = _binding!!
    private lateinit var historyAdapter: HistoryAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentHistoryBinding.inflate(layoutInflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        historyAdapter = HistoryAdapter()
        historyAdapter.setOnItemClickCallback(object : HistoryAdapter.OnItemClickCallback {
            override fun onItemClicked(transaction: TransactionWithDetail) {
                val jsonString = Gson().toJson(transaction)
                val bundle = bundleOf("data" to jsonString)
                findNavController().navigate(
                    R.id.action_historyFragment_to_detailHistoryFragment,
                    bundle
                )
            }
        })

        binding.rvHistory.adapter = historyAdapter
        binding.rvHistory.addItemDecoration(
            DividerItemDecoration(
                requireActivity(),
                LinearLayoutManager.VERTICAL
            )
        )

        viewModel.getTransactionsWithDetail().observe(viewLifecycleOwner, transactionObserver)

    }

    private val transactionObserver = Observer<Resource<List<TransactionWithDetail>>> { res ->
        when (res) {
            is Resource.Loading -> {
                binding.refresh.isRefreshing = true
            }
            is Resource.Success -> {
                binding.refresh.isRefreshing = false
                if (res.data != null) {
                    var previousDate = ""
                    var date: String

                    val listHistory = mutableListOf<History>()
                    for (x in res.data) {
                        date = Utils.formatDate(x.transaction.created_date)
                        if (date != previousDate) {
                            val newHistory = History(1, x)
                            listHistory.add(newHistory)
                            previousDate = date
                        }

                        val newHistory = History(0, x)
                        listHistory.add(newHistory)
                    }
                    historyAdapter.updateData(listHistory)
                }
            }
            is Resource.Error -> {
                binding.refresh.isRefreshing = false
                Toast.makeText(requireActivity(), res.message, Toast.LENGTH_LONG).show()
            }
        }

    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }


}