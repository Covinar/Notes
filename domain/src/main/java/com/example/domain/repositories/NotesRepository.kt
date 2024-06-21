package com.example.domain.repositories

import com.example.domain.models.Note

interface NotesRepository {

    fun insertNote(): Note

    fun deleteNode(note: Note)

}