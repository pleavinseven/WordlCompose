package com.example.wordlesixcompose

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.material.*
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.example.wordlesixcompose.ui.HomeScreen
import com.example.wordlesixcompose.ui.Title


class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            Scaffold(
                topBar = {
                    TopAppBar(
                        backgroundColor = Color.White,
                        title = { Title() },
                        elevation = 30.dp
                    )
                }
            ) {
                HomeScreen()
            }
        }
    }
}



