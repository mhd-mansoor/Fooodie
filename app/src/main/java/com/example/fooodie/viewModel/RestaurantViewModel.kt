package com.example.fooodie.viewModel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.fooodie.UiError
import com.example.fooodie.model.Restaurant
import com.example.fooodie.network.RetrofitInstance
import com.example.fooodie.repository.RestaurantRepositoryImpl
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import retrofit2.HttpException
import java.io.IOException

class RestaurantViewModel : ViewModel() {

    private val _restaurant = MutableStateFlow<List<Restaurant>>(emptyList())
    val restaurant: StateFlow<List<Restaurant>> = _restaurant
    private val _errorMessage = MutableStateFlow<UiError?>(null)
    val errorMessage: StateFlow<UiError?> = _errorMessage
    private val _isLoading = MutableStateFlow(false) // Add a loading state
    val isLoading: StateFlow<Boolean> = _isLoading

    private val restaurantRepository = RestaurantRepositoryImpl(RetrofitInstance.api)

    fun searchRestaurant(keyword: String) {
        viewModelScope.launch {
            _isLoading.value = true
            try {
                val fetchedRestaurant = restaurantRepository.getRestaurantNearby(keyword)

                if (fetchedRestaurant.results.isNotEmpty()) {
                    _restaurant.value = fetchedRestaurant.results.map { it.toRestaurant() }
                    _errorMessage.value = null
                } else {
                    _errorMessage.value = UiError.NoResultsError
                }

            } catch (e: IOException) {
                _errorMessage.value = UiError.NetworkError
            } catch (e: HttpException) {
                _errorMessage.value = UiError.ServerError
            } catch (e: Exception) {
                _errorMessage.value = UiError.GenericError
            } finally {
                _isLoading.value = false
            }
        }
    }
}

