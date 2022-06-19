package com.example.fishmarket.ui.menu.add_menu

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
import com.example.fishmarket.R
import com.example.fishmarket.data.repository.menu.source.local.entity.MenuEntity
import com.example.fishmarket.data.source.remote.Resource
import com.example.fishmarket.databinding.FragmentAddMenuBinding
import com.example.fishmarket.domain.model.Menu
import com.example.fishmarket.utilis.FileUtil
import com.example.fishmarket.utilis.Utils
import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.StorageReference
import com.google.firebase.storage.ktx.storage
import id.zelory.compressor.Compressor
import kotlinx.coroutines.launch
import org.koin.androidx.navigation.koinNavGraphViewModel
import java.io.ByteArrayOutputStream
import java.io.File
import java.util.*

class AddMenuFragment : Fragment() {

    private val viewModel: AddMenuViewModel by koinNavGraphViewModel(R.id.menu)
    private var _binding: FragmentAddMenuBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        viewModel.setCategoryId("")
        viewModel.setCategoryName("")
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

        val array = resources.getStringArray(R.array.unit_array)
        val adapter = ArrayAdapter(
            requireActivity(),
            android.R.layout.simple_spinner_dropdown_item,
            array
        )

        binding.atvUnit.setAdapter(adapter)

        binding.imgMenu.setOnClickListener {
            openGalleryForImage()
        }

        binding.btnSave.setOnClickListener {
            val id = Utils.getRandomString()
            val name = binding.etMenuName.text.toString()
            val price = binding.etMenuPrice.text.toString()
            val idCategory = viewModel.categoryId.value
            val unit = binding.atvUnit.text.toString()
            val createdDate = System.currentTimeMillis()


            if (name == "") {
                binding.etMenuName.error = resources.getString(R.string.warning_menu_name_empty)
            } else if (price == "") {
                binding.etMenuPrice.error = resources.getString(R.string.warning_price_menu_empty)
            } else if (idCategory == "") {
                binding.etIdCategory.error =
                    resources.getString(R.string.warning_category_menu_empty)
            } else if (unit == "") {
                binding.atvUnit.error = resources.getString(R.string.warning_unit_menu_empty)
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
                        val menu = MenuEntity(
                            id = id,
                            name = name,
                            price = price.toInt(),
                            unit = unit,
                            image = downloadUri.toString(),
                            id_category = idCategory ?: "",
                            created_date = createdDate
                        )

                        viewModel.insertMenu(menu).observe(viewLifecycleOwner, insertMenuObserver)
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


    private val insertMenuObserver = Observer<Resource<Menu>> { res ->
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

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (resultCode == Activity.RESULT_OK && requestCode == REQUEST_CODE) {
            val file = FileUtil.from(requireActivity(), data?.data)
            compressImage(file)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    companion object {
        const val REQUEST_CODE = 100
    }


}