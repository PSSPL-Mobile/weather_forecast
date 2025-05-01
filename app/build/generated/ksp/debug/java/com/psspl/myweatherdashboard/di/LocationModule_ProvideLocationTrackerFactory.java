package com.psspl.myweatherdashboard.di;

import android.content.Context;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.psspl.myweatherdashboard.data.location.LocationTracker;
import dagger.internal.DaggerGenerated;
import dagger.internal.Factory;
import dagger.internal.Preconditions;
import dagger.internal.Provider;
import dagger.internal.QualifierMetadata;
import dagger.internal.ScopeMetadata;
import javax.annotation.processing.Generated;

@ScopeMetadata("javax.inject.Singleton")
@QualifierMetadata("dagger.hilt.android.qualifiers.ApplicationContext")
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
public final class LocationModule_ProvideLocationTrackerFactory implements Factory<LocationTracker> {
  private final Provider<Context> contextProvider;

  private final Provider<FusedLocationProviderClient> fusedLocationClientProvider;

  public LocationModule_ProvideLocationTrackerFactory(Provider<Context> contextProvider,
      Provider<FusedLocationProviderClient> fusedLocationClientProvider) {
    this.contextProvider = contextProvider;
    this.fusedLocationClientProvider = fusedLocationClientProvider;
  }

  @Override
  public LocationTracker get() {
    return provideLocationTracker(contextProvider.get(), fusedLocationClientProvider.get());
  }

  public static LocationModule_ProvideLocationTrackerFactory create(
      Provider<Context> contextProvider,
      Provider<FusedLocationProviderClient> fusedLocationClientProvider) {
    return new LocationModule_ProvideLocationTrackerFactory(contextProvider, fusedLocationClientProvider);
  }

  public static LocationTracker provideLocationTracker(Context context,
      FusedLocationProviderClient fusedLocationClient) {
    return Preconditions.checkNotNullFromProvides(LocationModule.INSTANCE.provideLocationTracker(context, fusedLocationClient));
  }
}
