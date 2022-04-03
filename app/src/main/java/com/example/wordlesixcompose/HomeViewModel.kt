package com.example.wordlesixcompose

import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.toMutableStateList
import androidx.lifecycle.ViewModel

class HomeViewModel : ViewModel() {

    var currentRow = 0
    var guessArray = List(5) { List(6) { "" }.toMutableStateList() }
    var column = 0


    fun checkLetterPlacementIsCorrect() {
        for (row in guessArray) {
            for (letter in row) {
                if (letter in "GUNMAN") {
                    setColour(letter, 2)
                    println("green")
                } else {
                    setColour(letter, 0)
                    println("gray")
                }
            }
        }
    }


    private fun setColour(letter: String, colour: Int) { // 0 -> DarkGray, 1 -> Yellow, 2 -> Green

    }

    fun removeLetter() {
        try {
            guessArray[currentRow][column - 1] = ""
            column--
        } catch (e: IndexOutOfBoundsException) {

        }
    }
}
