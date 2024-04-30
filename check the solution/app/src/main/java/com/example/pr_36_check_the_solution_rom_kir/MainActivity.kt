package com.example.pr_36_check_the_solution_rom_kir

import android.os.Bundle
import android.os.SystemClock
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import kotlin.random.Random
import kotlin.math.roundToInt

class MainActivity : AppCompatActivity() {

    private lateinit var exampleTextView: TextView
    private lateinit var answerTextView: TextView
    private lateinit var startButton: Button
    private lateinit var correctButton: Button
    private lateinit var incorrectButton: Button
    private lateinit var totalExamplesTextView: TextView
    private lateinit var correctAnswersTextView: TextView
    private lateinit var timeTextView: TextView

    private var correctAnswers = 0
    private var totalAnswers = 0
    private var totalTime = 0L

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        exampleTextView = findViewById(R.id.exampleTextView)
        answerTextView = findViewById(R.id.answerTextView)
        startButton = findViewById(R.id.startButton)
        correctButton = findViewById(R.id.correctButton)
        incorrectButton = findViewById(R.id.incorrectButton)
        totalExamplesTextView = findViewById(R.id.totalExamplesTextView)
        correctAnswersTextView = findViewById(R.id.correctAnswersTextView)
        timeTextView = findViewById(R.id.timeTextView)

        startButton.setOnClickListener { generateExample() }
        correctButton.setOnClickListener { checkAnswer(true) }
        incorrectButton.setOnClickListener { checkAnswer(false) }
    }
    fun Double.isWhole() = this == Math.floor(this) && !java.lang.Double.isInfinite(this)

    private fun generateExample() {
        val number1 = Random.nextInt(10, 99)
        val number2 = Random.nextInt(10, 99)
        val operator = when (Random.nextInt(0, 4)) {
            0 -> "*"
            1 -> "/"
            2 -> "-"
            else -> "+"

        }
        totalAnswers++
        val example = "$number1 $operator $number2"
        var answer = when (operator) {
            "*" -> number1 * number2
            "/" -> {
                val result = number1.toDouble() / number2.toDouble()
                if (result.isWhole()) {
                    result.toInt()
                } else {
                    // Округление до двух знаков после запятой
                    val rounded = result * 100
                    val roundedInt = rounded.roundToInt()
                    roundedInt / 100.0
                }
            }

            "-" -> number1 - number2
            "+" -> number1 + number2
            else -> 0
        }

        if (answer is Double)
        {
            if (answer >= 60)
            {
                answer = Random.nextDouble(10.0, 100.0) // Генерируем случайное число в диапазоне от 10 до 99
            }
        } else if (answer is Int)
        {
            if (answer >= 60)
            {
                answer = Random.nextInt(10, 100) // Генерируем случайное число в диапазоне от 10 до 99
            }
        }



        exampleTextView.text = example
        answerTextView.text = answer.toString()
        updateCounters()
        correctButton.isEnabled = true
        incorrectButton.isEnabled = true
    }

    private fun checkAnswer(isCorrect: Boolean) {
        correctButton.isEnabled = false
        incorrectButton.isEnabled = false

        val startTime = SystemClock.elapsedRealtime()
        val elapsedTime = SystemClock.elapsedRealtime() - startTime
        totalTime += elapsedTime

        if (isCorrect) {
            correctAnswers++
        }
        totalAnswers++

        // Здесь можно обновить UI с новыми статистическими данными
        updateCounters()
    }

    private fun updateCounters() {
        totalExamplesTextView.text = "Решено примеров: $totalAnswers"
        correctAnswersTextView.text = "Правильно решено: $correctAnswers"
        // Обновление времени
        val elapsedTime = SystemClock.elapsedRealtime() - totalTime
        timeTextView.text = "Затраченное время: ${elapsedTime / 1000} секунд"
    }
    private fun Double.round(decimals: Int): Double {
        var multiplier = 1.0
        repeat(decimals) { multiplier *= 10 }
        return kotlin.math.round(this * multiplier) / multiplier
    }
}
