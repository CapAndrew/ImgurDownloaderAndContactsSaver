package com.example.imgurdownloaderandcontactssaver.imguruploader

import android.Manifest
import android.content.Intent
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.provider.MediaStore
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.fragment.app.Fragment
import com.example.imgurdownloaderandcontactssaver.R
import kotlinx.android.synthetic.main.imgur_fragment.*
import okhttp3.MediaType
import okhttp3.MultipartBody
import okhttp3.RequestBody
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.io.File
import java.io.IOException
import java.util.ArrayList

class ImgurUploaderFragment : Fragment() {

    companion object {
        fun newInstance() =
            ImgurUploaderFragment()

        private var chosenFile: File? = null
        private var returnUri: Uri? = null

        private val retrofit: Retrofit = Retrofit.Builder()
            .baseUrl("https://api.imgur.com/3/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        activity?.title = "Upload photo to Imgur"
        return inflater.inflate(R.layout.imgur_fragment, container, false)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == 1 && resultCode == AppCompatActivity.RESULT_OK && data != null && data.data != null) {
            returnUri = data.data
            try {
                val bitmap =
                    MediaStore.Images.Media.getBitmap(requireContext().contentResolver, returnUri)
                image.setImageBitmap(bitmap)
            } catch (e: IOException) {
                e.printStackTrace()
            }
            super.onActivityResult(requestCode, resultCode, data)
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                val permissionsList: MutableList<String> =
                    ArrayList()
                addPermission(permissionsList, Manifest.permission.READ_EXTERNAL_STORAGE)
                addPermission(permissionsList, Manifest.permission.WRITE_EXTERNAL_STORAGE)
                if (permissionsList.isNotEmpty()) ActivityCompat.requestPermissions(
                    requireActivity(),
                    permissionsList.toTypedArray(),
                    1003
                ) else filePath
            } else {
                filePath
            }
        }
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        choose_image.setOnClickListener {
            onChoose()
        }

        upload.setOnClickListener {
            onUpload()
        }

    }

    //Често стыренный код
    private fun onChoose() {
        val intent = Intent()
        intent.type = "image/*"
        intent.action = Intent.ACTION_GET_CONTENT
        startActivityForResult(Intent.createChooser(intent, "Select Picture"), 1)
    }

    private fun onUpload() {
        if (chosenFile == null) {
            Toast.makeText(requireContext(), "Choose a file before upload.", Toast.LENGTH_SHORT)
                .show()
            return
        }

        val imgurService: ImgurService = retrofit.create(ImgurService::class.java)
        val name = "Test name"
        val description = "Test description"
        imgurService.postImage(
            name,
            description, "", "",
            MultipartBody.Part.createFormData(
                "image",
                chosenFile!!.name,
                RequestBody.create(MediaType.parse("image/*"), chosenFile)
            )
        )?.enqueue(object : Callback<ImgurResponse?> {
            override fun onResponse(
                call: Call<ImgurResponse?>?,
                response: Response<ImgurResponse?>?
            ) {
                if (response != null) {
                    if (response.isSuccessful) {
                        Toast.makeText(requireContext(), "Upload successful !", Toast.LENGTH_SHORT)
                            .show()
                        Log.i("qwerqwerqwer", "${response.body()}")
                        response.body()
                    }
                }
            }

            override fun onFailure(call: Call<ImgurResponse?>?, t: Throwable) {
                Toast.makeText(
                    requireContext(),
                    "An unknown error has occured.",
                    Toast.LENGTH_SHORT
                )
                    .show()
                t.printStackTrace()
            }
        })
    }

    private fun addPermission(
        permissionsList: MutableList<String>,
        permission: String
    ) {
        if (if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                requireContext().checkSelfPermission(permission) !== android.content.pm.PackageManager.PERMISSION_GRANTED
            } else {
                TODO("VERSION.SDK_INT < M")
            }
        ) {
            permissionsList.add(permission)
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                shouldShowRequestPermissionRationale(permission)
            }
        }
    }

    private val filePath: Unit
        get() {
            val filePath: String? = returnUri?.let { DocumentHelper.getPath(requireContext(), it) }
            if (filePath == null || filePath.isEmpty()) return
            chosenFile = File(filePath)
            Log.d("FilePath", filePath)
        }
}