package ru.android.hat.login.activity

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import ru.android.hat.R

class LoginActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.a_login)
    }

    companion object {

        fun createIntent(context: Context): Intent = Intent(context, LoginActivity::class.java)
    }
}
