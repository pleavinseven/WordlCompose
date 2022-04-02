package com.example.wordlesixcompose.ui

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.KeyboardArrowLeft
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.wordlesixcompose.HomeViewModel
import com.example.wordlesixcompose.R


val viewModel = HomeViewModel()
val guessArray = viewModel.guessArray

@Preview(showBackground = true, showSystemUi = true)
@Composable
fun HomeScreen() {
    Column(
        modifier = Modifier
            .fillMaxWidth(),
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        Spacer(
            modifier = Modifier
                .fillMaxWidth()
                .height(40.dp)
        )
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .weight(1f)
        ) {
            WordGrid()
        }
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(0.dp, 4.dp),
            verticalArrangement = Arrangement.Bottom
        ) {
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

@Composable
fun WordGrid() {
    CardRow()
    CardRow()
    CardRow()
    CardRow()
    CardRow()
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
        MyEnterButton()
        MyKeyboardButton(text = "Z", width = 35)
        MyKeyboardButton(text = "X", width = 36)
        MyKeyboardButton(text = "C", width = 36)
        MyKeyboardButton(text = "V", width = 36)
        MyKeyboardButton(text = "B", width = 36)
        MyKeyboardButton(text = "N", width = 36)
        MyKeyboardButton(text = "M", width = 36)
        MyBackButton()

    }
}

@Composable
fun MyCard(text: String) {
    Card(
        modifier = Modifier
            .padding(4.dp, 8.dp)
            .height(55.dp)
            .aspectRatio(1f),
        backgroundColor = Color.White,
        border = BorderStroke(2.dp, Color.Black),
    ) {
        Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
            Text(
                text = text,
                fontSize = 20.sp,
                fontWeight = FontWeight.Bold
                )
        }

    }
}

@Composable
fun CardRow() {
    guessArray.chunked(6).forEach { rowCards ->
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceEvenly
        ) {
            rowCards.forEach {
                    MyCard(it)
            }
        }
    }
}

@Composable
fun MyKeyboardButton(text: String, width: Int) {

    val buttonColour by remember {
        mutableStateOf(Color.LightGray)
    }

    Button(
        onClick = {
            guessArray[viewModel.column] = text
            viewModel.column += 1
        },
        modifier = Modifier
            .width(width.dp)
            .height(60.dp)
            .padding(0.dp, 2.dp),
        colors = ButtonDefaults.buttonColors(backgroundColor = buttonColour)
    ) {
        Text(text = text, textAlign = TextAlign.Center)
    }
}

@Composable
fun MyBackButton() {
    Button(
        onClick = { viewModel.removeLetter()},
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

@Composable
fun MyEnterButton() {
    Button(
        onClick = { viewModel.checkLetterPlacementIsCorrect() },
        modifier = Modifier
            .width(70.dp)
            .height(60.dp)
            .padding(0.dp, 2.dp),
        colors = ButtonDefaults.buttonColors(backgroundColor = Color.LightGray)
    )
    { Text(text = "Enter", textAlign = TextAlign.Center) }
}
