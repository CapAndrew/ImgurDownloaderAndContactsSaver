package com.example.imgurdownloaderandcontactssaver.contactsSaver

import android.os.Bundle
import android.provider.ContactsContract
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.imgurdownloaderandcontactssaver.R
import kotlinx.android.synthetic.main.contacts_fragment.*

class ContactsSaverFragment : Fragment() {

    companion object {
        fun newInstance() =
            ContactsSaverFragment()

        private val contactAdapter =
            ContactAdapter()
    }

    lateinit var db: DataBaseHelper

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        activity?.title = "Save contacts to SQLite"
        return inflater.inflate(R.layout.contacts_fragment, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        recyclerView.setHasFixedSize(true)
        recyclerView.layoutManager = LinearLayoutManager(context)
        recyclerView.adapter =
            contactAdapter
        db = DataBaseHelper(requireContext())
        loadContacts.isEnabled = db.dataCount()>0
        saveContacts.isEnabled = contactAdapter.itemCount > 0

        val contactsList: MutableList<ContactItem> = getContacts()

        readContacts.setOnClickListener {
            contactAdapter.updateItem(contactsList)
            saveContacts.isEnabled = contactAdapter.itemCount > 0
        }

        clearContacts.setOnClickListener {
            contactAdapter.clearItem()
            saveContacts.isEnabled = false
        }

        saveContacts.setOnClickListener {
            db.deleteData()
            contactsList.forEach {
                db.insertData(it)
            }
            loadContacts.isEnabled = db.dataCount()>0
        }

        loadContacts.setOnClickListener {
            if(db.readData().isNotEmpty()){
                contactAdapter.updateItem(db.readData())
            }
        }


    }

    private fun getContacts(): MutableList<ContactItem> {
        val contactsList: MutableList<ContactItem> = ArrayList()
        val contentResolver = context?.contentResolver
        val cursor = contentResolver?.query(
            ContactsContract.CommonDataKinds.Phone.CONTENT_URI,
            null,
            null,
            null,
            null
        )

        cursor?.use {
            while (it.moveToNext()) {
                val displayName =
                    it.getString(cursor.getColumnIndex(ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME))
                val phone =
                    it.getString(cursor.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER))
                contactsList.add(
                    ContactItem(
                        displayName,
                        phone
                    )
                )
            }
        } ?: return contactsList
        return contactsList
    }
}