package com.example.mvp_mvvm.domain

import androidx.annotation.MainThread

interface LoginUseCase {
    fun login(login: String, password: String, @MainThread callback: (Boolean) -> Unit)
    fun registration()
    fun forgotPassword()
}