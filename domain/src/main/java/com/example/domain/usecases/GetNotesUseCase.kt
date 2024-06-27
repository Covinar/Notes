package com.example.domain.usecases

import com.example.domain.common.Resource
import com.example.domain.models.Note
import kotlinx.coroutines.flow.Flow

interface GetNotesUseCase {

    operator fun invoke(): Flow<Resource<List<Note>>>

}