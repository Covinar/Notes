package com.example.domain.usecases

import com.example.domain.models.Note

interface InsertNoteUseCase {

    fun insertNote(): Note

}