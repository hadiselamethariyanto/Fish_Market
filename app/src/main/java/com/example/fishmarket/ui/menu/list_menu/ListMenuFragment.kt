package com.example.fishmarket.ui.menu.list_menu

import android.app.AlertDialog
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.os.bundleOf
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.fishmarket.R
import com.example.fishmarket.data.repository.menu.source.local.entity.MenuEntity
import com.example.fishmarket.data.source.remote.Resource
import com.example.fishmarket.databinding.FragmentListMenuBinding
import com.example.fishmarket.domain.model.Menu
import com.example.fishmarket.ui.menu.MenuAdapter
import org.koin.androidx.viewmodel.ext.android.viewModel

class ListMenuFragment : Fragment() {

    private val viewModel: ListMenuViewModel by viewModel()
    private var _binding: FragmentListMenuBinding? = null
    private val binding get() = _binding!!
    private lateinit var menuAdapter: MenuAdapter


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentListMenuBinding.inflate(layoutInflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        menuAdapter = MenuAdapter()
        menuAdapter.setOnItemClickCallback(object : MenuAdapter.OnItemClickCallback {
            override fun onItemLongClicked(menu: Menu) {
                setAlertDialog(menu)
            }

            override fun onItemClicked(menu: Menu) {
                val bundle = bundleOf(
                    "id" to menu.id,
                    "name" to menu.name,
                    "price" to menu.price,
                    "unit" to menu.unit,
                    "image" to menu.image,
                    "idCategory" to menu.id_category,
                    "createdDate" to menu.created_date
                )

                findNavController().navigate(
                    R.id.action_menuFragment_to_editMenuFragment,
                    bundle
                )
            }

        })

        binding.rvMenu.adapter = menuAdapter
        binding.rvMenu.addItemDecoration(
            DividerItemDecoration(
                requireActivity(),
                LinearLayoutManager.VERTICAL
            )
        )

        binding.addMenu.setOnClickListener {
            findNavController().navigate(R.id.action_menuFragment_to_addMenuFragment)
        }

        viewModel.getMenus().observe(viewLifecycleOwner, menuObserver)
    }

    private val menuObserver = Observer<Resource<List<Menu>>> { res ->
        when (res) {
            is Resource.Loading -> {
                binding.refresh.isRefreshing = true
            }
            is Resource.Success -> {
                if (res.data != null) {
                    if (res.data.isEmpty()) {
                        binding.rvMenu.visibility = View.GONE
                        binding.llNoData.visibility = View.VISIBLE
                    } else {
                        binding.rvMenu.visibility = View.VISIBLE
                        binding.llNoData.visibility = View.GONE
                    }

                    menuAdapter.updateData(res.data)
                }
                binding.refresh.isRefreshing = false
            }
            is Resource.Error -> {
                binding.refresh.isRefreshing = false
            }
        }

    }

    private fun setAlertDialog(menu: Menu) {
        val builder = AlertDialog.Builder(requireActivity())
        builder.setTitle(requireActivity().resources.getString(R.string.delete_menu))
        builder.setMessage(
            requireActivity().resources.getString(
                R.string.delete_warning_message,
                menu.name
            )
        )

        builder.setPositiveButton(requireActivity().resources.getString(R.string.yes)) { _, _ ->
            val menuEntity = MenuEntity(
                id = menu.id,
                name = menu.name,
                price = menu.price,
                unit = menu.unit,
                image = menu.image,
                id_category = menu.id_category,
                created_date = menu.created_date
            )
            viewModel.deleteMenu(menuEntity).observe(viewLifecycleOwner) { res ->
                when (res) {
                    is Resource.Loading -> {
                        binding.refresh.isRefreshing = true
                    }
                    is Resource.Success -> {
                        binding.refresh.isRefreshing = false
                        if (res.data != null) {
                            menuAdapter.updateData(res.data)
                        }
                    }
                    is Resource.Error -> {
                        binding.refresh.isRefreshing = false
                        Toast.makeText(requireActivity(), res.message.toString(), Toast.LENGTH_LONG)
                            .show()
                    }
                }

            }
        }

        builder.setNegativeButton(requireActivity().resources.getString(R.string.no)) { _, _ ->

        }

        builder.show()
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

}