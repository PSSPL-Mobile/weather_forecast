package com.psspl.myweatherdashboard.presentation.ui

import android.Manifest
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExposedDropdownMenuBox
import androidx.compose.material3.ExposedDropdownMenuDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Tab
import androidx.compose.material3.TabRow
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.airbnb.lottie.compose.LottieAnimation
import com.airbnb.lottie.compose.LottieCompositionSpec
import com.airbnb.lottie.compose.LottieConstants
import com.airbnb.lottie.compose.animateLottieCompositionAsState
import com.airbnb.lottie.compose.rememberLottieComposition
import com.google.accompanist.permissions.ExperimentalPermissionsApi
import com.google.accompanist.permissions.isGranted
import com.google.accompanist.permissions.rememberPermissionState
import com.psspl.myweatherdashboard.R
import com.psspl.myweatherdashboard.domain.model.City
import com.psspl.myweatherdashboard.domain.model.HourlyWeather
import com.psspl.myweatherdashboard.domain.model.Weather
import com.psspl.myweatherdashboard.presentation.viewmodel.WeatherUiState
import com.psspl.myweatherdashboard.presentation.viewmodel.WeatherViewModel
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

/***
 * Name : WeatherScreen.kt
 * Author : Prakash Software Pvt Ltd
 * Date : 28 Apr 2025
 * Desc : Weather screen is used for show UI related information.
 **/
@Composable
@OptIn(ExperimentalPermissionsApi::class)
fun WeatherScreen(viewModel: WeatherViewModel = hiltViewModel()) {

    // Handle location permission
    val locationPermissionState = rememberPermissionState(
        permission = Manifest.permission.ACCESS_FINE_LOCATION
    )

    LaunchedEffect(locationPermissionState.status) {
        if (!locationPermissionState.status.isGranted) {
            locationPermissionState.launchPermissionRequest()
        }
        viewModel.onPermissionResult(locationPermissionState.status.isGranted)
    }

    val uiState by viewModel.uiState
    val weather = uiState.weather
    val selectedCity by viewModel.selectedCity
    val cities = viewModel.indianCities // Access the cities list

    WeatherContent(
        uiState = uiState,
        weather = weather,
        onTabSelected = { viewModel.selectTab(it) },
        selectedCity = selectedCity,
        cities = cities,
        onCitySelected = { viewModel.selectCity(it) }
    )
}

/***
 * @function WeatherContent()
 * Used to show weather content.
 * @param uiState : Used to change in ui state.
 * @param weather : Used to show weather info.
 * @param onTabSelected : Trigger when tab changed.
 * @param selectedCity : Stores selected city.
 * @param cities : Stores cities list.
 * @param onCitySelected : Trigger when city.
 */
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun WeatherContent(
    uiState: WeatherUiState,
    weather: Weather?,
    onTabSelected: (Int) -> Unit,
    selectedCity: City?,
    cities: List<City>,
    onCitySelected: (City) -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background)
            .padding(top = 16.dp, bottom = 16.dp)
    ) {
        if (uiState.isLoading) {
            Row(
                modifier = Modifier
                    .fillMaxSize()
                    .background(MaterialTheme.colorScheme.background),
                horizontalArrangement = Arrangement.Center,
                verticalAlignment = Alignment.CenterVertically
            ) {
                CircularProgressIndicator()
            }
        } else if (uiState.error != null) {
            Text(
                text = uiState.error,
                color = MaterialTheme.colorScheme.error,
                modifier = Modifier.align(Alignment.CenterHorizontally)
            )
        } else if (weather != null) {
            // Current weather
            CityDropdown(
                cities = cities,
                selectedCity = selectedCity,
                onCitySelected = onCitySelected,
            )
            CurrentWeatherSection(weather)

            // Tab for Hourly forecast only
            TabRow(selectedTabIndex = uiState.selectedTab) {
                Tab(
                    selected = uiState.selectedTab == 0,
                    onClick = { onTabSelected(0) },
                    text = { Text("Hourly") }
                )
            }

            // Forecast content
            when (uiState.selectedTab) {
                0 -> HourlyForecast(weather.hourly)
            }
        }
    }
}

/***
 * @function CityDropdown()
 * Used to show city drop down.
 * @param selectedCity : Stores selected city.
 * @param cities : Stores cities list.
 * @param onCitySelected : Trigger when city.
 */
