package com.example.contact_app.data

import android.net.Uri
import java.util.UUID

data class Contact(
    val name: String,
    val email: String,
    val phone: String,
    val imageUri: Uri?,
    val id: String = UUID.randomUUID().toString(),
)