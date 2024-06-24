package com.example.domain.usecases

import com.example.domain.models.Note

interface InsertNoteUseCase {

    operator fun invoke(note: Note)
}