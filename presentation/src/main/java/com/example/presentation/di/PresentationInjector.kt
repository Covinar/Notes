package com.example.presentation.di

import com.example.domain.usecases.DeleteNoteUseCase
import com.example.domain.usecases.GetNotesUseCase
import com.example.domain.usecases.InsertNoteUseCase
import com.example.presentation.notes.NotesViewModel

object PresentationInjector {

    private lateinit var getNotesUseCase: GetNotesUseCase
    private lateinit var deleteNoteUseCase: DeleteNoteUseCase
    private lateinit var insertNoteUseCase: InsertNoteUseCase

    fun init(
        getNotesUseCase: GetNotesUseCase,
        deleteNoteUseCase: DeleteNoteUseCase,
        insertNoteUseCase: InsertNoteUseCase
    ) {
        this.getNotesUseCase = getNotesUseCase
        this.insertNoteUseCase = insertNoteUseCase
        this.deleteNoteUseCase = deleteNoteUseCase
    }


    fun inject(factory: NotesViewModel.Factory) {
        factory.getNotesUseCase = getNotesUseCase
        factory.deleteNoteUseCase = deleteNoteUseCase
        factory.insertNoteUseCase = insertNoteUseCase
    }

}