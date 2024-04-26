package com.example.ozinsheapp.presentation.profile

import android.app.Application
import android.content.SharedPreferences
import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.ozinsheapp.data.model.AppTheme
import com.example.ozinsheapp.data.model.Resource
import com.example.ozinsheapp.di.OzinsheApplication
import com.example.ozinsheapp.domain.entity.profile.ChangePassword
import com.example.ozinsheapp.domain.entity.profile.UserInfo
import com.example.ozinsheapp.domain.usecase.profileUseCase.ChangePasswordUseCase
import com.example.ozinsheapp.domain.usecase.profileUseCase.GetUserInfoUseCase
import com.example.ozinsheapp.domain.usecase.profileUseCase.UpdateProfileBodyUseCase
import com.example.ozinsheapp.utils.ActivityLifecycleCallbacks
import com.example.ozinsheapp.utils.setLocaleInternal
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import java.util.Locale
import javax.inject.Inject

@HiltViewModel
class ProfileViewModel @Inject constructor(
    private val sharedPreferences: SharedPreferences,
    private val getUserInfoUseCase: GetUserInfoUseCase,
    private val changePasswordUseCase: ChangePasswordUseCase,
    private val updateProfileBodyUseCase: UpdateProfileBodyUseCase,
): ViewModel() {

    fun Application.setLocale(locale: Locale) {
        setLocaleInternal(locale)
        registerActivityLifecycleCallbacks(ActivityLifecycleCallbacks(locale))
        registerComponentCallbacks(com.example.ozinsheapp.utils.ApplicationCallbacks(this, locale))
    }

    fun setLocale(locale: Locale) {
        sharedPreferences.edit().putString("locale", locale.toString()).apply()
    }
    val theme: StateFlow<AppTheme> = OzinsheApplication.prefs.theme.stateIn(
        viewModelScope,
        SharingStarted.Eagerly,
        AppTheme.AUTO,
    )

    fun changeTheme(theme: AppTheme) {
        viewModelScope.launch { OzinsheApplication.prefs.changeTheme(theme) }
    }

    private val bearerToken = sharedPreferences.getString("accessToken", null)
    private val email = sharedPreferences.getString("email", null)

    private val _userEmail = MutableStateFlow("")
    val userEmail: StateFlow<String> = _userEmail

    private val _userInfo = MutableStateFlow<Resource<UserInfo>>(Resource.Unspecified)
    val userInfo = _userInfo.asStateFlow()

    private val _changePassword = MutableStateFlow<Resource<ChangePassword>>(Resource.Unspecified)
    val changePassword = _changePassword.asStateFlow()

    private val _updateProfile = MutableStateFlow<Resource<UserInfo>>(Resource.Unspecified)
    val updateProfile = _updateProfile.asStateFlow()

    init {
        initializeUserInfo()
    }
    private fun initializeUserInfo() {
        _userEmail.value = email ?: ""
    }

    fun getUserInfo() {
        viewModelScope.launch(Dispatchers.IO) {
            _userInfo.value = Resource.Loading
            try {
                val result = getUserInfoUseCase.getUserInfo(bearerToken!!)
                if (result.isSuccessful) {
                    _userInfo.value = Resource.Success(result.body())
                    result.body()?.user?.let { saveUserInfo(it.email) }
                } else {
                    _userInfo.value = Resource.Failure(result.errorBody().toString())
                }
            } catch (e: Exception) {
                Log.d("ProfileViewModel", "getUserInfo: ${e.message.toString()}")
            }
        }
    }

    fun changePassword(password: String) {
        viewModelScope.launch(Dispatchers.IO) {
            _changePassword.value = Resource.Loading
            try {
                val result = changePasswordUseCase.changePassword(bearerToken!!,password)
                if (result.isSuccessful) {
                    _changePassword.value = Resource.Success(result.body())
                } else {
                    _changePassword.value = Resource.Failure(result.errorBody().toString())
                }
            } catch (e: Exception) {
                Log.d("ProfileViewModel", "getUserInfo: ${e.message.toString()}")
            }
        }
    }
    fun updateProfile(birthDate: String, id: Int, language: String, name: String, phoneNumber: String) {
        viewModelScope.launch(Dispatchers.IO) {
            _updateProfile.value = Resource.Loading
            try {
                val result = updateProfileBodyUseCase.updateProfile(bearerToken!!,birthDate, id, language, name, phoneNumber)
                if (result.isSuccessful) {
                    _updateProfile.value = Resource.Success(result.body())
                } else {
                    _updateProfile.value = Resource.Failure(result.errorBody().toString())
                }
            } catch (e: Exception) {
                Log.d("ProfileViewModel", "updateProfile: ${e.message.toString()}")
            }
        }
    }
    fun changeState(){
        _changePassword.value = Resource.Unspecified
    }

    private fun saveUserInfo(email: String) {
        val editor = sharedPreferences.edit()
        editor.putString("email", email)
        editor.apply()
    }


}