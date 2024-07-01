package com.example.domain.repositories

import com.example.domain.common.Resource
import com.example.domain.models.Note
import kotlinx.coroutines.flow.Flow

interface NotesRepository {

    suspend fun insertNote(note: Note)

    suspend fun deleteNode(note: Note)

    fun getNotes(): Flow<Resource<List<Note>>>

    suspend fun getNote(id: Int): Note

}