package com.example.fishmarket.ui.table.edit_table

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import com.example.fishmarket.data.repository.table.source.local.entity.TableEntity
import com.example.fishmarket.databinding.FragmentEditTableBinding
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

        val id = arguments?.getInt("id")
        getTable(id.toString())

        viewModel.isSuccess.observe(viewLifecycleOwner) {
            if (it == 1) {
                findNavController().navigateUp()
            }
        }

        binding.btnSave.setOnClickListener {
            val name = binding.etTableName.text.toString()
            val table = TableEntity(id = id ?: 0, name = name)
            viewModel.updateTable(table)
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