package com.example.fishmarket.ui.restaurant.edit_restaurant

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import com.example.fishmarket.data.repository.restaurant.source.local.entity.RestaurantEntity
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
        val id = arguments?.getInt("id")
        getRestaurant(id.toString())

        viewModel.isSuccess.observe(viewLifecycleOwner) {
            if (it == 1) {
                findNavController().navigateUp()
            }
        }

        binding.btnSave.setOnClickListener {
            val name = binding.etRestaurantName.text.toString()
            val restaurantEntity = RestaurantEntity(id = id ?: 0, name = name)
            viewModel.updateRestaurant(restaurantEntity)
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