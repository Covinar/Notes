package com.example.presentation.notes

import android.text.BoringLayout
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.AbsoluteRoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Divider
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.domain.models.Note
import com.example.presentation.R
import com.example.presentation.utils.DashedHorizontalDivider

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun NotesScreen(
    viewModel: NotesViewModel
) {

    val state by viewModel.state.collectAsState()

    LaunchedEffect(key1 = "notes_screen", block = {
        viewModel.getNotes()
    })

    Scaffold(
        topBar = {
            TopBar(state.isEmpty)
        }
    ) {
        if (state.isEmpty) {
            EmptyNotes(
                modifier = Modifier.padding(it)
            )
        } else {
            Notes(
                notes = state.notes,
                modifier = Modifier.padding(it)
            )
        }

    }

}
@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun TopBar(
    isEmpty: Boolean
) {
    Column {
        TopAppBar(
            title = {
                Text(
                    text = stringResource(id = R.string.top_bar_title),
                    fontWeight = FontWeight.Bold
                )
            },
            actions = {
                if (!isEmpty) {
                    Icon(painter = painterResource(id = R.drawable.search),
                        modifier = Modifier.padding(8.dp),
                        contentDescription = null
                    )
                    Icon(painter = painterResource(id = R.drawable.edit),
                        modifier = Modifier.padding(8.dp),
                        contentDescription = null
                    )
                    Image(painter = painterResource(id = R.drawable.resource_new),
                        modifier = Modifier.padding(8.dp),
                        contentDescription = null
                    )
                }
            }
        )
        Divider(
            thickness = 2.dp,
            color = Color.Black
        )
    }

}

@Composable
private fun EmptyNotes(modifier: Modifier) {

    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(horizontal = 16.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Icon(
            modifier = Modifier.padding(bottom = 8.dp),
            painter = painterResource(id = R.drawable.illustration),
            contentDescription = null,
        )
        Text(
            modifier = Modifier.padding(bottom = 8.dp),
            text = stringResource(id = R.string.empty_notes_title),
            fontWeight = FontWeight.Bold
        )
        Text(
            modifier = Modifier.padding(bottom = 8.dp),
            textAlign = TextAlign.Center,
            text = stringResource(id = R.string.empty_notes_description),
            maxLines = 2
        )
        Button(
            onClick = {},
            colors = ButtonDefaults.buttonColors(
                containerColor = Color.Transparent,
                contentColor = Color.Black
            ),
            shape = AbsoluteRoundedCornerShape(8.dp),
            border = BorderStroke(2.dp, Color.Black)
        ) {
            Text(
                text = stringResource(id = R.string.empty_notes_button),
                fontWeight = FontWeight.Bold
            )
        }
    }

}

@Composable
private fun NoteItem(
    note: Note,
    isLast: Boolean
) {

    Column(
        modifier = Modifier
            .background(Color.White)
            .padding(start = 8.dp),
    ) {
        Row(
            modifier = Modifier.padding(vertical = 8.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Column(
                modifier = Modifier.weight(1F)
            ) {
                Text(
                    text = note.title,
                    fontWeight = FontWeight.Bold,
                    overflow = TextOverflow.Ellipsis
                )
                Text(
                    text = note.text,
                    overflow = TextOverflow.Ellipsis
                )
            }
            if (note.isPined) {
                Icon(
                    painter = painterResource(id = R.drawable.pin),
                    contentDescription = null,
                )
            }

        }
        if (!isLast) {
            DashedHorizontalDivider(modifier = Modifier.fillMaxWidth())
        }
    }
}

@Composable
private fun Notes(notes: List<Note>, modifier: Modifier) {

    LazyColumn(
        modifier = modifier
    ) {
        itemsIndexed(
            items = notes,
            key = { _, note ->
                note.id
            }
        ) { index, note ->
            NoteItem(
                note = note,
                isLast = index == notes.size - 1
            )
        }
    }

}

