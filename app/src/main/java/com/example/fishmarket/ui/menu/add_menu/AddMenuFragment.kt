package com.example.fishmarket.ui.menu.add_menu

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.navigation.fragment.findNavController
import com.example.fishmarket.data.repository.menu.source.local.entity.MenuEntity
import com.example.fishmarket.data.source.remote.Resource
import com.example.fishmarket.databinding.FragmentAddMenuBinding
import com.example.fishmarket.utilis.Utils
import org.koin.androidx.viewmodel.ext.android.viewModel

class AddMenuFragment : Fragment() {

    private val viewModel: AddMenuViewModel by viewModel()
    private var _binding: FragmentAddMenuBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentAddMenuBinding.inflate(layoutInflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


        binding.btnSave.setOnClickListener {
            val id = Utils.getRandomString()
            val name = binding.etMenuName.text.toString()
            val price = binding.etMenuPrice.text.toString()
            val idCategory = binding.etIdCategory.text.toString()
            val createdDate = System.currentTimeMillis()

            if (name == "") {
                binding.etMenuName.error = ""
            } else if (price == "") {
                binding.etMenuPrice.error = ""
            } else if (idCategory == "") {
                binding.etIdCategory.error = ""
            } else {
                val menu = MenuEntity(
                    id = id,
                    name = name,
                    price = price.toInt(),
                    id_category = idCategory,
                    created_date = createdDate
                )

                viewModel.insertMenu(menu).observe(viewLifecycleOwner) { res ->
                    when (res) {
                        is Resource.Loading -> {
                            binding.btnSave.isEnabled = false
                        }
                        is Resource.Success -> {
                            binding.btnSave.isEnabled = true
                            findNavController().navigateUp()
                        }
                        is Resource.Error -> {
                            binding.btnSave.isEnabled = true
                            Toast.makeText(
                                requireActivity(),
                                res.message.toString(),
                                Toast.LENGTH_LONG
                            ).show()
                        }
                    }
                }
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }


}