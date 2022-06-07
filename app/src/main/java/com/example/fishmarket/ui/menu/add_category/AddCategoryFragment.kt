package com.example.fishmarket.ui.menu.add_category

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.DialogFragment
import com.example.fishmarket.R
import com.example.fishmarket.databinding.FragmentAddCategoryBinding
import com.google.android.material.bottomsheet.BottomSheetDialogFragment

class AddCategoryFragment : BottomSheetDialogFragment() {

    private lateinit var viewModel: AddCategoryViewModel
    private var _binding: FragmentAddCategoryBinding? = null
    private val binding get() = _binding!!

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentAddCategoryBinding.inflate(layoutInflater, container, false)
        return binding.root
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }


}