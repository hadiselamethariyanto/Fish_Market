package com.example.fishmarket.ui.restaurant.edit_restaurant

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import com.example.fishmarket.R
import com.example.fishmarket.data.repository.restaurant.source.local.entity.RestaurantEntity
import com.example.fishmarket.data.source.remote.Resource
import com.example.fishmarket.databinding.FragmentEditRestaurantBinding
import com.example.fishmarket.domain.model.Restaurant
import com.example.fishmarket.utilis.Utils.afterTextChanged
import org.koin.androidx.viewmodel.ext.android.viewModel

class EditRestaurantFragment : Fragment() {

    private var _binding: FragmentEditRestaurantBinding? = null
    private val binding get() = _binding!!
    private val viewModel: EditRestaurantViewModel by viewModel()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentEditRestaurantBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val id = arguments?.getString("id")
        val createdDate = arguments?.getLong("createdDate")

        getRestaurant(id.toString())

        val etRestaurantName = binding.etRestaurantName
        val btnSave = binding.btnSave

        viewModel.restaurantFormState.observe(viewLifecycleOwner) {
            val restaurantState = it ?: return@observe

            btnSave.isEnabled = restaurantState.isDataValid

            if (restaurantState.restaurantNameError != null) {
                etRestaurantName.error = getString(R.string.warning_restaurant_name_empty)
            }
        }

        etRestaurantName.afterTextChanged {
            viewModel.restaurantDataChanged(etRestaurantName.text.toString())
        }

        btnSave.setOnClickListener {
            val restaurantEntity =
                RestaurantEntity(
                    id = id ?: "",
                    name = etRestaurantName.text.toString(),
                    createdDate = createdDate ?: 0
                )

            viewModel.updateRestaurant(restaurantEntity)
                .observe(viewLifecycleOwner, updateRestaurantObserver)
        }
    }

    private val updateRestaurantObserver = Observer<Resource<Restaurant>> { res ->
        when (res) {
            is Resource.Loading -> {
                binding.btnSave.isEnabled = false
            }
            is Resource.Success -> {
                findNavController().navigateUp()
                binding.btnSave.isEnabled = true
            }
            is Resource.Error -> {
                Toast.makeText(requireActivity(), res.message.toString(), Toast.LENGTH_LONG)
                    .show()
                binding.btnSave.isEnabled = true
            }
        }

    }

    private fun getRestaurant(id: String) {
        viewModel.getRestaurant(id).observe(viewLifecycleOwner) {
            binding.etRestaurantName.setText(it.name)
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

}