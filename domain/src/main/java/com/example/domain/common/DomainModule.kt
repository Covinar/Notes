package com.example.domain.common

import com.example.domain.repositories.NotesRepository
import com.example.domain.usecases.DeleteNoteUseCase
import com.example.domain.usecases.DeleteNoteUseCaseImpl
import com.example.domain.usecases.GetNoteUseCase
import com.example.domain.usecases.GetNoteUseCaseImpl
import com.example.domain.usecases.GetNotesUseCase
import com.example.domain.usecases.GetNotesUseCaseImpl
import com.example.domain.usecases.InsertNoteUseCase
import com.example.domain.usecases.InsertNoteUseCaseImpl

object DomainModule {

    fun provideDeleteNoteUseCase(notesRepository: NotesRepository): DeleteNoteUseCase = DeleteNoteUseCaseImpl(notesRepository)

    fun provideInsertNoteUseCase(notesRepository: NotesRepository): InsertNoteUseCase = InsertNoteUseCaseImpl(notesRepository)

    fun provideGetNotesUseCase(notesRepository: NotesRepository): GetNotesUseCase = GetNotesUseCaseImpl(notesRepository)

    fun provideGetNoteUseCase(notesRepository: NotesRepository): GetNoteUseCase = GetNoteUseCaseImpl(notesRepository)

}