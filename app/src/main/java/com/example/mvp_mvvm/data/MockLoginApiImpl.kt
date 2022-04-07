package com.example.mvp_mvvm.data

import com.example.mvp_mvvm.domain.LoginApi

class MockLoginApiImpl : LoginApi {
    override fun login(login: String, password: String): Boolean {
        Thread.sleep(2_00)
        return login == password
    }

    override fun registration(
        login: String,
        password: String,
        email: String,
        date: String
    ): Boolean {
        Thread.sleep(2_500)
        return true
    }

    override fun logout(): Boolean {
        Thread.sleep(3_000)
        return true
    }

    override fun forgotPassword(login: String): Boolean {
        Thread.sleep(1_000)
        return false
    }
}