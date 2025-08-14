package com.example.contact_app.data

import android.net.Uri
import java.util.UUID // Import UUID

data class Contact(
    val id: String = UUID.randomUUID().toString(), // Added unique ID
    val name: String,
    val email: String,
    val phone: String,
    val imageUri: Uri?
)