package com.example.mvp_mvvm.ui

import android.app.Activity
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.View
import android.view.inputmethod.InputMethodManager
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import com.example.mvp_mvvm.R
import com.example.mvp_mvvm.app
import com.example.mvp_mvvm.data.LoginUseCaseImpl
import com.example.mvp_mvvm.databinding.ActivityMainBinding
import com.example.mvp_mvvm.domain.LoginUseCase

class MainActivity : AppCompatActivity(), LoginContract.View {

    private lateinit var binding: ActivityMainBinding
    private var presenter: LoginContract.Presenter? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        presenter = restorePresenter()
        presenter?.onAttach(this)

        binding.loginButton.setOnClickListener {
            presenter?.onLogin(
                binding.inputLoginText.text.toString(),
                binding.inputPasswordText.text.toString()
            )
        }

        binding.forgotButton.setOnClickListener {
            presenter?.onForgotPassword()
        }

        binding.registrationButton.setOnClickListener {
            presenter?.onRegistration()
        }
    }

    private fun restorePresenter(): LoginPresenter {
        val presenter = lastCustomNonConfigurationInstance as? LoginPresenter
        return presenter ?: LoginPresenter(app.loginUseCase)
    }

    override fun onRetainCustomNonConfigurationInstance(): Any? {
        return presenter
    }

    override fun setSuccess() {
        AlertDialog.Builder(this)
            .setTitle(getString(R.string.alert_title_authorization_status))
            .setMessage(getString(R.string.success_alert_message))
            .setPositiveButton(
                getString(R.string.alert_positive_button_text)
            ) { dialog, _ -> dialog.dismiss() }
            .show()
    }

    override fun setError() {
        AlertDialog.Builder(this)
            .setTitle(getString(R.string.alert_title_authorization_status))
            .setMessage("Неверный логин / пароль")
            .setPositiveButton(
                getString(R.string.alert_positive_button_text)
            ) { dialog, _ -> dialog.dismiss() }
            .setNeutralButton(
                "Создать аккаунт"
            ) { dialog, _ -> dialog.dismiss(); presenter?.onRegistration() }
            .setNegativeButton(
                "Сообщить о проблеме"
            ) { dialog, _ -> dialog.dismiss(); presenter?.onForgotPassword() }
            .show()
    }

    override fun showProgress() {
        binding.loadingLayout.root.visibility = View.VISIBLE
        binding.loginButton.isEnabled = false
        binding.inputLoginLayout.isEnabled = false
        binding.inputPasswordLayout.isEnabled = false
        binding.forgotButton.isEnabled = false
        binding.registrationButton.isEnabled = false
        binding.fab.isEnabled = false
        hideKeyboard(this)
    }

    override fun hideProgress() {
        binding.loadingLayout.root.visibility = View.GONE
        binding.loginButton.isEnabled = true
        binding.inputLoginLayout.isEnabled = true
        binding.inputPasswordLayout.isEnabled = true
        binding.forgotButton.isEnabled = true
        binding.registrationButton.isEnabled = true
        binding.fab.isEnabled = true
    }

    private fun hideKeyboard(activity: Activity) {
        val imm: InputMethodManager =
            activity.getSystemService(INPUT_METHOD_SERVICE) as InputMethodManager
        var view: View? = activity.currentFocus
        if (view == null) {
            view = View(activity)
        }
        imm.hideSoftInputFromWindow(view.windowToken, 0)
    }
}