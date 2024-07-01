package com.example.notes.di

import android.content.Context
import com.example.data.common.DataModule
import com.example.data.database.NotesDao
import com.example.data.database.NotesDataBase
import com.example.domain.common.DomainModule
import com.example.domain.repositories.NotesRepository
import com.example.domain.usecases.DeleteNoteUseCase
import com.example.domain.usecases.GetNoteUseCase
import com.example.domain.usecases.GetNotesUseCase
import com.example.domain.usecases.InsertNoteUseCase
import com.example.presentation.di.PresentationInjector

object SingletonComponent {

    private lateinit var applicationContext: Context

    fun init(applicationContext: Context) {
        this.applicationContext = applicationContext
        initPresenterInjector()
    }

    private val notesDataBase: NotesDataBase by lazy {
        DataModule.provideNotesDataBase(applicationContext)
    }

    private val notesDao: NotesDao by lazy {
        DataModule.provideNotesDao(notesDataBase)
    }

    private val notesRepository: NotesRepository by lazy {
        DataModule.provideNotesRepository(notesDao)
    }

    private val insertNoteUseCase: InsertNoteUseCase
        get() = DomainModule.provideInsertNoteUseCase(notesRepository)

    private val deleteNoteUseCase: DeleteNoteUseCase
        get() = DomainModule.provideDeleteNoteUseCase(notesRepository)

    private val getNotesUseCase: GetNotesUseCase
        get() = DomainModule.provideGetNotesUseCase(notesRepository)

    private val getNoteUseCase: GetNoteUseCase
        get() = DomainModule.provideGetNoteUseCase(notesRepository)

    private fun initPresenterInjector() {
        PresentationInjector.init(
            getNotesUseCase, deleteNoteUseCase, insertNoteUseCase, getNoteUseCase
        )
    }

}