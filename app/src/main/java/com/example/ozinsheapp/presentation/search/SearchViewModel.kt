package com.example.ozinsheapp.presentation.search

import android.content.SharedPreferences
import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.ozinsheapp.data.model.Resource
import com.example.ozinsheapp.domain.entity.search.SearchList
import com.example.ozinsheapp.domain.entity.userhistory.Genre
import com.example.ozinsheapp.domain.entity.userhistory.Movie
import com.example.ozinsheapp.domain.usecase.searchUseCase.GetGenresUseCase
import com.example.ozinsheapp.domain.usecase.searchUseCase.SearchByNameUseCase
import com.example.ozinsheapp.domain.usecase.searchUseCase.SearchByQueryUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SearchViewModel @Inject constructor(
    private val searchByNameUseCase: SearchByNameUseCase,
    private val searchByQueryUseCase: SearchByQueryUseCase,
    private val getGenresUseCase: GetGenresUseCase,
    private val sharedPreferences: SharedPreferences
) : ViewModel() {

    private val _searchByName = MutableStateFlow<List<Movie>>(emptyList())
    val searchByName = _searchByName.asStateFlow()

    private val _searchByNameLoading = MutableStateFlow(false)
    val searchByNameLoading = _searchByNameLoading.asStateFlow()

    private val _searchByQuery = MutableStateFlow<Resource<SearchList>>(Resource.Unspecified)
    val searchByQuery = _searchByQuery.asStateFlow()

    private val _genres = MutableStateFlow<List<Genre>>(emptyList())
    val genres = _genres.asStateFlow()

    private val _searchByQueryLoading = MutableStateFlow(false)
    val searchByQueryLoading = _searchByQueryLoading.asStateFlow()

    private val _uiState = MutableStateFlow(false)
    val uiState = _uiState.asStateFlow()


    private val bearerToken = sharedPreferences.getString("accessToken", null)

    fun searchByName(text: String) {
        viewModelScope.launch {
            _searchByNameLoading.value = true
            try {
                val result = searchByNameUseCase.searchByName(bearerToken!!, text)
                _searchByName.value = result
            } catch (e: Exception) {
                Log.d("SearchViewModel", "searchByName: ${e.message}")
            }
            _searchByNameLoading.value = false
        }
    }

    fun searchByQuery(
        name: String? = null,
        genreId: Int? = null,
        year: Int? = null,
        categoryAgeId: Int? = null,
        categoryId: Int? = null
    ) {
        viewModelScope.launch {
            _searchByQuery.value = Resource.Loading
            try {
                val result = searchByQueryUseCase.searchByQuery(bearerToken!!,name,genreId,year,categoryAgeId, categoryId)
                if(result.isSuccessful){
                    val searchList = result.body()
                    _searchByQuery.value = Resource.Success(searchList)
                }else{
                    _searchByQuery.value = Resource.Failure(result.message())
                }
                Log.d("SearchViewModel", "searchByQuery:$result")
            } catch (e: Exception) {
                Log.d("SearchViewModel", "searchByName: ${e.message}")
            }
            _searchByQueryLoading.value = false
        }
    }

    fun getGenres() {
        viewModelScope.launch {
            _searchByQueryLoading.value = true
            try {
                val genres = getGenresUseCase.getGenres(bearerToken!!)
                _genres.value = genres
            } catch (e: Exception) {
                Log.d("SearchViewModel", "searchByName: ${e.message}")
            }
            _searchByQueryLoading.value = false
        }
    }

    fun changedUiState() {
        viewModelScope.launch {
            _uiState.update { currentState ->
                !currentState
            }
        }
    }
}