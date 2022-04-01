package com.example.wordlesixcompose

import androidx.compose.runtime.toMutableStateList
import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import java.util.concurrent.Flow

class HomeViewModel : ViewModel() {

    var guessArray: MutableList<String> = ArrayList()
    var row = 0
    val cards =  List(6) { "" }.toMutableStateList()


    fun checkLetterPlacementIsCorrect() {
        for (letter in guessArray) {
            if (letter in "GUNMAN") {
                setColour(letter, 2)
                println("green")
            } else {
                setColour(letter, 0)
                println("gray")
            }
            row += 1
        }
    }

    private fun setColour(letter: String, colour: Int) { // 0 -> DarkGray, 1 -> Yellow, 2 -> Green

    }

}
