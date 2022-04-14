package com.example.mvp_mvvm.data

import com.example.mvp_mvvm.domain.UserRepo
import com.example.mvp_mvvm.domain.entities.UserProfile

class UserRepoImpl: UserRepo {
    override fun getAllUsers(): List<UserProfile> {
        TODO("Not yet implemented")
    }

    override fun addUser(user: UserProfile) {
        TODO("Not yet implemented")
    }

    override fun delete(login: String) {
        TODO("Not yet implemented")
    }

    override fun deleteAll(): List<UserProfile> {
        TODO("Not yet implemented")
    }

    override fun updateUser(login: String, user: UserProfile) {
        TODO("Not yet implemented")
    }
}