package com.example.fishmarket.ui.menu.edit_menu

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import com.example.fishmarket.data.repository.menu.source.local.entity.MenuEntity
import com.example.fishmarket.data.source.remote.Resource
import com.example.fishmarket.databinding.FragmentEditMenuBinding
import org.koin.androidx.viewmodel.ext.android.viewModel

class EditMenuFragment : Fragment() {

    private val viewModel: EditMenuViewModel by viewModel()
    private var _binding: FragmentEditMenuBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentEditMenuBinding.inflate(layoutInflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val id = arguments?.getString("id")
        val name = arguments?.getString("name")
        val price = arguments?.getInt("price")
        val idCategory = arguments?.getString("idCategory")
        val createdDate = arguments?.getLong("createdDate")

        binding.etMenuName.setText(name)
        binding.etMenuPrice.setText(price.toString())
        binding.etIdCategory.setText(idCategory)

        binding.btnSave.setOnClickListener {
            val mName = binding.etMenuName.text
            val mPrice = binding.etMenuPrice.text
            val mIdCategory = binding.etIdCategory.text

            val newMenu = MenuEntity(
                id = id.toString(),
                name = mName.toString(),
                price = mPrice.toString().toInt(),
                id_category = mIdCategory.toString(),
                created_date = createdDate ?: 0
            )

            viewModel.editMenu(newMenu).observe(viewLifecycleOwner) { res ->
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