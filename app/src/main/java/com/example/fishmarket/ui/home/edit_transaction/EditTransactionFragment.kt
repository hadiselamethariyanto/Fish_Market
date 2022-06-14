package com.example.fishmarket.ui.home.edit_transaction

import android.os.Bundle
import android.text.InputType
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import com.example.fishmarket.App
import com.example.fishmarket.databinding.FragmentEditTransactionBinding
import com.google.android.material.bottomsheet.BottomSheetDialogFragment

class EditTransactionFragment : BottomSheetDialogFragment() {

    private var _binding: FragmentEditTransactionBinding? = null
    private val binding get() = _binding!!
    private val ct = App.getApp()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentEditTransactionBinding.inflate(layoutInflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val position = arguments?.getInt("position") ?: 0
        val name = arguments?.getString("name") ?: ""
        val price = arguments?.getInt("price") ?: 0
        val quantity = arguments?.getDouble("quantity") ?: 0.0
        val unit = arguments?.getString("unit") ?: ""

        binding.etMenuName.setText(name)
        binding.etMenuName.isEnabled = false
        binding.etMenuPrice.setText(price.toString())

        if (unit == "Decimal") {
            binding.etQuantity.setText(quantity.toString())
            binding.etQuantity.inputType =
                InputType.TYPE_CLASS_NUMBER or InputType.TYPE_NUMBER_FLAG_DECIMAL
        } else {
            binding.etQuantity.setText(quantity.toInt().toString())
            binding.etQuantity.inputType =
                InputType.TYPE_CLASS_NUMBER or InputType.TYPE_NUMBER_FLAG_SIGNED
        }

        binding.btnSave.setOnClickListener {
            val mPrice = binding.etMenuPrice.text.toString()
            val mQuantity = binding.etQuantity.text.toString()

            if (mPrice == "") {
                binding.etMenuPrice.error = ""
            } else if (mQuantity == "") {
                binding.etQuantity.error = ""
            } else {
                val product = ct.getCart().getProduct(position)
                product.quantity = mQuantity.toDouble()
                product.price = mPrice.toInt()
                dismiss()
            }
        }
    }

    override fun dismiss() {
        super.dismiss()
        findNavController().previousBackStackEntry?.savedStateHandle?.set("DISMISS", true)
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

}