package com.pess0a.androidkit.sample.data.dto

data class CountriesDto(
    val abbreviation: String,
    val capital: String,
    val currency: String,
    val id: Int,
    val media: Media,
    val name: String,
    val phone: String,
    val population: Int
)

data class Media(
    val emblem: String,
    val flag: String,
    val orthographic: String
)