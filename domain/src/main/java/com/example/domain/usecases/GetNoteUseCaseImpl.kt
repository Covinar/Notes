package com.example.domain.usecases

import com.example.domain.models.Note
import com.example.domain.repositories.NotesRepository

class GetNoteUseCaseImpl(
    private val notesRepository: NotesRepository
) : GetNoteUseCase{

    override suspend fun invoke(id: Int): Note {
        return notesRepository.getNote(id)
    }
}