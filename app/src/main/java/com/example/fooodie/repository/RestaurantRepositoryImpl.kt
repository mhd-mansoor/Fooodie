package com.example.fooodie.repository

import com.example.fooodie.model.RestaurantResponse
import com.example.fooodie.network.RestaurantApiService

class RestaurantRepositoryImpl(private val apiService: RestaurantApiService) : RestaurantRepository {
    override suspend fun getRestaurantNearby(keyword: String): RestaurantResponse {
        val response = apiService.searchRestaurants(
            location = "40.748817,-73.985428", /*Office location*/
            radius = 50000,
            keyword = keyword,
            apiKey = "AIzaSyC-Aq4dp_giCAd-vCIlPjaPZB6HSQYOlwk" /*API KEY (not working in BuildConfig.GOOGLE_PLACES_API_KEY , so as of now calling like this*/
        )
        return response
    }
}

