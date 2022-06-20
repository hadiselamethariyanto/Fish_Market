package com.example.fishmarket.ui.home.transaction

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.view.isVisible
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.fishmarket.R
import com.example.fishmarket.data.repository.transaction.source.local.entity.TransactionHomeEntity
import com.example.fishmarket.data.source.remote.Resource
import com.example.fishmarket.databinding.DialogChangeStatusTransactionBinding
import com.example.fishmarket.domain.model.Transaction
import com.example.fishmarket.ui.history.detail_history.DetailHistoryAdapter
import com.example.fishmarket.ui.home.add_transaction.SelectRestaurantAdapter
import com.example.fishmarket.utilis.Utils
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.google.gson.Gson
import org.koin.androidx.navigation.koinNavGraphViewModel

class ChangeStatusTransactionDialog : BottomSheetDialogFragment() {

    private var _binding: DialogChangeStatusTransactionBinding? = null
    private val binding get() = _binding!!
    private val homeViewModel: HomeViewModel by koinNavGraphViewModel(R.id.transaction)

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = DialogChangeStatusTransactionBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val json = arguments?.getString("transaction")
        val transaction = Gson().fromJson(json, TransactionHomeEntity::class.java)

        getDetailTransaction(transaction.id)

        val changeStatusAdapter = ChangeStatusTransactionAdapter(requireActivity())

        binding.rvStatus.adapter = changeStatusAdapter
        changeStatusAdapter.setOnItemClickCallBack(object :
            ChangeStatusTransactionAdapter.OnItemClickCallback {
            override fun onItemClicked(id: Int) {
                when (id) {
                    2 -> {
                        binding.llSelectRestaurant.visibility = View.VISIBLE
                        binding.rlDetail.visibility = View.GONE
                    }
                    4 -> {
                        binding.llSelectRestaurant.visibility = View.GONE
                        binding.rlDetail.visibility = View.VISIBLE
                    }
                    else -> {
                        binding.llSelectRestaurant.visibility = View.GONE
                        binding.rlDetail.visibility = View.GONE
                    }
                }
            }
        })

        val restaurantAdapter = SelectRestaurantAdapter()
        binding.rvRestaurant.adapter = restaurantAdapter
        binding.rvRestaurant.layoutManager = GridLayoutManager(requireActivity(), 3)

        homeViewModel.getRestaurant().observe(viewLifecycleOwner) { res ->
            when (res) {
                is Resource.Loading -> {

                }
                is Resource.Success -> {
                    if (res.data != null) {
                        restaurantAdapter.updateData(res.data)
                        restaurantAdapter.selectRestaurant(transaction.id_restaurant)
                    }
                }
                is Resource.Error -> {

                }
            }
        }

        binding.rvStatus.layoutManager = GridLayoutManager(requireActivity(), 4)

        homeViewModel.getStatusTransaction().observe(viewLifecycleOwner) { res ->
            when (res) {
                is Resource.Loading -> {
                    binding.btnSave.isVisible = false
                    binding.rvStatus.isVisible = false
                    binding.refresh.isVisible = true
                }
                is Resource.Success -> {
                    if (res.data != null) {
                        changeStatusAdapter.updateData(res.data)
                        changeStatusAdapter.selectStatus(transaction.status)
                    }
                    binding.refresh.isVisible = false
                    binding.btnSave.isVisible = true
                    binding.rvStatus.isVisible = true
                }
                is Resource.Error -> {
                    Toast.makeText(requireActivity(), res.message.toString(), Toast.LENGTH_LONG)
                        .show()
                    binding.refresh.isVisible = false
                    binding.btnSave.isVisible = true
                    binding.rvStatus.isVisible = true
                }
            }
        }

        binding.btnSave.setOnClickListener {
            val newStatus = changeStatusAdapter.getStatus()
            if (newStatus > transaction.status + 1) {
                Toast.makeText(
                    requireActivity(),
                    resources.getString(R.string.warning_select_progress),
                    Toast.LENGTH_LONG
                ).show()
            } else {
                if (newStatus == 2) {
                    if (restaurantAdapter.getSelectedPosition() == -1) {
                        Toast.makeText(
                            requireActivity(),
                            resources.getString(R.string.warning_message_select_restaurant),
                            Toast.LENGTH_LONG
                        ).show()
                    } else {
                        val restaurant = restaurantAdapter.getSelectedRestaurant()
                        homeViewModel.changeStatusTransaction(
                            transaction,
                            newStatus,
                            restaurant.id
                        ).observe(viewLifecycleOwner, changeStatusObserver)
                    }
                } else {
                    homeViewModel.changeStatusTransaction(transaction, newStatus, "")
                        .observe(viewLifecycleOwner, changeStatusObserver)
                }
            }
        }
    }

    private val changeStatusObserver = Observer<Resource<Transaction>> { res ->
        when (res) {
            is Resource.Loading -> {

            }
            is Resource.Success -> {
                dismiss()
            }
            is Resource.Error -> {

            }
        }
    }

    private fun getDetailTransaction(id: String) {
        homeViewModel.getDetailTransaction(id).observe(viewLifecycleOwner) { list ->
            if (list.isNotEmpty()) {
                val detailAdapter = DetailHistoryAdapter(list)
                binding.rvDetailTransaction.adapter = detailAdapter
                binding.rvDetailTransaction.addItemDecoration(
                    DividerItemDecoration(
                        requireActivity(),
                        LinearLayoutManager.VERTICAL
                    )
                )

                val totalFee = list.sumOf { it.quantity * it.price }
                binding.tvTotalFee.text = Utils.formatDoubleToRupiah(totalFee, requireActivity())
            }
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}