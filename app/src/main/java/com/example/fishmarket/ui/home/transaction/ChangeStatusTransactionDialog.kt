package com.example.fishmarket.ui.home.transaction

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.GridLayoutManager
import com.example.fishmarket.R
import com.example.fishmarket.databinding.DialogChangeStatusTransactionBinding
import com.example.fishmarket.ui.home.add_transaction.SelectRestaurantAdapter
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
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


        val status = arguments?.getInt("status")
        val id = arguments?.getInt("id")
        val idTable = arguments?.getInt("id_table") ?: 0
        val idRestaurant = arguments?.getInt("id_restaurant") ?: 0

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

        homeViewModel.getRestaurant().observe(viewLifecycleOwner) {
            restaurantAdapter.updateData(it)
            restaurantAdapter.selectRestaurant(idRestaurant)
        }

        binding.rvStatus.layoutManager = GridLayoutManager(requireActivity(), 4)

        homeViewModel.getStatusTransaction().observe(viewLifecycleOwner) {
            changeStatusAdapter.updateData(it)
            if (status != null) {
                changeStatusAdapter.selectStatus(status)
            }
        }

        homeViewModel.isSuccessUpdate.observe(viewLifecycleOwner) {
            if (it > 0) {
                dismiss()
                homeViewModel.resetSuccessUpdate()
            }
        }

        binding.btnSave.setOnClickListener {
            val newStatus = changeStatusAdapter.getStatus()
            if (id != null) {
                if (newStatus == 2) {
                    if (restaurantAdapter.getSelectedPosition() == -1) {
                        Toast.makeText(
                            requireActivity(),
                            resources.getString(R.string.warning_message_select_restaurant),
                            Toast.LENGTH_LONG
                        ).show()
                    } else {
                        val restaurant = restaurantAdapter.getSelectedRestaurant()
                        homeViewModel.changeStatusTransaction(id, newStatus, idTable, restaurant.id)
                    }
                } else {
                    homeViewModel.changeStatusTransaction(id, newStatus, idTable, 0)
                }
            }
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}