package com.pleavinseven.wordlesixcompose.database.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "wordlist")
data class WordList(
    @PrimaryKey(autoGenerate = true)
    val id: Int,
    val word: String,
    var used: Boolean
)