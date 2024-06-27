package com.example.domain.usecases

import com.example.domain.common.Resource
import com.example.domain.models.Note
import com.example.domain.repositories.NotesRepository
import kotlinx.coroutines.flow.Flow

class GetNotesUseCaseImpl(
    private val notesRepository: NotesRepository
) : GetNotesUseCase {

    override fun invoke(): Flow<Resource<List<Note>>> {
        return notesRepository.getNotes()
    }

}