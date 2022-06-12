package com.pleavinseven.wordlesixcompose

import android.app.Application
import android.content.Context
import android.widget.Toast
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.toMutableStateList
import androidx.compose.ui.graphics.Color
import androidx.lifecycle.*
import com.pleavinseven.wordlesixcompose.data.WordListDatabase
import com.pleavinseven.wordlesixcompose.data.repository.WordRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.net.HttpURLConnection
import java.net.URL


data class CardData(var text: String, var colour: Color)
data class KeyData(var text: String, val size: Int, val colour: Color)

class HomeViewModel(application: Application) : ViewModel() {

    var gameIsInPlay = mutableStateOf(true)
    lateinit var word: String
    private val wordDb = WordListDatabase.getDatabase(application)
    private val wordDao = wordDb.wordlistDao()
    private val repository = WordRepository(wordDao)

    init {
        getWord()
    }

    private var currentRow = 0
    private val green = Color(46, 125, 50)
    private val yellow = Color(245, 212, 66)
    var guessArray = List(5) { List(6) { CardData("", Color.White) }.toMutableStateList() }
    private var column = 0
    var rowChecked = false
    private var greenLetterList = mutableListOf<String>()
    private var yellowLetterList = mutableListOf<String>()
    private var grayLetterList = mutableListOf<String>()
    val firstRowKeyboard = listOf("Q", "W", "E", "R", "T", "Y", "U", "I", "O", "P")
        .map { text: String -> KeyData(text, 35, Color.White) }.toMutableStateList()
    val secondRowKeyboard = "ASDFGHJKL".toCharArray()
        .map { text: Char -> KeyData(text.toString(), 35, Color.White) }.toMutableStateList()
    val thirdRowKeyboard = "ZXCVBNM".toCharArray()
        .map { text: Char -> KeyData(text.toString(), 35, Color.White) }.toMutableStateList()


    private fun getWord() {
        viewModelScope.launch {
            word = repository.readWord().uppercase() // retrieve word from database
        }
    }

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
        //change card colours for each letter
        if (column == 6) {
            val copyWord = word.map { it }.toMutableList()
            val guess = guessArray[currentRow].joinToString("") { it.text }
            // remove letters from copies and check against each other to make sure yellow only called once
            // val guess cannot be used here because it is a string only,
            // guessArray[currentRow] contains more info such as colour of the square
            for (i in guessArray[currentRow].indices) {
                val letter = guessArray[currentRow][i]
                if (letter.text[0] == word[i]) {
                    guessArray[currentRow][i] = letter.copy(colour = green)
                    copyWord.remove(letter.text[0])
                    // add letter to list for keyboard
                    greenLetterList += letter.text

                }
            }
            for (i in guessArray[currentRow].indices) {
                val letter = guessArray[currentRow][i]
                if (letter.colour != green) {
                    if (letter.text in copyWord.toString()) {
                        guessArray[currentRow][i] = letter.copy(colour = yellow)
                        copyWord.remove(letter.text[0])
                        // add letter to list for keyboard
                        yellowLetterList += letter.text
                    } else {
                        guessArray[currentRow][i] = letter.copy(colour = Color.DarkGray)
                        // add letter to list for keyboard
                        grayLetterList += letter.text
                    }
                }
            }
            if (guess == word) {
                // if guess is correct buttons are blocked
                gameIsInPlay.value = false
                newGame()
            }
        }
        rowChecked = true
    }

    fun newGame() {
        greenLetterList.clear()
        grayLetterList.clear()
        yellowLetterList.clear()
        guessArray = List(5) { List(6) { CardData("", Color.White) }.toMutableStateList() }
        currentRow = 0
        column = 0
        viewModelScope.launch {
            repository.updateWord(word) // change this so it only happens if you got the word?
            word = repository.readWord() // retrieve word from database
        }
    }

    fun checkKeyboard() {
        //change key colours according to correctness
        val rows = arrayOf(firstRowKeyboard, secondRowKeyboard, thirdRowKeyboard)
        for (row in rows) {
            for (i in 0..9) {
                try {
                    val letter = row[i]
                    // green or gray keys cannot be changed
                    if (letter.colour == green || letter.colour == Color.DarkGray) {
                        continue
                    } else {
                        if (letter.text in greenLetterList) {
                            row[i] = letter.copy(text = letter.text, colour = green)
                            continue
                        }

                        if (letter.text in yellowLetterList) {
                            row[i] = letter.copy(text = letter.text, colour = yellow)
                            continue
                        }

                        if (letter.text in grayLetterList) {
                            row[i] = letter.copy(text = letter.text, colour = Color.DarkGray)
                        }
                    }
                    // not all rows are length 9: catch anything longer
                } catch (e: java.lang.IndexOutOfBoundsException) {
                    continue
                }
            }
        }
    }

    suspend fun checkWordExists(): Boolean {
        // API call: returns true if word is in dictionary
        var guess = ""
        var exists = false
        for (i in guessArray[currentRow].indices) {
            guess += guessArray[currentRow][i].text.lowercase()
        }
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

    fun removeLetter() {
        try {
            guessArray[currentRow][column - 1] = CardData("", Color.White)
            column--
        } catch (e: IndexOutOfBoundsException) {
            // do nothing
        }
    }

    fun toastWordNotFound(context: Context) {
        Toast.makeText(context, "Word does not exist", Toast.LENGTH_SHORT).show()
    }
}
