package com.example.fishmarket.ui.table.list_table

import android.app.AlertDialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.fishmarket.R
import com.example.fishmarket.data.repository.table.source.local.entity.TableEntity
import com.example.fishmarket.databinding.FragmentTableBinding
import org.koin.androidx.viewmodel.ext.android.viewModel

class TableFragment : Fragment() {

    private var _binding: FragmentTableBinding? = null
    private val binding get() = _binding!!
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
        val tableAdapter = TableAdapter()
        tableAdapter.setOnItemClickCallback(object : TableAdapter.OnItemClickCallBack {
            override fun onItemLongClicked(table: TableEntity) {
                setAlertDialog(table)
            }

            override fun onItemClicked(table: TableEntity) {
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
        viewModel.getTables().observe(viewLifecycleOwner) {
            if (it.isNotEmpty()) {
                tableAdapter.updateData(it)
                binding.rvTable.visibility = View.VISIBLE
                binding.llNoData.visibility = View.GONE
            } else {
                binding.rvTable.visibility = View.GONE
                binding.llNoData.visibility = View.VISIBLE
            }
        }
    }

    private fun setAlertDialog(table: TableEntity) {
        val builder = AlertDialog.Builder(requireActivity())
        builder.setTitle(requireActivity().resources.getString(R.string.delete_table_title))
        builder.setMessage(
            requireActivity().resources.getString(
                R.string.delete_warning_message,
                table.name
            )
        )

        builder.setPositiveButton(requireActivity().resources.getString(R.string.yes)) { _, _ ->
            viewModel.deleteTable(table)
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