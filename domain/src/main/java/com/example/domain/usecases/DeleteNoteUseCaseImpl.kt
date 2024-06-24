package com.example.domain.usecases

import com.example.domain.models.Note
import com.example.domain.repositories.NotesRepository

class DeleteNoteUseCaseImpl(
    private val notesRepository: NotesRepository
) : DeleteNoteUseCase {

    override fun invoke(note: Note) {
        notesRepository.deleteNode(note)
    }

}