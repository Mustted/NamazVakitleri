package tr.xip.prayertimes.model

import java.io.Serializable

class Location : Serializable {
    var databaseId: Int = 0
        internal set
    var countryId: String?
    var countryName: String?
    var cityId: String?
    var cityName: String?
    var countyId: String?
    var countyName: String? = null

    var fajrNotification: Int? = 0
    var sunriseNotification: Int? = 0
    var dhuhrNotification: Int? = 0
    var asrNotification: Int? = 0
    var maghribNotification: Int? = 0
    var ishaNotification: Int? = 0

    val name: String?
        get() = countyName ?: cityName

    constructor(countryId: String?, countryName: String?, cityId: String?, cityName: String?, countyId: String?, countyName: String?) {
        this.countryId = countryId
        this.countryName = countryName
        this.cityId = cityId
        this.cityName = cityName
        this.countyId = countyId
        this.countyName = countyName
    }

    constructor(databaseId: Int?, countryId: String?, countryName: String?, cityId: String?,
                cityName: String?, countyId: String?, countyName: String?, fajrNotification: Int?,
                sunriseNotification: Int?, dhuhrNotification: Int?, asrNotification: Int?,
                maghribNotification: Int?, ishaNotification: Int?) {
        this.databaseId = databaseId ?: this.databaseId
        this.countryId = countryId
        this.countryName = countryName
        this.cityId = cityId
        this.cityName = cityName
        this.countyId = countyId
        this.countyName = countyName
        this.fajrNotification = fajrNotification
        this.sunriseNotification = sunriseNotification
        this.dhuhrNotification = dhuhrNotification
        this.asrNotification = asrNotification
        this.maghribNotification = maghribNotification
        this.ishaNotification = ishaNotification
    }
}
