package com.example.fishmarket.ui.report

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.fishmarket.R
import com.example.fishmarket.databinding.FragmentDetailTransactionDialogBinding
import com.example.fishmarket.ui.history.detail_history.DetailHistoryAdapter
import com.example.fishmarket.utilis.Utils
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
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

        viewModel.getDetailTransactionRestaurant(idRestaurant, first, second)
            .observe(viewLifecycleOwner) { list ->
                detailHistoryAdapter = DetailHistoryAdapter(list)
                binding.rvDetail.adapter = detailHistoryAdapter
                binding.rvDetail.addItemDecoration(
                    DividerItemDecoration(
                        requireActivity(),
                        LinearLayoutManager.VERTICAL
                    )
                )

                val newList = list.filter { it.status }
                binding.tvTotalIncome.text =
                    Utils.formatDoubleToRupiah(
                        newList.sumOf { data -> data.price * data.quantity },
                        requireActivity()
                    )
            }

    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

}