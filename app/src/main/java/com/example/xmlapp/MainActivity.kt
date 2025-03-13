package com.example.xmlapp

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import android.view.LayoutInflater
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

class MainActivity : AppCompatActivity(), ContactAdapter.OnContactDeleteListener {

    private lateinit var recyclerView: RecyclerView
    private lateinit var btnAddContact: Button
    private lateinit var contactAdapter: ContactAdapter
    private val contactList = mutableListOf<Contact>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        recyclerView = findViewById(R.id.recyclerView)
        btnAddContact = findViewById(R.id.btnAddContact)

        contactList.add(Contact("Иван Иванов", "+7 (999) 123-45-67"))
        contactList.add(Contact("Мария Петрова", "+7 (999) 765-43-21"))

        contactAdapter = ContactAdapter(contactList, this)
        recyclerView.layoutManager = LinearLayoutManager(this)
        recyclerView.adapter = contactAdapter

        btnAddContact.setOnClickListener {
            showAddContactDialog()
        }
    }

    private fun showAddContactDialog() {
        val dialogView = LayoutInflater.from(this).inflate(R.layout.dialog_add_contact, null)
        val etName = dialogView.findViewById<EditText>(R.id.etName)
        val etPhone = dialogView.findViewById<EditText>(R.id.etPhone)

        AlertDialog.Builder(this)
            .setView(dialogView)
            .setPositiveButton("Add") { _, _ ->
                val name = etName.text.toString().trim()
                val phone = etPhone.text.toString().trim()

                if (name.isNotEmpty() && phone.isNotEmpty()) {
                    addContact(name, phone)
                } else {
                    Toast.makeText(this, "\n" +
                            "Fill in all fields", Toast.LENGTH_SHORT).show()
                }
            }
            .setNegativeButton("Cancel") { dialog, _ ->
                dialog.dismiss()
            }
            .create()
            .show()
    }

    private fun addContact(name: String, phone: String) {
        contactList.add(Contact(name, phone))
        contactAdapter.notifyItemInserted(contactList.size - 1)
        Toast.makeText(this, "Contact added", Toast.LENGTH_SHORT).show()
    }

    override fun onContactDelete(position: Int) {
        if (position in contactList.indices) {
            contactList.removeAt(position)
            contactAdapter.notifyItemRemoved(position)
            Toast.makeText(this, "\n" +
                    "Contact deleted", Toast.LENGTH_SHORT).show()
        }
    }
}