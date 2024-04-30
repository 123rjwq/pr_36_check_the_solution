package com.example.pr_36_check_the_solution_rom_kir

import android.os.Bundle
import android.os.SystemClock
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import kotlin.random.Random
import kotlin.math.roundToInt



class MainActivity : AppCompatActivity() {

    var answer: Int = 0
    var answer_2 = 0

    private lateinit var exampleTextView: TextView
    private lateinit var answerTextView: TextView
    private lateinit var startButton: Button
    private lateinit var correctButton: Button
    private lateinit var incorrectButton: Button
    private lateinit var totalExamplesTextView: TextView
    private lateinit var correctAnswersTextView: TextView
    private lateinit var incorrectAnswersTextView: TextView
    private lateinit var timeTextView: TextView
    private val answerTimes = mutableListOf<Long>()

    private var startTime: Long = 0

    private var correctAnswers = 0
    private var incorrectAnswers = 0
    private var totalAnswers = 0

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
        incorrectAnswersTextView = findViewById(R.id.incorrectAnswersTextView)
        timeTextView = findViewById(R.id.timeTextView)

        startButton.setOnClickListener { generateExample() }
        correctButton.setOnClickListener { checkAnswer(true) }
        incorrectButton.setOnClickListener { checkAnswer(false) }
    }
    fun Double.isWhole() = this == Math.floor(this) && !java.lang.Double.isInfinite(this)
    private fun generateExample() {
        // Код генерации примера
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
                answer_2 = Random.nextDouble(10.0, 100.0).toInt() // Генерируем случайное число в диапазоне от 10 до 99
            }else{answer_2=answer.toInt()}
        } else if (answer is Int)
        {
            if (answer >= 60)
            {
                answer_2 = Random.nextInt(10, 100) // Генерируем случайное число в диапазоне от 10 до 99
            }else{answer_2=answer.toInt()}
        }


        startTime = SystemClock.elapsedRealtime()
        exampleTextView.text = example
        answerTextView.text = answer_2.toString()
        updateCounters()
        correctButton.isEnabled = true
        incorrectButton.isEnabled = true
        startButton.isEnabled = false

    }

    private fun checkAnswer(isCorrect: Boolean) {
        correctButton.isEnabled = false
        incorrectButton.isEnabled = false
        startButton.isEnabled = true
        val elapsedTime = SystemClock.elapsedRealtime() - startTime
        answerTimes.add(elapsedTime)
        startTime = 0

        // Преобразование answer в Int для сравнения
        var answerInt = answer.toInt()
        // Преобразование answer_2 в Int для сравнения
        var answer_2_Int = answer_2.toInt()

        if (isCorrect)
        {
            if  (answerInt==answer_2_Int)
            {
                correctAnswers++
            }else{incorrectAnswers++}
        } else
        {
            if  (!(answerInt==answer_2_Int))
            {
                correctAnswers++
            }
            else{incorrectAnswers++}
        }
        //totalAnswers++

        updateCounters()
        calculateAndDisplayTimeStats()
    }

    private fun updateCounters() {
        totalExamplesTextView.text = "Решено примеров: $totalAnswers"

        // Вычисление процента верных ответов
        val percentage = if (totalAnswers > 0) 100.0 * correctAnswers / totalAnswers else 0.0
        // Обновление UI с процентом верных ответов
        correctAnswersTextView.text = "Правильно решено: $correctAnswers (${percentage.round(2)}%)"
        incorrectAnswersTextView.text = "Неправильно решено: $incorrectAnswers"
    }

    private fun calculateAndDisplayTimeStats() {
        val minTime = answerTimes.minOrNull() ?: 0
        val maxTime = answerTimes.maxOrNull() ?: 0
        val avgTime = answerTimes.average().toLong()

        // Обновление UI с новыми статистическими данными
        timeTextView.text = "Минимальное время: $minTime мс, Максимальное время: $maxTime мс, Среднее время: $avgTime мс"
    }

    // Расширение для округления чисел с плавающей точкой
    fun Double.round(decimals: Int): Double {
        var multiplier = 1.0
        repeat(decimals) { multiplier *= 10 }
        return kotlin.math.round(this * multiplier) / multiplier
    }
}
