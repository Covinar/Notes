package com.example.data.common

import android.app.Application
import android.content.Context
import androidx.room.Room
import com.example.data.database.NotesDao
import com.example.data.database.NotesDataBase
import com.example.data.repositories.NotesRepositoryImpl
import com.example.domain.repositories.NotesRepository

object DataModule {

    fun provideNotesDataBase(applicationContext: Context) : NotesDataBase =
        Room.databaseBuilder(
        applicationContext,
        NotesDataBase::class.java,
        "notes.db"
    ).build()

    fun provideNotesDao(notesDataBase: NotesDataBase): NotesDao = notesDataBase.getDao()

    fun provideNotesRepository(notesDao: NotesDao): NotesRepository = NotesRepositoryImpl(notesDao)

}