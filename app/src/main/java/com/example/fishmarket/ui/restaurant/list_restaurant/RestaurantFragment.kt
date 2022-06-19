package com.example.fishmarket.ui.restaurant.list_restaurant

import android.app.AlertDialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.fishmarket.R
import com.example.fishmarket.data.repository.restaurant.source.local.entity.RestaurantEntity
import com.example.fishmarket.data.source.remote.Resource
import com.example.fishmarket.databinding.FragmentRestaurantBinding
import com.example.fishmarket.domain.model.Restaurant
import com.example.fishmarket.domain.model.RestaurantWithTransaction
import com.example.fishmarket.utilis.Utils
import org.koin.androidx.viewmodel.ext.android.viewModel

class RestaurantFragment : Fragment() {

    private var _binding: FragmentRestaurantBinding? = null
    private val binding get() = _binding!!
    private lateinit var restaurantAdapter: RestaurantAdapter
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

        setupRestaurantAdapter()

        viewModel.getRestaurantWithTransaction()
            .observe(viewLifecycleOwner, getRestaurantWithTransactionObserver)

        binding.fabAddRestaurant.setOnClickListener {
            findNavController().navigate(R.id.action_navigation_dashboard_to_navigation_add_restaurant)
        }
    }

    private fun setupRestaurantAdapter() {
        restaurantAdapter = RestaurantAdapter(requireActivity())
        restaurantAdapter.setOnItemClickCallback(object : RestaurantAdapter.OnItemClickCallback {
            override fun onItemLongClicked(restaurant: Restaurant) {
                setAlertDialog(restaurant)
            }

            override fun onItemClicked(restaurant: Restaurant) {
                val bundle =
                    bundleOf("id" to restaurant.id, "createdDate" to restaurant.createdDate)
                findNavController().navigate(
                    R.id.action_navigation_dashboard_to_navigation_edit_restaurant,
                    bundle
                )
            }

        })

        binding.rvRestaurant.adapter = restaurantAdapter
        binding.rvRestaurant.layoutManager =
            LinearLayoutManager(requireActivity(), LinearLayoutManager.VERTICAL, false)
        binding.rvRestaurant.addItemDecoration(
            DividerItemDecoration(
                requireActivity(),
                LinearLayoutManager.VERTICAL
            )
        )
    }

    private fun setAlertDialog(restaurant: Restaurant) {
        val builder = AlertDialog.Builder(requireActivity())
        builder.setTitle(requireActivity().resources.getString(R.string.delete_restaurant_title))
        builder.setMessage(
            requireActivity().resources.getString(
                R.string.delete_warning_message,
                restaurant.name
            )
        )

        builder.setPositiveButton(requireActivity().resources.getString(R.string.yes)) { _, _ ->
            val restaurantEntity = RestaurantEntity(
                id = restaurant.id,
                name = restaurant.name,
                createdDate = restaurant.createdDate
            )
            viewModel.deleteRestaurant(restaurantEntity)
                .observe(viewLifecycleOwner, deleteRestaurantObserver)
        }

        builder.setNegativeButton(requireActivity().resources.getString(R.string.no)) { _, _ ->
        }

        builder.show()
    }

    private val getRestaurantWithTransactionObserver =
        Observer<Resource<List<RestaurantWithTransaction>>> { res ->
            when (res) {
                is Resource.Loading -> {
                    binding.refresh.isRefreshing = true
                }
                is Resource.Success -> {
                    binding.refresh.isRefreshing = false
                    if (res.data != null) {
                        if (res.data.isNotEmpty()) {
                            restaurantAdapter.updateData(res.data)
                            binding.rvRestaurant.visibility = View.VISIBLE
                            binding.llNoData.visibility = View.GONE
                        } else {
                            binding.rvRestaurant.visibility = View.GONE
                            binding.llNoData.visibility = View.VISIBLE
                        }
                    }
                }
                is Resource.Error -> {
                    Toast.makeText(requireActivity(), res.message, Toast.LENGTH_LONG).show()
                    binding.refresh.isRefreshing = false
                }
            }
        }

    private val deleteRestaurantObserver =
        Observer<Resource<List<RestaurantWithTransaction>>> { res ->
            when (res) {
                is Resource.Loading -> {
                    binding.refresh.isRefreshing = true
                }
                is Resource.Success -> {
                    binding.refresh.isRefreshing = false
                    if (res.data != null) {
                        restaurantAdapter.updateData(res.data)
                    }
                }
                is Resource.Error -> {
                    binding.refresh.isRefreshing = false
                    Utils.showMessage(requireActivity(), res.message.toString())
                }
            }

        }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}