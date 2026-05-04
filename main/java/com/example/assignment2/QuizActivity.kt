package com.example.assignment2

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class QuizActivity : AppCompatActivity() {
    private var index = 0
    private var score = 0
    private lateinit var questionText: TextView
    private lateinit var feedbackText: TextView
    private lateinit var hackButton: Button
    private lateinit var mythButton: Button
    private lateinit var nextButton: Button

    private val questions = arrayOf(
        "Putting your phone in rice fixes water damage",
        "Using keyboard shortcuts improves productivity",
        "Drinking coffee completely dehydrates you",
        "Writing tasks down improves memory",
        "Charging phone overnight destroys battery"
    )

    private val answers = arrayOf(false, true, false, true, false)
    private val explanations = arrayOf(
        "Myth: Rice does not fix water damage.",
        "Hack: Saves time and improves productivity.",
        "Myth: Coffee still hydrates you.",
        "Hack: Writing improves memory.",
        "Myth: Phones prevent overcharging."
    )

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_quiz)

        // Handle edge-to-edge display and system bar padding
        val mainView = findViewById<View>(R.id.main)
        if (mainView != null) {
            ViewCompat.setOnApplyWindowInsetsListener(mainView) { v, insets ->
                val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
                v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
                insets
            }
        }

        // Initialize UI components
        questionText = findViewById(R.id.questionText)
        feedbackText = findViewById(R.id.feedbackText)
        hackButton = findViewById(R.id.hackButton)
        mythButton = findViewById(R.id.mythButton)
        nextButton = findViewById(R.id.nextButton)

        loadQuestion()

        // Set button click listeners
        hackButton.setOnClickListener { checkAnswer(true) }
        mythButton.setOnClickListener { checkAnswer(false) }

        nextButton.setOnClickListener {
            index++

            if (index < questions.size) {
                loadQuestion()
            } else {
                // Navigate to ActivityScore when quiz is finished
                val intent = Intent(this, ActivityScore::class.java)
                intent.putExtra("score", score)
                intent.putExtra("total", questions.size)
                startActivity(intent)
                finish()
            }
        }
    }

    /**
     * Loads the current question and resets UI state for the new question.
     */
    private fun loadQuestion() {
        questionText.text = questions[index]
        feedbackText.text = ""
        hackButton.isEnabled = true
        mythButton.isEnabled = true
        nextButton.visibility = View.INVISIBLE
    }

    /**
     * Checks the user's answer, provides feedback, and updates the score.
     */
    @SuppressLint("SetTextI18n")
    private fun checkAnswer(userAnswer: Boolean) {
        // Disable answer buttons and show the next button
        hackButton.isEnabled = false
        mythButton.isEnabled = false
        nextButton.visibility = View.VISIBLE

        if (userAnswer == answers[index]) {
            feedbackText.text = "Correct! 🎉\n${explanations[index]}"
            score++
        } else {
            feedbackText.text = "Wrong! ❌\n${explanations[index]}"
        }
    }
}
