package com.example.wordlesixcompose

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.wordlesixcompose.ui.HomeScreen
import com.example.wordlesixcompose.ui.Title
import com.example.wordlesixcompose.ui.theme.WordleSixComposeTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            Scaffold(
                topBar = { TopAppBar(
                        backgroundColor = Color.White,
                        title = { Title() },
                        elevation = 30.dp
                ) }
            ) {
                HomeScreen()
            }
        }
    }
}



