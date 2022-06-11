package com.pleavinseven.wordlesixcompose.ui

import android.app.Application
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material.AlertDialog
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.Card
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material.TextButton
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.filled.KeyboardArrowLeft
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.wordlesixcompose.R
import com.pleavinseven.wordlesixcompose.HomeViewModel
import com.pleavinseven.wordlesixcompose.ui.model.Game
import com.pleavinseven.wordlesixcompose.ui.model.GameState
import com.pleavinseven.wordlesixcompose.ui.model.Guess
import com.pleavinseven.wordlesixcompose.ui.model.Keyboard


@Preview(showBackground = true, showSystemUi = true)
@Composable
fun HomeScreen(viewModel: HomeViewModel = HomeViewModel(LocalContext.current.applicationContext as Application)) {
    val context = LocalContext.current
    val gameState = viewModel.gameState
    (gameState.value as? GameState.InProgress)?.let {
        HomeContent(
            game = it.game,
            showGameEndPopUp = it.showGameEndPopUp,
            onKeyClick = { viewModel.onKeyClicked(it) },
            onSubmitClick = { viewModel.onSubmitClick(context) },
            onBackspaceClick = { viewModel.onBackspaceClick() },
            onDismissGameEndPopUp = { viewModel.onDismissGameEndPopUp() },
            onNextWordClick = { viewModel.onNextWordClick() }
        )
    }
}

@Composable
private fun HomeContent(
    game: Game,
    showGameEndPopUp: Boolean,
    onKeyClick: (String) -> Unit,
    onSubmitClick: () -> Unit,
    onBackspaceClick: () -> Unit,
    onDismissGameEndPopUp: () -> Unit,
    onNextWordClick: () -> Unit
) {
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
            WordGrid(game.guesses)
        }
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(0.dp, 4.dp),
            verticalArrangement = Arrangement.Bottom
        ) {
            KeyboardView(
                keyboard = game.keyboard,
                onKeyClick = { text -> onKeyClick(text) },
                onSubmitClick = { onSubmitClick() },
                onBackspaceClick = { onBackspaceClick() }
            )
        }
    }
        GameEndPopUp(
            showGameEndPopUp,
            word = game.word,
            onDismiss = onDismissGameEndPopUp,
            onNextWordClick = onNextWordClick
        )
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
fun KeyboardView(
    keyboard: Keyboard,
    onKeyClick: (String) -> Unit,
    onSubmitClick: () -> Unit,
    onBackspaceClick: () -> Unit
) {
    Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceEvenly) {
        keyboard.firstRow.forEach {
            MyKeyboardButton(it.text, it.colour, onClick = { onKeyClick(it.text) })
        }
    }

    Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceEvenly) {
        keyboard.secondRow.forEach {
            MyKeyboardButton(it.text, it.colour, onClick = { onKeyClick(it.text) })
        }
    }

    Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceEvenly) {
        MyEnterButton(onSubmitClick)
        keyboard.thirdRow.forEach {
            MyKeyboardButton(it.text, it.colour, onClick = { onKeyClick(it.text) })
        }
        MyBackButton(onBackspaceClick)
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
fun WordGrid(guesses: List<Guess>) {
    guesses.forEach { guess ->
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceEvenly
        ) {
            guess.cards.forEach {
                MyCard(it.text, it.colour)
            }
        }
    }
}

@Composable
fun MyKeyboardButton(text: String, colour: Color, onClick: () -> Unit) {

    Button(
        onClick = onClick,
        modifier = Modifier
            .width(35.dp)
            .height(60.dp)
            .padding(0.dp, 2.dp),
        colors = ButtonDefaults.buttonColors(backgroundColor = colour),
        border = BorderStroke(2.dp, Color.LightGray)
    ) {
        Text(text = text, textAlign = TextAlign.Center)
    }
}

@Composable
fun MyBackButton(onClick: () -> Unit) {
    Button(
        onClick = onClick,
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
fun MyEnterButton(onClick: () -> Unit) {
    Button(
        onClick = onClick,
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
fun GameEndPopUp(
    showGameEndPopUp: Boolean,
    word: String,
    onDismiss: () -> Unit,
    onNextWordClick: () -> Unit
) {
    if (showGameEndPopUp) {
        AlertDialog(
            onDismissRequest = onDismiss,
            title = {
                Text(
                    text = "Congrats! The word was $word!",
                    color = Color.Black
                )
            },
            shape = MaterialTheme.shapes.medium,
            backgroundColor = MaterialTheme.colors.surface,
            confirmButton = {
                TextButton(
                    onClick = onNextWordClick,
                    modifier = Modifier
                        .width(53.dp)
                        .height(50.dp),
                    colors = ButtonDefaults.buttonColors(backgroundColor = Color.White),
                    border = BorderStroke(2.dp, Color.LightGray),
                )
                { Text(text = "Next Word") }
            }
        )
    }
}


