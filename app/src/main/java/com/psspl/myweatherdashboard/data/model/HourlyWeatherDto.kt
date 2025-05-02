import com.psspl.myweatherdashboard.data.model.CloudsDto
import com.psspl.myweatherdashboard.data.model.CoordDto
import com.psspl.myweatherdashboard.data.model.MainDto
import com.psspl.myweatherdashboard.data.model.WeatherConditionDto
import com.psspl.myweatherdashboard.data.model.WindDto
import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

/***
 * Name : HourlyWeatherDto.kt
 * Author : Prakash Software Pvt Ltd
 * Date : 28 Apr 2025
 * Desc : Data class for OpenWeatherMap hourly weather API response.
 * Maps JSON response to Kotlin objects.
 */
@JsonClass(generateAdapter = true)
data class HourlyWeatherDto(
    @Json(name = "cod") val code: String,
    @Json(name = "message") val message: Int,
    @Json(name = "cnt") val count: Int,
    @Json(name = "list") val list: List<HourlyForecastEntryDto>,
    @Json(name = "city") val city: CityDto
)

/***
 * Name : HourlyForecastEntryDto.kt
 * Author : Prakash Software Pvt Ltd
 * Date : 28 Apr 2025
 * Desc : Data class for an hourly forecast entry in the OpenWeatherMap API response.
 * Represents a single forecast data point.
 */
@JsonClass(generateAdapter = true)
data class HourlyForecastEntryDto(
    @Json(name = "dt") val timestamp: Long,
    @Json(name = "main") val main: MainDto,
    @Json(name = "weather") val weather: List<WeatherConditionDto>,
    @Json(name = "clouds") val clouds: CloudsDto,
    @Json(name = "wind") val wind: WindDto,
    @Json(name = "visibility") val visibility: Int,
    @Json(name = "pop") val pop: Float,
    @Json(name = "sys") val sys: SysDto,
    @Json(name = "dt_txt") val dateTimeText: String
)

/***
 * Name : CityDto.kt
 * Author : Prakash Software Pvt Ltd
 * Date : 28 Apr 2025
 * Desc : Data class for city details in the OpenWeatherMap API response.
 * Contains geographical and administrative info.
 */
@JsonClass(generateAdapter = true)
data class CityDto(
    @Json(name = "id") val id: Int,
    @Json(name = "name") val name: String,
    @Json(name = "coord") val coord: CoordDto,
    @Json(name = "country") val country: String,
    @Json(name = "population") val population: Int,
    @Json(name = "timezone") val timezone: Int,
    @Json(name = "sunrise") val sunrise: Long,
    @Json(name = "sunset") val sunset: Long
)

/***
 * Name : SysDto.kt
 * Author : Prakash Software Pvt Ltd
 * Date : 28 Apr 2025
 * Desc : Data class for system data in the OpenWeatherMap API response.
 * Represents part-of-day information.
 */
@JsonClass(generateAdapter = true)
data class SysDto(
    @Json(name = "pod") val partOfDay: String
)