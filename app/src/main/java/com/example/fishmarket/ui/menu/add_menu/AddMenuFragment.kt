package com.example.fishmarket.ui.menu.add_menu

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.navigation.fragment.findNavController
import com.example.fishmarket.R
import com.example.fishmarket.data.repository.menu.source.local.entity.MenuEntity
import com.example.fishmarket.data.source.remote.Resource
import com.example.fishmarket.databinding.FragmentAddMenuBinding
import com.example.fishmarket.utilis.Utils
import org.koin.androidx.navigation.koinNavGraphViewModel

class AddMenuFragment : Fragment() {

    private val viewModel: AddMenuViewModel by koinNavGraphViewModel(R.id.menu)
    private var _binding: FragmentAddMenuBinding? = null
    private val binding get() = _binding!!

    override fun onStart() {
        super.onStart()
        viewModel.setCategoryName("")
        viewModel.setCategoryId("")
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentAddMenuBinding.inflate(layoutInflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.etIdCategory.isFocusable = false
        binding.etIdCategory.isClickable = true
        binding.etIdCategory.setOnClickListener {
            SelectCategoryDialog().show(childFragmentManager, "")
        }

        viewModel.categoryName.observe(viewLifecycleOwner) {
            binding.etIdCategory.setText(it)
        }

        binding.btnSave.setOnClickListener {
            val id = Utils.getRandomString()
            val name = binding.etMenuName.text.toString()
            val price = binding.etMenuPrice.text.toString()
            val idCategory = viewModel.categoryId.value
            val createdDate = System.currentTimeMillis()

            if (name == "") {
                binding.etMenuName.error = resources.getString(R.string.warning_menu_name_empty)
            } else if (price == "") {
                binding.etMenuPrice.error = resources.getString(R.string.warning_price_menu_empty)
            } else if (idCategory == "") {
                binding.etIdCategory.error =
                    resources.getString(R.string.warning_category_menu_empty)
            } else {
                val menu = MenuEntity(
                    id = id,
                    name = name,
                    price = price.toInt(),
                    id_category = idCategory ?: "",
                    created_date = createdDate
                )

                viewModel.insertMenu(menu).observe(viewLifecycleOwner) { res ->
                    when (res) {
                        is Resource.Loading -> {
                            binding.btnSave.isEnabled = false
                        }
                        is Resource.Success -> {
                            binding.btnSave.isEnabled = true
                            findNavController().popBackStack(R.id.menuFragment, false)
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