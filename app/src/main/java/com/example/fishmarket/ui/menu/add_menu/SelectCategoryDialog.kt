package com.example.fishmarket.ui.menu.add_menu

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.Toast
import androidx.fragment.app.DialogFragment
import androidx.recyclerview.widget.DividerItemDecoration
import com.example.fishmarket.R
import com.example.fishmarket.data.repository.category.source.local.entity.CategoryEntity
import com.example.fishmarket.data.source.remote.Resource
import com.example.fishmarket.databinding.FragmentSelectCategoryDialogBinding
import com.example.fishmarket.ui.menu.list_category.CategoryAdapter
import org.koin.androidx.navigation.koinNavGraphViewModel

class SelectCategoryDialog : DialogFragment() {

    private val viewModel: AddMenuViewModel by koinNavGraphViewModel(R.id.menu)
    private var _binding: FragmentSelectCategoryDialogBinding? = null
    private val binding get() = _binding!!


    override fun onStart() {
        super.onStart()
        dialog?.window?.setLayout(
            LinearLayout.LayoutParams.MATCH_PARENT,
            LinearLayout.LayoutParams.WRAP_CONTENT
        )
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentSelectCategoryDialogBinding.inflate(layoutInflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val categoryAdapter = CategoryAdapter()
        categoryAdapter.setOnItemClickCallback(object : CategoryAdapter.OnItemClickCallback {
            override fun onItemLongClicked(category: CategoryEntity) {

            }

            override fun onItemClicked(category: CategoryEntity) {
                viewModel.setCategoryId(category.id)
                viewModel.setCategoryName(category.name)
                dismiss()
            }

        })
        binding.rvCategory.adapter = categoryAdapter
        binding.rvCategory.addItemDecoration(
            DividerItemDecoration(
                requireActivity(),
                LinearLayout.VERTICAL
            )
        )

        viewModel.getCategories().observe(viewLifecycleOwner) { res ->
            when (res) {
                is Resource.Loading -> {

                }
                is Resource.Success -> {
                    if (res.data != null) {
                        categoryAdapter.updateData(res.data)
                    }
                }
                is Resource.Error -> {
                    Toast.makeText(requireActivity(), res.message.toString(), Toast.LENGTH_LONG)
                        .show()
                }
            }
        }

    }

}