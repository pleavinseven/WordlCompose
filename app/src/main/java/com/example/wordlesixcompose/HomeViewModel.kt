package com.example.wordlesixcompose

import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.toMutableStateList
import androidx.compose.ui.graphics.Color
import androidx.lifecycle.ViewModel

data class CardData(val text: String, var colour: Color)

class HomeViewModel : ViewModel() {


    var currentRow = 0

    //    val guessArray = List(5) { List(6) { "" }.toMutableStateList() }
    val guessArray = List(5) { List(6) { CardData("", Color.White) }.toMutableStateList() }
    var column = 0
    var rowChecked = false
    val word = "GUNMAN"
    // mutable colour for background

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
                    guessArray[currentRow][column] = CardData(text, Color.White)
                }
            }
        }
    }


    fun checkLetterPlacementIsCorrect() {
        if (column == 6) {
            // remove letters form copies and check against each other to make sure yellow only called once
            var yellowUsed = false
            val copyWord = word.map { it }.toMutableList()
            val copyGuess = guessArray[currentRow].map { it }.toMutableList()
            guessArray[currentRow].forEachIndexed { index, letter ->
                if (letter.text[0] == word[index]) {
                    letter.colour = Color.Green
                    copyGuess.remove(letter)
                    copyWord.remove(letter.text[0])


                    // sort this ->


                } else if (letter.text in word) {
                    for (cardData in guessArray[currentRow]) {
                        if (cardData.text == letter.text && cardData.colour == Color.Yellow) {
                            yellowUsed = true
                        }
                        when (yellowUsed) {
                            !yellowUsed -> letter.colour = Color.Yellow
                            else -> letter.colour = Color.DarkGray
                        }
                        yellowUsed = false
                    }
                } else {
                    letter.colour = Color.DarkGray
                }
            }
        }

        rowChecked = true
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
