package ru.android.hat.login.activity

import android.os.Bundle
import android.os.PersistableBundle
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import ru.android.hat.zoom.ZoomHelper
import us.zoom.sdk.ZoomError
import us.zoom.sdk.ZoomSDKInitializeListener

class SplashActivity : AppCompatActivity(), ZoomSDKInitializeListener {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        ZoomHelper.init(this, this)
    }

    override fun onZoomSDKInitializeResult(errorCode: Int, internalErrorCode: Int) {
        Log.d(LOG_TAG, "onZoomSDKInitializeResult errorCode: $errorCode internalErrorCode: $internalErrorCode")

        if (errorCode == ZoomError.ZOOM_ERROR_SUCCESS) {
            startActivity(LoginActivity.createIntent(this))
            finish()
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