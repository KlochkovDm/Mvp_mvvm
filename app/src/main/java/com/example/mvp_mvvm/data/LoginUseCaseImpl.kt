package com.example.mvp_mvvm.data

import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.os.Handler
import androidx.annotation.MainThread
import androidx.core.content.ContextCompat.startActivity
import com.example.mvp_mvvm.domain.LoginApi
import com.example.mvp_mvvm.domain.LoginUseCase

class LoginUseCaseImpl(
    private val api: LoginApi,
    private val context: Context
    ) : LoginUseCase {

    override fun login(
        login: String,
        password: String,
        @MainThread callback: (Boolean) -> Unit
    ) {
        Thread {
            val result = api.login(login, password)
                callback(result)
        }.start()
    }

    override fun registration(login: String, password: String) {
        Thread {
                api.registration(login, password)
                val browserIntent = Intent(
                    Intent.ACTION_VIEW,
                    Uri.parse("https://accounts.google.com/signup/v2/webcreateaccount?flowName=GlifWebSignIn&flowEntry=SignUp")
                )
                browserIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
                startActivity(context, browserIntent, Bundle())
        }.start()
    }

    override fun forgotPassword(login: String) {
        Thread {
                api.forgotPassword(login)
                val browserIntent = Intent(
                    Intent.ACTION_VIEW,
                    Uri.parse("https://accounts.google.com/signin/v2/recoveryidentifier?flowName=GlifWebSignIn&flowEntry=AccountRecovery")
                )
                browserIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
                startActivity(context, browserIntent, Bundle())
        }.start()
    }
}