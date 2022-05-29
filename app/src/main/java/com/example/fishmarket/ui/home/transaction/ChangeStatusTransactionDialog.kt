package com.example.fishmarket.ui.home.transaction

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.GridLayoutManager
import com.example.fishmarket.R
import com.example.fishmarket.data.repository.transaction.source.local.entity.TransactionHomeEntity
import com.example.fishmarket.data.source.remote.Resource
import com.example.fishmarket.databinding.DialogChangeStatusTransactionBinding
import com.example.fishmarket.ui.home.add_transaction.SelectRestaurantAdapter
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

        val changeStatusAdapter = ChangeStatusTransactionAdapter(requireActivity())

        binding.rvStatus.adapter = changeStatusAdapter
        changeStatusAdapter.setOnItemClickCallBack(object :
            ChangeStatusTransactionAdapter.OnItemClickCallback {
            override fun onItemClicked(id: Int) {
                if (id == 2) {
                    binding.rvRestaurant.visibility = View.VISIBLE
                    binding.tvSelectRestaurant.visibility = View.VISIBLE
                } else {
                    binding.rvRestaurant.visibility = View.GONE
                    binding.rvRestaurant.visibility = View.GONE
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

        homeViewModel.getStatusTransaction().observe(viewLifecycleOwner) {
            changeStatusAdapter.updateData(it)
            changeStatusAdapter.selectStatus(transaction.status)
        }

        homeViewModel.isSuccessUpdate.observe(viewLifecycleOwner) {
            if (it > 0) {
                dismiss()
                homeViewModel.resetSuccessUpdate()
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
                        )
                    }
                } else {
                    homeViewModel.changeStatusTransaction(transaction, newStatus, "")
                }
            }
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}