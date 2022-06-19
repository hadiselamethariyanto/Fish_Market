package com.example.fishmarket.ui.table.edit_table

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import com.example.fishmarket.R
import com.example.fishmarket.data.repository.table.source.local.entity.TableEntity
import com.example.fishmarket.data.source.remote.Resource
import com.example.fishmarket.databinding.FragmentEditTableBinding
import com.example.fishmarket.domain.model.Table
import com.example.fishmarket.utilis.Utils.afterTextChanged
import org.koin.androidx.viewmodel.ext.android.viewModel


class EditTableFragment : Fragment() {

    private var _binding: FragmentEditTableBinding? = null
    private val binding get() = _binding!!
    private val viewModel: EditTableViewModel by viewModel()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentEditTableBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val id = arguments?.getString("id")
        val status = arguments?.getBoolean("status")
        val createdDate = arguments?.getLong("createdDate")

        getTable(id.toString())

        viewModel.tableFormState.observe(viewLifecycleOwner) {
            val tableState = it ?: return@observe

            binding.btnSave.isEnabled = tableState.isDataValid

            if (tableState.tableNameError != null) {
                binding.etTableName.error = getString(R.string.not_valid_table_name)
            }
        }

        val etTableName = binding.etTableName
        val btnSave = binding.btnSave

        etTableName.afterTextChanged {
            viewModel.dataTableChanged(etTableName.text.toString())
        }

        btnSave.setOnClickListener {
            val table = TableEntity(
                id = id ?: "",
                name = etTableName.text.toString(),
                status = status ?: false,
                createdDate = createdDate ?: 0
            )
            viewModel.updateTable(table).observe(viewLifecycleOwner, editTableObserver)
        }
    }

    private val editTableObserver = Observer<Resource<Table>> { res ->
        when (res) {
            is Resource.Loading -> {
                binding.btnSave.isEnabled = false
            }
            is Resource.Success -> {
                findNavController().popBackStack(R.id.navigation_table, false)
                binding.btnSave.isEnabled = false
            }
            is Resource.Error -> {
                Toast.makeText(requireActivity(), res.message.toString(), Toast.LENGTH_LONG)
                    .show()
                binding.btnSave.isEnabled = false
            }
        }

    }

    private fun getTable(id: String) {
        viewModel.getTable(id).observe(viewLifecycleOwner) {
            binding.etTableName.setText(it.name)
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }


}