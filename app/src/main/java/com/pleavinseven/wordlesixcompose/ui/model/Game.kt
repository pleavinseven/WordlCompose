package com.pleavinseven.wordlesixcompose.ui.model

data class Game(
    val word: String,
    val guesses: List<Guess> = List(5) { Guess(List(6) { Letter.Unknown() }) },
    val greenLetters: List<String> = emptyList(),
    val yellowLetters: List<String> = emptyList(),
    val grayLetters: List<String> = emptyList(),
    val keyboard: Keyboard = Keyboard()
)