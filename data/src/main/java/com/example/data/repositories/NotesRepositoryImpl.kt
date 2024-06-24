package com.example.data.repositories

import com.example.data.datasources.local.NotesLocalDataSource
import com.example.data.toEntity
import com.example.data.toNote
import com.example.domain.models.Note
import com.example.domain.repositories.NotesRepository

class NotesRepositoryImpl(
    private val notesLocalDataSource: NotesLocalDataSource
) : NotesRepository {

    override fun insertNote(): Note {
        return notesLocalDataSource.insertNote().toNote()
    }

    override fun deleteNode(note: Note) {
        notesLocalDataSource.deleteNote(note.toEntity())
    }

}