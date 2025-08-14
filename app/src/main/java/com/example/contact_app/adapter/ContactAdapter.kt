package com.example.contact_app.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.contact_app.R
import com.example.contact_app.R.color.dark_blue
import com.example.contact_app.data.Contact
import com.example.contact_app.databinding.ContactItemBinding

class ContactAdapter(
    private val onDeleteClicked: (Contact) -> Unit
) : ListAdapter<Contact, ContactAdapter.ContactViewHolder>(CONTACT_COMPARATOR) {

    class ContactViewHolder(
        private val binding: ContactItemBinding,
        private val onDeleteClicked: (Contact) -> Unit
    ) : RecyclerView.ViewHolder(binding.root) {

        fun bind(contact: Contact) {
            binding.contactName.text = contact.name
            binding.contactEmail.text = contact.email
            binding.contactPhone.text = contact.phone

            if (contact.imageUri != null) binding.contactImage.setImageURI(contact.imageUri)
            else {
                binding.contactImage.apply {
                    setBackgroundColor(resources.getColor(dark_blue, null))
                    setAnimation(R.raw.image_no_preview)
                }
            }

            binding.deleteButton.setOnClickListener {
                onDeleteClicked(contact)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ContactViewHolder {
        val binding = ContactItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ContactViewHolder(binding, onDeleteClicked)
    }

    override fun onBindViewHolder(holder: ContactViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    companion object {
        private val CONTACT_COMPARATOR = object : DiffUtil.ItemCallback<Contact>() {
            override fun areItemsTheSame(oldItem: Contact, newItem: Contact): Boolean {
                return oldItem.id == newItem.id
            }

            override fun areContentsTheSame(oldItem: Contact, newItem: Contact): Boolean {
                return oldItem == newItem
            }
        }
    }
}
