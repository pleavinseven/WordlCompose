package com.pleavinseven.wordlesixcompose.ui.model

import androidx.compose.ui.graphics.Color
import com.pleavinseven.wordlesixcompose.ui.theme.Green
import com.pleavinseven.wordlesixcompose.ui.theme.Yellow

sealed class Letter {
    abstract val text: String
    abstract val colour: Color

    data class CorrectLetterInRightPosition(override val text: String): Letter() {
        override val colour: Color = Green
    }

    data class CorrectLetterInWrongPosition(override val text: String): Letter() {
        override val colour: Color = Yellow
    }

    data class IncorrectLetter(override val text: String): Letter() {
        override val colour: Color = Color.DarkGray
    }

    data class Unknown(override val text: String = ""): Letter() {
        override val colour: Color = Color.White
    }
}