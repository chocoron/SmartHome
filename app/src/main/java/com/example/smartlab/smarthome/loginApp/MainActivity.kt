package com.example.smartlab.smarthome.loginApp


import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.CountDownTimer
import com.example.smartlab.smarthome.R

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.boot_screen)

        object : CountDownTimer(5 * 1000, 1000) {
            override fun onTick(millisUntilFinished: Long) {}
            override fun onFinish() {
                val intent = Intent(this@MainActivity, Login::class.java)
                startActivity(intent)
            }
        }.start()
    }
}