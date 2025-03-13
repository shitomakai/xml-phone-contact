package com.example.xmlapp

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class ContactAdapter(
    private val contactList: MutableList<Contact>,
    private val deleteListener: OnContactDeleteListener
) : RecyclerView.Adapter<ContactAdapter.ContactViewHolder>() {

    interface OnContactDeleteListener {
        fun onContactDelete(position: Int)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ContactViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_contact, parent, false)
        return ContactViewHolder(view)
    }

    override fun onBindViewHolder(holder: ContactViewHolder, position: Int) {
        val contact = contactList[position]
        holder.tvName.text = contact.name
        holder.tvPhone.text = contact.phone

        holder.btnDelete.setOnClickListener {
            deleteListener.onContactDelete(holder.adapterPosition)
        }
    }

    override fun getItemCount(): Int = contactList.size

    class ContactViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val tvName: TextView = itemView.findViewById(R.id.tvName)
        val tvPhone: TextView = itemView.findViewById(R.id.tvPhone)
        val btnDelete: Button = itemView.findViewById(R.id.btnDelete)
    }
}