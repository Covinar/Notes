package com.example.presentation.notedetails

import androidx.compose.foundation.layout.Column
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.tooling.preview.Preview
import com.example.domain.models.Note
import java.util.Date

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun NotesDetails(note: Note) {

    Column() {
        var text by remember { mutableStateOf("Hello") }

        TextField(
            value = text,
            onValueChange = { text = it },
            label = { Text("Label") }
        )
    }

}

@Composable
@Preview
fun NotesDetailsPreview() {
    NotesDetails(note = Note(1, "Title", "Text", Date().time, false))
}