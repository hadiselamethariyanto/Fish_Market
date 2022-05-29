package com.example.fishmarket.ui.home.add_transaction

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.navigation.fragment.findNavController
import com.example.fishmarket.R
import com.example.fishmarket.data.source.remote.Resource
import com.example.fishmarket.databinding.FragmentAddTransactionBinding
import org.koin.androidx.navigation.koinNavGraphViewModel

class AddTransactionFragment : Fragment() {

    private var _binding: FragmentAddTransactionBinding? = null
    private val binding get() = _binding!!
    private val viewModel: AddTransactionViewModel by koinNavGraphViewModel(R.id.transaction)

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentAddTransactionBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel.table.observe(viewLifecycleOwner) {
            binding.etTable.setText(it.name)
        }

        viewModel.restaurant.observe(viewLifecycleOwner) {
            binding.etRestaurant.setText(it.name)
        }


        binding.etTable.keyListener = null
        binding.etTable.setOnClickListener {
            findNavController().navigate(R.id.action_navigation_add_transaction_to_navigation_dialog_select_table)
        }

        binding.etRestaurant.keyListener = null
        binding.etRestaurant.setOnClickListener {
            findNavController().navigate(R.id.action_navigation_add_transaction_to_navigation_dialog_select_restaurant)
        }

        binding.btnSave.setOnClickListener {
            val table = binding.etTable.text.toString()
            if (table == "") {
                binding.etTable.error = ""
            } else {
                viewModel.addTransaction().observe(viewLifecycleOwner) { res ->
                    when (res) {
                        is Resource.Loading -> {
                            binding.btnSave.isEnabled = false
                        }
                        is Resource.Success -> {
                            findNavController().navigateUp()
                            viewModel.resetTransaction()
                            binding.btnSave.isEnabled = false
                        }
                        is Resource.Error -> {
                            Toast.makeText(
                                requireActivity(),
                                res.message.toString(),
                                Toast.LENGTH_LONG
                            ).show()
                            binding.btnSave.isEnabled = false
                        }
                    }
                }
            }
        }
    }


}