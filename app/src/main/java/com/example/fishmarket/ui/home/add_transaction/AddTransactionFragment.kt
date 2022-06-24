package com.example.fishmarket.ui.home.add_transaction

import android.os.Bundle
import android.view.*
import androidx.fragment.app.Fragment
import android.widget.Toast
import androidx.core.os.bundleOf
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.fishmarket.App
import com.example.fishmarket.R
import com.example.fishmarket.data.source.remote.Resource
import com.example.fishmarket.databinding.FragmentAddTransactionBinding
import com.example.fishmarket.domain.model.Category
import com.example.fishmarket.utilis.Utils
import org.koin.androidx.navigation.koinNavGraphViewModel

class AddTransactionFragment : Fragment() {

    private var _binding: FragmentAddTransactionBinding? = null
    private val binding get() = _binding!!
    private val viewModel: AddTransactionViewModel by koinNavGraphViewModel(R.id.home)
    private val ct = App.getApp()
    private lateinit var addTransactionAdapter: AddTransactionAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentAddTransactionBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setDismissObserver()
        getCategories()

        val gridLayoutManager = GridLayoutManager(requireActivity(), 2)
        addTransactionAdapter = AddTransactionAdapter(ct, this)
        addTransactionAdapter.setOnItemClickCallback(object :
            AddTransactionAdapter.OnItemClickCallback {
            override fun onItemEdit(menu: com.example.fishmarket.domain.model.Menu) {
                for (i in 0 until ct.getCart().cartSize) {
                    val product = ct.getCart().getProduct(i)
                    if (product.id == menu.id) {
                        val bundle = bundleOf(
                            "position" to i,
                            "name" to product.name,
                            "price" to product.price,
                            "quantity" to product.quantity,
                            "unit" to product.unit
                        )

                        findNavController().navigate(
                            R.id.action_navigation_add_transaction_to_editTransactionFragment,
                            bundle
                        )
                    }
                }
            }
        })

        binding.rvMenu.adapter = addTransactionAdapter
        binding.rvMenu.layoutManager = gridLayoutManager

        binding.llSaveTransaction.setOnClickListener {
            findNavController().navigate(R.id.action_navigation_add_transaction_to_reviewTransactionFragment)
        }

        viewModel.getMenus("0").observe(viewLifecycleOwner, menusObserver)
    }


    private fun setDismissObserver() {
        findNavController().currentBackStackEntry?.savedStateHandle?.getLiveData<Boolean>(
            "DISMISS"
        )?.observe(viewLifecycleOwner) {
            it?.let {
                checkCart()
                addTransactionAdapter.notifyDataSetChanged()
            }
        }
    }

    fun checkCart() {
        val total = ct.getCart().totalItem
        if (total == 0) {
            binding.llSaveTransaction.visibility = View.GONE
        } else {
            val totalFee = ct.getCart().totalFee
            binding.tvTotalItem.text = "$total Items"
            binding.tvTotalFee.text = Utils.formatNumberToRupiah(totalFee, requireActivity())
            binding.llSaveTransaction.visibility = View.VISIBLE
        }
    }

    private val menusObserver =
        Observer<Resource<List<com.example.fishmarket.domain.model.Menu>>> { res ->
            when (res) {
                is Resource.Loading -> {
                    binding.refresh.isRefreshing = true
                }
                is Resource.Success -> {
                    binding.refresh.isRefreshing = false
                    if (res.data != null) {
                        if (res.data.isEmpty()) {
                            binding.rvMenu.visibility = View.GONE
                            binding.llNoData.visibility = View.VISIBLE
                        } else {
                            binding.rvMenu.visibility = View.VISIBLE
                            binding.llNoData.visibility = View.GONE
                        }
                        addTransactionAdapter.updateData(res.data)
                    }
                }
                is Resource.Error -> {
                    binding.refresh.isRefreshing = false
                    Toast.makeText(requireActivity(), res.message.toString(), Toast.LENGTH_LONG)
                        .show()
                }
            }

        }

    private fun getCategories() {
        val categoryTransactionAdapter = CategoryTransactionAdapter(requireActivity())
        categoryTransactionAdapter.setOnItemClickCallback(object :
            CategoryTransactionAdapter.OnItemClickCallback {
            override fun onItemClicked(categoryEntity: Category) {
                viewModel.getMenus(categoryEntity.id).observe(viewLifecycleOwner, menusObserver)
            }
        })

        binding.rvCategory.adapter = categoryTransactionAdapter
        binding.rvCategory.layoutManager =
            LinearLayoutManager(requireActivity(), LinearLayoutManager.HORIZONTAL, false)

        viewModel.getCategories().observe(viewLifecycleOwner) { res ->
            when (res) {
                is Resource.Loading -> {

                }
                is Resource.Success -> {
                    if (res.data != null) {
                        if (res.data.isEmpty()) {
                            binding.rvCategory.visibility = View.GONE
                        } else {
                            binding.rvCategory.visibility = View.VISIBLE
                        }
                        categoryTransactionAdapter.updateData(res.data)
                    }
                }
                is Resource.Error -> {
                    Toast.makeText(requireActivity(), res.message.toString(), Toast.LENGTH_LONG)
                        .show()
                }
            }
        }
    }


    override fun onResume() {
        super.onResume()
        checkCart()
    }


}