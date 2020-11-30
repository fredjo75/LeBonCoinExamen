package com.example.leboncoinexamen.data.network

import com.example.leboncoinexamen.data.datasource.network.model.NetworkAlbum
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import retrofit2.Call
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import retrofit2.http.GET

private const val BASE_URL = "https://static.leboncoin.fr/"

private val moshi = Moshi.Builder()
    .add(KotlinJsonAdapterFactory())
    .build()

private val retrofit = Retrofit.Builder()
    .addConverterFactory(MoshiConverterFactory.create(moshi))
    .baseUrl(BASE_URL)
    .build()

interface AlbumApiService {
    @GET("img/shared/technical-test.json")
    fun getAlbums(): Call<List<NetworkAlbum>>
}

object AlbumApi {
    val retrofitService: AlbumApiService by lazy {
        retrofit.create(AlbumApiService::class.java)
    }
}