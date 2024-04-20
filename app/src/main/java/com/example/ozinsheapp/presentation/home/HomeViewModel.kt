package com.example.ozinsheapp.presentation.home

import android.content.SharedPreferences
import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.ozinsheapp.domain.entity.home.HomeMoviesItem
import com.example.ozinsheapp.domain.entity.home.MoviesMainItem
import com.example.ozinsheapp.domain.entity.userhistory.CategoryAge
import com.example.ozinsheapp.domain.entity.userhistory.Genre
import com.example.ozinsheapp.domain.entity.userhistory.Movie
import com.example.ozinsheapp.domain.usecase.homeUseCase.GetCategoryAgeUseCase
import com.example.ozinsheapp.domain.usecase.homeUseCase.GetGenreListUseCase
import com.example.ozinsheapp.domain.usecase.homeUseCase.GetMoviesMainUseCase
import com.example.ozinsheapp.domain.usecase.homeUseCase.GetMoviesUseCase
import com.example.ozinsheapp.domain.usecase.homeUseCase.GetUserHistoryUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val getUserHistoryUseCase: GetUserHistoryUseCase,
    private val getMoviesMainUseCase: GetMoviesMainUseCase,
    private val getMoviesUseCase: GetMoviesUseCase,
    private val getGenreListUseCase: GetGenreListUseCase,
    private val getCategoryAgeUseCase: GetCategoryAgeUseCase,
    private val sharedPreferences: SharedPreferences
) : ViewModel() {

    private val _userHistory = MutableStateFlow<List<Movie>>(emptyList())
    val userHistory = _userHistory.asStateFlow()

    private val _moviesMain = MutableStateFlow<List<MoviesMainItem>>(emptyList())
    val moviesMain = _moviesMain.asStateFlow()

    private val _movies = MutableStateFlow<List<HomeMoviesItem>>(emptyList())
    val movies = _movies.asStateFlow()

    private val _genres = MutableStateFlow<List<Genre>>(emptyList())
    val genres = _genres.asStateFlow()

    private val _categoryAges = MutableStateFlow<List<CategoryAge>>(emptyList())
    val categoryAges = _categoryAges.asStateFlow()

    private val _isLoadingUserHistory = MutableStateFlow(false)
    val isLoadingUserHistory = _isLoadingUserHistory.asStateFlow()

    private val _isLoadingMoviesMain = MutableStateFlow(false)
    val isLoadingMoviesMain = _isLoadingMoviesMain.asStateFlow()

    private val _isLoadingMovies = MutableStateFlow(false)
    val isLoadingMovies = _isLoadingMovies.asStateFlow()

    private val _isLoadingGenre = MutableStateFlow(false)
    val isLoadingGenre = _isLoadingGenre.asStateFlow()

    private val _isLoadingCategoryAge = MutableStateFlow(false)
    val isLoadingCategoryAge = _isLoadingCategoryAge.asStateFlow()

    private val bearerToken = sharedPreferences.getString("accessToken", null)

    fun getUserHistory() {
        viewModelScope.launch(Dispatchers.IO) {
            _isLoadingUserHistory.value = true
            try {
                val result = bearerToken?.let { getUserHistoryUseCase.getUserHistory(it) }
                if (result != null) {
                    _userHistory.value = result
                }
                Log.d("HomeViewModel", "success")
            } catch (e: Exception) {
                _isLoadingUserHistory.value = false
                Log.d("HomeViewModel", "exception: ${e.message.toString()}")
            }
            _isLoadingUserHistory.value = true
        }
    }

    fun getMoviesMain() {
        viewModelScope.launch(Dispatchers.IO) {
            _isLoadingMoviesMain.value = true
            try {
                val result = bearerToken?.let { getMoviesMainUseCase.getMainMovies(it) }
                if (result != null) {
                    _moviesMain.value = result
                }
            } catch (e: Exception) {
                _isLoadingMoviesMain.value = false
                Log.d("HomeViewModel", "exception: ${e.message.toString()}")
            }
            _isLoadingMoviesMain.value = true
        }
    }

    private val moviesMap = mutableMapOf<String, MutableStateFlow<List<HomeMoviesItem>>>()

    fun getMovies(categoryName: String): StateFlow<List<HomeMoviesItem>> {
        // Check if movies for the category already exist in the map
        if (!moviesMap.containsKey(categoryName)) {
            // If not, create a new MutableStateFlow for this category
            moviesMap[categoryName] = MutableStateFlow(emptyList())
            // Load movies for the category
            loadMovies(categoryName)
        }
        // Return the StateFlow for this category
        return moviesMap.getValue(categoryName)
    }

    private fun loadMovies(categoryName: String) {
        viewModelScope.launch(Dispatchers.IO) {
            try {
                _isLoadingMovies.value = true
                val result = bearerToken?.let { getMoviesUseCase.getMovies(it) }
                if (result != null) {
                    val filteredMovies = result.filter { it.categoryName == categoryName }
                    moviesMap[categoryName]?.value = filteredMovies
                }
            } catch (e: Exception) {
                Log.d("HomeViewModel", "exception: ${e.message.toString()}")
            } finally {
                _isLoadingMovies.value = false
            }
        }
    }

    fun getGenres() {
        viewModelScope.launch(Dispatchers.IO) {
            _isLoadingGenre.value = true
            try {
                val result = bearerToken?.let { getGenreListUseCase.getGenresList(it) }
                if (result != null) {
                    _genres.value = result
                }
            } catch (e: Exception) {
                _isLoadingGenre.value = false
                Log.d("HomeViewModel", "exception: ${e.message.toString()}")
            }
            _isLoadingGenre.value = true
        }
    }
    fun getCategoryAges() {
        viewModelScope.launch(Dispatchers.IO) {
            _isLoadingCategoryAge.value = true
            try {
                val result = bearerToken?.let { getCategoryAgeUseCase.getCategoriesAgeList(it) }
                if (result != null) {
                    _categoryAges.value = result
                }
            } catch (e: Exception) {
                _isLoadingCategoryAge.value = false
                Log.d("HomeViewModel", "exception: ${e.message.toString()}")
            }
            _isLoadingCategoryAge.value = true
        }
    }
}