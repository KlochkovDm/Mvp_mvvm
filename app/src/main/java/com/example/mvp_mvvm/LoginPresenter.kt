package com.example.mvp_mvvm

import android.app.Activity
import android.os.Handler
import android.os.Looper
import android.widget.Toast
import java.lang.Thread.sleep

class LoginPresenter : LoginContract.Presenter {

    private var view: LoginContract.View? = null
    private val uiHandler = Handler(Looper.getMainLooper())
    private var isSuccess: Boolean = false

    override fun onAttach(view: LoginContract.View) {
        this.view = view
    }

    override fun onLogin(login: String, password: String) {
        view?.showProgress()
        Thread {
            sleep(2_000)
            uiHandler.post {
                view?.hideProgress()
                if (checkCredentials(login, password)) {
                    view?.setSuccess()
                    isSuccess = true
                } else {
                    view?.setError()
                    isSuccess = false
                }
            }
        }.start()
    }

    private fun checkCredentials(login: String, password: String): Boolean {
        return login == password
    }

    override fun onRegistration() {
        Toast.makeText((view as Activity), "Registration Starts", Toast.LENGTH_SHORT).show()
    }

    override fun onForgotPassword() {
        Toast.makeText((view as Activity), "Password restore procedure starts", Toast.LENGTH_SHORT)
            .show()
    }
}