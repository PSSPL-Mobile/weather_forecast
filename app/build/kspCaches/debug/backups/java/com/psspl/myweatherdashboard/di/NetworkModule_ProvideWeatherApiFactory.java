package com.psspl.myweatherdashboard.di;

import com.psspl.myweatherdashboard.data.network.WeatherApi;
import dagger.internal.DaggerGenerated;
import dagger.internal.Factory;
import dagger.internal.Preconditions;
import dagger.internal.Provider;
import dagger.internal.QualifierMetadata;
import dagger.internal.ScopeMetadata;
import javax.annotation.processing.Generated;
import retrofit2.Retrofit;

@ScopeMetadata("javax.inject.Singleton")
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
public final class NetworkModule_ProvideWeatherApiFactory implements Factory<WeatherApi> {
  private final Provider<Retrofit> retrofitProvider;

  public NetworkModule_ProvideWeatherApiFactory(Provider<Retrofit> retrofitProvider) {
    this.retrofitProvider = retrofitProvider;
  }

  @Override
  public WeatherApi get() {
    return provideWeatherApi(retrofitProvider.get());
  }

  public static NetworkModule_ProvideWeatherApiFactory create(Provider<Retrofit> retrofitProvider) {
    return new NetworkModule_ProvideWeatherApiFactory(retrofitProvider);
  }

  public static WeatherApi provideWeatherApi(Retrofit retrofit) {
    return Preconditions.checkNotNullFromProvides(NetworkModule.INSTANCE.provideWeatherApi(retrofit));
  }
}
