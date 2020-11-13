package com.example.imgurdownloaderandcontactssaver

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import kotlinx.android.synthetic.main.main_fragment.*

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

}