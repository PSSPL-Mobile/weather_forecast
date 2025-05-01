package com.psspl.myweatherdashboard.di;

import com.squareup.moshi.Moshi;
import dagger.internal.DaggerGenerated;
import dagger.internal.Factory;
import dagger.internal.Preconditions;
import dagger.internal.Provider;
import dagger.internal.QualifierMetadata;
import dagger.internal.ScopeMetadata;
import javax.annotation.processing.Generated;
import okhttp3.OkHttpClient;
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
public final class NetworkModule_ProvideRetrofitFactory implements Factory<Retrofit> {
  private final Provider<Moshi> moshiProvider;

  private final Provider<OkHttpClient> okHttpClientProvider;

  public NetworkModule_ProvideRetrofitFactory(Provider<Moshi> moshiProvider,
      Provider<OkHttpClient> okHttpClientProvider) {
    this.moshiProvider = moshiProvider;
    this.okHttpClientProvider = okHttpClientProvider;
  }

  @Override
  public Retrofit get() {
    return provideRetrofit(moshiProvider.get(), okHttpClientProvider.get());
  }

  public static NetworkModule_ProvideRetrofitFactory create(Provider<Moshi> moshiProvider,
      Provider<OkHttpClient> okHttpClientProvider) {
    return new NetworkModule_ProvideRetrofitFactory(moshiProvider, okHttpClientProvider);
  }

  public static Retrofit provideRetrofit(Moshi moshi, OkHttpClient okHttpClient) {
    return Preconditions.checkNotNullFromProvides(NetworkModule.INSTANCE.provideRetrofit(moshi, okHttpClient));
  }
}
