package com.example.mvp_mvvm.ui

import com.example.mvp_mvvm.domain.LoginUseCase
import com.example.mvp_mvvm.utils.Publisher

class LoginViewModel(private val loginUseCase: LoginUseCase) : LoginContract.ViewModel {
    override val shouldShowProgress: Publisher<Boolean> = Publisher()
    override val isSuccess: Publisher<Boolean> = Publisher()

    override fun onLogin(login: String, password: String) {
        shouldShowProgress.post(true)

        loginUseCase.login(login, password) { result ->
            shouldShowProgress.post(false)
            if (result) {
                isSuccess.post(true)
            } else {
                isSuccess.post(false)
            }
        }
    }

    override fun onRegistration(login: String, password: String) {
        shouldShowProgress.post(true)
        loginUseCase.registration(login, password)
        shouldShowProgress.post(false)
    }

    override fun onForgotPassword(login: String) {
        shouldShowProgress.post(true)
        loginUseCase.forgotPassword(login)
        shouldShowProgress.post(false)
    }
}