package com.example.contact_app

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import com.example.contact_app.adapter.ContactAdapter
import com.example.contact_app.data.Contact
import com.example.contact_app.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private val contacts = mutableListOf<Contact>()
    private lateinit var contactAdapter: ContactAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        installSplashScreen()

        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setupRecyclerView()
        setupFabs()
        refreshUI()
    }

    private fun setupRecyclerView() {
        contactAdapter = ContactAdapter { contact ->
            contacts.remove(contact)
            refreshUI()
        }
        binding.recyclerViewContacts.adapter = contactAdapter
    }

    private fun setupFabs() {
        binding.fabAddContact.setOnClickListener {
            val bottomSheetFragment = AddContactBottomSheetFragment { contact ->
                contacts.add(contact)
                binding.recyclerViewContacts.smoothScrollToPosition(contacts.size - 1)
                refreshUI()
            }
            bottomSheetFragment.show(supportFragmentManager, AddContactBottomSheetFragment.TAG)
        }

        binding.fabDeleteLastContact.setOnClickListener {
            contacts.removeLastOrNull()
            refreshUI()
        }
    }

    private fun refreshUI() {
        contactAdapter.submitList(contacts.toList())

        if (contacts.isEmpty()) {
            binding.recyclerViewContacts.visibility = View.GONE
            binding.fabDeleteLastContact.visibility = View.GONE
            binding.emptyContact.visibility = View.VISIBLE
        } else {
            binding.recyclerViewContacts.visibility = View.VISIBLE
            binding.fabDeleteLastContact.visibility = View.VISIBLE
            binding.emptyContact.visibility = View.GONE
        }

        binding.fabAddContact.visibility = if (contacts.size >= 6) View.GONE else View.VISIBLE
    }
}
