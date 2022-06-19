package com.example.fishmarket.ui.menu.add_category

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import com.example.fishmarket.R
import com.example.fishmarket.data.repository.category.source.local.entity.CategoryEntity
import com.example.fishmarket.data.source.remote.Resource
import com.example.fishmarket.databinding.FragmentAddCategoryBinding
import com.example.fishmarket.domain.model.Category
import com.example.fishmarket.utilis.Utils
import com.example.fishmarket.utilis.Utils.afterTextChanged
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import org.koin.androidx.viewmodel.ext.android.viewModel

class AddCategoryFragment : BottomSheetDialogFragment() {

    private val viewModel: AddCategoryViewModel by viewModel()
    private var _binding: FragmentAddCategoryBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentAddCategoryBinding.inflate(layoutInflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val etCategoryName = binding.etCategoryName
        val btnSave = binding.btnSave

        etCategoryName.afterTextChanged {
            viewModel.categoryDataChanged(etCategoryName.text.toString())
        }

        viewModel.categoryFormState.observe(viewLifecycleOwner) {
            val categoryState = it ?: return@observe

            btnSave.isEnabled = categoryState.isDataValid

            if (categoryState.categoryNameError != null) {
                etCategoryName.error = getString(R.string.warning_category_name_empty)
            }
        }

        binding.btnSave.setOnClickListener {
            val id = Utils.getRandomString()
            val name = binding.etCategoryName.text.toString()
            val createdDate = System.currentTimeMillis()

            val categoryEntity = CategoryEntity(id = id, name = name, created_date = createdDate)
            viewModel.addCategory(categoryEntity).observe(viewLifecycleOwner, addCategoryObserver)
        }
    }

    private val addCategoryObserver = Observer<Resource<Category>> { res ->
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

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }


}