package com.fabiolourenco.catbreedsapp.core.network.injection

import com.fabiolourenco.catbreedsapp.BuildConfig
import com.fabiolourenco.catbreedsapp.core.network.ApiHelper
import com.fabiolourenco.catbreedsapp.core.network.ApiHelperImpl
import com.fabiolourenco.catbreedsapp.core.network.api.CatApi
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

@Module
@InstallIn(SingletonComponent::class)
object NetworkModule {

    @Provides
    @Singleton
    fun provideRetrofit(): Retrofit {
        val client = OkHttpClient.Builder()
            .addInterceptor { chain ->
                val original = chain.request()
                val requestBuilder = original.newBuilder()
                    .header("Content-Type", "application/json")
                    .header(
                        "x-api-key",
                        "live_rKMG5agKTREWa9mESRdMYFvX5l9FzNX7LnYDb5lyDFU9U1mkeczp3dt7TcTnaUjJ"
                    )
                val request = requestBuilder.build()
                chain.proceed(request)
            }.also {
                if (BuildConfig.DEBUG) {
                    val interceptor = HttpLoggingInterceptor()
                    interceptor.setLevel(HttpLoggingInterceptor.Level.BODY)
                    it.addInterceptor(interceptor)
                }
            }
            .build()

        return Retrofit.Builder()
            .baseUrl("https://api.thecatapi.com/v1/")
            .client(client)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    @Provides
    @Singleton
    fun provideCatApi(retrofit: Retrofit): CatApi = retrofit.create(CatApi::class.java)

    @Provides
    @Singleton
    fun provideApiHelper(catApi: CatApi): ApiHelper = ApiHelperImpl(catApi = catApi)
}