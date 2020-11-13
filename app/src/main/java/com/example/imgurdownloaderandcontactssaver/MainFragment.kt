package com.example.imgurdownloaderandcontactssaver

import android.Manifest
import android.content.pm.PackageManager
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import kotlinx.android.synthetic.main.contacts_fragment.*
import kotlinx.android.synthetic.main.main_fragment.*
import kotlinx.android.synthetic.main.main_fragment.saveContacts

class MainFragment : Fragment() {

    companion object {
        fun newInstance() = MainFragment()
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        activity?.title = "Menu"
        return inflater.inflate(R.layout.main_fragment, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        if (!checkContactsPermission()) {
            askPermission(Manifest.permission.READ_CONTACTS)
        } else {
            saveContacts.isEnabled = true
        }

        uploadToImgur.setOnClickListener {
            fragmentManager?.beginTransaction()
                ?.replace(R.id.container, ImgurUploaderFragment.newInstance())
                ?.addToBackStack(null)
                ?.commit()
        }

        saveContacts.setOnClickListener {
            fragmentManager?.beginTransaction()
                ?.replace(R.id.container, ContactsSaverFragment.newInstance())
                ?.addToBackStack(null)
                ?.commit()
        }
    }

    private fun askPermission(vararg permissions: String) {
        requestPermissions(permissions, 0)
    }

    private fun checkContactsPermission(): Boolean {
        return PackageManager.PERMISSION_GRANTED == ContextCompat.checkSelfPermission(
            requireContext(),
            Manifest.permission.READ_CONTACTS
        )
    }

}