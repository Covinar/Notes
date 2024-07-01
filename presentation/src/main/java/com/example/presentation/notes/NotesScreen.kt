package com.example.presentation.notes

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.AbsoluteRoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
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
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.pluralStringResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import com.example.domain.models.Note
import com.example.presentation.R
import com.example.presentation.utils.DashedHorizontalDivider

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun NotesScreen(
    viewModel: NotesViewModel,
    onOpenNote: (id: Int) -> Unit,
) {

    val state by viewModel.state.collectAsState()

    LaunchedEffect(key1 = "notes_screen", block = {
        viewModel.getNotes()
    })

    Scaffold(
        topBar = {
            TopBar(
                checkedNotesCount = state.checkedNotes.size,
                isCheckedMoreThenOne = state.checkedNotes.isNotEmpty(),
                isEditMode = state.isEditMode,
                isSearchMode = state.isSearchMode,
                isEmpty = state.isEmpty,
                searchQuery = state.searchQuery,
                onAddClicked = {
                    onOpenNote(0)
                },
                onSearchClicked = {
                    viewModel.changeSearchMode()
                },
                onBackClicked = {
                    viewModel.changeSearchMode()
                    viewModel.search("")
                },
                onSearchQuery = {
                    viewModel.search(it)
                },
                onCloseClicked = {
                    viewModel.changeDeleteMode()
                    viewModel.changeEditMode()
                    viewModel.updateCheckedNotesList()
                },
                onEditClicked = {
                    viewModel.changeEditMode()
                },
                onDeleteButtonClicked = {
                    viewModel.changeDeleteMode()
                }
            )
        },
        containerColor = Color.White
    ) {
        if (state.isEmpty) {
            EmptyNotes(
                modifier = Modifier.padding(it),
                onButtonClicked = {
                    onOpenNote(0)
                }
            )
        } else {
            Box(
                modifier = Modifier
                    .fillMaxSize(),
            ) {
                Notes(
                    notes = state.notes,
                    modifier = Modifier.padding(it),
                    isEditMode = state.isEditMode,
                    isNoteChecked = { note ->
                        state.isChecked(note)
                    },
                    onNoteClicked = { note ->
                        if (!state.isEditMode) {
                            onOpenNote(note.id)
                        }
                    },
                    onPinIconClicked = { note ->
                        viewModel.unPinNote(note = note)
                    },
                    onUncheckIconClicked = { note ->
                        viewModel.checkedNote(note)
                    },
                    onCheckIconClicked = { note ->
                        viewModel.uncheckNote(note)
                    }
                )
                if (state.isDeleteMode && state.checkedNotes.isNotEmpty()) {
                    DeleteDialog(
                        modifier = Modifier
                            .padding(it)
                            .align(Alignment.BottomCenter),
                        checkedNotesCount = state.checkedNotes.size,
                        onBackClicked = {
                            viewModel.changeDeleteMode()
                        },
                        onDeleteClicked = {
                            viewModel.deleteNotes()
                            viewModel.changeDeleteMode()
                        }
                    )
                }
            }
        }
    }


}
@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun TopBar(
    checkedNotesCount: Int,
    isCheckedMoreThenOne: Boolean,
    isSearchMode: Boolean,
    isEditMode: Boolean,
    isEmpty: Boolean,
    searchQuery: String,
    onAddClicked: () -> Unit,
    onSearchClicked: () -> Unit,
    onBackClicked: () -> Unit,
    onSearchQuery: (String) -> Unit,
    onCloseClicked: () -> Unit,
    onEditClicked: () -> Unit,
    onDeleteButtonClicked: () -> Unit
    ) {
    Column {
        TopAppBar(
            title = {
                if (!isSearchMode){
                    Text(
                        text = stringResource(id = R.string.top_bar_title),
                        fontWeight = FontWeight.Bold
                    )
                } else {
                    TextField(
                        modifier = Modifier.fillMaxWidth(),
                        value = searchQuery,
                        textStyle = TextStyle(fontWeight = FontWeight.Bold),
                        onValueChange = {
                            onSearchQuery(it)
                        },
                        colors = TextFieldDefaults.textFieldColors(
                            containerColor = Color.White
                        )
                    )
                }
            },
            navigationIcon = {
                if (isSearchMode) {
                    Icon(
                        painter = painterResource(id = R.drawable.ic_back),
                        contentDescription = null,
                        modifier = Modifier.clickable { onBackClicked() }
                    )
                }
                if (isEditMode) {
                    Icon(
                        painter = painterResource(id = R.drawable.ic_close),
                        contentDescription = null,
                        modifier = Modifier
                            .padding(horizontal = 8.dp)
                            .clickable { onCloseClicked() }
                    )
                }
            },
            actions = {
                if (!isEmpty && !isSearchMode && !isEditMode) {
                    Icon(painter = painterResource(id = R.drawable.ic_search),
                        modifier = Modifier
                            .padding(8.dp)
                            .clickable {
                                onSearchClicked()
                            },
                        contentDescription = null,
                    )
                    Icon(painter = painterResource(id = R.drawable.ic_edit),
                        modifier = Modifier
                            .padding(8.dp)
                            .clickable {
                                onEditClicked()
                            },
                        contentDescription = null
                    )
                    Image(painter = painterResource(id = R.drawable.ic_add),
                        modifier = Modifier
                            .padding(8.dp)
                            .clickable {
                                onAddClicked()
                            },
                        contentDescription = null
                    )
                }
                if (isEditMode && isCheckedMoreThenOne) {
                    Button(
                        onClick = {
                            onDeleteButtonClicked()
                        },
                        colors = ButtonDefaults.buttonColors(
                            containerColor = Color.Transparent,
                            contentColor = Color.Black
                        ),
                        shape = AbsoluteRoundedCornerShape(8.dp),
                        border = BorderStroke(2.dp, Color.Black)
                    ) {
                        Text(
                            text = stringResource(id = R.string.top_bar_button, checkedNotesCount),
                            fontWeight = FontWeight.Bold
                        )
                    }
                }
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

@Composable
private fun EmptyNotes(
    modifier: Modifier,
    onButtonClicked: () -> Unit
) {

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
            onClick = {
                onButtonClicked()
            },
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
    isLast: Boolean,
    isEditMode: Boolean,
    isChecked: Boolean,
    onClicked: () -> Unit,
    onPinIconClicked: () -> Unit,
    onUncheckIconClicked: () -> Unit,
    onCheckIconClicked: () -> Unit,
    ) {

    Column(
        modifier = Modifier
            .background(Color.White)
            .padding(start = 8.dp)
            .clickable {
                onClicked()
            },
    ) {
        Row(
            modifier = Modifier.padding(vertical = 8.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            if (isEditMode) {
                if (!isChecked) {
                    Icon(
                        painter = painterResource(id = R.drawable.ic_unchecked),
                        contentDescription = null,
                        modifier = Modifier
                            .padding(horizontal = 8.dp)
                            .clickable {
                                onUncheckIconClicked()
                            }
                    )
                } else {
                    Image(
                        painter = painterResource(id = R.drawable.ic_checked),
                        contentDescription = null,
                        modifier = Modifier
                            .padding(horizontal = 8.dp)
                            .clickable {
                                onCheckIconClicked()
                            }
                    )
                }
            }

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
                    painter = painterResource(id = R.drawable.ic_pin),
                    contentDescription = null,
                    modifier = Modifier
                        .padding(end = 8.dp)
                        .clickable {
                            onPinIconClicked()
                        }
                )
            }

        }
        if (!isLast) {
            DashedHorizontalDivider(modifier = Modifier.fillMaxWidth())
        }
    }
}

@Composable
fun DeleteDialog(
    modifier: Modifier,
    checkedNotesCount: Int,
    onBackClicked: () -> Unit,
    onDeleteClicked: () -> Unit
) {
    Box(
        modifier = modifier
            .background(Color.White)
    ) {
        Column {
            Divider(
                thickness = 2.dp,
                color = Color.Black
            )
            Text(
                text = pluralStringResource(
                    id = R.plurals.delete_dialog_title,
                    count = checkedNotesCount,
                    checkedNotesCount
                ),
                fontWeight = FontWeight.Bold,
                modifier = Modifier.padding(start = 8.dp, top = 8.dp)
            )
            Text(
                text = stringResource(id = R.string.delete_dialog_description),
                modifier = Modifier.padding(start = 8.dp)
            )
            
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(8.dp)
            ) {
                Button(
                    onClick = {
                        onBackClicked()
                    },
                    modifier = Modifier
                        .weight(1F)
                        .padding(end = 8.dp),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = Color.Black,
                        contentColor = Color.Black
                    ),
                    shape = AbsoluteRoundedCornerShape(8.dp)
                ) {
                    Text(
                        text = stringResource(id = R.string.delete_dialog_back_button),
                        color = Color.White,
                        fontWeight = FontWeight.Bold
                    )
                }
                Button(
                    onClick = {
                        onDeleteClicked()
                    },
                    modifier = Modifier
                        .weight(1F)
                        .padding(start = 8.dp),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = Color.Transparent,
                        contentColor = Color.Black
                    ),
                    shape = AbsoluteRoundedCornerShape(8.dp),
                    border = BorderStroke(2.dp, Color.Black)
                ) {
                    Text(
                        text = stringResource(id = R.string.delete_dialog_delete_button),
                        color = Color.Black,
                        fontWeight = FontWeight.Bold
                    )
                }
            }
        }
    }
}

@Composable
private fun Notes(
    notes: List<Note>,
    modifier: Modifier,
    isEditMode: Boolean,
    isNoteChecked: (Note) -> Boolean,
    onNoteClicked: (note: Note) -> Unit,
    onPinIconClicked: (Note) -> Unit,
    onUncheckIconClicked: (Note) -> Unit,
    onCheckIconClicked: (Note) -> Unit
) {

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
                isLast = index == notes.size - 1,
                isEditMode = isEditMode,
                isChecked = isNoteChecked(note),
                onClicked = {
                    onNoteClicked(note)
                },
                onPinIconClicked = {
                    onPinIconClicked(note)
                },
                onUncheckIconClicked = {
                    onUncheckIconClicked(note)
                },
                onCheckIconClicked = {
                    onCheckIconClicked(note)
                }
            )
        }
    }

}

