package com.example.fishmarket.ui.payment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.fishmarket.R
import com.example.fishmarket.databinding.FragmentAddDiscountBinding
import com.example.fishmarket.ui.home.transaction.HomeViewModel
import com.example.fishmarket.utilis.Utils.afterTextChanged
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import org.koin.androidx.navigation.koinNavGraphViewModel


class AddDiscountFragment : BottomSheetDialogFragment() {

    private var _binding: FragmentAddDiscountBinding? = null
    private val binding get() = _binding!!
    private val homeViewModel: HomeViewModel by koinNavGraphViewModel(R.id.transaction)

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentAddDiscountBinding.inflate(layoutInflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val originalFee = arguments?.getInt("original_fee") ?: 0

        binding.etDiscount.afterTextChanged {
            homeViewModel.addDiscountDataChanged(binding.etDiscount.text.toString(), originalFee)
        }

        homeViewModel.addDiscountFormState.observe(viewLifecycleOwner) {
            val state = it ?: return@observe

            binding.btnSave.isEnabled = state.isDataValid

            if (state.discountError != null) {
                binding.etDiscount.error = getString(state.discountError)
            }
        }

        binding.btnSave.setOnClickListener {
            homeViewModel.setDiscount(binding.etDiscount.text.toString().toInt())
            dismiss()
        }
    }

}