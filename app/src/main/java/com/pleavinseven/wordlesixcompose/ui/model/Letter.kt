package com.pleavinseven.wordlesixcompose.ui.model

import androidx.compose.ui.graphics.Color
import com.pleavinseven.wordlesixcompose.ui.theme.Green

sealed class Letter {
    abstract val text: String
    abstract val colour: Color

    data class CorrectInRightPosition(override val text: String): Letter() {
        override val colour: Color = Green
    }

    data class CorrectInWrongPosition(override val text: String): Letter() {
        override val colour: Color = Color.Yellow
    }

    data class WrongLetter(override val text: String): Letter() {
        override val colour: Color = Color.DarkGray
    }

    data class Unknown(override val text: String = ""): Letter() {
        override val colour: Color = Color.White
    }
}