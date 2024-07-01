package com.example.domain.usecases

import com.example.domain.models.Note

interface DeleteNoteUseCase {

    suspend operator fun invoke(note: Note)

}