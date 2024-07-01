package com.example.presentation.di

import com.example.domain.usecases.DeleteNoteUseCase
import com.example.domain.usecases.GetNoteUseCase
import com.example.domain.usecases.GetNotesUseCase
import com.example.domain.usecases.InsertNoteUseCase
import com.example.presentation.notedetails.NoteDetailsViewModel
import com.example.presentation.notes.NotesViewModel

object PresentationInjector {

    private lateinit var getNotesUseCase: GetNotesUseCase
    private lateinit var deleteNoteUseCase: DeleteNoteUseCase
    private lateinit var insertNoteUseCase: InsertNoteUseCase
    private  lateinit var getNoteUseCase: GetNoteUseCase
    fun init(
        getNotesUseCase: GetNotesUseCase,
        deleteNoteUseCase: DeleteNoteUseCase,
        insertNoteUseCase: InsertNoteUseCase,
        getNoteUseCase: GetNoteUseCase
    ) {
        this.getNotesUseCase = getNotesUseCase
        this.insertNoteUseCase = insertNoteUseCase
        this.deleteNoteUseCase = deleteNoteUseCase
        this.getNoteUseCase = getNoteUseCase
    }

    fun inject(factory: NotesViewModel.Factory) {
        factory.getNotesUseCase = getNotesUseCase
        factory.deleteNoteUseCase = deleteNoteUseCase
        factory.insertNoteUseCase = insertNoteUseCase
    }

    fun inject(factory: NoteDetailsViewModel.Factory) {
        factory.getNoteUseCase = getNoteUseCase
        factory.insertNoteUseCase = insertNoteUseCase
        factory.deleteNoteUseCase = deleteNoteUseCase
    }
}