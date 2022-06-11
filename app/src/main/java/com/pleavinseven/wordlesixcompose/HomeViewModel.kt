package com.pleavinseven.wordlesixcompose

import android.app.Application
import android.content.Context
import android.widget.Toast
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.pleavinseven.wordlesixcompose.data.WordListDatabase
import com.pleavinseven.wordlesixcompose.data.repository.WordRepository
import com.pleavinseven.wordlesixcompose.ui.model.Game
import com.pleavinseven.wordlesixcompose.ui.model.GameState
import com.pleavinseven.wordlesixcompose.ui.model.Letter
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.net.HttpURLConnection
import java.net.URL

class HomeViewModel(application: Application) : ViewModel() {

    private val wordDb = WordListDatabase.getDatabase(application)
    private val wordDao = wordDb.wordlistDao()
    private val repository = WordRepository(wordDao)

    private val _gameState: MutableState<GameState> = mutableStateOf(GameState.NotStarted)
    val gameState: State<GameState> by lazy {
        startGame()
        _gameState
    }

    private var currentRow = 0
    private var column = 0
    var rowChecked = false

    private fun startGame() {
        viewModelScope.launch {
            // retrieve word from database
            _gameState.value = GameState.InProgress(
                game = Game(word = repository.readWord().uppercase()),
                showGameEndPopUp = false
            )
        }
    }

    fun onKeyClicked(text: String) {
        try {
            val gameStateInProgress = _gameState.value as GameState.InProgress
            val game = gameStateInProgress.game
            val currentGuesses = game.guesses
            val guess = currentGuesses[currentRow]
            val updatedGuess = guess.copy(cards = guess.cards.mapIndexed { i, card ->
                if (i == column) {
                    Letter.Unknown(text)
                } else card
            })
            column += 1
            val updatedGame =
                game.copy(guesses = currentGuesses.mapIndexed { i, currentGuess ->
                    if (i == currentRow) updatedGuess else currentGuess
                })
            _gameState.value = gameStateInProgress.copy(game = updatedGame)
        } catch (e: java.lang.IndexOutOfBoundsException) {
            if (currentRow == 4) {
                // cant go beyond currentRow index 4 -- do nothing
            } else {
                if (rowChecked) {
                    rowChecked = false
                    currentRow++
                    column = 0
                    onKeyClicked(text)
                }
            }
        }
    }


    private fun checkLetterPlacementIsCorrect() {
        //change card colours for each letter
        if (column == 6) {
            val game = (_gameState.value as GameState.InProgress).game
            val word = game.word
            val copyWord = word.map { it }.toMutableList()
            val currentGuesses = game.guesses
            val currentGuess = currentGuesses[currentRow]
            // remove letters from copies and check against each other to make sure yellow only called once
            // val guess cannot be used here because it is a string only,
            // guessArray[currentRow] contains more info such as colour of the square

            val greenLetterList = game.greenLetters.toMutableList()
            val yellowLetterList = game.yellowLetters.toMutableList()
            val grayLetterList = game.grayLetters.toMutableList()

            val updatedCards = currentGuess.cards.mapIndexed { index, card ->
                when {
                    card.text[0] == word[index] -> {
                        copyWord.remove(card.text[0])
                        // add letter to list for keyboard
                        greenLetterList += card.text
                        Letter.CorrectInRightPosition(card.text)
                    }
                    card.text in copyWord.toString() -> {
                        copyWord.remove(card.text[0])
                        // add letter to list for keyboard
                        yellowLetterList += card.text
                        Letter.CorrectInWrongPosition(card.text)
                    }
                    else -> {
                        // add letter to list for keyboard
                        grayLetterList += card.text
                        Letter.WrongLetter(card.text)
                    }
                }
            }

            val guessWord = currentGuess.cards.joinToString("") { it.text }
            val showGameEndPopUp = guessWord == word

            _gameState.value =
                GameState.InProgress(
                    game = game.copy(
                        guesses = game.guesses.mapIndexed { index, guess ->
                            if (index == currentRow) guess.copy(cards = updatedCards) else guess
                        },
                        greenLetters = greenLetterList,
                        yellowLetters = yellowLetterList,
                        grayLetters = grayLetterList
                    ),
                    showGameEndPopUp = showGameEndPopUp
                )
        }

        rowChecked = true
    }

    private fun checkKeyboard() {
        //change key colours according to correctness
        val gameStateInProgress = _gameState.value as GameState.InProgress
        val game = gameStateInProgress.game
        val keyboard = game.keyboard

        _gameState.value = gameStateInProgress.copy(
            game = game.copy(
                keyboard = keyboard.copy(
                    firstRow = getUpdatedRow(keyboard.firstRow, game),
                    secondRow = getUpdatedRow(keyboard.secondRow, game),
                    thirdRow = getUpdatedRow(keyboard.thirdRow, game)
                )
            )
        )
    }

    private fun getUpdatedRow(
        row: List<Letter>,
        game: Game
    ) = row.map { letter ->
        when (letter.text) {
            in game.greenLetters -> Letter.CorrectInRightPosition(letter.text)
            in game.yellowLetters -> Letter.CorrectInWrongPosition(letter.text)
            in game.grayLetters -> Letter.WrongLetter(letter.text)
            else -> Letter.Unknown(letter.text)
        }
    }

    private suspend fun checkWordExists(): Boolean {
        // API call: returns true if word is in dictionary
        var exists = false
        val game = (_gameState.value as GameState.InProgress).game
        val currentGuess = game.guesses[currentRow]
        val guess = currentGuess.cards.joinToString("") { it.text }.lowercase()
        val job = CoroutineScope(Dispatchers.IO).launch {
            val url = URL("https://api.dictionaryapi.dev/api/v2/entries/en/$guess")
            val urlConnection = url.openConnection() as HttpURLConnection
            val responseCode = urlConnection.responseCode
            if (responseCode in 200..299) {
                exists = true
            }
        }
        job.join()
        return exists
    }

    fun onNextWordClick() {
        currentRow = 0
        column = 0
        viewModelScope.launch {
            repository.updateWord((_gameState.value as GameState.InProgress).game.word.lowercase())
            startGame()
        }

    }

    fun onSubmitClick(context: Context) {
        viewModelScope.launch {
            if (checkWordExists()) {
                checkLetterPlacementIsCorrect()
                checkKeyboard()
                currentRow++
                column = 0
            } else {
                toastWordNotFound(context)
            }
        }
    }

    fun onBackspaceClick() {
        removeLetter()
    }

    private fun removeLetter() {
        try {
            val game = (_gameState.value as GameState.InProgress).game
            val currentGuess = game.guesses[currentRow]
            val updatedGuess =
                currentGuess.copy(cards = currentGuess.cards.mapIndexed { index, card ->
                    if (index == column - 1) {
                        Letter.Unknown("")
                    } else card
                })
            _gameState.value =
                GameState.InProgress(
                    game = game.copy(guesses = game.guesses.mapIndexed { index, guess ->
                        if (index == currentRow) updatedGuess else guess
                    }),
                    showGameEndPopUp = false
                )
            column--
        } catch (e: IndexOutOfBoundsException) {
            // do nothing
        }
    }

    private fun toastWordNotFound(context: Context) {
        Toast.makeText(context, "Word does not exist", Toast.LENGTH_SHORT).show()
    }

    fun onDismissGameEndPopUp() {
        _gameState.value = (_gameState.value as GameState.InProgress).copy(showGameEndPopUp = false)
    }
}
