package com.example.fishmarket.ui.home.transaction

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.GridLayoutManager
import com.example.fishmarket.R
import com.example.fishmarket.databinding.DialogChangeStatusTransactionBinding
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

        val changeStatusAdapter = ChangeStatusTransactionAdapter(requireActivity())

        binding.rvStatus.adapter = changeStatusAdapter
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
                homeViewModel.changeStatusTransaction(id, newStatus, idTable)
            }
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}