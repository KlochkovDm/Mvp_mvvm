package com.example.mvp_mvvm.ui

import android.app.Activity
import android.widget.Toast
import com.example.mvp_mvvm.domain.LoginUseCase

class LoginPresenter (private val loginUseCase: LoginUseCase) : LoginContract.Presenter {

    private var view: LoginContract.View? = null
    private var isSuccess: Boolean = false

    override fun onAttach(view: LoginContract.View) {
        this.view = view
    }

    override fun onLogin(login: String, password: String) {
        view?.showProgress()

        loginUseCase.login(login, password) { result ->
            view?.hideProgress()
            if (result) {
                view?.setSuccess()
                isSuccess = true
            } else {
                view?.setError()
                isSuccess = false
            }
        }
    }

    override fun onRegistration() {
        loginUseCase.registration()
        Toast.makeText((view as Activity), "Registration starts", Toast.LENGTH_LONG).show()
    }

    override fun onForgotPassword() {
        loginUseCase.forgotPassword()
        Toast.makeText((view as Activity), "Forgot Password starts", Toast.LENGTH_LONG).show()
    }
}