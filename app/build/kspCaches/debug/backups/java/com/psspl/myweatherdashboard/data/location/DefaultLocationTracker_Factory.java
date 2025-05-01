package com.psspl.myweatherdashboard.data.location;

import android.content.Context;
import com.google.android.gms.location.FusedLocationProviderClient;
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
public final class DefaultLocationTracker_Factory implements Factory<DefaultLocationTracker> {
  private final Provider<Context> contextProvider;

  private final Provider<FusedLocationProviderClient> fusedLocationClientProvider;

  public DefaultLocationTracker_Factory(Provider<Context> contextProvider,
      Provider<FusedLocationProviderClient> fusedLocationClientProvider) {
    this.contextProvider = contextProvider;
    this.fusedLocationClientProvider = fusedLocationClientProvider;
  }

  @Override
  public DefaultLocationTracker get() {
    return newInstance(contextProvider.get(), fusedLocationClientProvider.get());
  }

  public static DefaultLocationTracker_Factory create(Provider<Context> contextProvider,
      Provider<FusedLocationProviderClient> fusedLocationClientProvider) {
    return new DefaultLocationTracker_Factory(contextProvider, fusedLocationClientProvider);
  }

  public static DefaultLocationTracker newInstance(Context context,
      FusedLocationProviderClient fusedLocationClient) {
    return new DefaultLocationTracker(context, fusedLocationClient);
  }
}
