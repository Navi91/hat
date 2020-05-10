package ru.android.hat.login.activity

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import io.reactivex.disposables.CompositeDisposable
import kotlinx.android.synthetic.main.a_login.loginButton
import kotlinx.android.synthetic.main.a_login.passwordEditText
import kotlinx.android.synthetic.main.a_login.progressLayout
import kotlinx.android.synthetic.main.a_login.usernameEditText
import ru.android.hat.R
import ru.android.hat.core.plusAssign
import ru.android.hat.core.showToast
import ru.android.hat.zoom.auth.AuthIdentityExpiredAuthEvent
import ru.android.hat.zoom.auth.IdentityExpiredAuthEvent
import ru.android.hat.zoom.auth.SDKLoginResultAuthEvent
import ru.android.hat.zoom.auth.SDKLogoutResultAuthEvent
import ru.android.hat.zoom.auth.ZoomAuthEvent
import ru.android.hat.zoom.auth.ZoomAuthHelper
import us.zoom.sdk.ZoomApiError
import us.zoom.sdk.ZoomAuthenticationError
import us.zoom.sdk.ZoomSDK

class LoginActivity : AppCompatActivity() {

    private val compositeDisposable = CompositeDisposable()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.a_login)

        loginButton.setOnClickListener { performLogin() }
    }

    override fun onStart() {
        super.onStart()

        compositeDisposable += ZoomAuthHelper.eventSubject
                .subscribe({
                    Log.d(LOG_TAG, "auth event: $it")

                    handleAuthEvent(it)
                }, {
                    Log.e(LOG_TAG, "Auth event error: $it")
                })
    }

    override fun onStop() {
        super.onStop()

        compositeDisposable.clear()
    }

    private fun performLogin() {
        val username = usernameEditText.text.toString().trim()
        val password = passwordEditText.text.toString().trim()

        if (username.isBlank() || password.isBlank()) {
            showToast("Invalid credentials")
            return
        }

        if (ZoomSDK.getInstance().loginWithZoom(username, password) == ZoomApiError.ZOOM_API_ERROR_SUCCESS) {
            progressLayout.visibility = View.VISIBLE
        } else {
            showToast("ZoomSDK has not been initialized successfully or sdk is logging in.")
        }
    }

    private fun handleAuthEvent(event: ZoomAuthEvent) = when (event) {
        is SDKLoginResultAuthEvent -> handleLoginResult(event.result)
        is SDKLogoutResultAuthEvent -> handleLogoutResult(event.result)
        is IdentityExpiredAuthEvent -> handleIdentityExpired()
        is AuthIdentityExpiredAuthEvent -> handleAuthIdentityExpired()
    }

    private fun handleLoginResult(result: Int) {
        progressLayout.visibility = View.GONE

        when (result) {
            ZoomAuthenticationError.ZOOM_AUTH_ERROR_SUCCESS -> {
                showToast("Auth success")
            }
            ZoomAuthenticationError.ZOOM_AUTH_ERROR_USER_NOT_EXIST -> {
                showToast("User not exist")
            }
            ZoomAuthenticationError.ZOOM_AUTH_ERROR_WRONG_PASSWORD -> {
                showToast("Wrong password")
            }
            else -> showToast("Unknown error")
        }
    }

    private fun handleLogoutResult(result: Int) {
    }

    private fun handleIdentityExpired() {
    }

    private fun handleAuthIdentityExpired() {
    }

    companion object {

        private const val LOG_TAG = "LoginActivity"

        fun createIntent(context: Context): Intent = Intent(context, LoginActivity::class.java)
    }
}

