package com.example.fishmarket.ui.restaurant

import android.app.AlertDialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.fishmarket.R
import com.example.fishmarket.data.repository.restaurant.source.local.entity.RestaurantEntity
import com.example.fishmarket.databinding.FragmentRestaurantBinding
import org.koin.android.viewmodel.ext.android.viewModel

class RestaurantFragment : Fragment() {

    private var _binding: FragmentRestaurantBinding? = null
    private val binding get() = _binding!!
    private val viewModel: RestaurantViewModel by viewModel()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentRestaurantBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val restaurantAdapter = RestaurantAdapter()
        restaurantAdapter.setOnItemClickCallback(object : RestaurantAdapter.OnItemClickCallback {
            override fun onItemLongClicked(restaurant: RestaurantEntity) {
                setAlertDialog(restaurant)
            }

            override fun onItemClicked(restaurant: RestaurantEntity) {
                val bundle = bundleOf("id" to restaurant.id)
                findNavController().navigate(
                    R.id.action_navigation_dashboard_to_navigation_edit_restaurant,
                    bundle
                )
            }

        })

        binding.rvRestaurant.adapter = restaurantAdapter
        binding.rvRestaurant.addItemDecoration(
            DividerItemDecoration(
                requireActivity(),
                LinearLayoutManager.VERTICAL
            )
        )

        viewModel.getRestaurant().observe(viewLifecycleOwner) {
            if (it.isNotEmpty()) {
                restaurantAdapter.updateData(it)
                binding.rvRestaurant.visibility = View.VISIBLE
                binding.llNoData.visibility = View.GONE
            } else {
                binding.rvRestaurant.visibility = View.GONE
                binding.llNoData.visibility = View.VISIBLE
            }
        }

        binding.fabAddRestaurant.setOnClickListener {
            findNavController().navigate(R.id.action_navigation_dashboard_to_navigation_add_restaurant)
        }
    }

    private fun setAlertDialog(restaurantEntity: RestaurantEntity) {
        val builder = AlertDialog.Builder(requireActivity())
        builder.setTitle(requireActivity().resources.getString(R.string.delete_restaurant_title))
        builder.setMessage(
            requireActivity().resources.getString(
                R.string.delete_warning_message,
                restaurantEntity.name
            )
        )

        builder.setPositiveButton(requireActivity().resources.getString(R.string.yes)) { _, _ ->
            viewModel.deleteRestaurant(restaurantEntity)
        }

        builder.setNegativeButton(requireActivity().resources.getString(R.string.no)) { _, _ ->

        }

        builder.show()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}