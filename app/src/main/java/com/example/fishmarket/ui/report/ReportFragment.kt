package com.example.fishmarket.ui.report

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.os.bundleOf
import androidx.core.util.Pair
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.fishmarket.R
import com.example.fishmarket.data.source.remote.Resource
import com.example.fishmarket.databinding.FragmentReportBinding
import com.example.fishmarket.domain.model.RestaurantTransaction
import com.example.fishmarket.utilis.Utils
import com.google.android.material.datepicker.MaterialDatePicker
import org.koin.androidx.navigation.koinNavGraphViewModel

class ReportFragment : Fragment() {

    private var _binding: FragmentReportBinding? = null
    private val binding get() = _binding!!
    private var restaurantTransactionAdapter = RestaurantTransactionAdapter()
    private val viewModel: ReportViewModel by koinNavGraphViewModel(R.id.reportFragment)
    private val currentTimeInMillis = System.currentTimeMillis()
    private val first = Utils.getFirstOfDayTimeInMillis(currentTimeInMillis)
    private val second = Utils.getEndOfDayTimeInMillis(currentTimeInMillis)

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentReportBinding.inflate(layoutInflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val dateRangePicker =
            MaterialDatePicker.Builder.dateRangePicker()
                .setTitleText("Select dates")
                .setSelection(
                    Pair(
                        MaterialDatePicker.thisMonthInUtcMilliseconds(),
                        MaterialDatePicker.todayInUtcMilliseconds()
                    )
                )
                .build()

        dateRangePicker.addOnPositiveButtonClickListener {
            val first = dateRangePicker.selection?.first ?: 0
            val second = Utils.getEndOfDayTimeInMillis(dateRangePicker.selection?.second ?: 0)

            viewModel.dateRangeChanged(first, second)
        }

        viewModel.dateRangeFormState.observe(viewLifecycleOwner) {
            binding.content.tvDate.text = resources.getString(
                R.string.selected_date,
                Utils.formatDate(it.first ?: this.first),
                Utils.formatDate(it.second ?: this.second)
            )

            viewModel.getRangeTransaction(it.first ?: this.first, it.second ?: this.second)
                .observe(viewLifecycleOwner, reportObserver)
        }

        restaurantTransactionAdapter.setOnItemClickCallback(object :
            RestaurantTransactionAdapter.OnItemClickCallback {
            override fun onItemClicked(
                idRestaurant: String,
                restaurantName: String,
                transactionCount: Int,
                originalFee: Int,
                discount: Int,
                totalFee: Int
            ) {
                val dateRange = viewModel.dateRangeFormState.value
                val first = dateRange?.first ?: this@ReportFragment.first
                val second = dateRange?.second ?: this@ReportFragment.second

                val bundle =
                    bundleOf(
                        "idRestaurant" to idRestaurant,
                        "first" to first,
                        "second" to second,
                        "restaurantName" to restaurantName,
                        "transactionCount" to transactionCount,
                        "originalFee" to originalFee,
                        "discount" to discount,
                        "totalFee" to totalFee
                    )

                findNavController().navigate(
                    R.id.action_reportFragment_to_detailTransactionDialog,
                    bundle
                )
            }
        })

        binding.content.rvRestaurant.adapter = restaurantTransactionAdapter
        binding.content.rvRestaurant.addItemDecoration(
            DividerItemDecoration(
                requireActivity(),
                LinearLayoutManager.VERTICAL
            )
        )

        binding.content.tvDate.setOnClickListener {
            dateRangePicker.show(requireActivity().supportFragmentManager, "")
        }
    }

    private val reportObserver = Observer<Resource<List<RestaurantTransaction>>> { res ->
        when (res) {
            is Resource.Loading -> {
                binding.content.content.visibility = View.GONE
                binding.loading.progress.visibility = View.VISIBLE
            }
            is Resource.Success -> {
                binding.content.content.visibility = View.VISIBLE
                binding.loading.progress.visibility = View.GONE

                val data = res.data
                if (data != null) {
                    binding.content.tvTotalSales.text =
                        data.sumOf { it.transactionCount }.toString()
                    binding.content.tvTotalIncome.text = Utils.formatDoubleToRupiah(
                        data.sumOf { it.income }, requireActivity()
                    )

                    val discount = data.sumOf { it.discount }
                    val originalFee = data.sumOf { it.originalFee }

                    binding.content.tvOriginalFeeAndDiscount.text = Utils.formatNumberToRupiah(
                        originalFee,
                        requireActivity()
                    ) + " - " + Utils.formatNumberToRupiah(discount, requireActivity())

                    restaurantTransactionAdapter.updateData(data)
                }

            }
            is Resource.Error -> {
                binding.content.content.visibility = View.VISIBLE
                binding.loading.progress.visibility = View.GONE
                Toast.makeText(requireActivity(), res.message, Toast.LENGTH_LONG).show()
            }
        }
    }


}