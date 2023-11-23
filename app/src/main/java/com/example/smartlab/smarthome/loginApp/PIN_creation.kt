package com.example.smartlab.smarthome.loginApp

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.location.Address
import android.os.Bundle
import android.widget.Button
import android.widget.ImageView
import androidx.appcompat.app.AppCompatActivity
import com.example.smartlab.smarthome.R

class PIN_creation : AppCompatActivity() {

    // Определение списков кнопок и кругов (предположительно, индикаторов ввода номера).
    private val buttons = mutableListOf<Button>()
    private val circles = mutableListOf<ImageView>()

    // Счетчик введенных символов в PIN.
    private var selectedCount = 0

    // Список для хранения цифр PIN-кода.
    private var pinList = mutableListOf<Int>()

    // Объявление SharedPreferences для сохранения PIN-кода.
    private lateinit var sharedPref: SharedPreferences

    // Функция встроенного жизненного цикла Android, вызывается при создании экземпляра активности.
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        // Устанавливает макет этой активности из XML-файла.
        setContentView(R.layout.activity_pin_creation)

        // Находим все необходимые кнопки на макете и добавляем их в список кнопок.
        buttons.apply {
            add(findViewById(R.id.button1))
            add(findViewById(R.id.button2))
            add(findViewById(R.id.button3))
            add(findViewById(R.id.button4))
            add(findViewById(R.id.button5))
            add(findViewById(R.id.button6))
            add(findViewById(R.id.button7))
            add(findViewById(R.id.button8))
            add(findViewById(R.id.button9))
        }

        // Находим все необходимые изображения кругов на макете и добавляем их в список кругов.
        circles.apply {
            add(findViewById(R.id.circle1))
            add(findViewById(R.id.circle2))
            add(findViewById(R.id.circle3))
            add(findViewById(R.id.circle4))
        }

        // Инициализируем SharedPreferences для сохранения данных.
        sharedPref = this.getPreferences(Context.MODE_PRIVATE)

        // Для каждой кнопки устанавливаем listener, реагирующий на клик.
        buttons.forEach { button ->
            button.setOnClickListener {
                if (selectedCount < 4) {
                    // Добавляем в список pinList цифру, соответствующую нажатой кнопке.
                    pinList.add(button.text.toString().toInt())
                    // Меняем визуализацию для соответствующего индикатора ввода (круга).
                    circles[selectedCount].setBackgroundResource(R.drawable.state_pressed)
                    // Увеличиваем счетчик введенных символов.
                    selectedCount++
                    // Если были введены все 4 символа, задаем последний этап процесса.
                    if (selectedCount == 4) {
                        // Возвращаем визуализацию индикаторов ввода в исходное состояние.
                        circles.forEach { circle -> circle.setBackgroundResource(R.drawable.circle) }
                        // Сохраняем PIN-код в SharedPreferences.
                        savePIN()
                        // Переходим к следующей активности.
                        startActivity(Intent(this, Address::class.java))
                    }
                }
            }
        }
    }

    // Функция для сохранения PIN-кода в SharedPreferences.
    private fun savePIN() {
        with(sharedPref.edit()) {
            // Переводим список цифр PIN-кода в строку и сохраняем в SharedPreferences.
            putString("PIN", pinList.joinToString(""))
            apply()
        }
    }
}