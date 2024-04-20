package com.example.ozinsheapp.presentation.registration

import android.content.SharedPreferences
import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.ozinsheapp.data.model.Resource
import com.example.ozinsheapp.domain.entity.registration.RegistrationResponse
import com.example.ozinsheapp.domain.usecase.registrationUseCase.SingInUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor(
    private val singInUseCase: SingInUseCase,
    private val sharedPreferences: SharedPreferences
) : ViewModel() {

    private val _login = MutableStateFlow<Resource<RegistrationResponse>>(Resource.Unspecified)
    val login = _login.asStateFlow()

    fun login(email: String, password: String) {
        viewModelScope.launch(Dispatchers.IO) {
            _login.value = Resource.Loading
            Log.d("LoginViewModel", "loading")
            try {
                val result = singInUseCase.signIn(email,password)
                if(result.isSuccessful){
                    _login.value = Resource.Success(result.body())
                    result.body()?.let { saveToken(it.accessToken) }
                }else{
                    _login.value = Resource.Failure(result.errorBody().toString())
                    Log.d("LoginViewModel", "failure")
                }
            }catch (e: Exception){
                Log.d("LoginViewModel", "exception: ${e.message.toString()}")
            }
        }
    }
    fun changeState(){
        _login.value = Resource.Unspecified
    }
    private fun saveToken(accessToken: String) {
        val editor = sharedPreferences.edit()
        editor.putString("accessToken", accessToken)
        editor.apply()
    }
}