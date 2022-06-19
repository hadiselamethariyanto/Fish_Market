package com.example.fishmarket.ui.restaurant.add_restaurant

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import com.example.fishmarket.R
import com.example.fishmarket.data.repository.restaurant.source.local.entity.RestaurantEntity
import com.example.fishmarket.data.source.remote.Resource
import com.example.fishmarket.databinding.FragmentAddRestaurantBinding
import com.example.fishmarket.domain.model.Restaurant
import com.example.fishmarket.utilis.Utils
import com.example.fishmarket.utilis.Utils.afterTextChanged
import org.koin.androidx.viewmodel.ext.android.viewModel

class AddRestaurantFragment : Fragment() {

    private var _binding: FragmentAddRestaurantBinding? = null
    private val binding get() = _binding!!
    private val viewModel: AddRestaurantViewModel by viewModel()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentAddRestaurantBinding.inflate(layoutInflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

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
            val restaurantEntity = RestaurantEntity(
                name = etRestaurantName.text.toString(),
                id = Utils.getRandomString(),
                createdDate = System.currentTimeMillis()
            )
            viewModel.addRestaurant(restaurantEntity)
                .observe(viewLifecycleOwner, addRestaurantObserver)
        }
    }

    private val addRestaurantObserver = Observer<Resource<Restaurant>> { res ->
        when (res) {
            is Resource.Loading -> {
                binding.btnSave.isEnabled = false
            }
            is Resource.Success -> {
                binding.btnSave.isEnabled = true
                findNavController().popBackStack(R.id.navigation_dashboard, false)
            }
            is Resource.Error -> {
                Utils.showMessage(requireActivity(), res.message.toString())
                binding.btnSave.isEnabled = true
            }
        }
    }


    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

}