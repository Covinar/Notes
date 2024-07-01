package com.example.domain.usecases

import com.example.domain.models.Note

interface GetNoteUseCase {

    suspend operator fun invoke(id: Int) : Note

}