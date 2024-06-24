package com.example.domain.usecases

import com.example.domain.models.Note

interface DeleteNoteUseCase {

    operator fun invoke(note: Note)

}