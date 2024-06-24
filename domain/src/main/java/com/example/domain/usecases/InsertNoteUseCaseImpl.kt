package com.example.domain.usecases

import com.example.domain.common.Resource
import com.example.domain.models.Note
import com.example.domain.repositories.NotesRepository
import kotlinx.coroutines.flow.Flow

class InsertNoteUseCaseImpl(
    private val notesRepository: NotesRepository
) : InsertNoteUseCase {

    override fun insertNote(): Flow<Resource<Note>> {
        return notesRepository.insertNote()
    }

}