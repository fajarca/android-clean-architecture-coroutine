package io.fajarca.marvel.di.module

import android.app.Application
import dagger.Module
import dagger.Provides
import io.fajarca.marvel.BuildConfig
import io.fajarca.marvel.data.service.ApiService
import okhttp3.Cache
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import javax.inject.Singleton

@Module
class NetworkModule {
    @Provides
    @Singleton
    fun provideHttpCache(application: Application): Cache {
        val cacheSize: Long = 10 * 10 * 1024
        return Cache(application.cacheDir, cacheSize)
    }

    @Provides
    @Singleton
    fun provideLoggingInterceptor(): HttpLoggingInterceptor {
        val loggingInterceptor = HttpLoggingInterceptor()
        loggingInterceptor.level = HttpLoggingInterceptor.Level.BODY
        return loggingInterceptor
    }

    @Provides
    @Singleton
    fun provideOkHttpClient(loggingInterceptor: HttpLoggingInterceptor, cache: Cache, authInterceptor: Interceptor): OkHttpClient {
        val client = OkHttpClient.Builder()
        client.cache(cache)
        client.addInterceptor(loggingInterceptor)
        client.addInterceptor(authInterceptor)
        return client.build()
    }

    @Provides
    @Singleton
    fun provideAuthInterceptor(): Interceptor {

        return Interceptor { chain ->

            val request = chain.request()
                .url()
                .newBuilder()
                .addQueryParameter("apikey", BuildConfig.API_KEY)
                .addQueryParameter("ts","1576669205601")
                .addQueryParameter("hash", BuildConfig.API_HASH)
                .build()

            val newRequest = chain.request()
                .newBuilder()
                .url(request)
                .build()

            chain.proceed(newRequest)
        }
    }


    @Provides
    @Singleton
    fun provideRetrofit(okHttpClient: OkHttpClient): Retrofit {
        return Retrofit.Builder()
            .baseUrl(BuildConfig.BASE_URL)
            .addConverterFactory(MoshiConverterFactory.create())
            .client(okHttpClient)
            .build()
    }

    @Provides
    @Singleton
    fun provideApiService(retrofit: Retrofit) = retrofit.create(ApiService::class.java)
}