package com.example.wordlesixcompose

import androidx.compose.runtime.toMutableStateList
import androidx.lifecycle.ViewModel

class HomeViewModel : ViewModel() {

    var guessArray = List(6) { "" }.toMutableStateList()
    var column = 0


    fun checkLetterPlacementIsCorrect() {
        for (letter in guessArray) {
            if (letter in "GUNMAN") {
                setColour(letter, 2)
                println("green")
            } else {
                setColour(letter, 0)
                println("gray")
            }
        }
    }

    private fun setColour(letter: String, colour: Int) { // 0 -> DarkGray, 1 -> Yellow, 2 -> Green

    }

    fun removeLetter() {
        guessArray[column - 1] = ""
        column--
    }
}
