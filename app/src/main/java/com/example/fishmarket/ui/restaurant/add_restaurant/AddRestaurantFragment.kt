package com.example.fishmarket.ui.restaurant.add_restaurant

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.navigation.fragment.findNavController
import com.example.fishmarket.data.repository.restaurant.source.local.entity.RestaurantEntity
import com.example.fishmarket.data.source.remote.Resource
import com.example.fishmarket.databinding.FragmentAddRestaurantBinding
import com.example.fishmarket.utilis.Utils
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

        binding.btnSave.setOnClickListener {
            val name = binding.etRestaurantName.text.toString()
            val uniqueID = Utils.getRandomString()
            val createdDate = System.currentTimeMillis()
            viewModel.addRestaurant(
                RestaurantEntity(
                    name = name,
                    id = uniqueID,
                    createdDate = createdDate
                )
            )
                .observe(viewLifecycleOwner) { res ->
                    when (res) {
                        is Resource.Loading -> {
                            binding.btnSave.isEnabled = false
                        }
                        is Resource.Success -> {
                            binding.btnSave.isEnabled = true
                            findNavController().navigateUp()
                        }
                        is Resource.Error -> {
                            Toast.makeText(
                                requireActivity(),
                                res.message.toString(),
                                Toast.LENGTH_LONG
                            ).show()
                            binding.btnSave.isEnabled = true
                        }
                    }
                }
        }
    }


    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

}