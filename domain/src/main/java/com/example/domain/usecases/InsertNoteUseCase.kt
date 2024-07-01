package com.example.domain.usecases

import com.example.domain.models.Note

interface InsertNoteUseCase {

    suspend operator fun invoke(note: Note)
}