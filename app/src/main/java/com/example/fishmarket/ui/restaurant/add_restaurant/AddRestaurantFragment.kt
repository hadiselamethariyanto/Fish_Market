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
import org.koin.androidx.viewmodel.ext.android.viewModel
import java.util.*

class AddRestaurantFragment : Fragment() {

    private var _binding: FragmentAddRestaurantBinding? = null
    private val binding get() = _binding!!
    private val char = "ABCDEFGHIJKLMNOPQRSTUVWXYZ1234567890"
    private val randomStrengthLength = 12
    private var random = Random()
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
            val uniqueID = getRandomString()
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

    private fun getRandomString(): String {
        val randStr = StringBuffer()
        for (i in 0 until randomStrengthLength) {
            val number = getRandomNumber()
            val ch = char[number]
            randStr.append(ch)
        }
        return randStr.toString()
    }

    private fun getRandomNumber(): Int {
        val randomInt: Int = random.nextInt(char.length)
        return if (randomInt - 1 == -1) {
            randomInt
        } else {
            randomInt - 1
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

}