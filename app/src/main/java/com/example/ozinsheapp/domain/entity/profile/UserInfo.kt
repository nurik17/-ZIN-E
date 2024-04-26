package com.example.ozinsheapp.domain.entity.profile

import androidx.annotation.Keep

@Keep
data class UserInfo(
    val birthDate: Any,
    val id: Int,
    val language: String,
    val name: Any,
    val phoneNumber: Any,
    val user: User
)