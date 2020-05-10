package ru.android.hat.zoom.auth

import io.reactivex.subjects.BehaviorSubject
import us.zoom.sdk.ZoomSDK
import us.zoom.sdk.ZoomSDKAuthenticationListener

object ZoomAuthHelper : ZoomSDKAuthenticationListener {

    val eventSubject = BehaviorSubject.create<ZoomAuthEvent>()

    init {
        ZoomSDK.getInstance().addAuthenticationListener(this)
    }

    override fun onZoomSDKLoginResult(result: Long) = eventSubject.onNext(SDKLoginResultAuthEvent(result.toInt()))

    override fun onZoomIdentityExpired() = eventSubject.onNext(IdentityExpiredAuthEvent)

    override fun onZoomSDKLogoutResult(result: Long) = eventSubject.onNext(SDKLogoutResultAuthEvent(result.toInt()))

    override fun onZoomAuthIdentityExpired() = eventSubject.onNext(AuthIdentityExpiredAuthEvent)
}