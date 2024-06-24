package com.example.data.repositories

import com.example.data.database.NotesDao
import com.example.data.mappers.toEntity
import com.example.domain.models.Note
import com.example.domain.repositories.NotesRepository

class NotesRepositoryImpl(
    private val notesDao: NotesDao
) : NotesRepository {

    override fun insertNote(note: Note) {
        notesDao.insertNote(note.toEntity())
    }

    override fun deleteNode(note: Note) {
        notesDao.deleteNote(note.toEntity())
    }

}