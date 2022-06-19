package com.example.fishmarket.ui.menu.edit_category

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import com.example.fishmarket.R
import com.example.fishmarket.data.repository.category.source.local.entity.CategoryEntity
import com.example.fishmarket.data.source.remote.Resource
import com.example.fishmarket.databinding.FragmentEditCategoryBinding
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import org.koin.androidx.viewmodel.ext.android.viewModel

class EditCategoryFragment : BottomSheetDialogFragment() {

    private val viewModel: EditCategoryViewModel by viewModel()
    private var _binding: FragmentEditCategoryBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentEditCategoryBinding.inflate(layoutInflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val id = arguments?.getString("id")
        val name = arguments?.getString("name")
        val createdDate = arguments?.getLong("created_date")

        binding.etCategoryName.setText(name)

        binding.btnSave.setOnClickListener {

            val mName = binding.etCategoryName.text.toString()
            if (mName == "") {
                binding.etCategoryName.error =
                    resources.getString(R.string.warning_category_name_empty)
            } else {
                val category = CategoryEntity(
                    id = id.toString(),
                    name = mName,
                    created_date = createdDate ?: 0
                )

                viewModel.editCategory(category).observe(viewLifecycleOwner) { res ->
                    when (res) {
                        is Resource.Loading -> {
                            binding.btnSave.isEnabled = false
                        }
                        is Resource.Success -> {
                            binding.btnSave.isEnabled = true
                            dismiss()
                        }
                        is Resource.Error -> {
                            binding.btnSave.isEnabled = true
                            Toast.makeText(
                                requireActivity(),
                                res.message.toString(),
                                Toast.LENGTH_LONG
                            )
                                .show()
                        }
                    }
                }
            }

        }
    }

}