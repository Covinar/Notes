package com.example.data.datasources.local

import com.example.data.database.NotesDao
import com.example.data.entities.NoteEntity

class NotesLocalDataSourceImpl(
    private val notesDao: NotesDao
) : NotesLocalDataSource {
    override fun insertNote(noteEntity: NoteEntity) {
        notesDao.insertNote()
    }

    override fun deleteNote(noteEntity: NoteEntity) {
        notesDao.deleteNote(noteEntity)
    }

}