package com.example.mvp_mvvm.domain

import androidx.annotation.WorkerThread

interface LoginApi {
    @WorkerThread
    fun login(login: String, password: String): Boolean

    @WorkerThread
    fun registration(login: String, password: String)

    @WorkerThread
    fun logout(): Boolean

    @WorkerThread
    fun forgotPassword(login: String)
}