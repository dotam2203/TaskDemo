package com.task
data class DataModel(
    var adult: Boolean = false,
    var backdrop_path: String = "",
    var budget: Double = 0.0,
    var homepage: String = "",
    var id: Int = 0,
    var imdb_id: String = "",
    var original_language: String = "",
    var original_title: String = "",
    var overview: String = "",
    var popularity: Double = 0.0,
    var poster_path: String = "",
    var release_date: String = "",
    var revenue: String = "",
    var runtime: Double = 0.0,
    var status: String = "",
    var tagline: String = "",
    var title: String = "",
    var video: Boolean = false,
    var vote_average: Double = 0.0,
    var vote_count: Double = 0.0,
    var genres: ArrayList<Genres>? = null,
    var productionCompany: ArrayList<ProductionCompanies> ?= null,
    var productionCountry: ArrayList<ProductionCountries> ?= null,
    var spokenLanguage: ArrayList<SpokenLanguages> ?= null
)
data class Genres(
        var id: Int = 0,
        var name: String = ""
    )
    data class ProductionCompanies(
        var id: Int = 0,
        var logo_path: String = "",
        var name: String = "",
        var origin_country: String = ""
    )
    data class ProductionCountries(
        var iso_3166_1: String = "",
        var name: String = ""
    )
    data class SpokenLanguages(
        var english_name: String = "",
        var iso_639_1: String = "",
        var name: String = ""
    )
