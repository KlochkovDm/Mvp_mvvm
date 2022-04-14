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
import com.example.mvp_mvvm.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private var viewModel: LoginContract.ViewModel? = null
    private val handler: Handler by lazy { Handler(Looper.getMainLooper()) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        viewModel = restoreViewModel()

        binding.loginButton.setOnClickListener {
            viewModel?.onLogin(
                binding.loginEditText.text.toString(),
                binding.passwordEditText.text.toString()
            )
        }

        binding.forgotPasswordButton.setOnClickListener {
            viewModel?.onForgotPassword(
                binding.loginEditText.text.toString()
            )
        }

        binding.registrationButton.setOnClickListener {
            viewModel?.onRegistration(
                binding.loginEditText.text.toString(),
                binding.passwordEditText.text.toString()
            )
        }

        viewModel?.shouldShowProgress?.subscribe(handler) { shouldShowProgress ->
            if (shouldShowProgress == true) {
                showProgress()
            } else {
                hideProgress()
            }
        }

        viewModel?.isSuccess?.subscribe(handler) {
            if (it == true) {
                setSuccess()
            } else {
                setError()
            }
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        viewModel?.isSuccess?.unsubscribeAll()
        viewModel?.shouldShowProgress?.unsubscribeAll()
    }

    private fun restoreViewModel(): LoginViewModel {
        val viewModel = lastCustomNonConfigurationInstance as? LoginViewModel
        return viewModel ?: LoginViewModel(app.loginUseCase)
    }

    override fun onRetainCustomNonConfigurationInstance(): Any? {
        return viewModel
    }

    private fun setSuccess() {
        AlertDialog.Builder(this)
            .setTitle(getString(R.string.alert_title_authorization_status))
            .setMessage(getString(R.string.success_alert_message))
            .setPositiveButton(
                getString(R.string.alert_positive_button_text)
            ) { dialog, _ -> dialog.dismiss() }
            .show()
    }

    private fun setError() {
        AlertDialog.Builder(this)
            .setTitle(getString(R.string.alert_title_authorization_status))
            .setMessage("Неверный логин / пароль")
            .setPositiveButton(
                getString(R.string.alert_positive_button_text)
            ) { dialog, _ -> dialog.dismiss() }
            .setNeutralButton(
                "Создать аккаунт"
            ) { dialog, _ ->
                dialog.dismiss(); viewModel?.onRegistration(
                binding.loginEditText.text.toString(),
                binding.passwordEditText.text.toString()
            )
            }
            .setNegativeButton(
                "Сообщить о проблеме"
            ) { dialog, _ ->
                dialog.dismiss(); viewModel?.onForgotPassword(
                binding.loginEditText.text.toString()
            )
            }
            .show()
    }

    private fun showProgress() {
        binding.loadingLayout.root.visibility = View.VISIBLE
        binding.loginButton.isEnabled = false
        binding.loginInputLayout.isEnabled = false
        binding.passwordInputLayout.isEnabled = false
        binding.forgotPasswordButton.isEnabled = false
        binding.registrationButton.isEnabled = false
        binding.fab.isEnabled = false
        hideKeyboard(this)
    }

    private fun hideProgress() {
        binding.loadingLayout.root.visibility = View.GONE
        binding.loginButton.isEnabled = true
        binding.loginInputLayout.isEnabled = true
        binding.passwordInputLayout.isEnabled = true
        binding.forgotPasswordButton.isEnabled = true
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