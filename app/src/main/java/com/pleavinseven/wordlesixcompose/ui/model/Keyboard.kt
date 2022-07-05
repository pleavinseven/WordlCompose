package com.pleavinseven.wordlesixcompose.ui.model

data class Keyboard(
    val firstRow: List<Letter> = "QWERTYUIOP".toCharArray()
        .map { text: Char -> Letter.Unknown(text.toString()) },
    val secondRow: List<Letter> = "ASDFGHJKL".toCharArray()
        .map { text: Char -> Letter.Unknown(text.toString()) },
    val thirdRow: List<Letter> = "ZXCVBNM".toCharArray()
        .map { text: Char -> Letter.Unknown(text.toString()) }
)