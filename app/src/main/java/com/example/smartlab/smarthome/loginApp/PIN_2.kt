package com.example.smartlab.smarthome.loginApp

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.widget.ImageView
import android.widget.Button
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.smartlab.smarthome.Setting.MainScreen
import com.example.smartlab.smarthome.R


class PIN_2 : AppCompatActivity() {
    private val pinCode = StringBuilder()  // Контейнер для вводимого пин-кода
    // Ожидаемый корректный пин-код, загружается из SharedPreferences
    private val correctPinCode: String by lazy {
        getSharedPreferences("login_data", Context.MODE_PRIVATE).getString("pin", "") ?: ""
    }

    private val buttons = mutableListOf<Button>()  // Список кнопок для ввода пин-кода
    private val circles = mutableListOf<ImageView>()  // Список изображений-индикаторов ввода пин-кода

    // Метод, вызываемый при создании Activity
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_pin2)

        // Добавление кнопок в список
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

        // Установка слушателей на каждую кнопку
        // При нажатии на кнопку к переменной pinCode добавляется соответствующая цифра,
        // а затем вызывается функция updatePinCodeState() для обновления состояния кода
        buttons.forEachIndexed { index, button ->
            button.setOnClickListener {
                pinCode.append(index + 1)
                updatePinCodeState()
            }
        }

        // Добавление изображений-индикаторов в список
        circles.apply {
            add(findViewById(R.id.circle1))
            add(findViewById(R.id.circle2))
            add(findViewById(R.id.circle3))
            add(findViewById(R.id.circle4))
        }

        // Установка слушателя на кнопку возврата
        // При нажатии на нее, произойдет переход на экран Login
        val enterButton: Button = findViewById(R.id.enterButton)
        enterButton.setOnClickListener {
            val intent = Intent(this, Login::class.java)
            startActivity(intent)
        }
    }

    // Метод для обновления состояния кода
    // Если длина введенного пин-кода равна 4, то происходит проверка с корректным пин-кодом
    // Если пин-коды совпадают, то происходит переход на экран MainScreen
    // Если пин-коды не совпадают, то поля для ввода сбрасываются и появляется уведомление о некорректном пин-коде
    private fun updatePinCodeState() {

        circles.forEachIndexed { index, imageView ->
            imageView.setImageResource(
                // Если текущий индекс меньше длины введенного пин-кода,
                // то устанавливается изображение заполненного круга. Это может
                // быть использовано для визуализации вводимого пользователем пин-кода.
                if (index < pinCode.length) R.drawable.circle
                // Если текущий индекс больше или равен длине введенного пин-кода,
                // то устанавливается изображение незаполненного круга.
                else R.drawable.circle_selector
            )
        }

// Если длина введенного пин-кода равна 4 (стандартная длина пин-кода),
// то выполняется следующая логика:
        if (pinCode.length == 4) {
            // Если введенный пин-код соответствует корректному пин-коду...
            if (pinCode.toString() == correctPinCode) {
                // ...то создается и запускается новое внутреннее намерение (Intent),
                // которое переносит пользователя на главный экран приложения.
                val intent = Intent(this, MainScreen::class.java)
                startActivity(intent)
                // Если введенный пин-код не соответствует корректному пин-коду...
            } else {
                // ...то пин-код очищается,
                pinCode.clear()
                // круги визуализации пин-кода становятся незаполненными,
                circles.forEach { imageView ->
                    imageView.setImageResource(R.drawable.circle_selector)
                }
                // и выводится сообщение для пользователя о том, что введенный пин-код неверный.
                Toast.makeText(this, "Неверный PIN", Toast.LENGTH_SHORT).show()
            }
        }
    }
}