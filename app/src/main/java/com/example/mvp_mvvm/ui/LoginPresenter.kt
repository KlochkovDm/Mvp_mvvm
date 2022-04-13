package com.example.mvp_mvvm.ui

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

    override fun onRegistration(login: String, password: String) {
        view?.showProgress()
        loginUseCase.registration(login, password)
        view?.hideProgress()
    }

    override fun onForgotPassword(login: String) {
        view?.showProgress()
        loginUseCase.forgotPassword(login)
        view?.hideProgress()
    }
}