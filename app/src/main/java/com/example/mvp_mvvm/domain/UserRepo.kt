package com.example.mvp_mvvm.domain

import com.example.mvp_mvvm.domain.entities.UserProfile

interface UserRepo {

    fun getAllUsers(): List<UserProfile>
    fun addUser(user: UserProfile)
    fun delete (login: String)
    fun deleteAll(): List<UserProfile>
    fun updateUser(login: String, user: UserProfile)
}