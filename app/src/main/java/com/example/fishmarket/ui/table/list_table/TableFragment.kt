package com.example.fishmarket.ui.table.list_table

import android.app.AlertDialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.fishmarket.R
import com.example.fishmarket.data.repository.table.source.local.entity.TableEntity
import com.example.fishmarket.data.source.remote.Resource
import com.example.fishmarket.databinding.FragmentTableBinding
import com.example.fishmarket.domain.model.Table
import org.koin.androidx.viewmodel.ext.android.viewModel

class TableFragment : Fragment() {

    private var _binding: FragmentTableBinding? = null
    private val binding get() = _binding!!
    private lateinit var tableAdapter: TableAdapter
    private val viewModel: TableViewModel by viewModel()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentTableBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        getTable()

        binding.fabAddTable.setOnClickListener {
            findNavController().navigate(R.id.action_navigation_table_to_navigation_add_table)
        }
    }

    private fun getTable() {
        tableAdapter = TableAdapter()
        tableAdapter.setOnItemClickCallback(object : TableAdapter.OnItemClickCallBack {
            override fun onItemLongClicked(table: Table) {
                setAlertDialog(table)
            }

            override fun onItemClicked(table: Table) {
                val bundle = bundleOf(
                    "id" to table.id,
                    "status" to table.status,
                    "createdDate" to table.createdDate
                )
                findNavController().navigate(
                    R.id.action_navigation_table_to_navigation_edit_table,
                    bundle
                )
            }

        })

        binding.rvTable.adapter = tableAdapter
        binding.rvTable.addItemDecoration(
            DividerItemDecoration(
                requireActivity(),
                LinearLayoutManager.VERTICAL
            )
        )

        viewModel.getTables().observe(viewLifecycleOwner) { res ->
            when (res) {
                is Resource.Loading -> {
                    binding.refresh.isRefreshing = true
                }
                is Resource.Success -> {
                    if (res.data != null) {
                        if (res.data.isNotEmpty()) {
                            tableAdapter.updateData(res.data)
                            binding.rvTable.visibility = View.VISIBLE
                            binding.llNoData.visibility = View.GONE
                        } else {
                            binding.rvTable.visibility = View.GONE
                            binding.llNoData.visibility = View.VISIBLE
                        }
                    }
                    binding.refresh.isRefreshing = false
                }
                is Resource.Error -> {
                    Toast.makeText(requireActivity(), res.message.toString(), Toast.LENGTH_LONG)
                        .show()
                    binding.refresh.isRefreshing = false
                }
            }

        }
    }

    private fun setAlertDialog(table: Table) {
        val builder = AlertDialog.Builder(requireActivity())
        builder.setTitle(requireActivity().resources.getString(R.string.delete_table_title))
        builder.setMessage(
            requireActivity().resources.getString(
                R.string.delete_warning_message,
                table.name
            )
        )

        builder.setPositiveButton(requireActivity().resources.getString(R.string.yes)) { _, _ ->
            val tableEntity = TableEntity(
                id = table.id,
                name = table.name,
                status = table.status,
                createdDate = table.createdDate
            )
            viewModel.deleteTable(tableEntity).observe(viewLifecycleOwner) { res ->
                when (res) {
                    is Resource.Loading -> {
                        binding.refresh.isRefreshing = true
                    }
                    is Resource.Success -> {
                        if (res.data != null) {
                            tableAdapter.updateData(res.data)
                        }
                        binding.refresh.isRefreshing = false
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