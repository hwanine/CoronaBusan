package com.jhlee.coronabusan

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import androidx.appcompat.app.AppCompatActivity

class SplashActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        Thread.sleep(500)
        val intent = Intent(this, MainActivity::class.java)
        startActivity(intent)
        finish()
    }
}