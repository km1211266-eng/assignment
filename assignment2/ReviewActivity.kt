package com.example.assignment2

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class ReviewActivity : AppCompatActivity() {
    private var index = 0
    private lateinit var questionText: TextView
    private lateinit var feedbackText: TextView
    private lateinit var prevButton: Button
    private lateinit var nextButton: Button
    private lateinit var backToMainButton: Button

    private val questions = arrayOf(
        "Putting your phone in rice fixes water damage",
        "Using keyboard shortcuts improves productivity",
        "Drinking coffee completely dehydrates you",
        "Writing tasks down improves memory",
        "Charging phone overnight destroys battery"
    )

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
        setContentView(R.layout.activity_review)

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
        prevButton = findViewById(R.id.prevButton)
        nextButton = findViewById(R.id.nextButton)
        backToMainButton = findViewById(R.id.backToMainButton)

        loadReview()

        // Set button click listeners
        prevButton.setOnClickListener {
            if (index > 0) {
                index--
                loadReview()
            }
        }

        nextButton.setOnClickListener {
            if (index < questions.size - 1) {
                index++
                loadReview()
            }
        }

        backToMainButton.setOnClickListener {
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
            finish()
        }
    }

    /**
     * Loads the current question and its explanation for review.
     */
    private fun loadReview() {
        questionText.text = questions[index]
        feedbackText.text = explanations[index]

        // Enable/disable navigation buttons based on current index
        prevButton.isEnabled = index > 0
        nextButton.isEnabled = index < questions.size - 1
    }
}
