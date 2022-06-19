package com.example.fishmarket.ui.menu.edit_category

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import com.example.fishmarket.R
import com.example.fishmarket.data.repository.category.source.local.entity.CategoryEntity
import com.example.fishmarket.data.source.remote.Resource
import com.example.fishmarket.databinding.FragmentEditCategoryBinding
import com.example.fishmarket.domain.model.Category
import com.example.fishmarket.utilis.Utils
import com.example.fishmarket.utilis.Utils.afterTextChanged
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

        val etCategoryName = binding.etCategoryName
        val btnSave = binding.btnSave

        etCategoryName.setText(name)
        etCategoryName.afterTextChanged {
            viewModel.dataCategoryChanged(etCategoryName.text.toString())
        }

        viewModel.categoryFormState.observe(viewLifecycleOwner) {
            val categoryState = it ?: return@observe

            btnSave.isEnabled = categoryState.isDataValid

            if (categoryState.categoryNameError != null) {
                etCategoryName.error = getString(R.string.warning_category_name_empty)
            }
        }

        btnSave.setOnClickListener {
            val category = CategoryEntity(
                id = id.toString(),
                name = etCategoryName.text.toString(),
                created_date = createdDate ?: 0
            )
            viewModel.editCategory(category).observe(viewLifecycleOwner, editCategoryObserver)
        }

    }

    private val editCategoryObserver = Observer<Resource<Category>> { res ->
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
                Utils.showMessage(requireActivity(), res.message.toString())
            }
        }
    }

}