package com.example.imgurdownloaderandcontactssaver

import android.view.View
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class ContactViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
    private var contactName: TextView = itemView.findViewById(R.id.contact_name)
    private var contactPhone: TextView = itemView.findViewById(R.id.contact_phone)

    fun bind(item: ContactItem){
        contactName.text = item.name
        contactPhone.text = item.phone
    }
}