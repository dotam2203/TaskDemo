package com.apis

import com.apis.Constants.API_KEY
import com.apis.Constants.BASE_URL
import com.services.ApiService
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.Protocol
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.*
import java.util.concurrent.TimeUnit
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object CallApiRetrofit {
  @Provides
  @Singleton
  fun getApiUrl(): Retrofit = Retrofit.Builder()
    .baseUrl(BASE_URL).client(OkHttpClient().newBuilder().also { client ->
      val loggingInterceptor = HttpLoggingInterceptor()
      loggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY)
      client.apply {
        addNetworkInterceptor(loggingInterceptor)
        connectTimeout(120, TimeUnit.SECONDS)
        connectTimeout(120, TimeUnit.SECONDS)
        protocols(Collections.singletonList(Protocol.HTTP_1_1))
      }
    }.build())
    .addConverterFactory(GsonConverterFactory.create())
    .build()

  @Provides
  @Singleton
  fun loadApi(): ApiService = getApiUrl().create(ApiService::class.java)
}