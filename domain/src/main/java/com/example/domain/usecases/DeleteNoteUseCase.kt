package com.example.domain.usecases

import com.example.domain.models.Note

interface DeleteNoteUseCase {

    fun deleteNote(note: Note)

}