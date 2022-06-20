package com.example.fishmarket.ui.menu

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.fishmarket.R
import com.example.fishmarket.databinding.FragmentMenuBinding
import com.example.fishmarket.ui.menu.list_category.ListCategoryFragment
import com.example.fishmarket.ui.menu.list_menu.ListMenuFragment
import com.google.android.material.tabs.TabLayoutMediator

class MenuFragment : Fragment() {

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
        setupTabs()
    }

    private fun setupTabs() {
        val fragmentList = listOf(ListMenuFragment(), ListCategoryFragment())
        val tabTitle =
            listOf(
                resources.getString(R.string.title_menu),
                resources.getString(R.string.title_category)
            )

        val sectionsPagerAdapter = SectionsPagerAdapter(fragmentList, requireActivity())
        binding.viewPager.adapter = sectionsPagerAdapter

        TabLayoutMediator(
            binding.tabs,
            binding.viewPager
        ) { tab, position ->
            tab.text = tabTitle[position]
        }.attach()
    }


    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }


}