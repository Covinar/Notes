package com.example.presentation.notedetails

import android.annotation.SuppressLint
import androidx.activity.compose.BackHandler
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Divider
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.example.presentation.R

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun NoteDetails(
    viewModel: NoteDetailsViewModel,
    id: Int,
    onBackIconClicked: () -> Unit
) {

    val state by viewModel.state.collectAsState()

    LaunchedEffect(key1 = "notes_details_screen", block = {
        viewModel.getNote(id)
    })

    Scaffold(
        topBar = {
            TopBar(
                isPined = state.note.isPined,
                onBackIconClicked = {
                    viewModel.updateNote()
                    onBackIconClicked()
                },
                onPinIconClicked = {
                    viewModel.changePin()
                }

            )
        }
    ) {
        Column(
            modifier = Modifier
                .padding(it)
        ) {

            TextField(
                modifier = Modifier
                    .fillMaxWidth(),
                value = state.note.title,
                textStyle = TextStyle(fontWeight = FontWeight.Bold),
                onValueChange = { title ->
                    viewModel.updateTitle(title)
                },
                placeholder = { Text(text = "Title")},
                colors = TextFieldDefaults.textFieldColors(
                    containerColor = Color.White
                )
            )

            TextField(
                modifier = Modifier
                    .fillMaxSize(),
                value = state.note.text,
                onValueChange = { text ->
                    viewModel.updateText(text)
                },
                placeholder = { Text(text = "Enter text") },
                colors = TextFieldDefaults.textFieldColors(
                    containerColor = Color.White
                )
            )
        }
    }

}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun TopBar(
    isPined: Boolean,
    onBackIconClicked: () -> Unit,
    onPinIconClicked: () -> Unit
) {

    val backHandlingEnabled by remember { mutableStateOf(true) }

    BackHandler(backHandlingEnabled) {
        onBackIconClicked()
    }

    Column {
        TopAppBar(
            title = {},
            actions = {
                val painter = if (isPined) painterResource(id = R.drawable.ic_pin) else painterResource(id = R.drawable.ic_unpin)
                Icon(
                    painter = painter,
                    contentDescription = null,
                    modifier = Modifier
                        .padding(8.dp)
                        .clickable {
                            onPinIconClicked()
                        }
                )
            },
            navigationIcon = {
                Icon(
                    painter = painterResource(id = R.drawable.ic_back),
                    contentDescription = null,
                    modifier = Modifier
                        .padding(8.dp)
                        .clickable {
                            onBackIconClicked()
                        }
                )
            },
            colors = TopAppBarDefaults.centerAlignedTopAppBarColors(
                containerColor = Color.White
            )
        )
        Divider(
            thickness = 2.dp,
            color = Color.Black
        )
    }

}
