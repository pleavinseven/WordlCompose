package com.pleavinseven.wordlesixcompose.ui.model

sealed interface GameState {
    object NotStarted: GameState
    data class InProgress(val game: Game, val showGameEndPopUp: Boolean): GameState
}