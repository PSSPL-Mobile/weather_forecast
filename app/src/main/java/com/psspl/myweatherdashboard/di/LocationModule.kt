package com.psspl.myweatherdashboard.di

import android.content.Context
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import com.psspl.myweatherdashboard.data.location.DefaultLocationTracker
import com.psspl.myweatherdashboard.data.location.LocationTracker
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

/***
 * Name : LocationModule.kt
 * Author : Prakash Software Pvt Ltd
 * Date : 28 Apr 2025
 * Desc : Dagger module for providing location-related dependencies.
 * */
@Module
@InstallIn(SingletonComponent::class)
object LocationModule {

    /***
     * Provides a singleton FusedLocationProviderClient instance.
     * @param context Application context for location services.
     * @return FusedLocationProviderClient instance.
     */
    @Provides
    @Singleton
    fun provideFusedLocationClient(@ApplicationContext context: Context): FusedLocationProviderClient {
        return LocationServices.getFusedLocationProviderClient(context)
    }

    /***
     * Provides a singleton LocationTracker instance.
     * @param context Application context.
     * @param fusedLocationClient FusedLocationProviderClient for location tracking.
     * @return LocationTracker implementation.
     */
    @Provides
    @Singleton
    fun provideLocationTracker(
        @ApplicationContext context: Context,
        fusedLocationClient: FusedLocationProviderClient,
    ): LocationTracker {
        return DefaultLocationTracker(context, fusedLocationClient)
    }
}