package com.example.pyatnashki

import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.util.AttributeSet
import android.view.MotionEvent
import android.view.View
import android.widget.Toast

class FifteenPuzzleView(context: Context, attrs: AttributeSet? = null) : View(context, attrs) {

    private val paint = Paint().apply {
        color = Color.BLACK
        textSize = 60f
        textAlign = Paint.Align.CENTER
    }
    private val tilePaint = Paint().apply {
        color = Color.LTGRAY
    }

    private var board = Array(4) { IntArray(4) }
    private var emptyCell = Pair(3, 3)
    private var gameWon = false

    init {
        resetBoard()
    }

    fun resetBoard() {
        val numbers = (1..15).toMutableList().shuffled()
        for (i in 0 until 4) {
            for (j in 0 until 4) {
                board[i][j] = if (i * 4 + j < 15) numbers[i * 4 + j] else 0
            }
        }
        emptyCell = Pair(3, 3)
        gameWon = false
        invalidate()  // Перерисовать доску
    }

    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)
        val cellSize = width / 4

        for (i in 0 until 4) {
            for (j in 0 until 4) {
                val x = j * cellSize
                val y = i * cellSize
                if (board[i][j] != 0) {
                    canvas.drawRect(x.toFloat(), y.toFloat(), (x + cellSize).toFloat(), (y + cellSize).toFloat(), tilePaint)
                    canvas.drawText(board[i][j].toString(), (x + cellSize / 2).toFloat(), (y + cellSize / 2 + paint.textSize / 2).toFloat(), paint)
                }
            }
        }
    }

    override fun onTouchEvent(event: MotionEvent): Boolean {
        if (event.action == MotionEvent.ACTION_DOWN && !gameWon) {
            val cellSize = width / 4
            val x = (event.x / cellSize).toInt()
            val y = (event.y / cellSize).toInt()
            moveTile(x, y)
            return true
        }
        return super.onTouchEvent(event)
    }

    private fun moveTile(x: Int, y: Int) {
        val (emptyX, emptyY) = emptyCell
        if ((x == emptyX && Math.abs(y - emptyY) == 1) || (y == emptyY && Math.abs(x - emptyX) == 1)) {
            board[emptyY][emptyX] = board[y][x]
            board[y][x] = 0
            emptyCell = Pair(x, y)
            invalidate()
            checkWin()
        }
    }

    private fun checkWin() {
        var won = true
        for (i in 0 until 4) {
            for (j in 0 until 4) {
                if (i == 3 && j == 3) break
                if (board[i][j] != i * 4 + j + 1) {
                    won = false
                    break
                }
            }
        }
        if (won) {
            gameWon = true
            Toast.makeText(context, "Вы выиграли!", Toast.LENGTH_LONG).show()
        }
    }
}
