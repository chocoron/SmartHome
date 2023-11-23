package com.example.smartlab.smarthome.Setting

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.TextView
import com.example.smartlab.smarthome.R
import com.example.smartlab.smarthome.loginApp.SupabaseClient1

class MainScreen : AppCompatActivity() {
    private lateinit var mainAdressTextView: TextView // Виджет для отображения основного адреса
    private lateinit var supabaseClient: SupabaseClient1 // Инициализация клиента supabase

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main_screen)

        // Инициализируем виджет адреса и клиент Supabase со своим URL и ключом
        mainAdressTextView = findViewById(R.id.main_adress)
        supabaseClient = SupabaseClient1.Builder("https://gjklzqltczjrjtbdgmym.supabase.co", "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJpc3MiOiJzdXBhYmFzZSIsInJlZiI6Imdqa2x6cWx0Y3pqcmp0YmRnbXltIiwicm9sZSI6InNlcnZpY2Vfcm9sZSIsImlhdCI6MTcwMDE4NzY0NSwiZXhwIjoyMDE1NzYzNjQ1fQ.MtY5V_8JrPj392EhT9yhg0uO3kW5e_rvpTvu83uB0l0").build()
        loadAddressFromDatabase() // Загрузить адрес из базы данных
    }

    // Загрузить адрес из базы данных
    private fun loadAddressFromDatabase() {
        // Запрос к базе данных. Здесь важно правильно обработать возвращаемые данные.
        val response = supabaseClient
            .from("addresses")
            .select()
            .execute()

        // Получаем адрес и устанавливаем его в виджет
        if (response.data != null) {
            val address = response.data["columnName"] // толбец с адресом
            mainAdressTextView.text = address.toString()
        } else {
            // Обработка ошибок
            print(response.error?.message)
        }
    }

    // Обработчик нажатия для добавления комнаты
    fun addR(view: View) {
        val intent = Intent(this, AddRoom::class.java) // Создаём намерение перейти на экран добавления комнаты
        startActivity(intent) // Запускаем процесс перехода
    }

    // Обработчик нажатия для открытия настроек профиля
    fun setting(view: View) {
        val intent = Intent(this, Profile::class.java) // Создаем намерение перейти на экран профиля
        startActivity(intent) // Запускаем процесс перехода
    }
}