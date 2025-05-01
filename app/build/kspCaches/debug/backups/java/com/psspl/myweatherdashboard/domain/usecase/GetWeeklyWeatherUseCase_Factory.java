package com.psspl.myweatherdashboard.domain.usecase;

import com.psspl.myweatherdashboard.domain.repository.WeatherRepository;
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
public final class GetWeeklyWeatherUseCase_Factory implements Factory<GetWeeklyWeatherUseCase> {
  private final Provider<WeatherRepository> repositoryProvider;

  public GetWeeklyWeatherUseCase_Factory(Provider<WeatherRepository> repositoryProvider) {
    this.repositoryProvider = repositoryProvider;
  }

  @Override
  public GetWeeklyWeatherUseCase get() {
    return newInstance(repositoryProvider.get());
  }

  public static GetWeeklyWeatherUseCase_Factory create(
      Provider<WeatherRepository> repositoryProvider) {
    return new GetWeeklyWeatherUseCase_Factory(repositoryProvider);
  }

  public static GetWeeklyWeatherUseCase newInstance(WeatherRepository repository) {
    return new GetWeeklyWeatherUseCase(repository);
  }
}
