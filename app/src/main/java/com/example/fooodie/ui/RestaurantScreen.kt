package com.example.fooodie.ui

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.fooodie.R
import com.example.fooodie.UiError
import com.example.fooodie.model.Restaurant
import com.example.fooodie.viewModel.RestaurantViewModel
@Composable
fun RestaurantScreen(viewModel: RestaurantViewModel = viewModel()) {
    val places by viewModel.restaurant.collectAsState()
    val errorMessage = viewModel.errorMessage.collectAsState().value
    val isLoading by viewModel.isLoading.collectAsState()
    val context = LocalContext.current
    var searchTerm by remember { mutableStateOf(context.getString(R.string.pizza)) } // Default to pizza

    Column(modifier = Modifier.fillMaxSize()) {
        // Search Bar
        OutlinedTextField(
            value = searchTerm,
            onValueChange = { searchTerm = it },
            label = { Text(stringResource(R.string.search_for_pizza_or_juice)) },
            modifier = Modifier.fillMaxWidth().padding(16.dp)
        )

        // Search Button
        Button(
            onClick = { viewModel.searchRestaurant(searchTerm) },
            modifier = Modifier.padding(16.dp)
        ) {
            Text(stringResource(R.string.search))
        }

        // Show progress bar while loading
        if (isLoading) {
            CircularProgressIndicator(
                modifier = Modifier
                    .padding(16.dp)
                    .align(Alignment.CenterHorizontally)
            )
        }

        // Show error message if there is one
        errorMessage?.let {
            val errorString = when (it) {
                UiError.NetworkError -> context.getString(R.string.error_network)
                UiError.ServerError -> context.getString(R.string.error_server)
                UiError.NoResultsError -> context.getString(R.string.error_no_results)
                UiError.GenericError -> context.getString(R.string.error_generic)
            }

            Text(
                text = errorString,
                color = MaterialTheme.colorScheme.error,
                style = MaterialTheme.typography.bodyLarge,
                modifier = Modifier.padding(16.dp)
            )
        }

        // Places List
        LazyColumn(modifier = Modifier.fillMaxSize()) {
            items(places) { place ->
                RestaurantItem(place)
            }
        }
    }
}

@Composable
fun RestaurantItem(restaurant: Restaurant) {
    Card(modifier = Modifier.fillMaxWidth().padding(8.dp)) {
        Column(modifier = Modifier.padding(8.dp)) {
            Text(text = restaurant.name, style = MaterialTheme.typography.bodyLarge)
            Text(text = restaurant.address, style = MaterialTheme.typography.bodyMedium)
            Text(text = stringResource(R.string.rating, restaurant.rating), style = MaterialTheme.typography.bodyMedium)
        }
    }
}
