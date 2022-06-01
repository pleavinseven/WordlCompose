package com.pleavinseven.wordlesixcompose.ui

import android.app.Application
import android.content.Context
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.filled.KeyboardArrowLeft
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.PopupProperties
import com.pleavinseven.wordlesixcompose.HomeViewModel
import com.example.wordlesixcompose.R
import kotlinx.coroutines.launch


@Preview(showBackground = true, showSystemUi = true)
@Composable
fun HomeScreen() {
    val viewModel = HomeViewModel(LocalContext.current.applicationContext as Application)
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
            WordGrid(viewModel)
        }
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(0.dp, 4.dp),
            verticalArrangement = Arrangement.Bottom
        ) {
            Keyboard(viewModel)
        }
    }
    GameWinPopUp(viewModel = viewModel)
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
fun Keyboard(viewModel: HomeViewModel) {
    Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceEvenly) {
        viewModel.firstRowKeyboard.forEach {
            MyKeyboardButton(viewModel, it.text, it.size, it.colour)
        }
    }

    Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceEvenly) {
        viewModel.secondRowKeyboard.forEach {
            MyKeyboardButton(viewModel, it.text, it.size, it.colour)
        }
    }

    Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceEvenly) {
        MyEnterButton(viewModel)
        viewModel.thirdRowKeyboard.forEach {
            MyKeyboardButton(viewModel, it.text, it.size, it.colour)
        }
        MyBackButton(viewModel)

    }
}


@Composable
fun MyCard(text: String, colour: Color) {

    Card(
        modifier = Modifier
            .padding(4.dp, 8.dp)
            .height(55.dp)
            .aspectRatio(1f),
        backgroundColor = colour,
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
fun WordGrid(viewModel: HomeViewModel) {
    viewModel.guessArray.forEach { rowCards ->
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceEvenly
        ) {
            rowCards.forEach {
                MyCard(it.text, it.colour)
            }
        }
    }
}

@Composable
fun MyKeyboardButton(viewModel: HomeViewModel, text: String, width: Int, colour: Color) {

    Button(
        onClick = {
            if (viewModel.gameIsInPlay.value) {
                viewModel.addLettersToGrid(text)
            }
        },
        modifier = Modifier
            .width(width.dp)
            .height(60.dp)
            .padding(0.dp, 2.dp),
        colors = ButtonDefaults.buttonColors(backgroundColor = colour),
        border = BorderStroke(2.dp, Color.LightGray)
    ) {
        Text(text = text, textAlign = TextAlign.Center)
    }
}

@Composable
fun MyBackButton(viewModel: HomeViewModel) {
    Button(
        onClick = {
            if (viewModel.gameIsInPlay.value) {
                viewModel.removeLetter()
            }
        },
        modifier = Modifier
            .width(50.dp)
            .height(60.dp)
            .padding(0.dp, 2.dp),
        colors = ButtonDefaults.buttonColors(backgroundColor = Color.White),
        border = BorderStroke(2.dp, Color.LightGray)
    ) {
        Icon(
            Icons.Filled.KeyboardArrowLeft,
            contentDescription = "Remove last letter"
        )
    }
}

@Composable
fun MyEnterButton(viewModel: HomeViewModel) {
    val coroutineScope = rememberCoroutineScope()
    val mContext = LocalContext.current

    Button(
        onClick = {
            if (viewModel.gameIsInPlay.value) {
                coroutineScope.launch {
                    if (viewModel.checkWordExists()) {
                        viewModel.checkLetterPlacementIsCorrect()
                        viewModel.checkKeyboard()
                    } else {
                        viewModel.toastWordNotFound(mContext)
                    }
                }
            }
        },
        modifier = Modifier
            .width(50.dp)
            .height(60.dp)
            .padding(0.dp, 2.dp),
        colors = ButtonDefaults.buttonColors(backgroundColor = Color.White),
        border = BorderStroke(2.dp, Color.LightGray)
    )
    {
        Icon(
            Icons.Filled.CheckCircle,
            contentDescription = "Enter"
        )
    }
}

@Composable
fun GameWinPopUp(viewModel: HomeViewModel) {
    val openDialog = remember { mutableStateOf(viewModel.gameIsInPlay) }
    if (!openDialog.value.value) {
        AlertDialog(
            onDismissRequest = { openDialog.value.value = true },
            title = { Text(text = "Congrats", color = Color.Black) },
            shape = MaterialTheme.shapes.medium,
            backgroundColor = MaterialTheme.colors.surface,
            buttons = {
                Row(
                    modifier = Modifier.padding(all = 8.dp),
                    horizontalArrangement = Arrangement.Center
                ) {
                    Button(

                        modifier = Modifier.fillMaxWidth(),
                        onClick = {
                            openDialog.value.value = true
                        }
                    ) {
                        Text("Dismiss")
                    }
                }
            }
        )
    }
}


