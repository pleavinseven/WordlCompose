package com.example.wordlesixcompose.ui

import android.graphics.drawable.Icon
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.GridCells
import androidx.compose.foundation.lazy.LazyVerticalGrid
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.KeyboardArrowLeft
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
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
fun HomeScreen() {
    Column(
        modifier = Modifier
            .fillMaxWidth(),
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        Column(
            modifier = Modifier.fillMaxWidth().weight(1f)
        ) {
            WordGrid()
        }
        Column(modifier = Modifier
            .fillMaxWidth().padding(0.dp, 4.dp),
            verticalArrangement = Arrangement.Bottom) {
            Keyboard()
        }
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
fun WordGrid() {
    LazyVerticalGrid(
        cells = GridCells.Fixed(6),
        modifier = Modifier,
        state = rememberLazyListState(),

        ) {
        items(30) { item ->
            Card(
                modifier = Modifier
                    .padding(4.dp, 8.dp)
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
fun Keyboard() {
    Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceEvenly) {
        MyKeyboardButton(text = "Q", 35)
        MyKeyboardButton(text = "W", 35)
        MyKeyboardButton(text = "E", 35)
        MyKeyboardButton(text = "R", 35)
        MyKeyboardButton(text = "T", 35)
        MyKeyboardButton(text = "Y", 35)
        MyKeyboardButton(text = "U", 35)
        MyKeyboardButton(text = "I", 35)
        MyKeyboardButton(text = "O", 35)
        MyKeyboardButton(text = "P", 35)

//        Button(onClick = { /*TODO*/ }, Modifier.weight(1F)) {
//            Text(text = "Q", textAlign = TextAlign.Center)
//        }

    }
    Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceEvenly) {
        MyKeyboardButton(text = "A", width = 40)
        MyKeyboardButton(text = "S", width = 40)
        MyKeyboardButton(text = "D", width = 40)
        MyKeyboardButton(text = "F", width = 40)
        MyKeyboardButton(text = "G", width = 40)
        MyKeyboardButton(text = "H", width = 40)
        MyKeyboardButton(text = "J", width = 40)
        MyKeyboardButton(text = "K", width = 40)
        MyKeyboardButton(text = "L", width = 40)
    }
    Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceEvenly) {
        MyKeyboardButton(text = "Enter", width = 70)
        MyKeyboardButton(text = "Z", width = 35)
        MyKeyboardButton(text = "X", width = 36)
        MyKeyboardButton(text = "C", width = 36)
        MyKeyboardButton(text = "V", width = 36)
        MyKeyboardButton(text = "B", width = 36)
        MyKeyboardButton(text = "N", width = 36)
        MyKeyboardButton(text = "M", width = 36)
//        IconButton(onClick = { /*TODO*/ }, modifier = Modifier.width(35.dp).background(Color.Blue)) {
//            Icon(Icons.Filled.KeyboardArrowLeft, contentDescription = "Back")
//        }
        Button(onClick = { /*TODO*/ },
            modifier = Modifier
                .width(50.dp)
                .height(60.dp)
                .padding(0.dp, 2.dp),
            colors = ButtonDefaults.buttonColors(backgroundColor = Color.LightGray)
                ) {
            Icon(
                Icons.Filled.KeyboardArrowLeft,
                contentDescription = "Back"
            )
        }
    }
}

@Composable
fun MyKeyboardButton(text: String, width: Int) {
    Button(onClick = { /*TODO*/ },
        modifier = Modifier
            .width(width.dp)
            .height(60.dp)
            .padding(0.dp, 2.dp),
        colors = ButtonDefaults.buttonColors(backgroundColor = Color.LightGray)) {
        Text(text = text, textAlign = TextAlign.Center)
    }
}
