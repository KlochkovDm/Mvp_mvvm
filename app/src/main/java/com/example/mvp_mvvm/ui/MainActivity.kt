package com.example.mvp_mvvm.ui

import android.app.Activity
import android.os.Bundle
import android.view.View
import android.view.inputmethod.InputMethodManager
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import com.example.mvp_mvvm.R
import com.example.mvp_mvvm.app
import com.example.mvp_mvvm.databinding.ActivityMainBinding

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
                binding.loginEditText.text.toString(),
                binding.passwordEditText.text.toString()
            )
        }

        binding.forgotPasswordButton.setOnClickListener {
            presenter?.onForgotPassword(
                binding.loginEditText.text.toString()
            )
        }

        binding.registrationButton.setOnClickListener {
            presenter?.onRegistration(
                binding.loginEditText.text.toString(),
                binding.passwordEditText.text.toString()
            )
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
            ) { dialog, _ ->
                dialog.dismiss(); presenter?.onRegistration(
                binding.loginEditText.text.toString(),
                binding.passwordEditText.text.toString()
            )
            }
            .setNegativeButton(
                "Сообщить о проблеме"
            ) { dialog, _ -> dialog.dismiss(); presenter?.onForgotPassword(
                binding.loginEditText.text.toString()
            )
            }
            .show()
    }

    override fun showProgress() {
        binding.loadingLayout.root.visibility = View.VISIBLE
        binding.loginButton.isEnabled = false
        binding.loginInputLayout.isEnabled = false
        binding.passwordInputLayout.isEnabled = false
        binding.forgotPasswordButton.isEnabled = false
        binding.registrationButton.isEnabled = false
        binding.fab.isEnabled = false
        hideKeyboard(this)
    }

    override fun hideProgress() {
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