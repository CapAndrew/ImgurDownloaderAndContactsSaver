package com.example.imgurdownloaderandcontactssaver.imguruploader

import okhttp3.MultipartBody
import retrofit2.Call
import retrofit2.http.*


internal interface ImgurService {
    @Multipart
    @Headers("Authorization: Client-ID 5339e4840e8c090")
    @POST("upload")
    fun postImage(
        @Query("title") title: String?,
        @Query("description") description: String?,
        @Query("album") albumId: String?,
        @Query("account_url") username: String?,
        @Part file: MultipartBody.Part?
    ): Call<ImgurResponse?>?
}