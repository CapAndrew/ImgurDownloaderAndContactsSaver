package com.example.imgurdownloaderandcontactssaver.imguruploader

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.imgurdownloaderandcontactssaver.R

class ImgurUploaderFragment : Fragment() {

    companion object {
        fun newInstance() =
            ImgurUploaderFragment()
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        activity?.title = "Upload photo to Imgur"
        return inflater.inflate(R.layout.imgur_fragment, container, false)
    }
}