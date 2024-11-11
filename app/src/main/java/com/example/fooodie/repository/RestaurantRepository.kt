package com.example.fooodie.repository

import com.example.fooodie.model.RestaurantResponse

interface RestaurantRepository {
    suspend fun getRestaurantNearby(keyword: String): RestaurantResponse
}
