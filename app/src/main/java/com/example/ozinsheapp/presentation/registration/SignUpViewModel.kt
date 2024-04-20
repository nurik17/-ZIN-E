package com.example.ozinsheapp.presentation.registration

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.ozinsheapp.data.model.Resource
import com.example.ozinsheapp.domain.entity.registration.RegistrationResponse
import com.example.ozinsheapp.domain.usecase.registrationUseCase.SignUpUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import org.json.JSONObject
import javax.inject.Inject

@HiltViewModel
class SignUpViewModel @Inject constructor(
    private val signUpUseCase: SignUpUseCase
) : ViewModel() {

    private val _signUp = MutableStateFlow<Resource<RegistrationResponse>>(Resource.Unspecified)
    val signUp = _signUp.asStateFlow()

    fun signUp(email: String, password: String) {
        viewModelScope.launch(Dispatchers.IO) {
            _signUp.value = Resource.Loading
            try {
                val result = signUpUseCase.signUp(email,password)
                if(result.isSuccessful){
                    _signUp.value = Resource.Success(result.body())
                }else{
                    val errorResponse = result.errorBody()?.string()
                    val errorMessage = JSONObject(errorResponse!!).getString("message")
                    _signUp.value = Resource.Failure(errorMessage)
                    Log.d("SignUpViewModel", errorMessage)
                }
            }catch (e: Exception){
                Log.d("SignUpViewModel", e.message.toString())
            }
        }
    }

    fun changeState(){
        _signUp.value = Resource.Unspecified
    }
}