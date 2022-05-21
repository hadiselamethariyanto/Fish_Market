package com.example.fishmarket.ui.restaurant.add_restaurant

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import com.example.fishmarket.data.repository.restaurant.source.local.entity.RestaurantEntity
import com.example.fishmarket.databinding.FragmentAddRestaurantBinding
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

        viewModel.isSuccess.observe(viewLifecycleOwner) {
            findNavController().navigateUp()
        }

        binding.btnSave.setOnClickListener {
            val name = binding.etRestaurantName.text.toString()
            viewModel.addRestaurant(RestaurantEntity(name = name, id = 0))
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

}