package com.example.mvp_mvvm.ui

import androidx.annotation.MainThread

class LoginContract {
    interface View {
        @MainThread
        fun setSuccess()

        @MainThread
        fun setError()

        @MainThread
        fun showProgress()

        @MainThread
        fun hideProgress()
    }

    interface Presenter {
        @MainThread
        fun onAttach(view: View)

        @MainThread
        fun onLogin(login: String, password: String)

        @MainThread
        fun onRegistration(login: String, password: String)

        @MainThread
        fun onForgotPassword(login: String)
    }
}