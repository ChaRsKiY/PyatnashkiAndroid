package com.example.pyatnashki

import android.os.Bundle
import android.widget.Button
import android.widget.FrameLayout
import android.widget.TextView
import androidx.activity.ComponentActivity

class MainActivity : ComponentActivity() {
    private lateinit var fifteenPuzzleView: FifteenPuzzleView
    private lateinit var statusTextView: TextView
    private lateinit var resetButton: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        statusTextView = findViewById(R.id.statusTextView)
        resetButton = findViewById(R.id.resetButton)
        fifteenPuzzleView = FifteenPuzzleView(this)

        val puzzleContainer = findViewById<FrameLayout>(R.id.puzzleContainer)
        puzzleContainer.addView(fifteenPuzzleView)

        resetButton.setOnClickListener {
            fifteenPuzzleView.resetBoard()
            statusTextView.text = "Игра в Пятнашки"
        }
    }
}