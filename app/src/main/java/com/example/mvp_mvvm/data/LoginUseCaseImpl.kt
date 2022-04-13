package com.example.mvp_mvvm.data

import android.os.Handler
import android.widget.Toast
import androidx.annotation.MainThread
import com.example.mvp_mvvm.domain.LoginApi
import com.example.mvp_mvvm.domain.LoginUseCase

class LoginUseCaseImpl(
    private val api: LoginApi,
    private val uiHandler: Handler
    ) : LoginUseCase {
    override fun login(
        login: String,
        password: String,
        @MainThread callback: (Boolean) -> Unit
    ) {
        Thread {
            val result = api.login(login, password)
            uiHandler.post {
                callback(result)
            }
        }.start()
    }

    override fun registration() {

    }

    override fun forgotPassword() {

    }

}