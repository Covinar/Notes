package com.example.data.datasources.local

import com.example.data.entities.NoteEntity

interface NotesLocalDataSource {

    fun insertNote(noteEntity: NoteEntity)

    fun deleteNote(noteEntity: NoteEntity)

}