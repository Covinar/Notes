package com.example.domain.usecases

import com.example.domain.common.Resource
import com.example.domain.models.Note
import kotlinx.coroutines.flow.Flow

interface InsertNoteUseCase {

    fun insertNote(): Flow<Resource<Note>>

}