package com.example.wordlesixcompose

import androidx.lifecycle.ViewModel

class HomeViewModel : ViewModel() {

    var guessArray: MutableList<String> = ArrayList()

    fun checkLetterPlacementIsCorrect() {
        for (letter in guessArray) {
            if (letter in "GUNMAN") {
                setColour(letter, 2)
            } else {
                setColour(letter, 0)
            }
        }
    }

    private fun setColour(letter: String, colour: Int) { // 0 -> DarkGray, 1 -> Yellow, 2 -> Green

    }

}