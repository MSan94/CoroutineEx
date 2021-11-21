package com.prj.coroutineex.mvvm.retrofit

import okhttp3.Interceptor
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object NewsApiService {

    private val interceptor = Interceptor { chain ->
        val url = chain.request().url.newBuilder().addQueryParameter("apiKey","API_KEY").build()
        val request = chain.request()
            .newBuilder()
            .url(url)
            .build()
        chain.proceed(request)
    }

    private val apiClient = OkHttpClient().newBuilder().addInterceptor(interceptor).build()
    private fun getRetrofit() : Retrofit{
        return Retrofit.Builder().client(apiClient)
            .baseUrl("https://newsapi.org/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

}