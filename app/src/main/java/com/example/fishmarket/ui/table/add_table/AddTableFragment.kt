package com.example.fishmarket.ui.table.add_table

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import com.example.fishmarket.data.repository.table.source.local.entity.TableEntity
import com.example.fishmarket.databinding.FragmentAddTableBinding
import org.koin.androidx.viewmodel.ext.android.viewModel

class AddTableFragment : Fragment() {

    private var _binding: FragmentAddTableBinding? = null
    private val binding get() = _binding!!
    private val viewModel: AddTableViewModel by viewModel()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentAddTableBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel.isSuccess.observe(viewLifecycleOwner) {
            if (it > 0) {
                findNavController().navigateUp()
            }
        }

        binding.btnSave.setOnClickListener {
            val name = binding.etTableName.text.toString()
            val table = TableEntity(id = 0, name = name)
            viewModel.addTable(table)
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }


}