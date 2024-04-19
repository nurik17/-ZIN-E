package com.example.ozinsheapp.presentation.registration

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.ozinsheapp.data.model.Resource
import com.example.ozinsheapp.domain.entity.LoginResponse
import com.example.ozinsheapp.domain.usecase.registrationUseCase.SingInUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor(
    private val singInUseCase: SingInUseCase
) : ViewModel() {

    private val _login = MutableStateFlow<Resource<LoginResponse>>(Resource.Unspecified)
    val login = _login.asStateFlow()

    fun login(email: String, password: String) {
        viewModelScope.launch(Dispatchers.IO) {
            _login.value = Resource.Loading
            try {
                val result = singInUseCase.signIn(email,password)
                if(result.isSuccessful){
                    _login.value = Resource.Success(result.body())
                }
            }catch (e: Exception){
                Log.d("LoginViewModel", e.message.toString())
            }
        }
    }
}