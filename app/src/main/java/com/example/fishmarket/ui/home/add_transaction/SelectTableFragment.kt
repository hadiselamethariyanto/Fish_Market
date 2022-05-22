package com.example.fishmarket.ui.home.add_transaction

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.WindowManager
import android.widget.Toast
import androidx.fragment.app.DialogFragment
import androidx.recyclerview.widget.GridLayoutManager
import com.example.fishmarket.R
import com.example.fishmarket.databinding.DialogSelectTableBinding
import com.example.fishmarket.ui.table.list_table.TableViewModel
import org.koin.androidx.viewmodel.ext.android.viewModel
import org.koin.androidx.navigation.koinNavGraphViewModel


class SelectTableFragment : DialogFragment() {

    private var _binding: DialogSelectTableBinding? = null
    private val binding get() = _binding!!
    private val addTransactionViewModel: AddTransactionViewModel by koinNavGraphViewModel(R.id.transaction)

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = DialogSelectTableBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val tableAdapter = SelectTableAdapter()
        binding.rvTable.adapter = tableAdapter
        binding.rvTable.layoutManager = GridLayoutManager(requireActivity(), 3)

        addTransactionViewModel.getAvailableTable().observe(viewLifecycleOwner) {
            tableAdapter.updateData(it)
        }

        binding.btnSave.setOnClickListener {
            if (tableAdapter.getSelectedPosition() != -1) {
                val table = tableAdapter.getSelectedTable()
                addTransactionViewModel.setTable(table)
                dismiss()
            } else {
                Toast.makeText(
                    requireActivity(),
                    resources.getString(R.string.warning_message_select_table),
                    Toast.LENGTH_LONG
                ).show()
            }
        }

    }


    override fun onStart() {
        super.onStart()
        dialog?.window?.setLayout(
            WindowManager.LayoutParams.MATCH_PARENT,
            WindowManager.LayoutParams.WRAP_CONTENT
        )
    }


    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }


}