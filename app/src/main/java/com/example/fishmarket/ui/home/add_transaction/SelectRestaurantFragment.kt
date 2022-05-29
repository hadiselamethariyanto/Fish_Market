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
import com.example.fishmarket.data.source.remote.Resource
import com.example.fishmarket.databinding.DialogSelectRestaurantBinding
import com.example.fishmarket.ui.restaurant.list_restaurant.RestaurantViewModel
import org.koin.androidx.navigation.koinNavGraphViewModel
import org.koin.androidx.viewmodel.ext.android.viewModel

class SelectRestaurantFragment : DialogFragment() {

    private var _binding: DialogSelectRestaurantBinding? = null
    private val binding get() = _binding!!
    private val viewModel: RestaurantViewModel by viewModel()
    private val addRestaurantViewModel: AddTransactionViewModel by koinNavGraphViewModel(R.id.transaction)

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = DialogSelectRestaurantBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val selectRestaurantAdapter = SelectRestaurantAdapter()
        binding.rvRestaurant.adapter = selectRestaurantAdapter
        binding.rvRestaurant.layoutManager = GridLayoutManager(requireActivity(), 3)

        viewModel.getRestaurant().observe(viewLifecycleOwner) { res ->
            when (res) {
                is Resource.Loading -> {

                }
                is Resource.Success -> {
                    if (res.data!=null){
                        selectRestaurantAdapter.updateData(res.data)
                    }
                }
                is Resource.Error -> {

                }
            }
        }

        binding.btnSave.setOnClickListener {
            if (selectRestaurantAdapter.getSelectedPosition() != -1) {
                val restaurant = selectRestaurantAdapter.getSelectedRestaurant()
                addRestaurantViewModel.setRestaurant(restaurant)
                dismiss()

            } else {
                Toast.makeText(
                    requireActivity(),
                    resources.getString(R.string.warning_message_select_restaurant),
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