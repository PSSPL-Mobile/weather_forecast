package com.psspl.myweatherdashboard.di

import com.psspl.myweatherdashboard.data.repository.WeatherRepositoryImpl
import com.psspl.myweatherdashboard.domain.repository.WeatherRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

/***
 * Name : NetworkModule.kt
 * Author : Prakash Software Pvt Ltd
 * Date : 28 Apr 2025
 * Desc : Dagger module for providing repository bindings.
 **/
@Module
@InstallIn(SingletonComponent::class)
abstract class RepositoryModule {

    /***
     * Binds WeatherRepository implementation to its interface.
     * @param weatherRepositoryImpl Implementation of WeatherRepository.
     * @return WeatherRepository interface instance.
     */
    @Binds
    @Singleton
    abstract fun bindWeatherRepository(
        weatherRepositoryImpl: WeatherRepositoryImpl
    ): WeatherRepository
}