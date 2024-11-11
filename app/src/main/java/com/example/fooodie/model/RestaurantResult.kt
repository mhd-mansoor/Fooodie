package com.example.fooodie.model

data class RestaurantResult(
    val name: String,
    val vicinity: String,
    val rating: Double?
) {
    fun toRestaurant() = Restaurant(name = name, address = vicinity, rating = rating ?: 0.0)
}