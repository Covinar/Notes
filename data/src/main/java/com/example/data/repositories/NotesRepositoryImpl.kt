package com.example.data.repositories

import com.example.data.database.NotesDao
import com.example.data.mappers.toEntity
import com.example.data.mappers.toNote
import com.example.data.mappers.toNotes
import com.example.domain.common.Resource
import com.example.domain.models.Note
import com.example.domain.repositories.NotesRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.emitAll
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.map

class NotesRepositoryImpl(
    private val notesDao: NotesDao
) : NotesRepository {

    override suspend fun insertNote(note: Note) {
        notesDao.insertNote(note.toEntity())
    }

    override suspend fun deleteNode(note: Note) {
        notesDao.deleteNote(note.toEntity())
    }

    override fun getNotes(): Flow<Resource<List<Note>>> {
        return flow {
            emit(Resource.Loading)
            val notes = notesDao.getNotes().map {
                Resource.Success(it.toNotes())
            }
            emitAll(notes)
        }
    }

    override suspend fun getNote(id: Int): Note {
        return notesDao.getNote(id).toNote()
    }


}