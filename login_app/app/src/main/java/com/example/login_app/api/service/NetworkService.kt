package com.example.login_app.api.service

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory


public class NetworkService {

    private val BASE_URL = "http://10.0.2.2:44372"
    private var mRetrofit: Retrofit? = null
    init {
        mRetrofit = Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }
    companion object{
        private var mInstance: NetworkService? = null

        fun getInstance(): NetworkService? {
            if (mInstance == null) {
                mInstance = NetworkService()
            }
            return mInstance
        }

    }

    fun getJSONApi(): JSONServerApi? {
        return mRetrofit!!.create(JSONServerApi::class.java)
    }



}