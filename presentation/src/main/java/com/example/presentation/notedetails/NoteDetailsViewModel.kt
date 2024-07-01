package com.example.presentation.notedetails

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.example.domain.models.Note
import com.example.domain.usecases.DeleteNoteUseCase
import com.example.domain.usecases.GetNoteUseCase
import com.example.domain.usecases.GetNotesUseCase
import com.example.domain.usecases.InsertNoteUseCase
import com.example.presentation.di.PresentationInjector
import com.example.presentation.notes.NotesViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.util.Date

class NoteDetailsViewModel(
    private val getNoteUseCase: GetNoteUseCase,
    private val insertNoteUseCase: InsertNoteUseCase,
    private val deleteNoteUseCase: DeleteNoteUseCase
) : ViewModel() {

    private val _state = MutableStateFlow(State())
    val state = _state.asStateFlow()

    fun getNote(id: Int) {
        if (id == 0) return
        viewModelScope.launch(Dispatchers.IO) {
            val note = getNoteUseCase(id)
            withContext(Dispatchers.Main) {
                _state.update {
                    it.copy(note = note)
                }
            }
        }
    }

    fun updateNote() {
        if (state.value.note.title.isEmpty() && state.value.note.text.isEmpty()) {
            viewModelScope.launch(Dispatchers.IO) {
                deleteNoteUseCase(note = state.value.note)
            }
        } else {
            viewModelScope.launch(Dispatchers.IO) {
                insertNoteUseCase(note = state.value.note)
            }
        }
    }

    fun updateTitle(title: String) {
        val note = state.value.note.copy(title = title)
        _state.update { it.copy(note = note) }
    }

    fun updateText(text: String) {
        val note = state.value.note.copy(text = text)
        _state.update { it.copy(note = note) }
    }

    fun changePin() {
        val note = state.value.note.copy(isPined = state.value.note.isPined.not())
        _state.update { it.copy(note = note) }
    }

    data class State(
        val note: Note = Note(0, "", "", Date().time, false)
    )

    class Factory : ViewModelProvider.Factory {

        lateinit var getNoteUseCase: GetNoteUseCase
        lateinit var insertNoteUseCase: InsertNoteUseCase
        lateinit var deleteNoteUseCase: DeleteNoteUseCase

        override fun <T : ViewModel> create(modelClass: Class<T>): T {
            PresentationInjector.inject(this)
            return NoteDetailsViewModel(getNoteUseCase, insertNoteUseCase, deleteNoteUseCase) as T
        }

    }

}