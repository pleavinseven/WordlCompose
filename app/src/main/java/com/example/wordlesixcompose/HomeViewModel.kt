package com.example.wordlesixcompose

import androidx.compose.runtime.toMutableStateList
import androidx.compose.ui.graphics.Color
import androidx.lifecycle.ViewModel

data class CardData(var text: String, var colour: Color)
data class KeyData(var text: String, val size: Int, var colour: Color)

class HomeViewModel : ViewModel() {

    private var currentRow = 0
    val guessArray = List(5) { List(6) { CardData("", Color.White) }.toMutableStateList() }
    private var column = 0
    var rowChecked = false
    private val word = "LOVES"
    val firstRowKeyboard = "QWERTYUIOP".toCharArray()
        .map { symbol: Char -> KeyData(text = symbol.toString(), 35, Color.White) }
    val secondRowKeyboard = "ASDFGHJKL".toCharArray()
        .map { symbol: Char -> KeyData(text = symbol.toString(), 35, Color.White) }
    val thirdRowKeyboard = "ZXCVBNM".toCharArray()
        .map { symbol: Char -> KeyData(text = symbol.toString(), 35, Color.White) }


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
