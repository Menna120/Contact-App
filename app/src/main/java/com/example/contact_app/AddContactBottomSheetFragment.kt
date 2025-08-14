package com.example.contact_app

import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.result.PickVisualMediaRequest
import androidx.activity.result.contract.ActivityResultContracts
import com.example.contact_app.data.Contact
import com.example.contact_app.databinding.FragmentAddContactBottomSheetBinding
import com.google.android.material.bottomsheet.BottomSheetDialogFragment

class AddContactBottomSheetFragment(
    private val onEnterClicked: (Contact) -> Unit
) : BottomSheetDialogFragment() {

    private var _binding: FragmentAddContactBottomSheetBinding? = null
    private val binding get() = _binding!!

    private var selectedImageUri: Uri? = null

    val pickMedia = registerForActivityResult(ActivityResultContracts.PickVisualMedia()) { uri ->
        if (uri != null) {
            Log.d("PhotoPicker", "Selected URI: $uri")
            selectedImageUri = uri
            binding.contactImage.setImageURI(uri)
        } else {
            Log.d("PhotoPicker", "No media selected")
        }
    }

    override fun getTheme(): Int {
        return R.style.CustomBottomSheetDialog
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentAddContactBottomSheetBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.contactImagePicker.setOnClickListener {
            pickMedia.launch(
                PickVisualMediaRequest(
                    ActivityResultContracts.PickVisualMedia.ImageOnly
                )
            )
        }

        binding.enterContactButton.setOnClickListener {
            val name = binding.editTextName.text.toString().trim()
            val email = binding.editTextEmail.text.toString().trim()
            val phone = binding.editTextPhone.text.toString().trim()

            if (name.isNotEmpty() && email.isNotEmpty() && phone.isNotEmpty()) {
                onEnterClicked(Contact(name, email, phone, selectedImageUri))
                dismiss()
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    companion object {
        const val TAG = "ContactBottomSheetFragment"
    }
}