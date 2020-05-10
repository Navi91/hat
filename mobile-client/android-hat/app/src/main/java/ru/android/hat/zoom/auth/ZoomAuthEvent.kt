package ru.android.hat.zoom.auth

sealed class ZoomAuthEvent
class SDKLoginResultAuthEvent(val result: Int) : ZoomAuthEvent()
class SDKLogoutResultAuthEvent(val result: Int) : ZoomAuthEvent()
object IdentityExpiredAuthEvent : ZoomAuthEvent()
object AuthIdentityExpiredAuthEvent : ZoomAuthEvent()