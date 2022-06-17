package com.example.fishmarket.ui.settings

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import com.example.fishmarket.data.source.remote.Resource
import com.example.fishmarket.databinding.FragmentSettingsBinding
import com.example.fishmarket.ui.login.LoginActivity
import com.example.fishmarket.utilis.Utils
import org.koin.androidx.viewmodel.ext.android.viewModel

class SettingsFragment : Fragment() {

    private var _binding: FragmentSettingsBinding? = null
    private val binding get() = _binding!!
    private val viewModel: SettingsViewModel by viewModel()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentSettingsBinding.inflate(layoutInflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.logout.setOnClickListener {
            viewModel.logout().observe(viewLifecycleOwner, logoutObserver)
        }
    }

    private val logoutObserver = Observer<Resource<Boolean>> { res ->
        when (res) {
            is Resource.Loading -> {

            }
            is Resource.Success -> {
                startActivity(Intent(requireActivity(), LoginActivity::class.java))
                requireActivity().finish()
            }
            is Resource.Error -> {
                Utils.showMessage(requireActivity(), res.message.toString())
            }
        }

    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

}