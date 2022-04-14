package com.example.mvp_mvvm.ui

import androidx.annotation.MainThread
import com.example.mvp_mvvm.utils.Publisher

interface LoginContract {

    interface ViewModel {
        val shouldShowProgress: Publisher<Boolean>
        val isSuccess: Publisher<Boolean>

        @MainThread
        fun onLogin(login: String, password: String)

        @MainThread
        fun onRegistration(login: String, password: String)

        @MainThread
        fun onForgotPassword(login: String)
    }
}