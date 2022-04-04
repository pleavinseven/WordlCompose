package com.example.wordlesixcompose

import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.toMutableStateList
import androidx.lifecycle.ViewModel

class HomeViewModel : ViewModel() {

    var currentRow = 0
    val guessArray = List(5) { List(6) { "" }.toMutableStateList() }
    var column = 0
    var rowChecked = false
    val word = "GUNMAN"
    // mutable colour for background

    fun addLettersToGrid(text: String) {
        try {
            guessArray[currentRow][column] = text
            column += 1
        } catch (e: java.lang.IndexOutOfBoundsException) {
            if (currentRow == 4) {
                // cant go beyond currentRow index 4 -- do nothing
            } else {
                if (rowChecked) {
                    rowChecked = false
                    currentRow++
                    column = 0
                    guessArray[currentRow][column] = text
                }
            }
        }
    }


    fun checkLetterPlacementIsCorrect() {
        if (column == 6) {
                guessArray[currentRow].forEachIndexed { index, letter ->
                    if (letter[0] == word[index]) {
                        println("$index green")
                    } else if( letter in word){
                        println("$index yellow")
                    } else{
                        println("$index gray")
                    }
                }
            rowChecked = true
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
