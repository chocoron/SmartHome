package com.example.smartlab.smarthome.Setting

import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.smartlab.smarthome.R
import com.example.smartlab.smarthome.loginApp.SupabaseClient1

class Profile : AppCompatActivity() {
    private lateinit var usernameEditText: EditText
    private lateinit var emailEditText: EditText
    private lateinit var addressEditText: EditText

    // Идентификатор пользователя
    private var userId: Int = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_profile)

        // Получение данных из предыдущей активности, например, через Intent
        userId = intent.getIntExtra("userId", 0)

        // Получение ссылок на текстовые поля
        usernameEditText = findViewById(R.id.username)
        emailEditText = findViewById(R.id.email)
        addressEditText = findViewById(R.id.address)

        // Загрузка данных пользователя из базы данных
        loadUserData()

        // Обработчик кнопки "Сохранить"
        val saveButton: Button = findViewById(R.id.save)
        saveButton.setOnClickListener {
            saveUserData()
        }

        // Обработчик кнопки "Выйти"
        val exitButton: Button = findViewById(R.id.exit)
        exitButton.setOnClickListener {
            // Возвращаемся на главный экран
            finish()
        }

        // Обработчик кнопки "Назад"
        val backButton: ImageView = findViewById(R.id.back_btn)
        backButton.setOnClickListener {
            // Возвращаемся на предыдущую активность
            onBackPressed()
        }
    }

    // Загрузка данных пользователя из базы данных
    private fun loadUserData() {
        val supabase = SupabaseClient1(this)
        val userData = supabase.getUserData(userId)

        // Заполнение полей данными пользователя из базы данных
        usernameEditText.setText(userData.username)
        emailEditText.setText(userData.email)
        addressEditText.setText(userData.address)
    }

    // Сохранение изменений данных пользователя в базе данных
    private fun saveUserData() {
        val newUsername = usernameEditText.text.toString()
        val newEmail = emailEditText.text.toString()
        val newAddress = addressEditText.text.toString()

        // Проверка введенной электронной почты
        if (!isValidEmail(newEmail)) {
            Toast.makeText(this, "Некорректный адрес электронной почты", Toast.LENGTH_SHORT).show()
            return
        }

        val supabase = SupabaseClient1(this)
        val success = supabase.updateUserData(userId, newUsername, newEmail, newAddress)
        if (success) {
            Toast.makeText(this, "Данные сохранены", Toast.LENGTH_SHORT).show()
        } else {
            Toast.makeText(this, "Ошибка при сохранении данных", Toast.LENGTH_SHORT).show()
        }
    }

    // Проверка корректности адреса электронной почты
    private fun isValidEmail(email: String): Boolean {
        val emailRegex = Regex("^\\w+([.-]?\\w+)*@\\w+([.-]?\\w+)*(\\.\\w{2,3})+$")
        return email.matches(emailRegex)
    }
}