package com.example.presentation.notes

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.example.domain.common.Resource
import com.example.domain.models.Note
import com.example.domain.usecases.DeleteNoteUseCase
import com.example.domain.usecases.GetNotesUseCase
import com.example.domain.usecases.InsertNoteUseCase
import com.example.presentation.di.PresentationInjector
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.emitAll
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class NotesViewModel(
    private val getNotesUseCase: GetNotesUseCase,
    private val deleteNoteUseCase: DeleteNoteUseCase,
    private val insertNoteUseCase: InsertNoteUseCase
) : ViewModel() {

    private val _state = MutableStateFlow(State())
    val state: StateFlow<State> = _state.asStateFlow()

    private var originalNotes: List<Note> = mutableListOf()

    fun getNotes() {
        getNotesUseCase()
            .flowOn(Dispatchers.IO)
            .onEach { notes ->
                when (notes) {
                    Resource.Loading -> Unit
                    is Resource.Success -> {
                        val model = notes.model
                        val pined: MutableList<Note> = mutableListOf()
                        val unPined: MutableList<Note> = mutableListOf()
                        for (note in model) {
                            if (note.isPined) {
                                pined.add(note)
                            } else {
                                unPined.add(note)
                            }
                        }
                        pined.sortByDescending {
                            it.date
                        }
                        unPined.sortByDescending {
                            it.date
                        }
                        _state.update {
                            it.copy(
                                notes = pined.plus(unPined),
                            )
                        }
                        originalNotes = pined.plus(unPined)
                    }
                    is Resource.Error -> Unit
                }
            }
            .flowOn(Dispatchers.Main)
            .launchIn(viewModelScope)
    }

    fun unPinNote(note: Note) {
        viewModelScope.launch(Dispatchers.IO) {
            insertNoteUseCase(note = note.copy(isPined = false))
        }
    }

    fun changeSearchMode() {
        _state.update {
            it.copy(isSearchMode = it.isSearchMode.not())
        }
    }

    fun changeEditMode() {
        _state.update {
            it.copy(isEditMode = it.isEditMode.not())
        }
    }

    fun changeDeleteMode() {
        _state.update {
            it.copy(isDeleteMode = it.isDeleteMode.not())
        }
    }

    fun search(searchQuery: String) {
        _state.update { it.copy(searchQuery = searchQuery) }
        val notesInSearch: MutableList<Note> = mutableListOf()
        if (searchQuery.isEmpty()) {
           _state.update {
               it.copy(notes = originalNotes)
           }
        } else {
            for (note in originalNotes) {
                if (note.title.startsWith(searchQuery)) {
                    notesInSearch.add(note)
                }
            }
            _state.update {
                it.copy(notes = notesInSearch)
            }
        }
    }

    fun checkedNote(note: Note) {
        val checkedNotes = state.value.checkedNotes.toMutableList()
        checkedNotes.add(note)
        _state.update {
            it.copy(checkedNotes = checkedNotes)
        }
    }


    fun uncheckNote(note: Note) {
        val uncheckNotes = state.value.checkedNotes.toMutableList()
        uncheckNotes.remove(note)
        _state.update {
            it.copy(checkedNotes = uncheckNotes)
        }
    }

    fun updateCheckedNotesList() {
        _state.update {
            it.copy(checkedNotes = emptyList())
        }
    }

    fun deleteNotes() {
        val checkedNotes = state.value.checkedNotes
        viewModelScope.launch(Dispatchers.IO) {
            for (note in checkedNotes) {
                deleteNoteUseCase(note)
            }
            _state.update {
                it.copy(checkedNotes = emptyList())
            }
        }
    }

    data class State(
        val notes: List<Note> = emptyList(),
        val checkedNotes: List<Note> = emptyList(),
        val isSearchMode: Boolean = false,
        val isEditMode: Boolean = false,
        val isDeleteMode: Boolean = false,
        val searchQuery: String = ""
    ) {
        val isEmpty: Boolean
            get() = notes.isEmpty()

        fun isChecked(note: Note): Boolean {
            return checkedNotes.contains(note)
        }
    }

    class Factory : ViewModelProvider.Factory {

        lateinit var getNotesUseCase: GetNotesUseCase
        lateinit var deleteNoteUseCase: DeleteNoteUseCase
        lateinit var insertNoteUseCase: InsertNoteUseCase

        override fun <T : ViewModel> create(modelClass: Class<T>): T {
            PresentationInjector.inject(this)
            return NotesViewModel(getNotesUseCase, deleteNoteUseCase, insertNoteUseCase) as T
        }

    }

}