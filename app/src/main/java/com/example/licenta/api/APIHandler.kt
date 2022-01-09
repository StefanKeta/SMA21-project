package com.example.licenta.api

object APIHandler {
    const val GOOGLE_MAPS_API_BASE_URL = "https://maps.googleapis.com/maps/api/place/nearbysearch/"

    val googlePlacesService: PlaceAPIService
        get() = RetrofitClient.getClient(GOOGLE_MAPS_API_BASE_URL)
            .create(PlaceAPIService::class.java)
}