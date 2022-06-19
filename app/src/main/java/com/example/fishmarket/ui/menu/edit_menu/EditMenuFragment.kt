package com.example.fishmarket.ui.menu.edit_menu

import android.app.Activity
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.drawable.BitmapDrawable
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.bumptech.glide.Glide
import com.example.fishmarket.R
import com.example.fishmarket.data.repository.menu.source.local.entity.MenuEntity
import com.example.fishmarket.data.source.remote.Resource
import com.example.fishmarket.databinding.FragmentEditMenuBinding
import com.example.fishmarket.domain.model.Menu
import com.example.fishmarket.ui.menu.add_menu.AddMenuViewModel
import com.example.fishmarket.utilis.FileUtil
import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.StorageReference
import com.google.firebase.storage.ktx.storage
import id.zelory.compressor.Compressor
import kotlinx.coroutines.launch
import org.koin.androidx.navigation.koinNavGraphViewModel
import org.koin.androidx.viewmodel.ext.android.viewModel
import java.io.ByteArrayOutputStream
import java.io.File
import java.util.*

class EditMenuFragment : Fragment() {

    private val viewModel: AddMenuViewModel by koinNavGraphViewModel(R.id.menu)
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
        val unit = arguments?.getString("unit")
        val image = arguments?.getString("image")
        val idCategory = arguments?.getString("idCategory")
        val createdDate = arguments?.getLong("createdDate")

        viewModel.getCategory(idCategory.toString())

        binding.etMenuName.setText(name)
        binding.etMenuPrice.setText(price.toString())
        binding.atvUnit.setText(unit)

        viewModel.categoryName.observe(viewLifecycleOwner) {
            binding.etIdCategory.setText(it)
        }

        binding.etIdCategory.isClickable = true
        binding.etIdCategory.isFocusable = false

        binding.etIdCategory.setOnClickListener {
            findNavController().navigate(R.id.selectCategoryDialog)
        }

        val array = resources.getStringArray(R.array.unit_array)
        val adapter = ArrayAdapter(
            requireActivity(),
            android.R.layout.simple_spinner_dropdown_item,
            array
        )

        binding.atvUnit.setAdapter(adapter)

        Glide.with(requireActivity()).load(image).error(R.mipmap.image_placeholder)
            .into(binding.imgMenu)

        binding.imgMenu.setOnClickListener {
            openGalleryForImage()
        }

        binding.btnSave.setOnClickListener {
            val mName = binding.etMenuName.text.toString()
            val mPrice = binding.etMenuPrice.text.toString()
            val mIdCategory = viewModel.categoryId.value
            val mUnit = binding.atvUnit.text.toString()

            if (mName == "") {
                binding.etMenuName.error = resources.getString(R.string.warning_menu_name_empty)
            } else if (mPrice == "") {
                binding.etMenuPrice.error = resources.getString(R.string.warning_price_menu_empty)
            } else if (mIdCategory == "") {
                binding.etIdCategory.error =
                    resources.getString(R.string.warning_category_menu_empty)
            } else {
                binding.btnSave.isEnabled = false

                val storage = Firebase.storage
                val storageRef = storage.reference

                val imagesRef: StorageReference = storageRef.child(
                    "images/" + UUID.randomUUID().toString()
                )

                binding.imgMenu.isDrawingCacheEnabled = true
                binding.imgMenu.buildDrawingCache()
                val bitmap = (binding.imgMenu.drawable as BitmapDrawable).bitmap
                val baos = ByteArrayOutputStream()
                bitmap.compress(Bitmap.CompressFormat.JPEG, 100, baos)
                val data = baos.toByteArray()

                imagesRef.putBytes(data).continueWithTask { task ->
                    if (!task.isSuccessful) {
                        task.exception?.let {
                            throw it
                        }
                    }
                    imagesRef.downloadUrl
                }.addOnCompleteListener { task ->
                    if (task.isSuccessful) {
                        val downloadUri = task.result
                        val newMenu = MenuEntity(
                            id = id.toString(),
                            name = mName,
                            price = mPrice.toInt(),
                            unit = mUnit,
                            image = downloadUri.toString(),
                            id_category = mIdCategory.toString(),
                            created_date = createdDate ?: 0
                        )

                        viewModel.editMenu(newMenu).observe(viewLifecycleOwner, editMenuObserver)
                    } else {
                        binding.btnSave.isEnabled = true
                        Toast.makeText(
                            requireActivity(),
                            task.exception?.message.toString(),
                            Toast.LENGTH_LONG
                        ).show()
                    }
                }


            }
        }
    }

    private val editMenuObserver = Observer<Resource<Menu>> { res ->
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

    private fun openGalleryForImage() {
        val intent = Intent(Intent.ACTION_PICK)
        intent.type = "image/*"
        startActivityForResult(intent, REQUEST_CODE)
    }

    private fun compressImage(file: File) {
        lifecycleScope.launch {
            val compressedImage = Compressor.compress(requireActivity(), file)
            binding.imgMenu.setImageBitmap(BitmapFactory.decodeFile(compressedImage.absolutePath))
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (resultCode == Activity.RESULT_OK && requestCode == REQUEST_CODE) {
            val file = FileUtil.from(requireActivity(), data?.data)
            compressImage(file)
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

    companion object {
        const val REQUEST_CODE = 100
    }

}