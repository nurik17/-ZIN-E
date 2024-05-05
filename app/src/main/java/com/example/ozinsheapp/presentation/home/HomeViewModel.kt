package com.example.ozinsheapp.presentation.home

import android.content.SharedPreferences
import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.ozinsheapp.data.model.Resource
import com.example.ozinsheapp.domain.entity.home.HomeMoviesItem
import com.example.ozinsheapp.domain.entity.home.MoviesMainItem
import com.example.ozinsheapp.domain.entity.home.Screenshot
import com.example.ozinsheapp.domain.entity.home.Seasons
import com.example.ozinsheapp.domain.entity.profile.AddFavouriteMovieBody
import com.example.ozinsheapp.domain.entity.userhistory.CategoryAge
import com.example.ozinsheapp.domain.entity.userhistory.Genre
import com.example.ozinsheapp.domain.entity.userhistory.Movie
import com.example.ozinsheapp.domain.usecase.favouriteUseCase.AddFavouriteUseCase
import com.example.ozinsheapp.domain.usecase.favouriteUseCase.DeleteFromFavouriteUseCase
import com.example.ozinsheapp.domain.usecase.homeUseCase.GetCategoryAgeUseCase
import com.example.ozinsheapp.domain.usecase.homeUseCase.GetGenreListUseCase
import com.example.ozinsheapp.domain.usecase.homeUseCase.GetListScreenshotUseCase
import com.example.ozinsheapp.domain.usecase.homeUseCase.GetMoviesByIdUseCase
import com.example.ozinsheapp.domain.usecase.homeUseCase.GetMoviesMainUseCase
import com.example.ozinsheapp.domain.usecase.homeUseCase.GetMoviesUseCase
import com.example.ozinsheapp.domain.usecase.homeUseCase.GetSeasonInfoUseCase
import com.example.ozinsheapp.domain.usecase.homeUseCase.GetUserHistoryUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val getUserHistoryUseCase: GetUserHistoryUseCase,
    private val getMoviesMainUseCase: GetMoviesMainUseCase,
    private val getMoviesUseCase: GetMoviesUseCase,
    private val getGenreListUseCase: GetGenreListUseCase,
    private val getCategoryAgeUseCase: GetCategoryAgeUseCase,
    private val getMoviesByIdUseCase: GetMoviesByIdUseCase,
    private val getListScreenshotUseCase: GetListScreenshotUseCase,
    private val getSeasonInfoUseCase: GetSeasonInfoUseCase,
    private val addFavouriteUseCase: AddFavouriteUseCase,
    private val deleteFromFavouriteUseCase: DeleteFromFavouriteUseCase,
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

    private val _movieById = MutableStateFlow<Resource<Movie>>(Resource.Unspecified)
    val movieById = _movieById.asStateFlow()

    private val _screenshot = MutableStateFlow<List<Screenshot>>(emptyList())
    val screenshot = _screenshot.asStateFlow()

    private val _seasonInfo = MutableStateFlow<Resource<Seasons>>(Resource.Unspecified)
    val seasonInfo = _seasonInfo.asStateFlow()

    private val _addFavourite = MutableStateFlow<Resource<AddFavouriteMovieBody>>(Resource.Unspecified)
    val addFavourite = _addFavourite.asStateFlow()

    private val _deleteFavourite = MutableStateFlow<Resource<Unit>>(Resource.Unspecified)
    val deleteFavourite = _deleteFavourite.asStateFlow()

    private val _isLoadingUserHistory = MutableStateFlow(false)
    val isLoadingUserHistory = _isLoadingUserHistory.asStateFlow()

    private val _isLoadingMoviesMain = MutableStateFlow(true)
    val isLoadingMoviesMain = _isLoadingMoviesMain.asStateFlow()

    private val _isLoadingMovies = MutableStateFlow(false)
    val isLoadingMovies = _isLoadingMovies.asStateFlow()

    private val _isLoadingGenre = MutableStateFlow(false)
    val isLoadingGenre = _isLoadingGenre.asStateFlow()

    private val _isLoadingCategoryAge = MutableStateFlow(false)
    val isLoadingCategoryAge = _isLoadingCategoryAge.asStateFlow()

    private val _isLoadingScreenshot = MutableStateFlow(false)
    val isLoadingScreenshot = _isLoadingScreenshot.asStateFlow()

    private val bearerToken = sharedPreferences.getString("accessToken", null)

    fun getUserHistory() {
        viewModelScope.launch(Dispatchers.IO) {
            _isLoadingUserHistory.value = true
            try {
                val result = bearerToken?.let { getUserHistoryUseCase.getUserHistory(it) }
                if (result != null) {
                    _userHistory.update {
                        result
                    }
                }
            } catch (e: Exception) {
                _isLoadingUserHistory.value = false
                Log.d("HomeViewModel", "exception: ${e.message.toString()}")
            }
        }
    }

    fun getMoviesMain() {
        viewModelScope.launch(Dispatchers.IO) {
            _isLoadingMoviesMain.value = true
            try {
                val result = bearerToken?.let { getMoviesMainUseCase.getMainMovies(it) }
                if (result != null) {
                    _moviesMain.update {
                        result
                    }
                }
            } catch (e: Exception) {
                _isLoadingMoviesMain.value = false
                Log.d("HomeViewModel", "exception: ${e.message.toString()}")
            }
            _isLoadingMoviesMain.value = false
        }
    }

    private val moviesMap = mutableMapOf<String, MutableStateFlow<List<HomeMoviesItem>>>()

    fun getMovies(categoryName: String): StateFlow<List<HomeMoviesItem>> {
        if (!moviesMap.containsKey(categoryName)) {
            moviesMap[categoryName] = MutableStateFlow(emptyList())
            loadMovies(categoryName)
        }
        return moviesMap.getValue(categoryName)
    }

    private fun loadMovies(categoryName: String) {
        viewModelScope.launch(Dispatchers.IO) {
            try {
                _isLoadingMovies.value = true
                val result = bearerToken?.let { getMoviesUseCase.getMovies(it) }
                if (result != null) {
                    val filteredMovies = result.filter { it.categoryName == categoryName }
                    moviesMap[categoryName]?.update {
                        filteredMovies
                    }
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
                    _genres.update {
                        result
                    }
                }
            } catch (e: Exception) {
                _isLoadingGenre.value = false
                Log.d("HomeViewModel", "exception: ${e.message.toString()}")
            }
            _isLoadingGenre.value = false
        }
    }

    fun getCategoryAges() {
        viewModelScope.launch(Dispatchers.IO) {
            _isLoadingCategoryAge.value = true
            try {
                val result = bearerToken?.let { getCategoryAgeUseCase.getCategoriesAgeList(it) }
                if (result != null) {
                    _categoryAges.update {
                        result
                    }
                }
            } catch (e: Exception) {
                _isLoadingCategoryAge.value = false
                Log.d("HomeViewModel", "exception: ${e.message.toString()}")
            }
            _isLoadingCategoryAge.value = false
        }
    }

    fun getMoviesById(id: Int) {
        viewModelScope.launch(Dispatchers.IO) {
            _movieById.value = Resource.Loading
            try {
                val result = getMoviesByIdUseCase.getMoviesById(bearerToken!!, id)
                if (result.isSuccessful) {
                    _movieById.value = Resource.Success(result.body())
                } else {
                    _movieById.value = Resource.Failure(result.errorBody().toString())
                }
            } catch (e: Exception) {
                Log.d("HomeViewModel", "getMoviesById: ${e.message.toString()}")
            }
        }
    }

    fun getScreenShot(id: Int) {
        viewModelScope.launch(Dispatchers.IO) {
            _isLoadingScreenshot.value = true
            try {
                val result = getListScreenshotUseCase.getScreenshots(bearerToken!!, id)
                _screenshot.value = result
            } catch (e: Exception) {
                _isLoadingScreenshot.value = false
                Log.d("HomeViewModel", "getScreenShot: ${e.message.toString()}")
            }
            _isLoadingScreenshot.value = false
        }
    }

    fun getSeasonInfo(id: Int) {
        viewModelScope.launch(Dispatchers.IO) {
            _seasonInfo.value = Resource.Loading
            try {
                val result = getSeasonInfoUseCase.getSeasonInfo(bearerToken!!, id)
                if (result.isSuccessful) {
                    _seasonInfo.value = Resource.Success(result.body())
                } else {
                    _seasonInfo.value = Resource.Failure(result.errorBody().toString())
                }
            } catch (e: Exception) {
                Log.d("HomeViewModel", "getSeasonInfo: ${e.message.toString()}")
            }
        }
    }
    fun addToFavourite(movieId: Int){
        viewModelScope.launch(Dispatchers.IO) {
            _addFavourite.value = Resource.Loading
            try {
                val result = addFavouriteUseCase.addToFavourite(bearerToken!!, movieId)
                if (result.isSuccessful) {
                    _addFavourite.value = Resource.Success(result.body())
                } else {
                    _addFavourite.value = Resource.Failure(result.errorBody().toString())
                }
            } catch (e: Exception) {
                Log.d("HomeViewModel", "addToFavourite: ${e.message.toString()}")
            }
        }
    }

    fun deleteFromFavourite(movieId: Int){
        viewModelScope.launch(Dispatchers.IO) {
            _deleteFavourite.value = Resource.Loading
            try {
                val result = deleteFromFavouriteUseCase.deleteFromFavourite(bearerToken!!,movieId)
                if(result.isSuccessful){
                    _deleteFavourite.value = Resource.Success(result.body())
                }
            } catch (e: Exception) {
                _deleteFavourite.value = Resource.Failure(e.message ?: "Unknown error")
            }
        }
    }
    suspend fun loadData() {
        coroutineScope {
            val userHistoryDeferred = async { getUserHistory() }
            val moviesMainDeferred = async { getMoviesMain() }
            val genresDeferred = async { getGenres() }
            val categoryAgesDeferred = async { getCategoryAges() }

            userHistoryDeferred.await()
            moviesMainDeferred.await()
            genresDeferred.await()
            categoryAgesDeferred.await()
        }
    }
}