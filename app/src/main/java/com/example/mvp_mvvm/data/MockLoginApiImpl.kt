package com.example.mvp_mvvm.data

import com.example.mvp_mvvm.domain.LoginApi

class MockLoginApiImpl : LoginApi {
    override fun login(login: String, password: String): Boolean {
        Thread.sleep(2_000)
        return login == password
    }

    override fun registration(login: String, password: String) {
        Thread.sleep(3_000)
    }

    override fun logout(): Boolean {
        Thread.sleep(3_000)
        return true
    }

    override fun forgotPassword(login: String){
        Thread.sleep(4_000)
    }
}