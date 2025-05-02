package com.psspl.myweatherdashboard.di

import com.psspl.myweatherdashboard.BuildConfig
import com.psspl.myweatherdashboard.data.network.CurlLoggingInterceptor
import com.psspl.myweatherdashboard.data.network.WeatherApi
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import javax.inject.Singleton

/***
 * Name : NetworkModule.kt
 * Author : Prakash Software Pvt Ltd
 * Date : 28 Apr 2025
 * Desc : Dagger module for providing network-related dependencies.
 **/
@Module
@InstallIn(SingletonComponent::class)
object NetworkModule {

    /***
     * Provides a singleton Moshi instance for JSON parsing.
     * @return Moshi instance with Kotlin adapter.
     */
    @Provides
    @Singleton
    fun provideMoshi(): Moshi {
        return Moshi.Builder()
            .add(KotlinJsonAdapterFactory()) // Required for Kotlin data classes
            .build()
    }

    /***
     * Provides a singleton OkHttpClient with logging interceptors.
     * @return OkHttpClient instance with logging and cURL interceptors.
     */
    @Provides
    @Singleton
    fun provideOkHttpClient(): OkHttpClient {
        val loggingInterceptor = HttpLoggingInterceptor().apply {
            level = if (BuildConfig.DEBUG) {
                HttpLoggingInterceptor.Level.BODY
            } else {
                HttpLoggingInterceptor.Level.NONE
            }
        }
        val curlLoggingInterceptor = CurlLoggingInterceptor()

        return OkHttpClient.Builder()
            .addInterceptor(loggingInterceptor)
            .addInterceptor(curlLoggingInterceptor) // Add cURL interceptor
            .build()
    }

    /***
     * Provides a singleton Retrofit instance for API calls.
     * @param moshi Moshi instance for JSON conversion.
     * @param okHttpClient OkHttpClient for HTTP requests.
     * @return Retrofit instance with base URL and converters.
     */
    @Provides
    @Singleton
    fun provideRetrofit(moshi: Moshi, okHttpClient: OkHttpClient): Retrofit {
        return Retrofit.Builder()
            .baseUrl("https://api.openweathermap.org/") // Common base URL
            .client(okHttpClient)
            .addConverterFactory(MoshiConverterFactory.create(moshi))
            .build()
    }

    /***
     * Provides a singleton WeatherApi instance.
     * @param retrofit Retrofit instance to create the API.
     * @return WeatherApi instance for OpenWeatherMap API calls.
     */
    @Provides
    @Singleton
    fun provideWeatherApi(retrofit: Retrofit): WeatherApi {
        return retrofit.create(WeatherApi::class.java)
    }
}