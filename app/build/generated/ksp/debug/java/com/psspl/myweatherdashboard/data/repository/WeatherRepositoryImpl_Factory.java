package com.psspl.myweatherdashboard.data.repository;

import com.psspl.myweatherdashboard.data.location.LocationTracker;
import com.psspl.myweatherdashboard.data.network.WeatherApi;
import dagger.internal.DaggerGenerated;
import dagger.internal.Factory;
import dagger.internal.Provider;
import dagger.internal.QualifierMetadata;
import dagger.internal.ScopeMetadata;
import javax.annotation.processing.Generated;

@ScopeMetadata
@QualifierMetadata
@DaggerGenerated
@Generated(
    value = "dagger.internal.codegen.ComponentProcessor",
    comments = "https://dagger.dev"
)
@SuppressWarnings({
    "unchecked",
    "rawtypes",
    "KotlinInternal",
    "KotlinInternalInJava",
    "cast",
    "deprecation",
    "nullness:initialization.field.uninitialized"
})
public final class WeatherRepositoryImpl_Factory implements Factory<WeatherRepositoryImpl> {
  private final Provider<WeatherApi> weatherApiProvider;

  private final Provider<LocationTracker> locationTrackerProvider;

  public WeatherRepositoryImpl_Factory(Provider<WeatherApi> weatherApiProvider,
      Provider<LocationTracker> locationTrackerProvider) {
    this.weatherApiProvider = weatherApiProvider;
    this.locationTrackerProvider = locationTrackerProvider;
  }

  @Override
  public WeatherRepositoryImpl get() {
    return newInstance(weatherApiProvider.get(), locationTrackerProvider.get());
  }

  public static WeatherRepositoryImpl_Factory create(Provider<WeatherApi> weatherApiProvider,
      Provider<LocationTracker> locationTrackerProvider) {
    return new WeatherRepositoryImpl_Factory(weatherApiProvider, locationTrackerProvider);
  }

  public static WeatherRepositoryImpl newInstance(WeatherApi weatherApi,
      LocationTracker locationTracker) {
    return new WeatherRepositoryImpl(weatherApi, locationTracker);
  }
}
