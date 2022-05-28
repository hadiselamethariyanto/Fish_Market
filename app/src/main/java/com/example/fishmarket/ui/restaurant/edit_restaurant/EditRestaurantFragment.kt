package com.example.fishmarket.ui.restaurant.edit_restaurant

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.navigation.fragment.findNavController
import com.example.fishmarket.data.repository.restaurant.source.local.entity.RestaurantEntity
import com.example.fishmarket.data.source.remote.Resource
import com.example.fishmarket.databinding.FragmentEditRestaurantBinding
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

        binding.btnSave.setOnClickListener {
            val name = binding.etRestaurantName.text.toString()
            val restaurantEntity =
                RestaurantEntity(id = id ?: "", name = name, createdDate = createdDate ?: 0)
            viewModel.updateRestaurant(restaurantEntity).observe(viewLifecycleOwner) { res ->
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