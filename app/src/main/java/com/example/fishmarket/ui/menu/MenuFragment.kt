package com.example.fishmarket.ui.menu

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.fishmarket.data.source.remote.Resource
import com.example.fishmarket.databinding.FragmentMenuBinding
import org.koin.androidx.viewmodel.ext.android.viewModel

class MenuFragment : Fragment() {

    private val viewModel: MenuViewModel by viewModel()
    private var _binding: FragmentMenuBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentMenuBinding.inflate(layoutInflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val menuAdapter = MenuAdapter()
        binding.rvMenu.adapter = menuAdapter
        binding.rvMenu.addItemDecoration(
            DividerItemDecoration(
                requireActivity(),
                LinearLayoutManager.VERTICAL
            )
        )

        viewModel.getMenus().observe(viewLifecycleOwner) { res ->
            when (res) {
                is Resource.Loading -> {
                    binding.refresh.isRefreshing = true
                }
                is Resource.Success -> {
                    if (res.data != null) {
                        menuAdapter.updateData(res.data)
                    }
                    binding.refresh.isRefreshing = false
                }
                is Resource.Error -> {
                    binding.refresh.isRefreshing = false
                }
            }
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }


}