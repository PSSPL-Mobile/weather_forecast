package com.psspl.myweatherdashboard.presentation.viewmodel;

import com.psspl.myweatherdashboard.domain.usecase.GetCurrentWeatherUseCase;
import com.psspl.myweatherdashboard.domain.usecase.GetHourlyWeatherUseCase;
import com.psspl.myweatherdashboard.domain.usecase.GetLocationNameUseCase;
import com.psspl.myweatherdashboard.domain.usecase.GetPollutionDataUseCase;
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
public final class WeatherViewModel_Factory implements Factory<WeatherViewModel> {
  private final Provider<GetCurrentWeatherUseCase> getCurrentWeatherUseCaseProvider;

  private final Provider<GetHourlyWeatherUseCase> getHourlyWeatherUseCaseProvider;

  private final Provider<GetPollutionDataUseCase> getWeeklyWeatherUseCaseProvider;

  private final Provider<GetLocationNameUseCase> getLocationNameUseCaseProvider;

  public WeatherViewModel_Factory(
      Provider<GetCurrentWeatherUseCase> getCurrentWeatherUseCaseProvider,
      Provider<GetHourlyWeatherUseCase> getHourlyWeatherUseCaseProvider,
      Provider<GetPollutionDataUseCase> getWeeklyWeatherUseCaseProvider,
      Provider<GetLocationNameUseCase> getLocationNameUseCaseProvider) {
    this.getCurrentWeatherUseCaseProvider = getCurrentWeatherUseCaseProvider;
    this.getHourlyWeatherUseCaseProvider = getHourlyWeatherUseCaseProvider;
    this.getWeeklyWeatherUseCaseProvider = getWeeklyWeatherUseCaseProvider;
    this.getLocationNameUseCaseProvider = getLocationNameUseCaseProvider;
  }

  @Override
  public WeatherViewModel get() {
    return newInstance(getCurrentWeatherUseCaseProvider.get(), getHourlyWeatherUseCaseProvider.get(), getWeeklyWeatherUseCaseProvider.get(), getLocationNameUseCaseProvider.get());
  }

  public static WeatherViewModel_Factory create(
      Provider<GetCurrentWeatherUseCase> getCurrentWeatherUseCaseProvider,
      Provider<GetHourlyWeatherUseCase> getHourlyWeatherUseCaseProvider,
      Provider<GetPollutionDataUseCase> getWeeklyWeatherUseCaseProvider,
      Provider<GetLocationNameUseCase> getLocationNameUseCaseProvider) {
    return new WeatherViewModel_Factory(getCurrentWeatherUseCaseProvider, getHourlyWeatherUseCaseProvider, getWeeklyWeatherUseCaseProvider, getLocationNameUseCaseProvider);
  }

  public static WeatherViewModel newInstance(GetCurrentWeatherUseCase getCurrentWeatherUseCase,
                                             GetHourlyWeatherUseCase getHourlyWeatherUseCase,
                                             GetPollutionDataUseCase getPollutionDataUseCase,
                                             GetLocationNameUseCase getLocationNameUseCase) {
    return new WeatherViewModel(getCurrentWeatherUseCase, getHourlyWeatherUseCase, getPollutionDataUseCase, getLocationNameUseCase);
  }
}