@ExperimentalMaterial3Api
@Composable
fun CityDropdown(
    cities: List<City>,
    selectedCity: City?,
    onCitySelected: (City) -> Unit
) {
    var expanded by remember { mutableStateOf(false) }
    val selectedCityName =
        selectedCity?.let { "${it.city}, ${it.state}, ${it.country}" } ?: "Select City"

    ExposedDropdownMenuBox(
        modifier = Modifier.padding(top = 10.dp, end = 10.dp, start = 10.dp),
        expanded = expanded,
        onExpandedChange = { expanded = !expanded }
    ) {
        OutlinedTextField(
            value = selectedCityName,
            onValueChange = {},
            readOnly = true,
            label = { Text("Select City") },
            trailingIcon = { ExposedDropdownMenuDefaults.TrailingIcon(expanded = expanded) },
            modifier = Modifier
                .fillMaxWidth()
                .menuAnchor()
        )
        DropdownMenu(
            expanded = expanded,
            onDismissRequest = { expanded = false }
        ) {
            cities.forEach { city ->
                DropdownMenuItem(
                    text = { Text("${city.city}, ${city.state}, ${city.country}") },
                    onClick = {
                        onCitySelected(city)
                        expanded = false
                    }
                )
            }
        }
    }
}

/***
 * @function CurrentWeatherSection()
 * Used to show UI which shows current weather info.
 * @param weather : Stores info which I need to show in UI.
 */
@Composable
fun CurrentWeatherSection(weather: Weather) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        // Display location name if available, otherwise fall back to lat/long
        Text(
            text = weather.locationName ?: "Lat: ${weather.latitude}, Lon: ${weather.longitude}",
            fontSize = 16.sp,
            modifier = Modifier.padding(top = 8.dp)
        )
        Text(
            text = "${weather.current.temperature.toInt()}°C",
            fontSize = 48.sp,
            fontWeight = FontWeight.Bold
        )
        WeatherAnimation(weather.current.condition.icon)
        Text(
            text = weather.current.condition.main,
            fontSize = 24.sp,
            modifier = Modifier.padding(top = 8.dp)
        )

        // Display pollution data if available
        weather.pollution?.let { pollution ->
            Column(
                modifier = Modifier.padding(top = 16.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(
                    text = "Air Quality: ${pollution.getAirQualityName()}",
                    fontSize = 18.sp,
                    fontWeight = FontWeight.Bold
                )
                Text(
                    text = "PM2.5: ${pollution.pm25} µg/m³",
                    fontSize = 16.sp,
                    modifier = Modifier.padding(top = 4.dp)
                )
                Text(
                    text = "PM10: ${pollution.pm10} µg/m³",
                    fontSize = 16.sp,
                    modifier = Modifier.padding(top = 4.dp)
                )
            }
        }
    }
}

/***
 * @function WeatherAnimation()
 * Used to show weather animation in UI by use of lottie animation.
 * @param icon : Stores icon info in json (Lottie animation).
 */
@Composable
fun WeatherAnimation(icon: String) {
    // Map OpenWeatherMap icon to Lottie animation resource
    val lottieRes = when (icon) {
        "01d", "01n" -> R.raw.sunny
        "02d", "02n", "03d", "03n", "04d", "04n" -> R.raw.cloudy
        "09d", "09n", "10d", "10n", "11d", "11n" -> R.raw.rain
        else -> R.raw.sunny
    }

    val composition by rememberLottieComposition(LottieCompositionSpec.RawRes(lottieRes))
    val progress by animateLottieCompositionAsState(
        composition,
        iterations = LottieConstants.IterateForever
    )

    LottieAnimation(
        composition = composition,
        progress = { progress },
        modifier = Modifier.size(100.dp)
    )
}

/***
 * @function HourlyForecast()
 * Used to show weather animation in UI by use of lottie animation.
 * @param hourly : Stores hourly weather info.
 */
@Composable
fun HourlyForecast(hourly: List<HourlyWeather>) {
    LazyRow(
        modifier = Modifier
            .fillMaxWidth()
            .padding(top = 5.dp)
            .height(150.dp)
    ) {
        items(hourly) { weather ->
            HourlyWeatherItem(weather)
        }
    }
}

/***
 * @function HourlyWeatherItem()
 * Used to show hourly weather info data.
 * @param weather : Stores hourly weather info.
 */
@Composable
fun HourlyWeatherItem(weather: HourlyWeather) {
    Column(
        modifier = Modifier
            .width(80.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        Text(
            text = SimpleDateFormat("HH:mm", Locale.getDefault()).format(Date(weather.time * 1000)),
            fontSize = 14.sp,
            modifier = Modifier.weight(1f)
        )
        WeatherAnimation(weather.condition.icon)
        Text(
            text = "${weather.temperature.toInt()}°C",
            fontSize = 14.sp,
            modifier = Modifier.weight(1f)
        )
    }
}