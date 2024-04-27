package com.example.ozinsheapp.presentation.favourite

import android.content.SharedPreferences
import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.ozinsheapp.domain.entity.userhistory.Movie
import com.example.ozinsheapp.domain.usecase.favouriteUseCase.GetFavouriteMovieUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class FavouriteViewModel @Inject constructor(
    private val getFavouriteMovieUseCase: GetFavouriteMovieUseCase,
    sharedPreferences: SharedPreferences
): ViewModel() {

    private val _favouriteMovie = MutableStateFlow<List<Movie>>(emptyList())
    val favouriteMovie = _favouriteMovie.asStateFlow()

    private val _favouriteLoading = MutableStateFlow(false)
    val favouriteLoading = _favouriteLoading.asStateFlow()

    private val bearerToken = sharedPreferences.getString("accessToken", null)

    fun getFavouriteMovie() {
        viewModelScope.launch(Dispatchers.IO) {
            _favouriteLoading.value = true
            try {
                val result = bearerToken?.let { getFavouriteMovieUseCase.getFavouriteMovieList(it) }
                if (result != null) {
                    _favouriteMovie.value = result
                }
                Log.d("HomeViewModel", "success")
            } catch (e: Exception) {
                _favouriteLoading.value = false
                Log.d("HomeViewModel", "exception: ${e.message.toString()}")
            }
            _favouriteLoading.value = true
        }
    }

}