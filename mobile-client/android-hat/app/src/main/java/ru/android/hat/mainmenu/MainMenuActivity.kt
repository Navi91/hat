package ru.android.hat.mainmenu

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import ru.android.hat.R

class MainMenuActivity: AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.a_main_menu)
    }

    companion object {

        private const val LOG_TAG = "MainMenuActivity"

        fun createIntent(context: Context) = Intent(context, MainMenuActivity::class.java)
    }
}