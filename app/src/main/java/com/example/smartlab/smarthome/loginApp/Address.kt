package com.example.smartlab.smarthome.loginApp

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import androidx.appcompat.app.AppCompatActivity
import com.example.smartlab.smarthome.Setting.MainScreen
import com.example.smartlab.smarthome.R
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.serialization.Serializable


// Объявляем класс Address, который наследуется от AppCompatActivity
class Address : AppCompatActivity() {
    // Объявляем переменные для хранения ссылок на EditText и Button
    private lateinit var editAddress: EditText
    private lateinit var btnSave: Button

    // Обработчик события создания активити
    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_adress)

        // Находим элементы по id
        editAddress = findViewById(R.id.edit_adress)
        btnSave = findViewById(R.id.btn_save)

        // Устанавливаем обработчик нажатия на кнопку btnSave
        btnSave.setOnClickListener {
            // При нажатии вызываем функцию сохранения адреса в базу данных
            saveAddressToDatabase()
        }
    }
    @Serializable
data class Adress (val id: Int = 0, val name: String = "")
    // Функция для сохранения адреса в базу данных
    private fun saveAddressToDatabase() {

        val supabase = createSupabaseClient(
            supabaseUrl = "https://xyzcompany.supabase.co",
            supabaseKey = "public-anon-key"
        ) {
            install(GoTrue)
            install(Postgrest)
            //install other modules
        }

        val city = supabase.postgrest["adress"].select();

        val city = Adress(name = "The Shire", countryId = 554)
        supabase.postgrest["adress"].insert(city)

        // Считываем адрес из editAddress
        val address = editAddress.text.toString()

        // Инициализируем клиент Supabase
        val supabaseClient = SupabaseClient1.initialize("https://gjklzqltczjrjtbdgmym.supabase.co", "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJpc3MiOiJzdXBhYmFzZSIsInJlZiI6Imdqa2x6cWx0Y3pqcmp0YmRnbXltIiwicm9sZSI6InNlcnZpY2Vfcm9sZSIsImlhdCI6MTcwMDE4NzY0NSwiZXhwIjoyMDE1NzYzNjQ1fQ.MtY5V_8JrPj392EhT9yhg0uO3kW5e_rvpTvu83uB0l0")

        // Название таблицы, в которую будем сохранять адрес
        val tableName = "addresses"

        // Запускаем асинхронную задачу, в которой будем сохранять адрес в базу данных
        GlobalScope.launch(Dispatchers.IO) {
            // Выполняем вставку в базу данных
            val result = supabaseClient.table(tableName).insert(address = address).execute()

            // Проверяем, успешно ли выполнена вставка
            if (result.isSuccess) {
                // Если да, то переносим пользователя на экран MainScreen
                val intent = Intent(this@Address, MainScreen::class.java)
                startActivity(intent)
                // Завершаем текущую Activity, чтобы при нажатии "назад" пользователь не возвращался к этому экрану
                finish()
            } else {
                // В случае ошибки выводим сообщение об ошибке
                val errorMessage = result.error?.message ?: "Неизвестная ошибка"
                // Здесь можно обработать ошибку, например, показать сообщение об ошибке с помощью Toast или Snackbar
            }
        }
    }
}