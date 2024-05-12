package com.example.ozinsheapp.presentation.profile

import android.app.LocaleManager
import android.content.Context
import android.content.SharedPreferences
import android.content.res.Configuration
import android.content.res.Resources
import android.os.Build
import android.os.LocaleList
import android.util.Log
import androidx.appcompat.app.AppCompatDelegate
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.core.os.LocaleListCompat
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

private const val SWITCH_STATE_KEY = "switch_state"

@HiltViewModel
class ProfileViewModel @Inject constructor(
    private val sharedPreferences: SharedPreferences,
    private val getUserInfoUseCase: GetUserInfoUseCase,
    private val changePasswordUseCase: ChangePasswordUseCase,
    private val updateProfileBodyUseCase: UpdateProfileBodyUseCase,
): ViewModel() {

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
        getUserInfo()
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

    private fun saveUserInfo(email: String) {
        val editor = sharedPreferences.edit()
        editor.putString("email", email)
        editor.apply()
    }

    fun changeLocales(context: Context, localeString: String) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            context.getSystemService(LocaleManager::class.java)
                .applicationLocales = LocaleList.forLanguageTags(localeString)
        } else {
            AppCompatDelegate.setApplicationLocales(LocaleListCompat.create(Locale.forLanguageTag(localeString)))
        }
    }


    fun saveSwitchState(isChecked: Boolean) {
        val editor= sharedPreferences.edit()
        editor.putBoolean(SWITCH_STATE_KEY, isChecked)
        editor.apply()
    }

    fun restoreSwitchState(): MutableState<Boolean> {
        val isChecked = sharedPreferences.getBoolean(SWITCH_STATE_KEY, false) // По умолчанию unchecked
        return mutableStateOf(isChecked)
    }

    fun setLocale(context: Context, language: String) {
        val locale = Locale(language)
        Locale.setDefault(locale)
        val resources: Resources = context.resources
        val configuration: Configuration = resources.configuration
        configuration.setLocale(locale)
        resources.updateConfiguration(configuration, resources.displayMetrics)
    }
}