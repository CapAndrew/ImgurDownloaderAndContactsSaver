package com.example.imgurdownloaderandcontactssaver.contactsSaver

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.imgurdownloaderandcontactssaver.R

class ContactAdapter() : RecyclerView.Adapter<ContactViewHolder>() {

    private val contactsList = mutableListOf<ContactItem>()

    fun updateItem(newItems: List<ContactItem>) {
        contactsList.clear()
        contactsList.addAll(newItems)
        notifyDataSetChanged()
    }

    fun clearItem() {
        contactsList.clear()
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ContactViewHolder {
        val v: View =
            LayoutInflater.from(parent.context).inflate(R.layout.contact_item, parent, false)
        return ContactViewHolder(
            v
        )
    }

    override fun getItemCount(): Int {
        return contactsList.size
    }

    override fun onBindViewHolder(holder: ContactViewHolder, position: Int) {
        val contactItem: ContactItem = contactsList[position]
        holder.bind(contactItem)
    }
}