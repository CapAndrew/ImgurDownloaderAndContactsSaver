package com.example.imgurdownloaderandcontactssaver

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment

class ContactsSaverFragment : Fragment() {

    companion object{
        fun newInstance() = ContactsSaverFragment()
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        activity?.title  = "Save contacts to SQLite"
        return inflater.inflate(R.layout.contacts_fragment, container, false)
    }


}