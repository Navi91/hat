package ru.android.hat.zoom

import android.content.Context
import us.zoom.sdk.ZoomSDK
import us.zoom.sdk.ZoomSDKInitParams
import us.zoom.sdk.ZoomSDKInitializeListener
import us.zoom.sdk.ZoomSDKRawDataMemoryMode

object ZoomHelper {

    private val zoomSdk by lazy { ZoomSDK.getInstance() }

    fun init(context: Context, listener: ZoomSDKInitializeListener) {
        if (!zoomSdk.isInitialized) {
            val initParams = ZoomSDKInitParams().apply {
                appKey = "oelbsWNXI0TxY3nnAdCUfb4HeWVOzTElBVWi"
                appSecret = "HTit8VlL2zdgShGP1Ax5CYUWXQgfa4wHI8Ct"
                enableLog = true
                logSize = 50
                domain = "zoom.us"
                videoRawDataMemoryMode = ZoomSDKRawDataMemoryMode.ZoomSDKRawDataMemoryModeStack
            }

            zoomSdk.initialize(context, listener, initParams)
        }
    }
}