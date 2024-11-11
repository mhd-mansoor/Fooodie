package com.example.fooodie.network

import com.example.fooodie.model.RestaurantResponse
import retrofit2.http.GET
import retrofit2.http.Query

interface RestaurantApiService {
    @GET("maps/api/place/nearbysearch/json")
    suspend fun searchRestaurants(
        @Query("location") location: String,
        @Query("radius") radius: Int,
        @Query("keyword") keyword: String,
        @Query("key") apiKey: String
    ): RestaurantResponse
}
