package com.example.mvp_mvvm

import android.app.Application
import android.content.Context
import android.os.Handler
import android.os.Looper
import com.example.mvp_mvvm.data.LoginUseCaseImpl
import com.example.mvp_mvvm.data.MockLoginApiImpl
import com.example.mvp_mvvm.domain.LoginApi
import com.example.mvp_mvvm.domain.LoginUseCase

class App : Application() {
    private val loginApi: LoginApi by lazy {MockLoginApiImpl()}
    val loginUseCase : LoginUseCase by lazy {
        LoginUseCaseImpl(app.loginApi, applicationContext)
    }
}

val Context.app : App
    get() {
        return applicationContext as App
    }