package com.example.fishmarket.ui.home.transaction

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.fishmarket.databinding.DialogChangeStatusTransactionBinding
import com.google.android.material.bottomsheet.BottomSheetDialogFragment

class ChangeStatusTransactionDialog : BottomSheetDialogFragment() {

    private var _binding: DialogChangeStatusTransactionBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = DialogChangeStatusTransactionBinding.inflate(inflater, container, false)
        return binding.root
    }
}