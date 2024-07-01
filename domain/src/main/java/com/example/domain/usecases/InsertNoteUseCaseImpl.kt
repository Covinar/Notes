package com.example.domain.usecases

import com.example.domain.models.Note
import com.example.domain.repositories.NotesRepository

class InsertNoteUseCaseImpl(
    private val notesRepository: NotesRepository
) : InsertNoteUseCase {

    override suspend fun invoke(note: Note) {
        return notesRepository.insertNote(note)
    }

}