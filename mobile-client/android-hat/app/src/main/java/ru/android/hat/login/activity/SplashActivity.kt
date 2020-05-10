package ru.android.hat.login.activity

import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import io.reactivex.disposables.CompositeDisposable
import kotlinx.android.synthetic.main.a_splash.statusTextView
import ru.android.hat.R
import ru.android.hat.core.plusAssign
import ru.android.hat.mainmenu.MainMenuActivity
import ru.android.hat.zoom.ZoomHelper
import ru.android.hat.zoom.auth.SDKLoginResultAuthEvent
import ru.android.hat.zoom.auth.ZoomAuthHelper
import us.zoom.sdk.ZoomAuthenticationError
import us.zoom.sdk.ZoomError
import us.zoom.sdk.ZoomSDK
import us.zoom.sdk.ZoomSDKInitializeListener

class SplashActivity : AppCompatActivity(), ZoomSDKInitializeListener {

    private val compositeDisposable = CompositeDisposable()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        if (ZoomSDK.getInstance().isLoggedIn) {
            startActivity(MainMenuActivity.createIntent(this))
            finish()
            return
        }

        setContentView(R.layout.a_splash)

        statusTextView.text = "Init sdk"

        ZoomHelper.init(this, this)
    }

    override fun onStart() {
        super.onStart()

        compositeDisposable += ZoomAuthHelper.eventSubject
                .subscribe({
                    if (it is SDKLoginResultAuthEvent) {
                        startActivity(MainMenuActivity.createIntent(this))
                    }
                }, {

                })
    }

    override fun onStop() {
        super.onStop()

        compositeDisposable.clear()
    }

    override fun onZoomSDKInitializeResult(errorCode: Int, internalErrorCode: Int) {
        Log.d(LOG_TAG, "onZoomSDKInitializeResult errorCode: $errorCode internalErrorCode: $internalErrorCode")

        if (errorCode == ZoomError.ZOOM_ERROR_SUCCESS) {
            if (ZoomSDK.getInstance().tryAutoLoginZoom() == ZoomAuthenticationError.ZOOM_AUTH_ERROR_SUCCESS) {
                statusTextView.text = "Login"
            } else {
                startActivity(LoginActivity.createIntent(this))
                finish()
            }
        }
    }

    override fun onZoomAuthIdentityExpired() {
        Log.e(LOG_TAG, "onZoomAuthIdentityExpired")

        Toast.makeText(this, "Auth Identity Expire", Toast.LENGTH_LONG).show()
    }

    private companion object {
        const val LOG_TAG = "SplashActivity"
    }
}