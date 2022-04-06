package com.example.mvp_mvvm

class LoginContract {
    interface View {
        fun setSuccess()
        fun setError()
        fun showProgress()
        fun hideProgress()
    }

    interface Presenter {
        fun onAttach(view: View)
        fun onLogin(login: String, password: String)
        fun onRegistration()
        fun onForgotPassword()
    }
}