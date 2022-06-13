package com.example.fishmarket.ui.menu.list_category

import android.app.AlertDialog
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.os.bundleOf
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.fishmarket.R
import com.example.fishmarket.data.repository.category.source.local.entity.CategoryEntity
import com.example.fishmarket.data.source.remote.Resource
import com.example.fishmarket.databinding.FragmentListCategoryBinding
import org.koin.androidx.viewmodel.ext.android.viewModel

class ListCategoryFragment : Fragment() {

    private val viewModel: ListCategoryViewModel by viewModel()
    private var _binding: FragmentListCategoryBinding? = null
    private val binding get() = _binding!!
    private lateinit var categoryAdapter: CategoryAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentListCategoryBinding.inflate(layoutInflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        categoryAdapter = CategoryAdapter()
        categoryAdapter.setOnItemClickCallback(object : CategoryAdapter.OnItemClickCallback {
            override fun onItemLongClicked(category: CategoryEntity) {
                setAlertDialog(category)
            }

            override fun onItemClicked(category: CategoryEntity) {
                val bundle = bundleOf(
                    "id" to category.id,
                    "name" to category.name,
                    "created_date" to category.created_date
                )
                findNavController().navigate(
                    R.id.action_menuFragment_to_editCategoryFragment,
                    bundle
                )
            }
        })

        binding.rvCategory.adapter = categoryAdapter
        binding.rvCategory.addItemDecoration(
            DividerItemDecoration(
                requireActivity(),
                LinearLayoutManager.VERTICAL
            )
        )

        viewModel.getCategories().observe(viewLifecycleOwner) { res ->
            when (res) {
                is Resource.Loading -> {
                    binding.refresh.isRefreshing = true
                }
                is Resource.Success -> {
                    binding.refresh.isRefreshing = false
                    if (res.data != null) {
                        categoryAdapter.updateData(res.data)
                    }
                }
                is Resource.Error -> {
                    binding.refresh.isRefreshing = false
                    Toast.makeText(requireActivity(), res.message.toString(), Toast.LENGTH_LONG)
                        .show()
                }
            }
        }

        binding.addCategory.setOnClickListener {
            findNavController().navigate(R.id.action_menuFragment_to_addCategoryFragment)
        }
    }

    private fun setAlertDialog(categoryEntity: CategoryEntity) {
        val builder = AlertDialog.Builder(requireActivity())
        builder.setTitle(requireActivity().resources.getString(R.string.delete_category))
        builder.setMessage(
            requireActivity().resources.getString(
                R.string.delete_warning_message,
                categoryEntity.name
            )
        )

        builder.setPositiveButton(requireActivity().resources.getString(R.string.yes)) { _, _ ->
            viewModel.deleteCategory(categoryEntity).observe(viewLifecycleOwner) { res ->
                when (res) {
                    is Resource.Loading -> {
                        binding.refresh.isRefreshing = true
                    }
                    is Resource.Success -> {
                        binding.refresh.isRefreshing = false
                        if (res.data != null) {
                            categoryAdapter.updateData(res.data)
                        }
                    }
                    is Resource.Error -> {
                        binding.refresh.isRefreshing = false
                        Toast.makeText(requireActivity(), res.message.toString(), Toast.LENGTH_LONG)
                            .show()
                    }
                }

            }
        }

        builder.setNegativeButton(requireActivity().resources.getString(R.string.no)) { _, _ ->

        }

        builder.show()
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }


}