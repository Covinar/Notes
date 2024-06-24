package com.example.domain.repositories

import com.example.domain.common.Resource
import com.example.domain.models.Note
import kotlinx.coroutines.flow.Flow

interface NotesRepository {

    fun insertNote(): Flow<Resource<Note>>

    fun deleteNode(note: Note)

}