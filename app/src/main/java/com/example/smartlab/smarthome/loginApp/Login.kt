package com.example.smartlab.smarthome.loginApp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import android.content.Intent
import com.example.smartlab.smarthome.R
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class Login : AppCompatActivity() {
    // Объявление переменных для элементов UI
    private lateinit var emailEditText: EditText
    private lateinit var passwordEditText: EditText
    private lateinit var loginButton: Button
    private lateinit var registerButton: Button

    // Инициализация клиента Supabase
    private val supabase = SupabaseClient1.browserClient(
        "https://gjklzqltczjrjtbdgmym.supabase.co",
        "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJpc3MiOiJzdXBhYmFzZSIsInJlZiI6Imdqa2x6cWx0Y3pqcmp0YmRnbXltIiwicm9sZSI6InNlcnZpY2Vfcm9sZSIsImlhdCI6MTcwMDE4NzY0NSwiZXhwIjoyMDE1NzYzNjQ1fQ.MtY5V_8JrPj392EhT9yhg0uO3kW5e_rvpTvu83uB0l0"
    )

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_login)

        // Связывание переменных с реальными элементами UI
        emailEditText = findViewById(R.id.email)
        passwordEditText = findViewById(R.id.password)
        loginButton = findViewById(R.id.login_button)
        registerButton = findViewById(R.id.registration_button)

        // Обработчик события нажатия на кнопку входа
        loginButton.setOnClickListener {
            val email = emailEditText.text.toString()
            val password = passwordEditText.text.toString()

            GlobalScope.launch(Dispatchers.IO) {
                val responseResult = supabase.auth.signIn(email, password).execute()

                withContext(Dispatchers.Main) {
                    if (responseResult.error == null) {
                        // Успешный вход, переход к вводу пин-кода
                        val intent = Intent(this@Login, PIN_2::class.java)
                        startActivity(intent)
                        finish()
                    } else {
                        // Ошибка при входе
                        Toast.makeText(this@Login, "Ошибка при входе, проверьте ваш email и пароль", Toast.LENGTH_SHORT).show()
                    }
                }
            }
        }

        // Обработчик события нажатия на кнопку регистрации
        registerButton.setOnClickListener {
            val intent = Intent(this, Registration::class.java)
            startActivity(intent)
        }
    }
}