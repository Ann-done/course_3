package com.example.login_app.api.service

import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory


public class NetworkService {

    private val BASE_URL = "http://10.0.2.2:44372"
    private var mRetrofit: Retrofit? = null
    init {
        val interceptor = HttpLoggingInterceptor()
        interceptor.level = HttpLoggingInterceptor.Level.BODY

        val client = OkHttpClient.Builder()
                .addInterceptor(interceptor)

        mRetrofit = Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
                .client(client.build())
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