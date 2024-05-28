package com.fabiolourenco.corenetwork.injection

import android.content.Context
import com.fabiolourenco.corenetwork.ApiHelper
import com.fabiolourenco.corenetwork.ApiHelperImpl
import com.fabiolourenco.corenetwork.BuildConfig
import com.fabiolourenco.corenetwork.R
import com.fabiolourenco.corenetwork.api.CatApi
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
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
    fun provideRetrofit(@ApplicationContext context: Context): Retrofit {
        val client = OkHttpClient.Builder()
            .addInterceptor { chain ->
                val original = chain.request()
                val requestBuilder = original.newBuilder()
                    .header("Content-Type", "application/json")
                    .header("x-api-key", context.getString(R.string.api_key))
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
            .baseUrl(context.getString(R.string.cat_api_base_url))
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