package com.example.wordlesixcompose.ui

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.GridCells
import androidx.compose.foundation.lazy.LazyVerticalGrid
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material.Card
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.wordlesixcompose.R
import com.example.wordlesixcompose.ui.theme.WordleSixComposeTheme

@Preview(showBackground = true, showSystemUi = true)
@Composable
fun HomeScreen(){
        Column( modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            WordGrid()
            Keyboard()

        }
}


@Composable
fun Title() {
    Text(
        text = stringResource(R.string.login_title),
        Modifier.fillMaxWidth(),
        textAlign = TextAlign.Center,
        fontWeight = FontWeight.Bold,
        fontSize = 20.sp,
        fontFamily = FontFamily.SansSerif // change later
    )
}

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun WordGrid(){
        LazyVerticalGrid(
            cells = GridCells.Fixed(6),
            modifier = Modifier,
            state = rememberLazyListState(),

        ) {
            items(30) { item ->
                Card(
                    modifier = Modifier.padding(4.dp, 8.dp)
                        .aspectRatio(1f),
                    backgroundColor = Color.White,
                    border = BorderStroke(2.dp, Color.Black),
                ) {
                    Text(
                        text = "",
                        fontSize = 24.sp,
                        textAlign = TextAlign.Center,
                        modifier = Modifier.padding(24.dp)
                    )
                }
            }
        }
    }


@Composable
fun Keyboard(){
    // set keyboard here
}