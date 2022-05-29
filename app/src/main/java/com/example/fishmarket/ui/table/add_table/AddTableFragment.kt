package com.example.fishmarket.ui.table.add_table

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import com.example.fishmarket.data.repository.table.source.local.entity.TableEntity
import com.example.fishmarket.data.source.remote.Resource
import com.example.fishmarket.databinding.FragmentAddTableBinding
import com.example.fishmarket.utilis.Utils
import org.koin.androidx.viewmodel.ext.android.viewModel

class AddTableFragment : Fragment() {

    private var _binding: FragmentAddTableBinding? = null
    private val binding get() = _binding!!
    private val viewModel: AddTableViewModel by viewModel()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentAddTableBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


        binding.btnSave.setOnClickListener {
            val name = binding.etTableName.text.toString()
            val id = Utils.getRandomString()
            val createdDate = System.currentTimeMillis()
            val table = TableEntity(id = id, name = name, status = false, createdDate = createdDate)
            viewModel.addTable(table).observe(viewLifecycleOwner){res->
                when(res){
                    is Resource.Loading->{
                        binding.btnSave.isEnabled = false
                    }
                    is Resource.Success->{
                        findNavController().navigateUp()
                        binding.btnSave.isEnabled = true
                    }
                    is Resource.Error->{
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