package com.example.data.repositories

import com.example.data.datasources.local.NotesLocalDataSource
import com.example.data.mappers.toEntity
import com.example.domain.models.Note
import com.example.domain.repositories.NotesRepository

class NotesRepositoryImpl(
    private val notesLocalDataSource: NotesLocalDataSource
) : NotesRepository {

    override fun insertNote(note: Note) {
        return notesLocalDataSource.insertNote(note.toEntity())
    }

    override fun deleteNode(note: Note) {
        notesLocalDataSource.deleteNote(note.toEntity())
    }

}