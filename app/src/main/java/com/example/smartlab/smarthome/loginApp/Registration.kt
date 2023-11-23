package com.example.smartlab.smarthome.loginApp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import android.content.Intent
import androidx.lifecycle.lifecycleScope
import com.example.smartlab.smarthome.R
import io.github.jan.supabase.gotrue.gotrue
import io.github.jan.supabase.gotrue.providers.builtin.Email
import io.github.jan.supabase.postgrest.postgrest
import kotlinx.coroutines.launch
import kotlinx.serialization.Serializable
import java.lang.Exception

class Registration : AppCompatActivity() {

        // Переменные для элементов пользовательского интерфейса
        private lateinit var usernameEditText: EditText
        private lateinit var emailEditText: EditText
        private lateinit var passwordEditText: EditText
        private lateinit var registerButton: Button
        private lateinit var registrationButton: Button

        // Инициализация клиента Supabase
//        private val supabase = SupabaseClient1.browserClient("https://gjklzqltczjrjtbdgmym.supabase.co", "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJpc3MiOiJzdXBhYmFzZSIsInJlZiI6Imdqa2x6cWx0Y3pqcmp0YmRnbXltIiwicm9sZSI6InNlcnZpY2Vfcm9sZSIsImlhdCI6MTcwMDE4NzY0NSwiZXhwIjoyMDE1NzYzNjQ1fQ.MtY5V_8JrPj392EhT9yhg0uO3kW5e_rvpTvu83uB0l0")

        override fun onCreate(savedInstanceState: Bundle?) {
            super.onCreate(savedInstanceState)
            setContentView(R.layout.activity_registration)


            // Связывание переменных с реальными элементами пользовательского интерфейса
            usernameEditText = findViewById(R.id.username)
            emailEditText = findViewById(R.id.email)
            passwordEditText = findViewById(R.id.password)
            registerButton = findViewById(R.id.login_button)
            registrationButton = findViewById(R.id.registration_button)

            // Установка обработчика событий для кнопки регистрации
            registerButton.setOnClickListener {
                // Получение данных от пользователя
                val username = usernameEditText.text.toString()
                val email = emailEditText.text.toString()
                val password = passwordEditText.text.toString()

                // Проверка корректности введенных данных
                if (isValidUsername(username) && isValidEmail(email) && isValidPassword(password)) {
                    // Если данные верны, сохраняем информацию о новом пользователе в Supabase
                    saveUserToSupabase(username, email, password)
                    // и переходим на следующую страницу
                    goToNextPage()
                } else {
                    // Если данные некорректны, выводим уведомление о том, что есть ошибка в данных
                    Toast.makeText(this, "Неверный логин, email или пароль", Toast.LENGTH_SHORT).show()
                }
            }

            // Обработка нажатия на кнопку для перенаправления пользователя на экран входа
            registrationButton.setOnClickListener {
                val intent = Intent(this, Login::class.java)
                startActivity(intent)
            }
        }

        private fun isValidUsername(username: String): Boolean {
            // Проверка имени пользователя на пустоту
            return username.isNotEmpty()
        }

        private fun isValidEmail(email: String): Boolean {
            // Проверка формата адреса электронной почты
            return android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()
            // Проверка корректности адреса электронной почты
            val emailRegex = Regex("^\\w+([.-]?\\w+)*@\\w+([.-]?\\w+)*(\\.\\w{2,3})+$")
        }

        private fun isValidPassword(password: String): Boolean {
            // Проверка пароля на пустоту
            return password.isNotEmpty()
        }

        private fun saveUserToSupabase(username: String, email_: String, password_: String) {
            // Регистрируем нового пользователя в Supabase
            //val response = supabase.auth.signUp(email, password)
            try {
                lifecycleScope.launch {
                    val respons = SBclient.getClient().gotrue.signUpWith(Email) {
                        email = email_
                        password = password_
                    }
                    val session1 = SBclient.getClient().gotrue.currentSessionOrNull()
                    val id = session1?.user?.id

                    SBclient.getClient().postgrest["profile"].insert(Profile_DB(id, "tnmame", "1"))

                }
                Toast.makeText(this, "Регистрация прошла успешно", Toast.LENGTH_SHORT).show()
            } catch (e: Exception){
                Toast.makeText(this, "Не удалось зарегистрироваться", Toast.LENGTH_SHORT).show()
            }

/*
            if (response.error == null) {
                // Если регистрация успешна, сохраняем имя пользователя в состоянии
                supabase.from("profile").update("username", username)
                Toast.makeText(this, "Регистрация прошла успешно", Toast.LENGTH_SHORT).show()
            } else {
                Toast.makeText(this, "Не удалось зарегистрироваться", Toast.LENGTH_SHORT).show()
            }*/
        }

        private fun goToNextPage() {
            // Перенаправляем пользователя на следующую страницу после успешной регистрации
            val intent = Intent(this, PIN_creation::class.java)
            startActivity(intent)
            finish()
        }
    }
@Serializable
data class Profile_DB(val id: String? , val username: String, val adress: String)