package com.example.wordlesixcompose

import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.toMutableStateList
import androidx.compose.ui.graphics.Color
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

data class CardData(var text: String, var colour: Color)
data class KeyData(var text: String, var colour: Color)

class HomeViewModel : ViewModel() {


    var currentRow = 0
    val guessArray = List(5) { List(6) { CardData("", Color.White) }.toMutableStateList() }
    var column = 0
    var rowChecked = false
    val word = "GUNMAN"
    val rowOneKeys = arrayOf("Q", "W", "E", "R", "T", "Y", "U", "I", "O", "P")
    val rowTwoKeys = arrayOf("A", "S", "D", "F", "G", "H", "J", "K", "L")
    val rowThreeKeys = arrayOf("Z", "X", "C", "V", "B", "N", "M")


    fun addLettersToGrid(text: String) {
        try {
            guessArray[currentRow][column] = CardData(text, Color.White)
            column += 1
        } catch (e: java.lang.IndexOutOfBoundsException) {
            if (currentRow == 4) {
                // cant go beyond currentRow index 4 -- do nothing
            } else {
                if (rowChecked) {
                    rowChecked = false
                    currentRow++
                    column = 0
                    addLettersToGrid(text)
                }
            }
        }
    }


    fun checkLetterPlacementIsCorrect() {
        val copyWord = word.map { it }.toMutableList()
        val green = Color(46, 125, 50)
        if (column == 6) {
            // remove letters from copies and check against each other to make sure yellow only called once
            for (i in guessArray[currentRow].indices) {
                val letter = guessArray[currentRow][i]
                if (letter.text[0] == word[i]) {
                    guessArray[currentRow][i] = letter.copy(colour = green)
                    copyWord.remove(letter.text[0])
                }
            }
            for (i in guessArray[currentRow].indices) {
                val letter = guessArray[currentRow][i]
                if (letter.colour != green) {
                    if (letter.text in copyWord.toString()) {
                        guessArray[currentRow][i] = letter.copy(colour = Color.Yellow)
                        copyWord.remove(letter.text[0])
                    } else {
                        guessArray[currentRow][i] = letter.copy(colour = Color.DarkGray)
                    }
                }
            }
        }
        rowChecked = true
    }

    fun checkKeyboard() {

    }

    fun removeLetter() {
        try {
            guessArray[currentRow][column - 1] = CardData("", Color.White)
            column--
        } catch (e: IndexOutOfBoundsException) {

        }
    }
}


// if letter was ever green, button is always green.
// guess letters can be green or yellow?
