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
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.update
import java.util.Date

class NotesViewModel(
    private val getNotesUseCase: GetNotesUseCase,
    private val deleteNoteUseCase: DeleteNoteUseCase,
    private val insertNoteUseCase: InsertNoteUseCase
) : ViewModel() {

    private val _state = MutableStateFlow(State())
    val state: StateFlow<State> = _state.asStateFlow()

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
                        pined.sortedBy {
                            it.date
                        }
                        unPined.sortedBy {
                            it.date
                        }
                        _state.update {
                            it.copy(
                                notes = pined.plus(unPined)
                            )
                        }
                    }

                    is Resource.Error -> Unit
                }
            }
            .flowOn(Dispatchers.Main)
            .launchIn(viewModelScope)
    }

    data class State(
        val notes: List<Note> = emptyList()
    ) {
        val isEmpty: Boolean
            get() = notes.isEmpty()
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